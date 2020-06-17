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
            <pagination ref="pagination" v-bind:list="listCourse" v-bind:itemCount="8"></pagination>
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
        level_1: [],//一级分类
        level_2: [],//二级分类
      }
    },
    mounted() {
      let _this = this;
      //使用分页组件
      _this.$refs.pagination.size = 2;//默认显示的条数
      _this.listCourse(1);
      _this.allCategory();

    },
    methods: {
      /**
       * 全部课程
       */
      listCourse(page) {
        let _this = this;
        _this.$ajax.post(process.env.VUE_APP_SERVER + "/business/web/course/list", {
          pageNum: page,
          pageSize: _this.$refs.pagination.size,
        }).then((response) => {
          let resp = response.data;
          console.log("web查询所有课程的结果:{}", resp.content);
          if (resp.success) {
            _this.courses = resp.content.list;
            //渲染
            _this.$refs.pagination.render(page, resp.content.totalNum);
          } else {
            Toast.warning(resp.message);
          }
        }).catch((response) => {
          console.log("error:{}", response);
        })
      },

      /**
       * 加载所有分类
       */
      allCategory() {
        let _this = this;
        _this.$ajax.post(process.env.VUE_APP_SERVER + '/business/web/category/all', {}
        ).then(function (response) {
          //返回的数据
          let resp = response.data;
          //then异步执行,当.then()前的方法执行完后再执行then()内部的程序，这样就避免了，数据没获取到等的问题。
          console.log("查询分类结果:", resp.content);
          let categorys = resp.content;//返回真实数据

          //将所有的记录格式化成树形结构
          _this.level_1 = [];
          for (let i = 0; i < categorys.length; i++) {
            let categorySingle = categorys[i];//i
            if (categorySingle.parent === '00000000') {
              _this.level_1.push(categorySingle);
              for (let j = 0; j < categorys.length; j++) {
                let child = categorys[j];//j
                if (child.parent === categorySingle.id) {
                  if (Tool.isEmpty(categorySingle.children)) {
                    categorySingle.children = [];
                  }
                  categorySingle.children.push(child);
                  console.log(categorySingle.children);
                }
              }
            }
          }
          _this.level_2 = [];
          // 对当前一级分类中选中的表格触发一次点击事件，以刷新二级菜单列表
          // 注意：界面的渲染需要等vue绑定好变量后才做，所以加延时100ms
          setTimeout(function () {
            $("tr.active").trigger("click");
          }, 100)
        })
      },






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