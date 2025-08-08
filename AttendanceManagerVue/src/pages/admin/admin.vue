<template>
  <div>
    <el-col :span="3" style="height: 800px; border-right: 1px #ccc solid; margin-top: 20px;">
    <el-menu default-active="1" class="el-menu-vertical-demo" @open="handleOpen" @close="handleClose">
      <el-menu-item index="1" @click="goTo('/employee')"><span class="navigation">员工管理</span></el-menu-item>
      <el-menu-item index="2" @click="goTo('/department')"><span class="navigation">部门管理</span></el-menu-item>
      <el-menu-item index="3" @click="goTo('/finance')"><span class="navigation">财务管理</span></el-menu-item>
      <el-menu-item index="4" @click="goTo('/statistics')"><span class="navigation">统计模块</span></el-menu-item>
      <!-- <el-menu-item index="5" @click="goTo('/customer')"><span class="navigation">客户管理</span></el-menu-item> -->
      <!-- <el-submenu index="5">
        <template slot="title"><span class="navigation">会议通知模块</span></template>
          <el-menu-item index="5-1" @click="goTo('/meeting')"><span class="smallBar">查看会议记录</span></el-menu-item>
          <el-menu-item index="5-2" @click="goTo('/notice')"><span class="smallBar">查看通知</span></el-menu-item>
      </el-submenu> -->
    </el-menu>
    </el-col>
    <el-main>
      <router-view/>
    </el-main>
  </div>
</template>

<script>
  export default {
    created() {
      this.goTo("/employee");
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