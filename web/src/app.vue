<template>
  <div>
    <my-header></my-header>

    <router-view></router-view>

    <my-footer></my-footer>
  </div>
</template>

<script>
  import MyHeader from "./components/myHeader";
  import MyFooter from "./components/myFooter";
  export default {
    name: 'app',
    components: {MyFooter, MyHeader},
  }
</script>
