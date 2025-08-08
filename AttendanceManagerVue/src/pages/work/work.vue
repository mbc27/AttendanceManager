<template>
    <div>
    <el-col :span="3" style="height: 800px; border-right: 1px #ccc solid; margin-top: 20px;">
    <el-menu default-active="1" class="el-menu-vertical-demo" @open="handleOpen" @close="handleClose">

      <el-menu-item index="1" @click="goTo('/task')"><span class="navigation">我的任务</span></el-menu-item>
      <el-menu-item index="2" @click="goTo('/fixedAssets')"><span class="navigation">资产申请</span></el-menu-item>
      <el-menu-item index="3" @click="goTo('/leave')"><span class="navigation">请假申请</span></el-menu-item>
      <el-menu-item index="4" @click="goTo('/check')"><span class="navigation">我的考勤</span></el-menu-item>

      <el-menu-item index="6" @click="goTo('/customer')"><span class="navigation">我的客户</span></el-menu-item>
      <el-menu-item index="7" @click="goTo('/meeting')"><span class="navigation">我的会议</span></el-menu-item>
      <el-menu-item index="8" @click="goTo('/notice')"><span class="navigation">我的通知</span></el-menu-item>
      <el-menu-item index="9" @click="goTo('/updateData')"><span class="navigation">修改密码</span></el-menu-item>
    </el-menu>
  </el-col>
  <el-main>
      <router-view/>
  </el-main>
    </div>
</template>

<script>
  import axios from 'axios'
  export default {
    // created() {
    //   this.goTo("/todo");
    // },
    data(){
      return{
        number: sessionStorage.getItem("username"),
        isManage: false,
        type: '',
      }
    },
    created(){
      this.getUsername();
    },
    methods: {
      handleOpen(key, keyPath) {
        console.log(key, keyPath);
      },
      handleClose(key, keyPath) {
        console.log(key, keyPath);
      },
      goTo(path){
          this.$router.push(path);
      },
      getUsername(){
        axios.post('/employee/findByNumber',{
          number: this.number
        }).then(res => {
          this.type = res.data.employeeType;
          this.isManage = true;
        })
      },
    }
  }
</script>
<style scoped>
  .navigation {
    font-size: 18px;
  }
  .smallBar {
    margin-left: 20px;
  }
</style>