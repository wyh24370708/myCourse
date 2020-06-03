<template>
  <div>
    <h4 class="lighter">
      <i class="ace-icon fa fa-hand-o-right icon-animated-hand-pointer blue"></i>
      <a href="#modal-wizard" data-toggle="modal" class="pink"> {{course.name}} </a> :
      <i class="ace-icon fa fa-hand-o-right icon-animated-hand-pointer blue"></i>
      <a href="#modal-wizard" data-toggle="modal" class="pink"> {{chapter.name}} </a>
    </h4>
    <hr>
    <!--按钮 Start-->
    <p>
      <router-link to="/business/chapter" class="btn btn-white btn-default btn-round">
        <i class="ace-icon fa fa-arrow-left"></i>
        返回大章
      </router-link>
      &nbsp;&nbsp;
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
          <th>ID</th>
          <th>标题</th>
          <th>视频</th>
          <th>时长</th>
          <th>收费</th>
          <th>顺序</th>
        <th>操作</th>
      </tr>
      </thead>

      <tbody>
      <tr v-for="section in sections">
          <td>{{section.id}}</td>
          <td>{{section.title}}</td>
          <td>{{section.video}}</td>
          <td>{{section.time | formatSecond}}</td>
          <td>{{SECTION_CHARGE | optionKV(section.charge)}}</td>
          <td>{{section.sort}}</td>
        <td>
          <div class="hidden-sm hidden-xs btn-group">
            <!--修改按钮 start-->
            <button class="btn btn-white btn-xs btn-info btn-round" v-on:click="edit(section)">
              编辑
            </button>
            <!--修改按钮 end-->
            &nbsp;&nbsp;
            <!--删除按钮 start-->
            <button class="btn btn-white btn-xs btn-warning btn-round" v-on:click="del(section.id)">
              删除
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
            <h4 class="modal-title">新增小节</h4>
          </div>
          <div class="modal-body">
            <!--模态框内容 Start -->
            <form class="form-horizontal">

              <!-- freemaker生成 start -->
                  <div class="form-group">
                    <label for="inputTitle" class="col-sm-2 control-label">标题</label>
                    <div class="col-sm-10">
                      <input type="text" class="form-control" id="inputTitle"
                             v-model="section.title" >
                    </div>
                  </div>
                  <div class="form-group">
                    <label class="col-sm-2 control-label">课程</label>
                    <div class="col-sm-10">
                      <p class="form-control-static" > {{course.name}} </p>
                    </div>
                  </div>
                  <div class="form-group">
                    <label class="col-sm-2 control-label">大章</label>
                    <div class="col-sm-10">
                      <p class="form-control-static" > {{chapter.name}} </p>
                    </div>
                  </div>
                  <div class="form-group">
                    <label class="col-sm-2 control-label">视频</label>
                    <div class="col-md-10">
                      <!--上传文件的公共部分-->
                      <file
                        :use="PROFILE_USE1.COURSE.key"
                        :id="'video-upload'"
                        :suffixs="['mp4', 'avi', 'rmvb','rm']"
                        :text="'上传视频'"
                        :after-upload="afterUpload">
                      </file>
                      <!--视频预览代码,私有-->
                      <div class="row col-md-10">
                        <video :src="section.video" controls="controls"></video><!--视频控件-->
                      </div>
                    </div>
                  </div>
                  <div class="form-group">
                    <label for="inputTime" class="col-sm-2 control-label">时长</label>
                    <div class="col-sm-10">
                      <input type="text" class="form-control" id="inputTime"
                             v-model="section.time" >
                    </div>
                  </div>
                  <div class="form-group">
                    <label for="inputCharge" class="col-sm-2 control-label">收费</label>
                    <div class="col-sm-10">
                      <select type="text" class="form-control" id="inputCharge"
                              v-model="section.charge">
                        <option v-for="c in SECTION_CHARGE" v-bind:value="c.key">{{c.value}}</option>
                      </select>
                    </div>
                  </div>
                  <div class="form-group">
                    <label for="inputSort" class="col-sm-2 control-label">顺序</label>
                    <div class="col-sm-10">
                      <input type="text" class="form-control" id="inputSort"
                             v-model="section.sort" >
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
  import File from '../../components/file.vue'
  export default {
      components: {Pagination,File},//引入pagination组件
      name: 'business-section',
      mounted: function () {//页面加载初始化
          //激活样式方法一
          // this.$parent.activeSidebar("business-section-sidebar");//后面使用通用方法
          let _this = this;
          //_this.list();//没有使用分页组件
          //使用分页组件
          _this.$refs.pagination.size = 5;//默认显示的条数

          //取出缓存
          let chapter = SessionStorage.get(SESSION_KEY_CHAPTER);
          let course = SessionStorage.get(SESSION_KEY_COURSE);
          if (Tool.isEmpty(chapter) || Tool.isEmpty(course)){
            _this.$router.push("/welcome");
          }
          _this.chapter = chapter;
          _this.course = course;

          _this.list(1);//调用list的方法
      },
      data: function () {
        return {
            section: {}, //前台传入的数据
            sections: [],//初始化为空数组, 后台查询到的数据
            SECTION_CHARGE: SECTION_CHARGE,
            chapter: {},
            course: {},
            PROFILE_USE1: PROFILE_USE,
        }
      },
      methods: {
        list(page) {
            let _this = this;
            Loading.show();
            //传输数据,通过表单的形式和流的形式,vue是通过流的方式,需要以@requestBody来获取数据
            _this.$ajax.post(process.env.VUE_APP_SERVER + '/business/admin/section/list',{
                pageNum: page,
                pageSize: _this.$refs.pagination.size,
                courseId: _this.course.id,
                chapterId: _this.chapter.id
            }).then(function (response) {
                Loading.hide();
                //返回的数据
                let resp = response.data;
                //then异步执行,当.then()前的方法执行完后再执行then()内部的程序，这样就避免了，数据没获取到等的问题。
                console.log("查询小节结果:",resp.content);
                // 出现跨域问题,因为我们是前后端分离的
                // Access to XMLHttpRequest at 'http://127.0.0.1:9002/business/admin/section/list' from origin 'http://localhost:8080'
                // has been blocked by CORS policy: No 'Access-Control-Allow-Origin' header is present on the requested resource.

                _this.sections = resp.content.list;//返回真实数据
                //渲染
                _this.$refs.pagination.render(page,resp.content.totalNum);
            })
        },

        //弹出新增的模态框
        add() {
            let _this = this;
            _this.section = {};//清除上次编辑的内容
            $("#form-modal").modal({backdrop:'static'});//点击模态框以外的地方,模态框不关闭
            $("#form-modal").modal("show");//显示模态框
        },

        //新增小节
        save() {
            let _this = this;
            //保存课程id和大章id
            _this.section.courseId = _this.course.id;
            _this.section.chapterId = _this.chapter.id;
            /**
             * 前后都要做作校验,如果使用postman直接访问后台接口,就会出现问题
             */
            //校验字段

            if(1 != 1
                    || !Validator.require(_this.section.title, "标题")
                    || !Validator.length(_this.section.title, "标题", 1, 50)
                    // || !Validator.length(_this.section.video, "视频", 1, 200)
            ){
              return;
            }

            Loading.show();
            _this.$ajax.post(process.env.VUE_APP_SERVER + '/business/admin/section/save',
              _this.section
            ).then(function (response) {
                Loading.hide();
                //返回的数据
                let resp = response.data;
                //打印日志
                console.log("保存小节的结果:{}",resp.content);
                if (resp.success){//true
                    //清楚模态框的内容-->更改到add()方法内部
                    // _this.section.courseId = "";
                    // _this.section.name = "";

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
        edit(section) {
            let _this = this;
            //传参section复制一个到{}里面,不影响_this.section,即不会修改页面的section内容
            _this.section = $.extend({},section);
            $("#form-modal").modal("show");
        },

        //删除
        del(id) {
            let _this = this;
            //comfirm组件引入
            Confirm.show("删除小节后不可恢复,确认删除?",function () {
                Loading.show();
                _this.$ajax.delete(process.env.VUE_APP_SERVER + '/business/admin/section/delete/'+id
                ).then(function (response) {
                    Loading.hide();
                    //返回的数据
                    let resp = response.data;
                    //删除功能的日志
                    console.log("删除小节的结果:{}",resp.content);
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
         *
         */
        afterUpload(resp){
          let _this = this;
          let video = resp.content.path;
          _this.section.video = video;
        }
      }
  }
</script>

<style scoped>
  video{
    width: 100%;
    height: auto;
  }
</style>