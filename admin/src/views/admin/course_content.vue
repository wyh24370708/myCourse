<template>
  <div>
    <div class="row">
      <div class="col-lg-4">
        <h4 class="lighter">
          <i class="ace-icon fa fa-hand-o-right icon-animated-hand-pointer blue"></i>
          <router-link to="/business/course" class="pink"> {{course.name}} </router-link>
        </h4>
      </div>
      <div class="col-lg-8">
        {{saveContentDate}}
      </div>
    </div>
    <hr>
    <h4 class="modal-title">内容编辑</h4><br>
    <div class="row">
      <div class="col-md-3">
        <!--上传文件按钮 start-->
        <file
          :use="PROFILE_USE1.COURSE.key"
          :input-id="'content-file-upload'"
          :suffixs="['jpg', 'jpeg', 'png', 'mp4']"
          :text="'富文本上传文件'"
          :after-upload="afterUploadContentFile">
        </file>
        <!--上传文件按钮 end-->
      </div>
      <div class="col-md-9">
        <button v-on:click="saveContent()" style="float:right"
                class="btn btn-default btn-success btn-round" >
          <i class="ace-icon fa fa-floppy-o bigger-120"></i>
          保存
        </button>
      </div>

    </div>


    <!--上传文件的表单内容 start-->
    <table id="file-table" class="table  table-bordered table-hover">
      <thead>
      <!--表头-->
      <tr>
        <th>序号</th>
        <th>名称</th>
        <th>地址</th>
        <th>大小</th>
        <th>操作</th>
      </tr>
      </thead>

      <tbody>
      <tr v-for="(file,index) in files" v-bind:key="file.id">
        <td> {{ index+1 }} </td>
        <td> {{ file.name }} </td>
        <td> {{ file.url }} </td>
        <td> {{ file.size | formatFileSize }} </td>
        <td>
          <div class="hidden-sm hidden-xs btn-group">
            <!--删除按钮 start-->
            <button class="btn btn-white btn-xs btn-warning btn-round" v-on:click="delFile(file)">
              <i class="ace-icon fa fa-times red2"></i>
              删除
            </button>
            <!--删除按钮 end-->
          </div>
        </td>
      </tr>
      </tbody>
    </table>
    <!--上传文件的表单内容 end-->

    <!--富文本编辑器 新增内容 start 单独分离成页面-->

    <form class="form-horizontal">
      <div class="form-group">
        <div class="col-lg-12">
          <div id="content"></div>
        </div>
      </div>
    </form>
    <!--富文本编辑器  新增内容 end -->
  </div>
</template>
<script>
  import File from '../../components/file.vue'
  export default {
    name: "business-course_content",
    components:{File},
    /**
     * 【页面初始化】
     */
    mounted: function() {
      let _this = this;
      //取出缓存
      let course = SessionStorage.get(SESSION_KEY_COURSE);
      if (Tool.isEmpty(course)){
        _this.$router.push("/welcome");
      }
      _this.course = course;
      //初始化编辑框
      _this.init(course);
    },
    /**
     * 【初始化数据】
     */
    data: function () {
     return {
       course: {},
       saveContentDate: "",
       files: [],
       PROFILE_USE1: PROFILE_USE,
       autoSaveContentInterval: {},
     }
    },
    destroyed: function () {
      let _this = this;
      console.log("组件销毁...");
      clearInterval(_this.autoSaveContentInterval);
    },

    /**
     * 【调用方法】
     */
    methods: {
      /**
       * 【打开内容编辑框】
       */
      init(course) {
        let _this = this;
        let id = course.id;
        _this.course = course;
        //初始化Summernote
        $('#content').summernote({
          height: 300,
          focus: true
        });
        // 先清空历史文本
        $("#content").summernote('code', '');
        //初始化保存时间
        _this.saveContentDate = "";
        //加载内容文件列表
        _this.listContentFiles(id);
        Loading.show();
        _this.$ajax.post(process.env.VUE_APP_SERVER + '/business/admin/course/findContent/' + id
        ).then(function (response) {
          Loading.hide();
          let resp = response.data;

          if (resp.success) {//true
            if(resp.content) {
              $("#content").summernote('code', resp.content.content);
            }
            //设定定时保存
            /*
            * setTimeOut 单次的定时任务
            * setInterval 重复的定时任务
            */
            _this.autoSaveContentInterval = setInterval( function () {
              _this.saveContent();
            },60000);
          } else {//校验字段, 字段有问题
            Toast.warning(resp.message);
          }
        })
      },
      /**
       * 【保存课程内容】
       */
      saveContent() {
        let _this = this;
        Loading.show();
        //获取匹配的元素集中第一个summernote的HTML内容。 这个页面只有这一个summernote
        let content = $('#content').summernote('code');
        _this.$ajax.post(process.env.VUE_APP_SERVER + "/business/admin/course/saveContent",{
          id: _this.course.id,
          content: content
        }).then((response) =>{
          let resp = response.data;
          Loading.hide();
          if (resp.success){
            //Toast.success("保存成功!");  //已经设置文本自动保存,不必给出提示框
            let dateFormat = Tool.dateFormat("yyyy-MM-dd hh:mm:ss");
            _this.saveContentDate = "最近保存的时间: " + dateFormat;
          }else{
            Toast.warning(resp.message);
          }
        })
      },

      /**
       * 【加载内容文件列表】
       */
      listContentFiles(courseId){
        let _this = this;
        _this.$ajax.get(process.env.VUE_APP_SERVER + "/business/admin/course_content_file/list/" + courseId
        ).then((response)=>{
          let resp = response.data;
          _this.files = resp.content;
        })
      },

      /**
       * 【富文本编辑器 回调函数，上传文件处理结果.保存到课程内容中】
       *  流程:  1. 先使用文件上传组件,上传文件,并保存到文件管理中(profile表)
       *         2.  前端拿到profile表上传结果后,再次调用服务端,保存到course_content_file表中
       * @param resp
       */
      afterUploadContentFile(resp){
        let _this = this;
        let file = resp.content;
        file.courseId = _this.course.id;
        file.url = file.path;
        _this.$ajax.post(process.env.VUE_APP_SERVER + "/business/admin/course_content_file/save",
          file
        ).then((response)=>{
          let resp = response.data;
          _this.files.push(resp.content);
        })
      },

      /**
       * 删除内容编辑框的上传文件表记录
       * 目前只是删除了 course_content_file 表中内容,   profile表中内容没有删除,暂时可能会造成脏数据
       */
      delFile(file){
        let _this = this;
        //comfirm组件引入
        Confirm.show("删除文件后不可恢复,确认删除?", function () {
          _this.$ajax.delete(process.env.VUE_APP_SERVER + '/business/admin/course_content_file/delete/' + file.id
          ).then(function (response) {
            let resp = response.data;
            if (resp.success) {
              Toast.success("删除成功!");
              _this.init(_this.course);//重新刷新内容编辑框//也可以移除一项内容
            }
          })
        })
      },
    }
  }
</script>