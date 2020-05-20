/*使用sweetalert2弹窗插件
*
* 和组件无关的代码,也就是变化的代码使用,回调函数来做
*/
Confirm = {
    show: function (message,callback) {
        Swal.fire({
            title: '确定删除吗?',
            text: message,
            icon: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            confirmButtonText: '确认'
        }).then((result) => {//点击确定还是取消的结果
            if (result.value) {//true
                if (callback){
                    callback();
                }
            }
        })
    }
}