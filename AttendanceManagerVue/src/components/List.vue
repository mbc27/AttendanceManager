<template>
    <div>
        <el-menu :default-active="activeIndex" class="el-menu-demo" mode="horizontal" @select="handleSelect">
            <el-menu-item index="1" @click="goTo('/list')">首页</el-menu-item>
            <el-submenu index="2">
                <template slot="title">工作台</template>
                    <el-submenu index="2-1">
                        <template slot="title">我的任务</template>
                            <el-menu-item index="2-1-1">待办任务</el-menu-item>
                            <el-menu-item index="2-1-2">已办任务</el-menu-item>
                    </el-submenu>
                    <el-submenu index="2-2">
                        <template slot="title">我的申请</template>
                            <el-menu-item index="2-2-1">请假申请</el-menu-item>
                            <el-submenu index="2-2-2">
                                <template slot="title">固定资产申请</template>
                                    <el-menu-item index="2-2-2-1">购置申请</el-menu-item>
                                    <el-menu-item index="2-2-2-2">报废申请</el-menu-item>
                            </el-submenu>
                    </el-submenu>
                    <el-submenu index="2-4">
                        <template slot="title">我的管理</template>
                            <el-menu-item index="2-4-1">添加客户</el-menu-item>
                            <el-menu-item index="2-4-2">发布会议通知</el-menu-item>
                    </el-submenu>
            </el-submenu>
            <el-menu-item index="3">消息中心</el-menu-item>
            <el-submenu index="4">
                <template slot="title">管理员</template>
                    <el-menu-item index="4-1" @click="goTo('/employee')">员工管理</el-menu-item>
                    <el-menu-item index="4-2" @click="goTo('department')">部门管理</el-menu-item>
                    <el-menu-item index="4-3">财务管理</el-menu-item>
                    <el-menu-item index="4-4">统计模块</el-menu-item>
                    <el-menu-item index="4-5">客户管理</el-menu-item>
                    <el-menu-item index="4-6">会议通知模块</el-menu-item>
            </el-submenu>
        </el-menu>
        <router-view/>
    </div>
</template>

<script>
  export default {
    data() {
      return {
        activeIndex: '1'
      };
    },
    methods: {
      handleSelect(key, keyPath) {
        console.log(key, keyPath);
      },
      goTo(path){
          this.$router.push(path);
      }
    }
  }
</script>