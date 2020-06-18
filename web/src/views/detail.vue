<template>
  <div>
    <main role="main">
      <div class="album py-5 bg-light">
        <div class="container">
          <!--课程详情 start-->
          <div class="row course-head">
            <!--课程封面 start-->
            <div class="col-sm-6" id="cover-video-div">
              <img class="img-fluid" v-bind:src="course.image">
            </div>
            <!--课程封面 end-->

            <!--详情 start-->
            <div class="col-sm-6">
              <h1>{{course.name}}</h1>
              <p class="course-head-item">
                <span><i class="fa fa-clock-o"></i> {{(course.time) | formatSecond}}</span>
                <span>{{COURSE_LEVEL | optionKV(course.level)}}</span>
                <span><i class="fa fa-user"></i> {{course.enroll}}</span>
              </p>
              <p class="course-head-desc">{{course.summary}}</p>
              <p class="course-head-price">
                <span class="price-now text-danger"><i class="fa fa-yen"></i>&nbsp;{{course.price}}&nbsp;&nbsp;</span>
              </p>
              <p class="course-head-button-links">
                <a class="btn btn-lg btn-primary btn-shadow" href="javascript:;">立即报名</a>
              </p>
            </div>
            <!--详情 end-->
          </div>
          <!--课程 end-->

          <!--课程内容 & 章节 & 讲师 start-->
          <div class="row">
            <!--课程内容 & 章节 start-->
            <div class="col-sm-9">
              <!--Tabs  active表示激活,data-toggle=tab表示切换tab的激活状态-->
              <ul class="nav nav-tabs">
                <li class="nav-item">
                  <a class="nav-link" href="#info" data-toggle="tab">课程介绍</a>
                </li>
                <li class="nav-item">
                  <a class="nav-link active" href="#chapter" data-toggle="tab">章节目录</a>
                </li>
              </ul>

              <!--tab content-->
              <div class="tab-content">
                <div class="tab-pane" id="info" v-html="course.courseContent"></div>
                <div class="tab-pane active" id="chapter">
                  <div v-for="chapter in chapters" class="chapter">
                    <div class="chapter-chapter">
                      <span class="folded-button">{{chapter.name}}</span>
                    </div>
                    <div>
                      <table class="table table-striped">
                        <tr v-for="(sec, index) in chapter.sections" class="chapter-section-tr">
                          <td class="col-sm-8 col-xs-12">
                            <div class="section-title">
                              <i class="fa fa-video-camera d-none d-sm-inline"></i>&nbsp;&nbsp;
                              <!--d-none 等价于 dispaly none显示  sm分辨率时在显示-->
                              <span class="d-none d-sm-inline">第{{index+1}}节&nbsp;&nbsp;</span>
                              {{sec.title}}
                              <span v-show="sec.charge !== SECTION_CHARGE.CHARGE.key" class="badge badge-primary hidden-xs">免费</span>
                            </div>
                          </td>
                          <td class="col-sm-1 text-right">
                            <span class="badge badge-primary">{{sec.time | formatSecond}}</span>
                          </td>
                        </tr>
                      </table>
                    </div>
                  </div>
                </div>
              </div>
            </div>
            <!--课程内容 & 章节 end-->

            <!--讲师信息 start -->
            <div class="col-md-3">
              <div class="card" style="width: 18rem;">
                <img v-bind:src="teacher.image" class="card-img-top" alt="...">
                <div class="card-body">
                  <h5 class="card-title">{{teacher.name}}</h5>
                  <p class="card-text">{{teacher.position}}</p>
                  <p class="card-text">{{teacher.motto}}</p>
                  <a href="#" class="btn btn-primary">讲师所有课程(未做)</a>
                </div>
              </div>
            </div>
            <!--讲师信息 end -->
          </div>
          <!--课程内容 & 章节 讲师 end-->

        </div>
      </div>
    </main>
  </div>
</template>

<script>
  export default {
    name: "detail",
    data:function () {
      return{
        id: "",
        course:{},
        teacher:{},
        chapters:[],
        sections:[],
        COURSE_LEVEL: COURSE_LEVEL,
        SECTION_CHARGE: SECTION_CHARGE,
      }
    },
    mounted() {
      let _this = this;
      _this.id = _this.$route.query.id;
      _this.findCourse();
    },
    methods:{
      /**
       * 课程详情
       */
      findCourse(){
        let _this = this;
        _this.$ajax.get(process.env.VUE_APP_SERVER + "/business/web/course/find/"+ _this.id).then((response)=>{
          let resp = response.data;
          if (resp.success){
            _this.course = resp.content;
            _this.teacher = resp.content.teacher || {};
            _this.chapters = resp.content.chapters || {};
            _this.sections = resp.content.sections || {};

            //把小节放进大章
            for (let i = 0; i < _this.chapters.length; i++) {
              let chapter = _this.chapters[i];
              chapter.sections = [];
              for (let j = 0; j < _this.sections.length; j++) {
                let section = _this.sections[j];
                if (section.chapterId === chapter.id){
                  chapter.sections.push(section);
                }
              }
            }

          }
        })
      }
    }
  }
</script>

<style>
   .course-head {
   }
   .course-head h1 {
     font-size: 2rem;
     margin-bottom: 1.5rem;
   }
   .course-head-item span {
     margin-right: 1rem;
   }
   .course-head-desc {
     font-size: 1rem;
     color: #555
   }

   .course-head a {
   }

   .course-head-price {
     font-size: 2rem;
   }

   /*字体大小自适应  屏幕变小就会下面的样式*/
   @media (max-width: 700px) {
     .course-head h1 {
       font-size: 1.5rem;
     }
   }


   /* 章节列表 */
   .chapter {
     padding-bottom: 1.25rem;
   }
   .chapter-chapter {
     font-size: 1.25rem;
     padding: 0.8rem;
     background-color: #23527c;
     color: white;
     margin-top: 1rem;
   }
   .chapter-section-tr {
     font-size: 1rem;
   }
   .chapter-section-tr td{
     padding: 1rem 1.25rem 0.6rem;
     vertical-align: middle;
   }
   /*鼠标手势*/
   .chapter-section-tr td .section-title{
     color: #555;
   }

   .chapter-section-tr td .section-title:hover{
     color: #23527c;
     font-weight: bolder;
     cursor: pointer;/*鼠标手势*/
   }

   /*行头小图标*/
   .chapter-section-tr td .section-title i{
     color: #2a6496;
   }

   @media (max-width: 700px) {
     .chapter-chapter {
       font-size: 1.2rem;
     }
     .chapter-section-tr {
       font-size: 0.9rem;
     }
   }

</style>