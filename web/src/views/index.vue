<template>
  <main role="main">

    <section class="jumbotron text-center">
      <div class="container">
        <h1>在线视频课程平台</h1>
        <p class="lead text-muted">
          知识付费时代刚刚起步，在这个领域有很多的发展机会。整个课程以实战为基础，手把手从零开始，
          一步一步搭建一个完整的企业级开发架构。不讲废话，只讲干货.
        </p>
        <p>
          <button type="button" class="btn btn-success btn-lg my-auto ">进入所有课程</button>
        </p>
      </div>
    </section>

    <div class="album py-5 bg-light">
      <div class="container">
        <div class="title1">最新上线</div>
        <div class="row">
          <div v-for="courseNew in listNew" :key="courseNew.id" class="col-md-4 card-border"  >
            <course-card v-bind:course="courseNew"></course-card>
          </div>
        </div>

        <hr>

        <div class="title2">好课推荐</div>
        <div class="row">
          <div v-for="courseNew in listNew" :key="courseNew.id" class="col-md-4 card-border"  >
            <course-card v-bind:course="courseNew"></course-card>
          </div>

        </div>
      </div>
    </div>

  </main>
</template>

<script>
  import CourseCard from "../components/course_card";
  export default {
    name: 'index',
    components: {CourseCard},
    data:function () {
      return{
        listNew:[],
      }
    },
    mounted() {
      let _this = this;
      _this.findListNew();
    },
    methods:{
      /**
       * 获取新上好课,最近的三门
       */
      findListNew(){
        let _this = this;
        _this.$ajax.get(process.env.VUE_APP_SERVER + "/business/web/course/list-new").then((response)=>{
          let resp = response.data;
          console.log("查询新上好课的结果:{}",resp.content);
          if (resp.success) {
            _this.listNew = resp.content;
          }
        }).catch((response)=>{
          console.log("error:{}",response);
        })
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