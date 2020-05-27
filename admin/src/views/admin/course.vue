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

    <!--xxx start-->
    <div class="row">
      <div class="col-md-3" v-for="course in courses">
        <div class="thumbnail search-thumbnail" >
          <!--课程默认图片-->
          <img v-show="!course.image" class="media-object" src="/static/image/demo-course.jpg" />
          <!--数据库存的课程图片-->
          <img v-show="course.image" class="media-object" src="course.image" />
          <div class="caption">
            <div class="clearfix">
              <span class="pull-right label label-grey info-label">
                  {{COURSE_STATUS | optionKV(course.status)}}
              </span>
              <span class="pull-right label label-grey info-label">
                  {{COURSE_LEVEL | optionKV(course.level)}}
              </span>
              <span class="pull-right label label-grey info-label">
                  {{COURSE_CHARGE | optionKV(course.charge)}}
              </span>
            </div>

            <h3 class="search-title">
              <a href="#" class="blue">{{course.name}}</a>
            </h3>
            <p>
              <span class="blue bolder bigger-150">
                <i class="fa fa-rmb"></i>
                {{course.price}}&nbsp;
              </span>&nbsp;
            </p>
            <p>{{course.summary}}</p>
            <p>
              <span class="badge badge-info">{{course.id}}</span>
              <span class="badge badge-info">排序：{{course.sort}}</span>
              <span class="badge badge-info">时长：{{course.time}}</span>
            </p>
            <!--按钮 start-->
            <p>
              <!--大章按钮 start-->
              <button class="btn btn-white btn-xs btn-info btn-round" v-on:click="toChapter(course)">
                大章
              </button>
              <!--大章按钮 end-->
              &nbsp;
              <!--修改按钮 start-->
              <button class="btn btn-white btn-xs btn-info btn-round" v-on:click="edit(course)">
                编辑
              </button>
              <!--修改按钮 end-->
              &nbsp;
              <!--删除按钮 start-->
              <button class="btn btn-white btn-xs btn-warning btn-round" v-on:click="del(course.id)">
                删除
              </button>
              <!--删除按钮 end-->
            </p>
            <!--按钮 end-->
          </div>
        </div>
      </div>
    </div>
    <!--xxx end-->

    <!--表单内容 start-->
<!--    <table id="simple-table" class="table  table-bordered table-hover">-->
<!--      <thead>-->
<!--      &lt;!&ndash;表头&ndash;&gt;-->
<!--      <tr>-->
<!--                  <th>id</th>-->
<!--          <th>课程名称</th>-->
<!--          <th>概述</th>-->
<!--          <th>时长</th>-->
<!--          <th>价格(元)</th>-->
<!--          <th>封面</th>-->
<!--          <th>级别</th>-->
<!--          <th>收费</th>-->
<!--          <th>状态</th>-->
<!--          <th>报名数</th>-->
<!--          <th>顺序</th>-->
<!--        <th>操作</th>-->
<!--      </tr>-->
<!--      </thead>-->

<!--      <tbody>-->
<!--      <tr v-for="course in courses">-->
<!--          <td>{{course.id}}</td>-->
<!--          <td>{{course.name}}</td>-->
<!--          <td>{{course.summary}}</td>-->
<!--          <td>{{course.time}}</td>-->
<!--          <td>{{course.price}}</td>-->
<!--          <td>{{course.image}}</td>-->
<!--          <td>{{COURSE_LEVEL | optionKV(course.level)}}</td>-->
<!--          <td>{{COURSE_CHARGE | optionKV(course.charge)}}</td>-->
<!--          <td>{{COURSE_STATUS | optionKV(course.status)}}</td>-->
<!--          <td>{{course.enroll}}</td>-->
<!--          <td>{{course.sort}}</td>-->
<!--        <td>-->
<!--          <div class="hidden-sm hidden-xs btn-group">-->
<!--            &lt;!&ndash;修改按钮 start&ndash;&gt;-->
<!--            <button class="btn btn-xs btn-info" v-on:click="edit(course)">-->
<!--              <i class="ace-icon fa fa-pencil bigger-120"></i>-->
<!--            </button>-->
<!--            &lt;!&ndash;修改按钮 end&ndash;&gt;-->

<!--            &lt;!&ndash;删除按钮 start&ndash;&gt;-->
<!--            <button class="btn btn-xs btn-danger" v-on:click="del(course.id)">-->
<!--              <i class="ace-icon fa fa-trash-o bigger-120"></i>-->
<!--            </button>-->
<!--            &lt;!&ndash;删除按钮 end&ndash;&gt;-->

<!--          </div>-->

<!--          <div class="hidden-md hidden-lg">-->
<!--            <div class="inline pos-rel">-->
<!--              <button class="btn btn-minier btn-primary dropdown-toggle" data-toggle="dropdown" data-position="auto">-->
<!--                <i class="ace-icon fa fa-cog icon-only bigger-110"></i>-->
<!--              </button>-->

<!--              <ul-->
<!--                      class="dropdown-menu dropdown-only-icon dropdown-yellow dropdown-menu-right dropdown-caret dropdown-close">-->
<!--                <li>-->
<!--                  <a href="#" class="tooltip-info" data-rel="tooltip" title="View">-->
<!--                                        <span class="blue">-->
<!--                                          <i class="ace-icon fa fa-search-plus bigger-120"></i>-->
<!--                                        </span>-->
<!--                  </a>-->
<!--                </li>-->

<!--                <li>-->
<!--                  <a href="#" class="tooltip-success" data-rel="tooltip" title="Edit">-->
<!--                                        <span class="green">-->
<!--                                          <i class="ace-icon fa fa-pencil-square-o bigger-120"></i>-->
<!--                                        </span>-->
<!--                  </a>-->
<!--                </li>-->

<!--                <li>-->
<!--                  <a href="#" class="tooltip-error" data-rel="tooltip" title="Delete">-->
<!--                                        <span class="red">-->
<!--                                          <i class="ace-icon fa fa-trash-o bigger-120"></i>-->
<!--                                        </span>-->
<!--                  </a>-->
<!--                </li>-->
<!--              </ul>-->
<!--            </div>-->
<!--          </div>-->
<!--        </td>-->
<!--      </tr>-->
<!--      </tbody>-->
<!--    </table>&lt;!&ndash; /.row &ndash;&gt;-->
    <!--表单内容 end -->

    <!--新增内容 start -->
    <div id="form-modal" class="modal fade" tabindex="-1" role="dialog">
      <div class="modal-dialog" role="document">
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
            <h4 class="modal-title">新增课程表</h4>
          </div>
          <div class="modal-body">
            <!--模态框内容 Start -->
            <form class="form-horizontal">

              <!-- freemaker生成 start -->
                  <div class="form-group">
                    <label for="inputName" class="col-sm-2 control-label">课程名称</label>
                    <div class="col-sm-10">
                      <input type="text" class="form-control" id="inputName"
                             v-model="course.name" >
                    </div>
                  </div>
                  <div class="form-group">
                    <label for="inputSummary" class="col-sm-2 control-label">概述</label>
                    <div class="col-sm-10">
                      <textarea v-model="course.summary" type="text" class="form-control" id="inputSummary"></textarea>
                    </div>
                  </div>
                  <div class="form-group">
                    <label for="inputTime" class="col-sm-2 control-label">时长</label>
                    <div class="col-sm-10">
                      <input type="text" class="form-control" id="inputTime"
                             v-model="course.time" >
                    </div>
                  </div>
                  <div class="form-group">
                    <label for="inputPrice" class="col-sm-2 control-label">价格(元)</label>
                    <div class="col-sm-10">
                      <input type="text" class="form-control" id="inputPrice"
                             v-model="course.price" >
                    </div>
                  </div>
                  <div class="form-group">
                    <label for="inputImage" class="col-sm-2 control-label">封面</label>
                    <div class="col-sm-10">
                      <input type="text" class="form-control" id="inputImage"
                             v-model="course.image" >
                    </div>
                  </div>
                  <div class="form-group">
                    <label for="inputLevel" class="col-sm-2 control-label">级别</label>
                    <div class="col-sm-10">
                      <select type="text" class="form-control" id="inputLevel"
                              v-model="course.level">
                        <option v-for="courseLevel in COURSE_LEVEL" v-bind:value="courseLevel.key">
                          {{courseLevel.value}}
                        </option>
                      </select>
                    </div>
                  </div>
                  <div class="form-group">
                    <label for="inputCharge" class="col-sm-2 control-label">收费</label>
                    <div class="col-sm-10">
                      <select type="text" class="form-control" id="inputCharge"
                              v-model="course.charge">
                        <option v-for="courseCharge in COURSE_CHARGE" v-bind:value="courseCharge.key">
                          {{courseCharge.value}}
                        </option>
                      </select>
                    </div>
                  </div>
                  <div class="form-group">
                    <label for="inputStatus" class="col-sm-2 control-label">状态</label>
                    <div class="col-sm-10">
                      <select type="text" class="form-control" id="inputStatus"
                              v-model="course.status">
                        <option v-for="courseStatus in COURSE_STATUS" v-bind:value="courseStatus.key">
                          {{courseStatus.value}}
                        </option>
                      </select>
                    </div>
                  </div>
                  <div class="form-group">
                    <label for="inputEnroll" class="col-sm-2 control-label">报名数</label>
                    <div class="col-sm-10">
                      <input type="text" class="form-control" id="inputEnroll"
                             v-model="course.enroll" >
                    </div>
                  </div>
                  <div class="form-group">
                    <label for="inputSort" class="col-sm-2 control-label">顺序</label>
                    <div class="col-sm-10">
                      <input type="text" class="form-control" id="inputSort"
                             v-model="course.sort" >
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
      name: 'business-course',
      mounted: function () {//页面加载初始化
          //激活样式方法一
          // this.$parent.activeSidebar("business-course-sidebar");//后面使用通用方法
          let _this = this;
          //_this.list();//没有使用分页组件
          //使用分页组件
          _this.$refs.pagination.size = 5;//默认显示的条数
          _this.list(1);//调用list的方法
      },
      data: function () {
        return {
          course: {}, //前台传入的数据
          courses: [],//初始化为空数组, 后台查询到的数据
          COURSE_CHARGE: COURSE_CHARGE,
          COURSE_LEVEL: COURSE_LEVEL,
          COURSE_STATUS: COURSE_STATUS
        }
      },
      methods: {
        //获取数据
        list(page) {
            let _this = this;
            Loading.show();
            //传输数据,通过表单的形式和流的形式,vue是通过流的方式,需要以@requestBody来获取数据
            _this.$ajax.post(process.env.VUE_APP_SERVER + '/business/admin/course/list',{
                pageNum: page,
                pageSize: _this.$refs.pagination.size
            }).then(function (response) {
                Loading.hide();
                //返回的数据
                let resp = response.data;
                //then异步执行,当.then()前的方法执行完后再执行then()内部的程序，这样就避免了，数据没获取到等的问题。
                console.log("查询课程表结果:",resp.content);
                // 出现跨域问题,因为我们是前后端分离的
                // Access to XMLHttpRequest at 'http://127.0.0.1:9002/business/admin/course/list' from origin 'http://localhost:8080'
                // has been blocked by CORS policy: No 'Access-Control-Allow-Origin' header is present on the requested resource.

                _this.courses = resp.content.list;//返回真实数据
                //渲染
                _this.$refs.pagination.render(page,resp.content.totalNum);
            })
        },

        //弹出新增的模态框
        add() {
            let _this = this;
            _this.course = {};//清除上次编辑的内容
            $("#form-modal").modal({backdrop:'static'});//点击模态框以外的地方,模态框不关闭
            $("#form-modal").modal("show");//显示模态框
        },

        //新增课程表
        save() {
            let _this = this;
            /**
             * 前后都要做作校验,如果使用postman直接访问后台接口,就会出现问题
             */
            //校验字段
            if(1 != 1
                    || !Validator.require(_this.course.name, "课程名称")
                    || !Validator.length(_this.course.name, "课程名称", 1, 50)
                    || !Validator.length(_this.course.summary, "概述", 1, 2000)
            ){
              return;
            }

            Loading.show();
            _this.$ajax.post(process.env.VUE_APP_SERVER + '/business/admin/course/save',
              _this.course
            ).then(function (response) {
                Loading.hide();
                //返回的数据
                let resp = response.data;
                //打印日志
                console.log("保存课程表的结果:{}",resp.content);
                if (resp.success){//true
                    //清楚模态框的内容-->更改到add()方法内部
                    // _this.course.courseId = "";
                    // _this.course.name = "";

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
        edit(course) {
            let _this = this;
            //传参course复制一个到{}里面,不影响_this.course,即不会修改页面的course内容
            _this.course = $.extend({},course);
            $("#form-modal").modal("show");
        },

        //删除
        del(id) {
            let _this = this;
            //comfirm组件引入
            Confirm.show("删除课程表后不可恢复,确认删除?",function () {
                Loading.show();
                _this.$ajax.delete(process.env.VUE_APP_SERVER + '/business/admin/course/delete/'+id
                ).then(function (response) {
                    Loading.hide();
                    //返回的数据
                    let resp = response.data;
                    //删除功能的日志
                    console.log("删除课程表的结果:{}",resp.content);
                    if (resp.success){
                        //重新查询list
                        _this.list(1);
                        //删除成功的提示框
                        Toast.success("删除成功!");
                    }
                })
            })
        },

        //点击【大章】 缓存课程course
        toChapter(course) {
          let _this = this;
          //h5缓存 course
          SessionStorage.set("course",course);
          //页面跳转
          _this.$router.push("/business/chapter");
        }
      }
  }
</script>


<!--
scoped :style下样式只适用于当前的组件,防止互相污染
-->
<style scoped>
  .caption h3{
    font-size: 25px;
  }
</style>