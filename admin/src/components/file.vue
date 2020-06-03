<template>
  <div>
    <button type="button" v-on:click="selectFile()" >
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
      name: 'file',
      props: {
        text: {
          default: "上传文件"
        },
        //定义一个函数
        afterUpload: {
          type: Function,
          default: null,
        },
        inputId: {
          default: "file-upload"
        },
        suffixs: {
          default: []
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
            return;
          }
          formData.append("file",file);
          // key："file"必须和后端controller参数名一致
          _this.$ajax.post(process.env.VUE_APP_SERVER + "/file/admin/upload",
            formData
          ).then((response) => {
            let resp = response.data;
            console.log("上传文件成功: ",resp);
            //处理返回结果
            _this.afterUpload(resp);//回调函数
            // let image = resp.content;
            // console.log("头像的地址:{}",image);
            // _this.teacher.image = image;
          })
        },

        /**
         * 【上传组件】
         */
        selectFile() {
          $("#file-upload-input").trigger("click");
        }
      }
    }
</script>

