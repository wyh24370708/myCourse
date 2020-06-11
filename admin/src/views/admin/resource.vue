<template>
  <div>
    <!--按钮 Start-->
    <p>
      <button class="btn btn-white btn-default btn-round"
              v-on:click="list(1)">
        <i class="ace-icon fa fa-refresh"></i>
        刷新
      </button>
      &nbsp;
      <button v-on:click="save()"  class="btn btn-default btn-success btn-round" >
        <i class="ace-icon fa fa-floppy-o bigger-120"></i>
        保存
      </button>
    </p>
    <!--按钮 end -->
    <div class="row">
      <div class="col-md-6">
        <textarea id="resource-textarea" class="form-control" v-model="resourceStr" name="resource" rows="15"></textarea>
        <br>
      </div>
    </div>
    <!--表单内容 start-->
    <table id="simple-table" class="table  table-bordered table-hover">
      <thead>
      <!--表头-->
      <tr>
          <th>id</th>
          <th>名称</th>
          <th>页面</th>
          <th>请求</th>
          <th>父id</th>
          <th>操作</th>
      </tr>
      </thead>

      <tbody>
      <tr v-for="resource in resources">
          <td>{{resource.id}}</td>
          <td>{{resource.name}}</td>
          <td>{{resource.page}}</td>
          <td>{{resource.request}}</td>
          <td>{{resource.parent}}</td>
        <td>
          <div class="hidden-sm hidden-xs btn-group">
            <!--删除按钮 start-->
            <button class="btn btn-xs btn-danger" v-on:click="del(resource.id)">
              <i class="ace-icon fa fa-trash-o bigger-120"></i>
            </button>
            <!--删除按钮 end-->
          </div>

          <div class="hidden-md hidden-lg">
            <div class="inline pos-rel">
              <button class="btn btn-minier btn-primary dropdown-toggle" data-toggle="dropdown" data-position="auto">
                <i class="ace-icon fa fa-cog icon-only bigger-110"></i>
              </button>

              <ul
                      class="dropdown-menu dropdown-only-icon dropdown-yellow dropdown-menu-right dropdown-caret dropdown-close">
                <li>
                  <a href="#" class="tooltip-info" data-rel="tooltip" title="View">
                                        <span class="blue">
                                          <i class="ace-icon fa fa-search-plus bigger-120"></i>
                                        </span>
                  </a>
                </li>

                <li>
                  <a href="#" class="tooltip-success" data-rel="tooltip" title="Edit">
                                        <span class="green">
                                          <i class="ace-icon fa fa-pencil-square-o bigger-120"></i>
                                        </span>
                  </a>
                </li>

                <li>
                  <a href="#" class="tooltip-error" data-rel="tooltip" title="Delete">
                                        <span class="red">
                                          <i class="ace-icon fa fa-trash-o bigger-120"></i>
                                        </span>
                  </a>
                </li>
              </ul>
            </div>
          </div>
        </td>
      </tr>
      </tbody>
    </table><!-- /.row -->
    <!--表单内容 end -->

    <!--分页内容 start-->
    <!--v-bind:xxx是组件中props设置的属性 list表示函数 itemCount表示显示最多显示几个按钮-->
    <pagination ref="pagination" v-bind:list="list" v-bind:itemCount="8"></pagination>
    <!--分页内容 end-->
  </div>
</template>
<!--
  /admin用于控台类的接口
  /web用于网站类的接口
  接口设计中,用不同的请求前缀代表不同的入口
  做借口的隔离,方便做鉴定,统计,监控等

  $refs引用子组件
  vue中不能使用js的关键字, delete是js的关键字
-->
<script>
  //引入pagination组件
  import Pagination from '../../components/pagination.vue'
  export default {
      components: {Pagination},//引入pagination组件
      name: 'system-resource',
      mounted: function () {//页面加载初始化
          //激活样式方法一
          // this.$parent.activeSidebar("system-resource-sidebar");//后面使用通用方法
          let _this = this;
          //_this.list();//没有使用分页组件
          //使用分页组件
          _this.$refs.pagination.size = 10;//默认显示的条数
          _this.list(1);//调用list的方法
      },
      data: function () {
        return {
            resource: {}, //前台传入的数据
            resources: [],//初始化为空数组, 后台查询到的数据
            resourceStr: "",
        }
      },
      methods: {
        list(page) {
            let _this = this;
            Loading.show();
            //传输数据,通过表单的形式和流的形式,vue是通过流的方式,需要以@requestBody来获取数据
            _this.$ajax.post(process.env.VUE_APP_SERVER + '/system/admin/resource/list',{
                pageNum: page,
                pageSize: _this.$refs.pagination.size
            }).then(function (response) {
                Loading.hide();
                //返回的数据
                let resp = response.data;
                //then异步执行,当.then()前的方法执行完后再执行then()内部的程序，这样就避免了，数据没获取到等的问题。
                console.log("查询资源结果:",resp.content);
                // 出现跨域问题,因为我们是前后端分离的
                // Access to XMLHttpRequest at 'http://127.0.0.1:9002/system/admin/resource/list' from origin 'http://localhost:8080'
                // has been blocked by CORS policy: No 'Access-Control-Allow-Origin' header is present on the requested resource.

                _this.resources = resp.content.list;//返回真实数据
                //渲染
                _this.$refs.pagination.render(page,resp.content.totalNum);
            })
        },

        //新增资源
        save() {
            let _this = this;
            if(Tool.isEmpty(_this.resourceStr)){
              Toast.warning("资源不能为空！");
              return;
            }
            let resource_json = JSON.parse(_this.resourceStr);

            _this.$ajax.post(process.env.VUE_APP_SERVER + '/system/admin/resource/save',
              resource_json
            ).then(function (response) {
                //返回的数据
                let resp = response.data;
                //打印日志
                console.log("保存资源的结果:{}",resp.content);
                if (resp.success){//true
                    //清楚模态框的内容-->更改到add()方法内部
                    // _this.resource.courseId = "";
                    // _this.resource.name = "";

                    //关闭模态框
                    $("#form-modal").modal("hide");
                    //调用list方法
                    _this.list(1);
                    //保存成功的提示框
                    Toast.success("保存成功!");
                }else{//校验字段, 字段有问题
                    Toast.warning(resp.message);
                }
            })
        },

        //删除
        del(id) {
            let _this = this;
            //comfirm组件引入
            Confirm.show("删除资源后不可恢复,确认删除?",function () {
                Loading.show();
                _this.$ajax.delete(process.env.VUE_APP_SERVER + '/system/admin/resource/delete/'+id
                ).then(function (response) {
                    Loading.hide();
                    //返回的数据
                    let resp = response.data;
                    //删除功能的日志
                    console.log("删除资源的结果:{}",resp.content);
                    if (resp.success){
                        //重新查询list
                        _this.list(1);
                        //删除成功的提示框
                        Toast.success("删除成功!");
                    }
                })
            })
        }
      }
  }
</script>