

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
    <div class="row">
      <div class="col-md-3 center"
            v-for="teacher in teachers">
        <div>
          <span class="profile-picture">
            <!--课程默认图片-->
            <img v-show="!teacher.image"
                 class="editable img-responsive editable-click editable-empty"
                 src="/static/image/profile-pic.jpg"
                 v-bind::title="teacher.intro"/>
            <!--数据库存的课程图片-->
            <img v-show="teacher.image"
                 class="media-object"
                 v-bind:src="teacher.image"
                 v-bind::title="teacher.intro"/>
          </span>

          <div class="space-4"></div>

          <div class="width-80 label label-info label-xlg arrowed-in arrowed-in-right">
            <div class="inline position-relative">
              <a href="#" class="user-title-label dropdown-toggle" data-toggle="dropdown">
                <i class="ace-icon fa fa-circle light-green"></i>
                &nbsp;
                <span class="white">{{teacher.position}}</span>
              </a>
            </div>
          </div>
        </div>

        <div class="space-6"></div>

        <a href="#" v-bind:title="teacher.motto">
          <i class="ace-icon glyphicon glyphicon-user"></i>
          &nbsp;
          <span class="blue">
            {{teacher.name}} 【{{teacher.nickname}}】
          </span>
        </a>

        <div class="space-6"></div>

          <div class="profile-social-links align-center">
            <!--按钮 start-->
            <p>
              <button class="btn btn-white btn-xs btn-info btn-round"
                      v-on:click="edit(teacher)">
                编辑
              </button>&nbsp;
              <button class="btn btn-white btn-xs btn-warning btn-round"
                      v-on:click="del(teacher.id)">
                删除
              </button>&nbsp;
            </p>
            <!-- 按钮 end -->
          </div>
        </div>
      <div class="hr hr12 dotted"></div>
     </div>
    <!--表单内容 end-->

    <!--新增内容 start -->
    <div id="form-modal" class="modal fade" tabindex="-1" role="dialog">
      <div class="modal-dialog" role="document">
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
            <h4 class="modal-title">新增讲师</h4>
          </div>
          <div class="modal-body">
            <!--模态框内容 Start -->
            <form class="form-horizontal">

              <!-- freemaker生成 start -->
                  <div class="form-group">
                    <label for="inputName" class="col-sm-2 control-label">姓名</label>
                    <div class="col-sm-10">
                      <input type="text" class="form-control" id="inputName"
                             v-model="teacher.name" >
                    </div>
                  </div>
                  <div class="form-group">
                    <label for="inputNickname" class="col-sm-2 control-label">昵称</label>
                    <div class="col-sm-10">
                      <input type="text" class="form-control" id="inputNickname"
                             v-model="teacher.nickname" >
                    </div>
                  </div>
                  <div class="form-group">
                    <label for="inputImage" class="col-sm-2 control-label">头像</label>
                    <div class="col-sm-10">
                      <input type="file"
                             id="inputImage"
                             v-on:change="upLoadImage()" >
                    </div>
                  </div>
                  <div class="form-group">
                    <label for="inputPosition" class="col-sm-2 control-label">职位</label>
                    <div class="col-sm-10">
                      <input type="text" class="form-control" id="inputPosition"
                             v-model="teacher.position" >
                    </div>
                  </div>
                  <div class="form-group">
                    <label for="inputMotto" class="col-sm-2 control-label">座右铭</label>
                    <div class="col-sm-10">
                      <input type="text" class="form-control" id="inputMotto"
                             v-model="teacher.motto" >
                    </div>
                  </div>
                  <div class="form-group">
                    <label for="inputIntro" class="col-sm-2 control-label">简介</label>
                    <div class="col-sm-10">
                      <textarea v-model="teacher.intro"
                                class="form-control"
                                rows="5" id="inputIntro">
                      </textarea>
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
      name: 'business-teacher',
      mounted: function () {//页面加载初始化
          //激活样式方法一
          // this.$parent.activeSidebar("business-teacher-sidebar");//后面使用通用方法
          let _this = this;
          //_this.list();//没有使用分页组件
          //使用分页组件
          _this.$refs.pagination.size = 5;//默认显示的条数
          _this.list(1);//调用list的方法
      },
      data: function () {
        return {
            teacher: {}, //前台传入的数据
            teachers: []//初始化为空数组, 后台查询到的数据
        }
      },
      methods: {
        list(page) {
            let _this = this;
            Loading.show();
            //传输数据,通过表单的形式和流的形式,vue是通过流的方式,需要以@requestBody来获取数据
            _this.$ajax.post(process.env.VUE_APP_SERVER + '/business/admin/teacher/list',{
                pageNum: page,
                pageSize: _this.$refs.pagination.size
            }).then(function (response) {
                Loading.hide();
                //返回的数据
                let resp = response.data;
                //then异步执行,当.then()前的方法执行完后再执行then()内部的程序，这样就避免了，数据没获取到等的问题。
                console.log("查询讲师结果:",resp.content);
                // 出现跨域问题,因为我们是前后端分离的
                // Access to XMLHttpRequest at 'http://127.0.0.1:9002/business/admin/teacher/list' from origin 'http://localhost:8080'
                // has been blocked by CORS policy: No 'Access-Control-Allow-Origin' header is present on the requested resource.

                _this.teachers = resp.content.list;//返回真实数据
                //渲染
                _this.$refs.pagination.render(page,resp.content.totalNum);
            })
        },

        //弹出新增的模态框
        add() {
            let _this = this;
            _this.teacher = {};//清除上次编辑的内容
            $("#form-modal").modal({backdrop:'static'});//点击模态框以外的地方,模态框不关闭
            $("#form-modal").modal("show");//显示模态框
        },

        //新增讲师
        save() {
            let _this = this;
            /**
             * 前后都要做作校验,如果使用postman直接访问后台接口,就会出现问题
             */
            //校验字段   //表示freemaker对字段进行了格式处理,转换成字符串,不然就会出现问题 (2,000 --> 2000)
            if(1 != 1
                    || !Validator.require(_this.teacher.name, "姓名")
                    || !Validator.length(_this.teacher.name, "姓名", 1, 50)
                    || !Validator.length(_this.teacher.nickname, "昵称", 1, 50)
                    || !Validator.length(_this.teacher.image, "头像", 1, 100)
                    || !Validator.length(_this.teacher.position, "职位", 1, 50)
                    || !Validator.length(_this.teacher.motto, "座右铭", 1, 50)
                    || !Validator.length(_this.teacher.intro, "简介", 1, 500)
            ){
              return;
            }

            Loading.show();
            _this.$ajax.post(process.env.VUE_APP_SERVER + '/business/admin/teacher/save',
              _this.teacher
            ).then(function (response) {
                Loading.hide();
                //返回的数据
                let resp = response.data;
                //打印日志
                console.log("保存讲师的结果:{}",resp.content);
                if (resp.success){//true
                    //清楚模态框的内容-->更改到add()方法内部
                    // _this.teacher.courseId = "";
                    // _this.teacher.name = "";

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
        edit(teacher) {
            let _this = this;
            //传参teacher复制一个到{}里面,不影响_this.teacher,即不会修改页面的teacher内容
            _this.teacher = $.extend({},teacher);
            $("#form-modal").modal("show");
        },

        //删除
        del(id) {
            let _this = this;
            //comfirm组件引入
            Confirm.show("删除讲师后不可恢复,确认删除?",function () {
                Loading.show();
                _this.$ajax.delete(process.env.VUE_APP_SERVER + '/business/admin/teacher/delete/'+id
                ).then(function (response) {
                    Loading.hide();
                    //返回的数据
                    let resp = response.data;
                    //删除功能的日志
                    console.log("删除讲师的结果:{}",resp.content);
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
         * 【上传讲师头像】
         */
        upLoadImage() {
          let _this = this;
          Loading.show();
          let formData = new window.FormData;
          console.log("formData1:{}",formData)
          formData.append("file", document.querySelector("#inputImage").files[0]);
          console.log("formData2:{}",formData)
          // key："file"必须和后端controller参数名一致
          _this.$ajax.post(process.env.VUE_APP_SERVER + "/file/admin/upload",
            formData
          ).then((response) => {
            Loading.hide();
            let resp = response.data;
          })
        }
      }
  }
</script>