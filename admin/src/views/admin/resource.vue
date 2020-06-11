<template>
  <div>
    <!--按钮 Start-->
    <p>
      <button class="btn btn-white btn-default btn-round"
              v-on:click="allResource()">
        <i class="ace-icon fa fa-refresh"></i>
        刷新
      </button>
      &nbsp;
      <button v-on:click="save()"  class="btn btn-default btn-success btn-round" >
        <i class="ace-icon fa fa-floppy-o bigger-120"></i>
        保存
      </button>
    </p>
    <!--按钮 end -->
    <div class="row">
      <div class="col-md-6">
        <textarea id="resource-textarea" class="form-control" v-model="resourceStr" name="resource" rows="15"></textarea>
        <br>
      </div>
      <div class="col-sm-6">
        <div class="widget-box widget-color-green2">
          <div class="widget-header">
            <h4 class="widget-title lighter smaller">
              资源树
            </h4>
          </div>
          <div class="widget-body">
            <div class="widget-main padding-8">
              <ul id="resourceTree" class="ztree"></ul>
            </div>
          </div>
        </div>
      </div>
    </div>
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
  export default {
      name: 'system-resource',
      mounted: function () {//页面加载初始化
          //激活样式方法一
          // this.$parent.activeSidebar("system-resource-sidebar");//后面使用通用方法
          let _this = this;
          _this.allResource();//初始化树形结构
      },
      data: function () {
        return {
            resource: {}, //前台传入的数据
            resources: [],//初始化为空数组, 后台查询到的数据
            resourceStr: "",
            tree: {},//资源树
        }
      },
      methods: {
        /**
         * 【所有资源】
         * @param page
         */
        allResource(){
          let _this = this;
          Loading.show();
          _this.$ajax.post(process.env.VUE_APP_SERVER + '/system/admin/resource/all', {}
          ).then(function (response) {
            Loading.hide();
            let resp = response.data;
            //then异步执行,当.then()前的方法执行完后再执行then()内部的程序，这样就避免了，数据没获取到等的问题。
            console.log("查询资源结果:", resp.content);
            _this.resources = resp.content;//返回真实数据
            _this.initResourceTree();//初始化树形结构
          })
        },

        /**
         * 【资源树】
         */
        initResourceTree(){
          let _this = this;
          let setting = {
            check: {
              enable: true
            },
            data: {
              simpleData: {
                //三行配置数据, 左侧key是固定格式, 具体看zTree API文档
                idKey: "id",
                pIdKey: "parent",
                rootPId: "",
                enable: true
              }
            }
          };
          let zNodes = _this.resources;//树形结构的数据
          _this.tree = $.fn.zTree.init($("#resourceTree"), setting, zNodes);
          _this.tree.expandAll(true);//默认展开
        },

        save() {
            let _this = this;
            if(Tool.isEmpty(_this.resourceStr)){
              Toast.warning("资源不能为空！");
              return;
            }
            let resource_json = JSON.parse(_this.resourceStr);

            _this.$ajax.post(process.env.VUE_APP_SERVER + '/system/admin/resource/save',
              resource_json
            ).then(function (response) {
                //返回的数据
                let resp = response.data;
                //打印日志
                console.log("保存资源的结果:{}",resp.content);
                if (resp.success){//true
                    //加载资源树
                    _this.allResource();
                    //保存成功的提示框
                    Toast.success("保存成功!");
                }else{//校验字段, 字段有问题
                    Toast.warning(resp.message);
                }
            })
        },

        //删除t
        del(id) {
            let _this = this;
            //comfirm组件引入
            Confirm.show("删除资源后不可恢复,确认删除?",function () {
                Loading.show();
                _this.$ajax.delete(process.env.VUE_APP_SERVER + '/system/admin/resource/delete/'+id
                ).then(function (response) {
                    Loading.hide();
                    //返回的数据
                    let resp = response.data;
                    //删除功能的日志
                    console.log("删除资源的结果:{}",resp.content);
                    if (resp.success){
                        //重新查询list
                        _this.list(1);
                        //删除成功的提示框
                        Toast.success("删除成功!");
                    }
                })
            })
        }
      }
  }
</script>