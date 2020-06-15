<template>
  <div>
    <!--按钮 Start-->
    <p>
      <button class="btn btn-white btn-default btn-round"
              v-on:click="add()" v-show="hasResource('010101')">
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
          <th>登录名</th>
          <th>昵称</th>
          <th>密码</th>
        <th>操作</th>
      </tr>
      </thead>

      <tbody>
      <tr v-for="user in users">
          <td>{{user.id}}</td>
          <td>{{user.loginName}}</td>
          <td>{{user.name}}</td>
          <td>{{user.password}}</td>
        <td>
            <!--编辑角色 start-->
            <button type="button" v-on:click="editRole(user)"
                    class="btn btn-default btn-primary btn-round">
              编辑角色
            </button>&nbsp;
            <!--编辑角色 end-->
            <!--重置密码 start-->
            <button type="button" v-on:click="editPwd(user)"
                  class="btn btn-default btn-primary btn-round"
                    v-show="hasResource('010103')">
              重置密码
            </button>&nbsp;
            <!--重置密码 end-->
            &nbsp;
            <!--修改按钮 start-->
            <button type="button" v-on:click="edit(user)"
                    class="btn btn-default btn-primary btn-round"
                    v-show="hasResource('010101')">
              编辑用户
            </button>&nbsp;
            <!--修改按钮 end-->
            &nbsp;
            <!--删除按钮 start-->
            <button type="button" v-on:click="del(user.id)"
                    class="btn btn-default btn-danger btn-round"
                    v-show="hasResource('010102')">
              删除
            </button>&nbsp;
            <!--删除按钮 end-->
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
                    <label for="inputLoginName" class="col-sm-2 control-label">登录名</label>
                    <div class="col-sm-10">
                      <input type="text" class="form-control" id="inputLoginName"
                             v-model="user.loginName"
                               v-bind:disabled="user.id">
                    </div>
                  </div>
                  <div class="form-group">
                    <label for="inputName" class="col-sm-2 control-label">昵称</label>
                    <div class="col-sm-10">
                      <input type="text" class="form-control" id="inputName"
                             v-model="user.name" >
                    </div>
                  </div>
                  <div class="form-group" v-show="!user.id"><!--新增,id没有值,显示出来-->
                    <label for="inputPassword" class="col-sm-2 control-label">密码</label>
                    <div class="col-sm-10">
                      <input type="password" class="form-control" id="inputPassword"
                             v-model="user.password" >
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

    <!--编辑角色 start -->
    <div id="role-form-modal" class="modal fade" tabindex="-1" role="dialog">
      <div class="modal-dialog" role="document">
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
            <h4 class="modal-title">编辑角色</h4>
          </div>
          <div class="modal-body">
            <!--模态框内容 Start -->
            <form class="form-horizontal">
              <div class="form-group">
                <div class="row select">
                  <!--未分配的角色 start-->
                  <div class="col-md-5 select-item" style="height: 266px;" >
                    <div>
                      <h4>未分配角色</h4>
                    </div>
                    <select multiple="multiple" v-model="ungrantedSelected">
                      <option v-for="role in ungrantedRoles"
                              :key="role.id"
                              :value="role">
                        {{role.name}}
                      </option>
                    </select>
                  </div>
                  <!--未分配的角色 end-->

                  <div class="col-md-2 btn-item">
                    <p><span v-on:click="moveRight()">添加</span></p>
                    <p><span v-on:click="moveLeft()">取消</span></p>
                  </div>

                  <!--已分配的角色 start-->
                  <div class="col-md-5 select-item">
                    <div>
                      <h4>已分配角色</h4>
                    </div>
                    <select multiple="multiple" v-model="grantedSelected">
                      <option v-for="role in grantedRoles" :key="role.id"
                              :value="role">
                        {{role.name}}
                      </option>
                    </select>
                  </div>
                  <!--已分配的角色 end-->
                </div>
              </div>
            </form>
            <!--模态框内容 End -->
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
            <button type="button" class="btn btn-primary"
                    v-on:click="saveGrantedRole()">保存</button>
          </div>
        </div><!-- /.modal-content -->
      </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->
    <!--编辑角色 end -->

    <!--重置密码 start -->
    <div id="password-form-modal" class="modal fade" tabindex="-1" role="dialog">
      <div class="modal-dialog" role="document">
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
            <h4 class="modal-title">密码重置</h4>
          </div>
          <div class="modal-body">
            <!--模态框内容 Start -->
            <form class="form-horizontal">
                  <div class="form-group">
                    <label class="col-sm-2 control-label">新密码</label>
                    <div class="col-sm-10">
                      <input type="password" class="form-control" v-model="user.password">
                    </div>
                  </div>
            </form>
            <!--模态框内容 End -->
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
            <button type="button" class="btn btn-primary"
                    v-on:click="savePwd()">保存</button>
          </div>
        </div><!-- /.modal-content -->
      </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->
    <!--重置密码 end -->

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
      name: 'system-user',
      mounted: function () {//页面加载初始化
          //激活样式方法一
          // this.$parent.activeSidebar("system-user-sidebar");//后面使用通用方法
          let _this = this;
          //_this.list();//没有使用分页组件
          //使用分页组件
          _this.$refs.pagination.size = 5;//默认显示的条数
          _this.list(1);//调用list的方法

          //移除数组中的一个元素
          Array.prototype.remove = function(val) {
            var index = this.indexOf(val);
            if (index > -1) {
              this.splice(index, 1);
            }
          };
      },
      data: function () {
        return {
            user: {}, //前台传入的数据
            users: [],//初始化为空数组, 后台查询到的数据
            grantedRoles: [],//已分配
            ungrantedRoles: [],//未分配
            grantedSelected: [],//选中
            ungrantedSelected: [],//未选中
        }
      },
      methods: {

        /**
         * 权限判断
         */
        hasResource(id){
          return Tool.hasResource(id);
        },


        list(page) {
            let _this = this;
            Loading.show();
            //传输数据,通过表单的形式和流的形式,vue是通过流的方式,需要以@requestBody来获取数据
            _this.$ajax.post(process.env.VUE_APP_SERVER + '/system/admin/user/list',{
                pageNum: page,
                pageSize: _this.$refs.pagination.size
            }).then(function (response) {
                Loading.hide();
                //返回的数据
                let resp = response.data;
                //then异步执行,当.then()前的方法执行完后再执行then()内部的程序，这样就避免了，数据没获取到等的问题。
                console.log("查询结果:",resp.content);
                // 出现跨域问题,因为我们是前后端分离的
                // Access to XMLHttpRequest at 'http://127.0.0.1:9002/system/admin/user/list' from origin 'http://localhost:8080'
                // has been blocked by CORS policy: No 'Access-Control-Allow-Origin' header is present on the requested resource.

                _this.users = resp.content.list;//返回真实数据
                //渲染
                _this.$refs.pagination.render(page,resp.content.totalNum);
            })
        },

        //弹出新增的模态框
        add() {
            let _this = this;
            _this.user = {};//清除上次编辑的内容
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
                    || !Validator.require(_this.user.loginName, "登录名")
                    || !Validator.length(_this.user.loginName, "登录名", 1, 50)
                    || !Validator.length(_this.user.name, "昵称", 1, 50)
                    || !Validator.length(_this.user.password, "密码", 1, 50)
            ){
              return;
            }
            _this.user.password = hex_md5(_this.user.password + KEY);
            Loading.show();
            _this.$ajax.post(process.env.VUE_APP_SERVER + '/system/admin/user/save',
              _this.user
            ).then(function (response) {
                Loading.hide();
                //返回的数据
                let resp = response.data;
                //打印日志
                console.log("保存的结果:{}",resp.content);
                if (resp.success){//true
                    //清楚模态框的内容-->更改到add()方法内部
                    // _this.user.courseId = "";
                    // _this.user.name = "";

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
        edit(user) {
            let _this = this;
            //传参user复制一个到{}里面,不影响_this.user,即不会修改页面的user内容
            _this.user = $.extend({},user);
            $("#form-modal").modal("show");
        },

        //删除
        del(id) {
            let _this = this;
            //comfirm组件引入
            Confirm.show("删除后不可恢复,确认删除?",function () {
                Loading.show();
                _this.$ajax.delete(process.env.VUE_APP_SERVER + '/system/admin/user/delete/'+id
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
        },

        /**
         * 【重置密码与保存】
         */
        editPwd(user){
          let _this = this;
          //传参user复制一个到{}里面,不影响_this.user,即不会修改页面的user内容
          _this.user = $.extend({},user);
          _this.user.password = null;
          $("#password-form-modal").modal("show");
        },

        savePwd(){
          let _this = this;
          if(1 != 1
            || !Validator.require(_this.user.password, "新密码")
            || !Validator.length(_this.user.password, "新密码", 1, 50)
          ){
            return;
          }
          _this.user.password = hex_md5(_this.user.password + KEY);
          _this.$ajax.post(process.env.VUE_APP_SERVER + "/system/admin/user/savePwd",
            _this.user
          ).then((response)=>{
            let resp = response.data;
            if (resp.success){
              $("#password-form-modal").modal("hide");
              Toast.success("密码重置成功!");
              _this.list(1);
            }else{
              Toast.warning(resp.message);
            }
          })
        },

        /**
         * 编辑角色
         */
        editRole(user){
          let _this = this;
          _this.user = $.extend({},user);
          _this.allRoles(user.id);//加载所有角色
          $("#role-form-modal").modal({backdrop: 'static'});
          $("#role-form-modal").modal("show");
        },

        /**
         * 加载分配角色和未分配角色
         */
        allRoles(id){
          let _this = this;
          _this.$ajax.get(process.env.VUE_APP_SERVER + '/system/admin/user/selectRole/'+id
          ).then((response)=>{
            let resp = response.data;
            if (resp.success){
              _this.grantedRoles = resp.content.grantedRoles;//已分配的角色
              _this.ungrantedRoles = resp.content.ungrantedRoles;//未分配的角色
            }else{
              Toast.error(resp.message);
            }
          })
        },

        /**
         * 分配角色
         */
        moveRight(){
          let _this = this;
          _this.ungrantedSelected.forEach(item => {
            _this.grantedRoles.push(item);
            _this.ungrantedRoles.remove(item)
          });

          _this.ungrantedSelected = [];
        },
        /**
         * 取消角色
         */
        moveLeft(){
          let _this = this;
          _this.grantedSelected.forEach(item => {
            _this.ungrantedRoles.push(item);
            _this.grantedRoles.remove(item);
          });

          _this.grantedSelected = [];
        },

        /**
         * 保存分配的角色
         */
        saveGrantedRole(){
          let _this = this;
          _this.user.grantedRoles = _this.grantedRoles;
          _this.$ajax.post(process.env.VUE_APP_SERVER + '/system/admin/user/saveGrantedRole',
             _this.user
          ).then((response)=>{
            let resp = response.data;
            if (resp.success){
              $("#role-form-modal").modal("hide");
              Toast.success("用户角色保存成功");
            }else{
              Toast.error(resp.message);
            }
          })
        },

      }
  }
</script>

<style>
  .select {
    width: 600px;
    height: 340px;
  }

  .select div {
    float: left;
  }

  .select .select-item {
    padding: 5px 20px;
  }

  .select .select-item select {
    width: 220px;
    height: 300px;
    border: 1px #bbb solid;
    padding: 4px;
    font-size: 14px;
  }

  .btn-item {
    margin-top: 75px;
  }

  .btn-item p {
    margin-top: 16px;
  }

  .btn-item p span {
    display: block;
    text-align: center;
    width: 50px;
    height: 30px;
    cursor: pointer;
    border: 1px solid #bbb;
    line-height: 30px;
    font-size: 20px;
  }
</style>