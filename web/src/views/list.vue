<template>
  <main role="main">
    <div class="album py-5 bg-light">
      <div class="container">
        <div class="row">
          <div v-for="course in courses" :key="course.id" class="col-md-4 card-border"  >
            <course-card v-bind:course="course"></course-card>
        </div>
          <h3 v-show="courses.length === 0">课程还未上架</h3>
        </div>
      </div>
    </div>

  </main>
</template>

<script>
  import CourseCard from "../components/course_card";
  export default {
    name: 'list',
    components: {CourseCard},
    data:function () {
      return{
        courses:[],//所有课程
      }
    },
    mounted() {
      let _this = this;
      _this.listCourse();
    },
    methods:{
      /**
       * 获取新上好课,最近的三门
       */
      listCourse(){
        let _this = this;
        // _this.$ajax.get(process.env.VUE_APP_SERVER + "/business/web/course/list-new").then((response)=>{
        //   let resp = response.data;
        //   console.log("查询新上好课的结果:{}",resp.content);
        //   if (resp.success) {
        //     _this.listNew = resp.content;
        //   }
        // }).catch((response)=>{
        //   console.log("error:{}",response);
        // })
      }

    }
  }
</script>

<style scoped >
  .title1{
    margin-bottom: 2rem;
    color: #fafafa;
    letter-spacing: 0;
    text-shadow: 0px 1px 0px #999, 0px 2px 0px #888, 0px 3px 0px #777, 0px 4px 0px #666, 0px 5px 0px #555, 0px 6px 0px #444, 0px 7px 0px #333, 0px 8px 7px #001135;
    font-size: 2rem;
  }
  .title2{
    margin-bottom: 2rem;
    color: transparent;
    -webkit-text-stroke: 1px black;
    letter-spacing: 0.04em;
    font-size: 2rem;
  }

  .container .card-border{
    width: auto;
    height: 550px;
  }
</style>