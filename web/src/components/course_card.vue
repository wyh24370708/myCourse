<template>
  <div>
    <div class="card mb-4 shadow-sm course">
      <img v-bind:src="course.image" class="img-fluid">
      <div class="card-body">
        <h4 class="">{{course.name}}</h4>
        <p class="card-text">{{course.summary}}</p>
        <div class="d-flex justify-content-between align-items-center">
          <div class="btn-group">
            <button type="button" class="btn btn-sm btn-outline-secondary">详情</button>
          </div>
          <small class="text-muted">
            <span class="badge badge-info"><i class="fa fa-yen"></i>&nbsp;{{course.price}}</span>&nbsp;
            <span class="badge badge-info"><i class="fa fa-user"></i>&nbsp;123</span>&nbsp;
          </small>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
  export default {
    name: "courseCard",
    props:{
      course:{},
    },
    data:function () {
      return{}
    },
  }
</script>
<style>
  /*各个课程card宽高相同*/
  .course{
    width: auto;
    height: 450px ;
  }
  /*文字描述高度相同*/
  .course p{
    height: 100px;
  }

  .course h4 {
    font-size: 1.25rem;
    margin: 15px 0;
  }
  /*价格和人数样式大小*/
  .course .text-muted .badge{
    font-size: 1rem;
  }
</style>