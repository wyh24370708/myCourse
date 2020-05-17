<template>
  <div>
    <h1>wangyonghao, 欢迎登陆控台页面...</h1>
  </div>
</template>

<script>
  export default {
      name: 'welcome',
      mounted: function () {
        this.$parent.activeSidebar("welcome-sidebar");
      },
      methods: {

      }

  }
</script>

<style>

</style>