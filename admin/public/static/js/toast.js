//sweetalert2的toast，复制规定的代码
const Toast = Swal.mixin({
    toast: true,
    position: 'top-end',
    showConfirmButton: false,
    timer: 3000,
    timerProgressBar: true,
    onOpen: (toast) => {
        toast.addEventListener('mouseenter', Swal.stopTimer)
        toast.addEventListener('mouseleave', Swal.resumeTimer)
    }
})
//这个是全局的变量,其他的js可以引用
toast = {
    success: function (message) {
        Toast.fire({
            icon: 'success',
            title: message
        });
    },
    error: function (message) {
        Toast.fire({
            icon: 'error',
            title: message
        });
    },
    warning: function (message) {
        Toast.fire({
            icon: 'warning',
            title: message
        });
    }
}