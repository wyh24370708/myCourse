<template>
  <div id="app">
    <div class="main-container">
      <div class="main-content">
        <div class="row">
          <div class="col-sm-10 col-sm-offset-1">
            <div class="login-container">
              <!-- 中心标题 -->
              <div class="center">
                <h1>
                  <i class="ace-icon fa fa-leaf green"></i>
                  <span class="red">Ace </span>
                  <span class="white" id="id-text2">在线视频课程系统</span>
                </h1>
                <!--                <h4 class="blue" id="id-company-text">&copy; Company Name</h4>-->
              </div>

              <div class="space-6"></div>

              <div class="position-relative">
                <div id="login-box" class="login-box visible widget-box no-border">
                  <div class="widget-body">
                    <div class="widget-main">
                      <h4 class="header blue lighter bigger">
                        <i class="ace-icon fa fa-coffee green"></i>
                        Please Enter Your Information
                      </h4>

                      <div class="space-6"></div>

                      <form>
                          <fieldset>
                            <label class="block clearfix">
														<span class="block input-icon input-icon-right">
															<input type="text" class="form-control" placeholder="用户名"
                                      v-model="user.loginName"/>
															<i class="ace-icon fa fa-user"></i>
														</span>
                            </label>

                          <label class="block clearfix">
														<span class="block input-icon input-icon-right">
															<input type="password" class="form-control" placeholder="密码"
                                     v-model="user.password"/>
															<i class="ace-icon fa fa-lock"></i>
														</span>
                          </label>
                            <!-- 图形验证码 -->
                            <label class="block clearfix">
                              <span class="block input-icon input-icon-right">
                               <div class="input-group">
                                 <input type="text" class="form-control" placeholder="验证码"
                                      v-model="user.imageCode">
                                 <span class="input-group-addon" id="basic-addon2">
                                   <img v-on:click="loadImageCode()" id="image-code" alt="验证码"/>
                                 </span>
                               </div>
                              </span>
                            </label>


                          <div class="space"></div>

                          <div class="clearfix">
                            <label class="inline">
                              <input type="checkbox" class="ace" v-model="remember"/>
                              <span class="lbl"> Remember Me</span>
                            </label>

                            <button type="button"
                                    class="width-35 pull-right btn btn-sm btn-primary"
                                    v-on:click="login()">
                              <i class="ace-icon fa fa-key"></i>
                              <span class="bigger-110">Login</span>
                            </button>
                          </div>
                          <div class="space-4"></div>
                        </fieldset>
                      </form>
                    </div><!-- /.widget-main -->
                  </div><!-- /.widget-body -->
                </div><!-- /.login-box -->
            </div>
            </div>
          </div><!-- /.col -->
        </div><!-- /.row -->
      </div><!-- /.main-content -->
    </div><!-- /.main-container -->
  </div>
</template>
<script>
  export default {
    name: 'login',
    data:function(){
      return{
        user:{},
        remember: true,
        imageCodeToken: '',
      }
    },
    mounted: function () {//mounted初始化样式
      let _this = this;
      $("body").removeClass('no-skin');//删除登陆页面的body样式
      $("body").attr('class', 'login-layout blur-login'); //设置admin的body页面的的样式

      //页面初始化,回显记住的登陆用户信息
      let rememberUser = LocalStorage.get(LOCAL_KEY_REMEMBER_USER);
      if (rememberUser){
        _this.user = rememberUser;
      }

      //页面初始化,加载加载验证码
      _this.loadImageCode();
    },
    methods: {
      login() {
        let _this = this;
        // 将明文存储到缓存中
        // let InputPassword = _this.user.password;

        // 先判断密码是否缓存带出来的| 是:不加密  否:加密
        let md5_pwd = hex_md5(_this.user.password);
        let rememberUser = LocalStorage.get(LOCAL_KEY_REMEMBER_USER) || {};
        if (rememberUser.md5 !== md5_pwd){//缓存没有,说明没有被加密过
          //md5密码加密
          _this.user.password = hex_md5(_this.user.password + KEY);
        }

        _this.user.imageCodeToken = _this.imageCodeToken;

        _this.$ajax.post(process.env.VUE_APP_SERVER + '/system/admin/user/login',
          _this.user
        ).then(function (response) {
          let resp = response.data;
          if (resp.success){//true
            let loginUser = resp.content;
            console.log("登陆成功:{}",loginUser);
            Tool.setLoginUser(loginUser)//保存用户登陆信息
            //判断"记住我"
            if (_this.remember){
              let md5= hex_md5(_this.user.password);
              // 如果勾选记住我，则将用户名密码保存到本地缓存，这里需要保存密码明文，否则登录时又会再加一层密
              LocalStorage.set(LOCAL_KEY_REMEMBER_USER,{
                loginName: loginUser.loginName,
                password: _this.user.password,
                md5: md5,
              })
            }else{
              // 没有勾选“记住我”时，要把本地缓存清空，否则按照mounted的逻辑，下次打开时会自动显示用户名密码
              LocalStorage.set(LOCAL_KEY_REMEMBER_USER,null);
            }
            _this.$router.push("/welcome");
          }else{//校验字段, 字段有问题
            Toast.warning(resp.message);
            _this.user.password = "";
            _this.loadImageCode();
          }
        })
      },

      /**
       * 【加载图形验证码】
       */
      loadImageCode(){
        let _this = this;
        _this.imageCodeToken = Tool.uuid(8);
        $("#image-code").attr("src",process.env.VUE_APP_SERVER + "/system/admin/imageCode/checkCode/" + _this.imageCodeToken);
      }
    }
  }
</script>

<style scoped>
  .input-group-addon{
    padding: 0px;
  }
</style>
