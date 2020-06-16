<template>
  <main role="main">
    <div class="album py-5 bg-light">
      <div class="container">
        <!--课程内容 start-->
        <div class="row">
          <div v-for="course in courses" :key="course.id" class="col-md-4 card-border"  >
            <course-card v-bind:course="course"></course-card>
          </div>
          <h3 v-show="courses.length === 0">课程还未上架</h3>
        </div>
        <!--课程内容 end-->

        <!--分页内容 start-->
        <!--v-bind:xxx是组件中props设置的属性 list表示函数 itemCount表示显示最多显示几个按钮-->
        <div class="row">
          <div class="col-md-12 ">
            <pagination ref="pagination" v-bind:list="list" v-bind:itemCount="8"></pagination>
          </div>
        </div>
        <!--分页内容 end-->

      </div>
    </div>

  </main>
</template>

<script>
  import CourseCard from "../components/course_card";
  import Pagination from "../components/pagination";
  export default {
    name: 'list',
    components: {CourseCard,Pagination},
    data:function () {
      return{
        courses:[],//所有课程
      }
    },
    mounted() {
      let _this = this;
      //使用分页组件
      _this.$refs.pagination.size = 5;//默认显示的条数
      _this.listCourse(1);

    },
    methods:{
      /**
       * 全部课程
       */
      listCourse(page){
        let _this = this;
        _this.$ajax.post(process.env.VUE_APP_SERVER + "/business/web/course/list", {
            pageNum: page,
            pageSize: _this.$refs.pagination.size,
        }).then((response)=>{
          let resp = response.data;
          console.log("web查询所有课程的结果:{}",resp.content);
          if (resp.success) {
            _this.courses = resp.content.list;
            //渲染
            _this.$refs.pagination.render(page,resp.content.totalNum);
          }else{
            Toast.warning(resp.message);
          }
        }).catch((response)=>{
          console.log("error:{}",response);
        })
      }
    }
  }
</script>

<style scoped >
  .container .card-border{
    width: auto;
    height: 550px;
  }
  pagination{
    float: right;
  }
</style>