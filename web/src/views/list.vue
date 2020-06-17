<template>
  <main role="main">
    <div class="header-nav">
      <div class="clearfix">
        <div class="container">
          <div class="row">
            <div class="col-md-12">
              <a v-on:click="onClickLevel1('00000000')" id="category-00000000" href="javascript:;" class="cur">全部</a>
              <a v-for="o in level_1" v-on:click="onClickLevel1(o.id)" v-bind:id="'category-' + o.id" href="javascript:;">{{o.name}}</a>
            </div>
          </div>
        </div>
      </div>
    </div>

    <div class="skill clearfix">
      <div class="container">
        <div class="row">
          <div class="col-md-12">
            <a v-on:click="onClickLevel2('11111111')" id="category-11111111" href="javascript:;" class="on">不限</a>
            <a v-for="o in level_2" v-on:click="onClickLevel1(o.id)" v-bind:id="'category-' + o.id" href="javascript:;">{{o.name}}</a>

            <div style="clear:both"></div>
          </div>
        </div>
      </div>
    </div>


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
       * 【全部课程】
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
       * 【加载所有分类】
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
            }else{
              _this.level_2.push(categorySingle);
            }
          }
        })
      },

      /**
       * 【点击一级分类】
       */
      onClickLevel1(id){
        let _this = this;
      },

      /**
       * 【点击二级分类】
       */
      onClickLevel2(id){
        let _this = this;
      }





    }
  }
</script>

<style scoped >
  .container .card-border{
    width: auto;
    height: 550px;
  }

  .header-nav {
     height: 72px;
     background: #fff;
     box-shadow: 0 8px 16px 0 rgba(28,31,33,.1);
     padding: 16px 0;
     box-sizing: border-box;
     position: relative;
     z-index: 1;
     margin-top: 10px;
   }
  .header-nav>div {
    width: 1152px;
    padding-left: 12px;
    box-sizing: border-box;
    margin-left: auto;
    margin-right: auto;
  }

  .header-nav a {
    float: left;
    font-size: 16px;
    color: #07111b;
    line-height: 50px;
    height: 45px;
    position: relative;
    margin-right: 46px;
    font-weight: 700;
  }
  .header-nav a:hover {
    color: #c80;
  }
  .header-nav a.cur {
    color: #c80;
  }
  .header-nav a.cur:before {
    display: block;
  }
  .header-nav a:before {
    display: none;
    content: ' ';
    position: absolute;
    bottom: 0;
    background: #c80;
    width: 16px;
    height: 3px;
    left: 50%;
    margin-left: -8px;
  }
  /* 二级分类 */
  .skill {
    width: 100%;
    padding: 24px 0 0;
    position: relative;
    margin: 0 auto;
  }
  .skill a.on {
    color: #c80;
    background: rgba(204,136,0,.1);
  }
  .skill a {
    float: left;
    margin-right: 20px;
    padding: 0 12px;
    font-size: 14px;
    color: #4d555d;
    line-height: 32px;
    border-radius: 6px;
    margin-bottom: 12px;
  }
  .skill a:hover {
    background: #d9dde1;
  }
</style>