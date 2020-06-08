<template>
  <div>
    <button type="button" v-on:click="selectFile()"
            class="btn btn-default btn-purple btn-sm btn-round">
        <i class="ace-icon fa fa-cloud-upload bigger-120"></i>
      {{text}}
    </button>
    <br><br>
    <input type="file"
           class="hidden"
           ref="file"
           v-bind:id="inputId+'-input'"
           v-on:change="upLoadFile()" >
  </div>
</template>

<script>
    export default {
      name: 'bigFile',
      props: {
        //默认按钮的文本
        text: {
          default: "上传大文件"
        },
        //处理请求的结果的函数
        afterUpload: {
          type: Function,
          default: null,
        },
        //按钮的id
        inputId: {
          default: "file-upload"
        },
        //文件的后缀格式
        suffixs: {
          default: []
        },
        //用途
        use:{
          default: ""
        }
      },
      data: function () {
          return {}
      },
      methods: {
        /**
         * 【上传讲师头像】
         */
        upLoadFile() {
          let _this = this;
          let formData = new window.FormData;
          //引用子组件
          let file = _this.$refs.file.files[0];// document.querySelector("#inputImage").files[0]
          // let url = URL.createObjectURL(file);
          // document.getElementById(_this.inputId+"-input").src=url;

          //文件标识key--MD5
          console.log("file: ",file);
          console.log(JSON.stringify(file));
          let key = hex_md5(file.name + file.size + file.type);//可以直接使用这个32位数据
          let key_10 = parseInt(key,16);
          let key_62 = Tool._10to62(key_10);
          console.log(key,key_10,key_62);
          console.log(hex_md5(Array()));
          /*
           *  key = d41d8cd98f00b204e9800998ecf8427e
           *  key_10 = 2.8194976848941264e+38
           *  key_62 = 6sfSqfOwzmik4A4icMYuUe
           */


          // 判断文件格式
          let suffixs = _this.suffixs;//外面传入
          let filename = file.name;
          let fileSuffix = filename.substring(filename.lastIndexOf(".")+1,filename.length).toLowerCase().toString();
          let validator_flag = false;
          for (let i = 0; i < suffixs.length; i++) {
            if (fileSuffix === suffixs[i].toLowerCase()){
              validator_flag = true;
              break;
            }
          }
          if (!validator_flag){
            Toast.warning("文件格式不正确！只支持上传：" + suffixs.join(","));//?
            $("#"+ _this.inputId + "-input").val("");
            return;
          }

          /**
           * 分片上传,数据库的四个字段都是由前端提供,服务端的压力
           * 分片大小,分片初始索引,//设定为默认值
           * 分片的数量,分片的内容
           */
          let shardSize = 10 * 1024 * 1024;  //20M为一个分片
          let shardIndex = 1;                 //分片的索引
          let shardTotal = Math.ceil(file.size / shardSize);//计算分片总数
          //分片参数
          let formParam = {
            "shard": "",
            "use":_this.use,
            "shardSize":shardSize,
            "shardIndex":shardIndex,
            "shardTotal":shardTotal,
            "size":file.size,
            "name":file.name,
            "suffix":fileSuffix,
            "key":key_62,
          }
          _this.shardCheck(formParam);//先分片检查,再上传分片 //递归上传分片
        },

        //分片检查,,,上到第几个分片
        shardCheck(formParam){
          let _this = this;
          _this.$ajax.get(process.env.VUE_APP_SERVER + "/file/admin/shardCheck/"+
            formParam.key
          ).then((response)=>{
            let resp = response.data;
            if (resp.success){
              let content = resp.content;
              if (!content){
                console.log("检查到没有分片,从第一个分片开始上传...");
                formParam.shardIndex = 1;
                _this.uploadBigfile(formParam);
              } else if(content.shardIndex == content.shardTotal){
                //已上传分片 = 分片总数，说明已全部上传完，不需要再上传
                Toast.success("文件极速秒传");
                _this.afterUpload(resp);
                $("#" + _this.inputId + "-input").val("");//清除上次上传文件的值
              } else{//检查到有分片, 继续下一个分片的上传
                formParam.shardIndex = content.shardIndex + 1 ;
                console.log("找到文件记录，从分片" + formParam.shardIndex + "开始上传...");
                _this.uploadBigfile(formParam);
              }
            }else{
              Toast.warning("文件上传失败");
              $("#" + _this.inputId + "-input").val("");//清除上次上传文件的值
            }
          })
        },

        //上传文件
        uploadBigfile: function (formParam) {
          let _this = this;
          let shardIndex = formParam.shardIndex;
          let shardTotal = formParam.shardTotal;
          let shardSize = formParam.shardSize;
          let fileShard = _this.getFileShard(shardIndex, shardSize);
          Progress.show(parseInt((shardIndex-1)*100 / shardTotal));
          // 将图片转为base64进行传输
          let fileReader = new FileReader();
          //事件监听
          fileReader.onload = function (event) {
            let base64 = event.target.result;
            // console.log("base64:",base64);
            formParam.shard = base64;

            _this.$ajax.post(process.env.VUE_APP_SERVER + "/file/admin/oss-append",
              formParam
            ).then((response) => {
              let resp = response.data;
              Progress.show(parseInt(shardIndex * 100 / shardTotal));
              //递归调用
              if (shardIndex < shardTotal){
                formParam.shardIndex = formParam.shardIndex + 1;
                // 分片断点续传的测试
                // if (formParam.shardIndex === 3){
                //   return;
                // }
                _this.uploadBigfile(formParam);
              }else{
                Progress.hide();
                console.log("上传大文件成功: ", resp);
                Toast.success("文件上传成功!");
                _this.afterUpload(resp);//回调函数 //处理返回结果
                $("#" + _this.inputId + "-input").val("");//清除上次上传文件的值
              }
            })
          };
          fileReader.readAsDataURL(fileShard);
        },

        getFileShard: function (shardIndex, shardSize) {
          let _this = this;
          let file = _this.$refs.file.files[0];
          let start = (shardIndex - 1) * shardSize; //分片起始位置
          let end = Math.min(file.size, start + shardSize); //当前分片的结束位置
          let fileShard = file.slice(start, end); //文件的分片内容
          console.log("start,end:", start, end);
          return fileShard;
        },

        /**
         * 【上传组件】
         */
        selectFile() {
          let _this = this;
          $("#"+ _this.inputId + "-input").trigger("click");
        }
      }
    }
</script>

