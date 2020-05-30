<template>
  <div>
    <div class="row">
      <!--分类一级列表 start-->
      <div class="col-md-6">
        <!--按钮 Start-->
        <p>
          <button class="btn btn-white btn-default btn-round"
                  v-on:click="addLevel_1()">
            <i class="ace-icon fa fa-edit"></i>
            新增一级
          </button>
          &nbsp;
          <!--刷新按钮
          idea vue中的v-on爆红解决办法:
            seettings – 》 Editor --》 Inspections 进到具体页面
            XML -- Unbound XML namespace prefix
          -->
          <button class="btn btn-white btn-default btn-round"
                  v-on:click="all()">
            <i class="ace-icon fa fa-refresh"></i>
            刷新
          </button>
        </p>
        <!--按钮 end -->

        <!--表单内容 start-->
        <table id="level1-table" class="table  table-bordered table-hover">
          <thead>
          <!--表头-->
          <tr>
            <th>id</th>
            <th>名称</th>
            <th>顺序</th>
            <th>操作</th>
          </tr>
          </thead>

          <tbody>
          <tr v-for="category in level_1"
              v-on:click="queryLevel_1(category)"
              v-bind:class="{'active' : category.id === active.id}">
            <td>{{category.id}}</td>
            <td>{{category.name}}</td>
            <td>{{category.sort}}</td>
            <td>
              <div class="hidden-sm hidden-xs btn-group">
                <!--修改按钮 start-->
                <button class="btn btn-xs btn-info" v-on:click="edit(category)">
                  <i class="ace-icon fa fa-pencil bigger-120"></i>
                </button>
                <!--修改按钮 end-->

                <!--删除按钮 start-->
                <button class="btn btn-xs btn-danger" v-on:click="del(category.id)">
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
      </div>
      <!--分类一级列表 end-->

      <!--分类二级列表 start-->
      <div class="col-md-6">
        <!--按钮 Start-->
        <p>
          <button class="btn btn-white btn-default btn-round"
                  v-on:click="addLevel_2()">
            <i class="ace-icon fa fa-edit"></i>
            新增二级
          </button>
        </p>
        <!--按钮 end -->

        <!--表单内容 start-->
        <table id="level2-table" class="table  table-bordered table-hover">
          <thead>
          <!--表头-->
          <tr>
            <th>id</th>
            <th>名称</th>
            <th>顺序</th>
            <th>操作</th>
          </tr>
          </thead>

          <tbody>
          <tr v-for="category in level_2">
            <td>{{category.id}}</td>
            <td>{{category.name}}</td>
            <td>{{category.sort}}</td>
            <td>
              <div class="hidden-sm hidden-xs btn-group">
                <!--修改按钮 start-->
                <button class="btn btn-xs btn-info" v-on:click="edit(category)">
                  <i class="ace-icon fa fa-pencil bigger-120"></i>
                </button>
                <!--修改按钮 end-->

                <!--删除按钮 start-->
                <button class="btn btn-xs btn-danger" v-on:click="del(category.id)">
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
      </div>
      <!--分类二级列表 end-->
    </div>
    <!--新增内容 start -->
    <div id="form-modal" class="modal fade" tabindex="-1" role="dialog">
      <div class="modal-dialog" role="document">
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
            <h4 class="modal-title">新增分类</h4>
          </div>
          <div class="modal-body">
            <!--模态框内容 Start -->
            <form class="form-horizontal">

              <!-- freemaker生成 start -->
              <div class="form-group">
                <label class="col-sm-2 control-label">父级</label>
                <div class="col-sm-10">
                  <p class="form-control-static">{{ active.name || "无" }}</p>
                </div>
              </div>
              <div class="form-group">
                <label for="inputName" class="col-sm-2 control-label">名称</label>
                <div class="col-sm-10">
                  <input type="text" class="form-control" id="inputName"
                         v-model="category.name" >
                </div>
              </div>
              <div class="form-group">
                <label for="inputSort" class="col-sm-2 control-label">顺序</label>
                <div class="col-sm-10">
                  <input type="text" class="form-control" id="inputSort"
                         v-model="category.sort" >
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
  export default {
      name: 'business-category',
      /**
       * 【页面加载初始化】
       */
      mounted: function () {//页面加载初始化
          //激活样式方法一
          // this.$parent.activeSidebar("business-category-sidebar");//后面使用通用方法
          let _this = this;
          _this.all();//调用list的方法
      },
      /**
       * 【默认数据】
       */
      data: function () {
        return {
          category: {}, //前台传入的数据
          categorys: [], //初始化为空数组, 后台查询到的数据
          level_1: [],
          level_2: [],
          active: {}
        }
      },

      /**
       * 【方法】
       */
      methods: {
        /**
         * 【获取数据 树形结构】
         */
        all() {
            let _this = this;
            Loading.show();
            _this.$ajax.post(process.env.VUE_APP_SERVER + '/business/admin/category/all',{}
            ).then(function (response) {
                Loading.hide();
                //返回的数据
                let resp = response.data;
                //then异步执行,当.then()前的方法执行完后再执行then()内部的程序，这样就避免了，数据没获取到等的问题。
                console.log("查询分类结果:",resp.content);
                _this.categorys = resp.content;//返回真实数据
              
              //将所有的记录格式化成树形结构
              _this.level_1 = [];
              for (let i = 0; i < _this.categorys.length; i++) {
                // debugger
                let categorySingle = _this.categorys[i];//i
                if (categorySingle.parent === '00000000'){
                  _this.level_1.push(categorySingle);
                  for (let j = 0; j <_this.categorys.length; j++) {
                    let child = _this.categorys[j];//j
                    if (child.parent === categorySingle.id){
                      if (Tool.isEmpty(categorySingle.children)){
                        categorySingle.children = [];
                      }
                      categorySingle.children.push(child);
                      console.log(categorySingle.children);
                    }
                  }
                }
              }
            })
        },

        /**
         * 【弹出新增的模态框】
         */
        addLevel_1() {
          let _this = this;
          _this.active = {};
          _this.category = {
            parent: "00000000"
          }
          $("#form-modal").modal({backdrop:'static'});//点击模态框以外的地方,模态框不关闭
          $("#form-modal").modal("show");//显示模态框
        },
        addLevel_2() {
          let _this = this;
          //新增二类保证一类已经点击了
          if (Tool.isEmpty(_this.active)){
            Toast.warning("请先选中一级分类");
            return;
          }
          _this.category = {
            parent : _this.active.id
          }
          $("#form-modal").modal({backdrop:'static'});//点击模态框以外的地方,模态框不关闭
          $("#form-modal").modal("show");//显示模态框
        },

        /**
         * 【保存】
         */
        save() {
            let _this = this;
            /**
             * 前后都要做作校验,如果使用postman直接访问后台接口,就会出现问题
             */
            //校验字段   //?c表示freemaker对字段进行了格式处理,转换成字符串,不然就会出现问题 (2,000 --> 2000)
            if(1 != 1
                    || !Validator.require(_this.category.parent, "父id")
                    || !Validator.require(_this.category.name, "名称")
                    || !Validator.length(_this.category.name, "名称", 1, 50)
            ){
              return;
            }

            Loading.show();
            _this.$ajax.post(process.env.VUE_APP_SERVER + '/business/admin/category/save',
              _this.category
            ).then(function (response) {
                Loading.hide();
                //返回的数据
                let resp = response.data;
                //打印日志
                console.log("保存分类的结果:{}",resp.content);
                if (resp.success){//true
                    //清楚模态框的内容-->更改到add()方法内部
                    // _this.category.courseId = "";
                    // _this.category.name = "";

                    //关闭模态框
                    $("#form-modal").modal("hide");
                    //调用list方法
                    _this.all();
                    //保存成功的提示框
                    Toast.success("保存成功!");
                }else{//校验字段, 字段有问题
                    Toast.warning(resp.message);
                }
            })
        },

        /**
         * 【修改】
         */
        edit(category) {
            let _this = this;
            //传参category复制一个到{}里面,不影响_this.category,即不会修改页面的category内容
            _this.category = $.extend({},category);
            $("#form-modal").modal("show");
        },

        /**
         * 【删除】
         */
        del(id) {
          let _this = this;
          //comfirm组件引入
          Confirm.show("删除分类后不可恢复,确认删除?",function () {
              Loading.show();
              _this.$ajax.delete(process.env.VUE_APP_SERVER + '/business/admin/category/delete/'+id
              ).then(function (response) {
                  Loading.hide();
                  //返回的数据
                  let resp = response.data;
                  //删除功能的日志
                  console.log("删除分类的结果:{}",resp.content);
                  if (resp.success){
                      //重新查询list
                      _this.all();
                      //删除成功的提示框
                      Toast.success("删除成功!");
                  }
              })
          })
        },
        /**
         * 【查询一级分类】
         * @param category
         */
        queryLevel_1(category) {
          let _this = this;
          console.log("点击触发了...");
          _this.active = category;
          _this.level_2 = category.children;
          console.log("children:{}", category.children);

        }
      }
  }
</script>

<style scoped>
  .active td{
    background-color: #d6e9c6 !important;
  }
</style>