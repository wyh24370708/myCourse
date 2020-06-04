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
          let key = hex_md5(file);
          let key_10 = parseInt(key,16);
          let key_62 = Tool._10to62(key_10);
          console.log(key,key_10,key_62);
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
           */
          let shardSize = 20 * 1024 * 1024;   //20M为一个分片
          let shardIndex = 0;                 //分片的索引
          let start = shardIndex * shardSize; //分片起始位置
          let end = Math.min(file.size, start + shardSize); //当前分片的结束位置
          let fileShard = file.slice(start,end); //文件的分片内容
          console.log("start,end:", start,end);
          let shardTotal = Math.ceil(file.size / shardSize);//计算分片总数

          // key："file"必须和后端controller参数名一致
          formData.append("shard",fileShard);
          formData.append("use",_this.use);
          formData.append("shardSize",shardSize);
          formData.append("shardIndex",shardIndex);
          formData.append("shardTotal",shardTotal);
          formData.append("size",file.size);
          formData.append("name",file.name);
          formData.append("suffix",fileSuffix);
          formData.append("key",key_62);
          _this.$ajax.post(process.env.VUE_APP_SERVER + "/file/admin/uploadBigFile",
            formData
          ).then((response) => {
            let resp = response.data;
            console.log("上传大文件成功: ",resp);
            //处理返回结果
            _this.afterUpload(resp);//回调函数
            // let image = resp.content;
            // console.log("头像的地址:{}",image);
            // _this.teacher.image = image;
            //清楚上次上传文件的值
            $("#"+ _this.inputId + "-input").val("");
          })
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

