<template>
  <div>
    <!--按钮 Start-->
    <p>
      <button class="btn btn-white btn-default btn-round"
              v-on:click="add()">
        <i class="ace-icon fa fa-edit"></i>
        新增
      </button>
      &nbsp;
      <!--刷新按钮
      idea vue中的v-on爆红解决办法:
        seettings – 》 Editor --》 Inspections 进到具体页面
        XML -- Unbound XML namespace prefix
      -->
      <button class="btn btn-white btn-default btn-round"
              v-on:click="list(1)">
        <i class="ace-icon fa fa-refresh"></i>
        刷新
      </button>
    </p>
    <!--按钮 end -->

    <!--表单内容 start-->
    <table id="simple-table" class="table  table-bordered table-hover">
      <thead>
      <!--表头-->
      <tr>
        <th>id</th>
        <th>角色</th>
        <th>描述</th>
        <th>操作</th>
      </tr>
      </thead>

      <tbody>
      <tr v-for="role in roles">
          <td>{{role.id}}</td>
          <td>{{role.name}}</td>
          <td>{{role.desc}}</td>
        <td>
          <div class="hidden-sm hidden-xs btn-group">
            <!--资源按钮 start-->
            <button class="btn btn-xs btn-info" v-on:click="editResource(role)">
              <i class="ace-icon fa fa-list bigger-120"></i>
            </button>
            <!--资源按钮 end-->
            &nbsp;
            <!--修改按钮 start-->
            <button class="btn btn-xs btn-info" v-on:click="edit(role)">
              <i class="ace-icon fa fa-pencil bigger-120"></i>
            </button>
            <!--修改按钮 end-->
            &nbsp;
            <!--删除按钮 start-->
            <button class="btn btn-xs btn-danger" v-on:click="del(role.id)">
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

    <!--新增内容 start -->
    <div id="form-modal" class="modal fade" tabindex="-1" role="dialog">
      <div class="modal-dialog" role="document">
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
            <h4 class="modal-title">新增</h4>
          </div>
          <div class="modal-body">
            <!--模态框内容 Start -->
            <form class="form-horizontal">

              <!-- freemaker生成 start -->
                  <div class="form-group">
                    <label for="inputName" class="col-sm-2 control-label">角色</label>
                    <div class="col-sm-10">
                      <input type="text" class="form-control" id="inputName"
                             v-model="role.name" >
                    </div>
                  </div>
                  <div class="form-group">
                    <label for="inputDesc" class="col-sm-2 control-label">描述</label>
                    <div class="col-sm-10">
                      <input type="text" class="form-control" id="inputDesc"
                             v-model="role.desc" >
                    </div>
                  </div>
              <!-- freemaker生成 end -->

            </form>
            <!--模态框内容 End -->
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
            <button type="button" class="btn btn-primary"
                    v-on:click="save()">保存</button>
          </div>
        </div><!-- /.modal-content -->
      </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->
    <!--新增内容 end -->

    <!--角色关联资源 start -->
    <div id="resource-modal" class="modal fade" tabindex="-1" role="dialog">
      <div class="modal-dialog" role="document">
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
            <h4 class="modal-title">角色资源关联</h4>
          </div>
          <div class="modal-body">
            <!--模态框内容 Start -->
            <form class="form-horizontal">
              <!--zTree start -->
              <div class="form-group">
                <div class="col-md-10">
                  <ul id="resourceTree" class="ztree"></ul>
                </div>
              </div>
              <!--zTree end -->
            </form>
            <!--模态框内容 End -->
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
            <button type="button" class="btn btn-primary"
                    v-on:click="save()">保存</button>
          </div>
        </div><!-- /.modal-content -->
      </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->
    <!--角色关联资源 end -->

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
      name: 'system-role',
      mounted: function () {//页面加载初始化
          //激活样式方法一
          // this.$parent.activeSidebar("system-role-sidebar");//后面使用通用方法
          let _this = this;
          //_this.list();//没有使用分页组件
          //使用分页组件
          _this.$refs.pagination.size = 5;//默认显示的条数
          _this.list(1);//调用list的方法
          _this.allResource();//初始化加载资源树
      },
      data: function () {
        return {
            role: {}, //前台传入的数据
            roles: [],//初始化为空数组, 后台查询到的数据
            resources: [],
            tree: {},
        }
      },
      methods: {
        /**
         * 【编辑 角色资源】
         */
        editResource(role){
          let _this = this;
          _this.role = $.extend({},role);
          _this.allResource();
          $("#resource-modal").modal({backdrop: 'static'});//点击模态框以外的地方,模态框不关闭
          $("#resource-modal").modal('show');
        },
        /**
         * 【所有资源】
         * @param page
         */
        allResource(){
          let _this = this;
          Loading.show();
          _this.$ajax.post(process.env.VUE_APP_SERVER + '/system/admin/resource/all', {}
          ).then(function (response) {
            Loading.hide();
            let resp = response.data;
            //then异步执行,当.then()前的方法执行完后再执行then()内部的程序，这样就避免了，数据没获取到等的问题。
            console.log("查询资源结果:", resp.content);
            _this.resources = resp.content;//返回真实数据
            _this.initResourceTree();//初始化树形结构
          })
        },

        /**
         * 【资源树】
         */
        initResourceTree(){
          let _this = this;
          let setting = {
            check: {
              enable: true
            },
            data: {
              simpleData: {
                //三行配置数据, 左侧key是固定格式, 具体看zTree API文档
                idKey: "id",
                pIdKey: "parent",
                rootPId: "",
                enable: true
              }
            }
          };
          let zNodes = _this.resources;//树形结构的数据
          _this.tree = $.fn.zTree.init($("#resourceTree"), setting, zNodes);
          _this.tree.expandAll(true);//默认展开
        },

        list(page) {
            let _this = this;
            Loading.show();
            //传输数据,通过表单的形式和流的形式,vue是通过流的方式,需要以@requestBody来获取数据
            _this.$ajax.post(process.env.VUE_APP_SERVER + '/system/admin/role/list',{
                pageNum: page,
                pageSize: _this.$refs.pagination.size
            }).then(function (response) {
                Loading.hide();
                //返回的数据
                let resp = response.data;
                //then异步执行,当.then()前的方法执行完后再执行then()内部的程序，这样就避免了，数据没获取到等的问题。
                console.log("查询结果:",resp.content);
                // 出现跨域问题,因为我们是前后端分离的
                // Access to XMLHttpRequest at 'http://127.0.0.1:9002/system/admin/role/list' from origin 'http://localhost:8080'
                // has been blocked by CORS policy: No 'Access-Control-Allow-Origin' header is present on the requested resource.

                _this.roles = resp.content.list;//返回真实数据
                //渲染
                _this.$refs.pagination.render(page,resp.content.totalNum);
            })
        },

        //弹出新增的模态框
        add() {
            let _this = this;
            _this.role = {};//清除上次编辑的内容
            $("#form-modal").modal({backdrop:'static'});//点击模态框以外的地方,模态框不关闭
            $("#form-modal").modal("show");//显示模态框
        },

        //新增
        save() {
            let _this = this;
            /**
             * 前后都要做作校验,如果使用postman直接访问后台接口,就会出现问题
             */
            //校验字段   //?c表示freemaker对字段进行了格式处理,转换成字符串,不然就会出现问题 (2,000 --> 2000)
            if(1 != 1
                    || !Validator.require(_this.role.name, "角色")
                    || !Validator.length(_this.role.name, "角色", 1, 50)
                    || !Validator.require(_this.role.desc, "描述")
                    || !Validator.length(_this.role.desc, "描述", 1, 100)
            ){
              return;
            }

            Loading.show();
            _this.$ajax.post(process.env.VUE_APP_SERVER + '/system/admin/role/save',
              _this.role
            ).then(function (response) {
                Loading.hide();
                //返回的数据
                let resp = response.data;
                //打印日志
                console.log("保存的结果:{}",resp.content);
                if (resp.success){//true
                    //清楚模态框的内容-->更改到add()方法内部
                    // _this.role.courseId = "";
                    // _this.role.name = "";

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

        //修改
        edit(role) {
            let _this = this;
            //传参role复制一个到{}里面,不影响_this.role,即不会修改页面的role内容
            _this.role = $.extend({},role);
            $("#form-modal").modal("show");
        },

        //删除
        del(id) {
            let _this = this;
            //comfirm组件引入
            Confirm.show("删除后不可恢复,确认删除?",function () {
                Loading.show();
                _this.$ajax.delete(process.env.VUE_APP_SERVER + '/system/admin/role/delete/'+id
                ).then(function (response) {
                    Loading.hide();
                    //返回的数据
                    let resp = response.data;
                    //删除功能的日志
                    console.log("删除的结果:{}",resp.content);
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