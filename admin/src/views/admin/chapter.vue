<template>
  <div>
    <!--刷新按钮
      idea vue中的v-on爆红解决办法:
        seettings – 》 Editor --》 Inspections 进到具体页面
        XML -- Unbound XML namespace prefix
    -->
    <p>
      <button class="btn btn-white btn-default btn-round"
        v-on:click="list(1)">
        <i class="ace-icon fa fa-refresh"></i>
        刷新
      </button>
    </p>

    <table id="simple-table" class="table  table-bordered table-hover">
      <thead>
        <tr>
          <th>ID</th>
          <th>名称</th>
          <th>课程ID</th>
          <th>操作</th>
        </tr>
      </thead>

      <tbody>
        <tr v-for="chapter in chapters">
          <td>{{chapter.id}}</td>
          <td>{{chapter.name}}</td>
          <td>{{chapter.courseId}}</td>
          <td>
            <div class="hidden-sm hidden-xs btn-group">
              <button class="btn btn-xs btn-success">
                <i class="ace-icon fa fa-check bigger-120"></i>
              </button>

              <button class="btn btn-xs btn-info">
                <i class="ace-icon fa fa-pencil bigger-120"></i>
              </button>

              <button class="btn btn-xs btn-danger">
                <i class="ace-icon fa fa-trash-o bigger-120"></i>
              </button>

              <button class="btn btn-xs btn-warning">
                <i class="ace-icon fa fa-flag bigger-120"></i>
              </button>
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

    <!--v-bind:xxx是组件中props设置的属性 list表示函数 itemCount表示显示最多显示几个按钮-->
    <pagination ref="pagination" v-bind:list="list" v-bind:itemCount="8"></pagination>
  </div>
</template>
<!--
  /admin用于控台类的接口
  /web用于网站类的接口
  接口设计中,用不同的请求前缀代表不同的入口
  做借口的隔离,方便做鉴定,统计,监控等
-->
<script>
  //引入pagination组件
  import Pagination from '../../components/pagination.vue'
  export default {
      components: {Pagination},//引入pagination组件
      name: 'chapter',
      mounted: function () {
          //激活样式方法一
          // this.$parent.activeSidebar("business-chapter-sidebar");//后面使用通用方法
          let _this = this;
          //_this.list();//没有使用分页组件
          //使用分页组件
          _this.$refs.pagination.size = 5;//默认显示的条数
          _this.list(1);//调用list的方法
  },
      data: function () {
        return {
            chapters: []//初始化为空数组
        }
      },
      methods: {
        list(page) {
            let _this = this;
            //传输数据,通过表单的形式和流的形式,vue是通过流的方式,需要以@requestBody来获取数据
            _this.$ajax.post('http://127.0.0.1:9000/business/admin/chapter/list',{
                pageNum: page,
                pageSize: _this.$refs.pagination.size
            }).then(function (response) {
                //then异步执行,当.then()前的方法执行完后再执行then()内部的程序，这样就避免了，数据没获取到等的问题。
                console.log("查询大章结果:",response);
                // 出现跨域问题,因为我们是前后端分离的
                // Access to XMLHttpRequest at 'http://127.0.0.1:9002/business/admin/chapter/list' from origin 'http://localhost:8080'
                // has been blocked by CORS policy: No 'Access-Control-Allow-Origin' header is present on the requested resource.

                _this.chapters = response.data.list;//返回真实数据
                //渲染
                _this.$refs.pagination.render(page,response.data.totalNum);
            })
        }
      }
  }
</script>