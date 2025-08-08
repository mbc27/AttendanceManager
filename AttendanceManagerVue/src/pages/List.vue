<template>
  <div id="app" class="flex-c">
    <div style="margin-left: 20px; display: flex; justify-content: space-between;">
      <div style="text-align: left;">
        <el-row :gutter="16">
          <el-col :span="4" style="text-align: right;">
            <img src="@/assets/01.png" style="
              width: 60px;
              height: 60px;
              margin: 0 5px 0 0;
              -webkit-user-drag: none;
              -khtml-user-drag: none;
              -moz-user-drag: none;
              user-drag: none;
            " />
          </el-col>
          <el-col :span="20" style="font-size: 36px;font-weight: 600;text-align: left;margin: 7px 0 0 0;color: black;">
            <b>企业考勤管理系统</b>

          </el-col>
        </el-row>
      </div>
      <!-- <div style="font-size: 20px; margin-top: 30px; margin-right: 15px;" @click="goTo('/')">
        <i class="el-icon-switch-button"></i>退出
      </div> -->
    </div>
    <div class="header flex-s">
      <el-menu :default-active="activeIndex" class="el-menu-demo" mode="horizontal" @select="handleSelect">
        <el-menu-item index="1" @click="goTo('/home')"><i class="iconfont icon-r-home"  style="font-size: 28px;"></i><span class="navigation">
            首页</span></el-menu-item>
        <el-menu-item index="2" @click="goTo('/task')"><i class="iconfont icon-r-building"  style="font-size: 28px;"></i><span class="navigation">
            工作台</span></el-menu-item>
       
        <el-menu-item index="3" @click="goTo('/admin')" v-if="isAdmin">
          <i class="iconfont icon-r-setting" style="font-size: 28px;"></i><span
            class="navigation"> 管理员模块</span></el-menu-item>
        <el-menu-item index="4" @click="goTo('/')" style="float: right;"><i class="iconfont icon-r-no"  style="font-size: 22px;"></i><span
            class="navigation"> 退出</span></el-menu-item>
        
      </el-menu>
    </div>
    
    <el-main>
      <router-view />
    </el-main>
  </div>
</template>

<script>
import axios from 'axios'
export default {
  data() {
    return {
      activeIndex: '1',
      username: '',
      type: '',
      form: {
        id: '',
        number: sessionStorage.getItem("username"),
        name: '',
        sex: '',
        birthday: '',
        departmentID: '',
        position: '',
        type: '',
        entryDate: '',
        phone: '',
        address: '',
        password: '',
        checkPass: '',
      },
      isAdmin: false,
    };
  },
  created() {
    this.getUsername();
    // axios.get("http://wthrcdn.etouch.cn/weather_mini?city=长沙")
    // .then(function(response) {
    //   console.log(response.data.data);
    // })
    // .catch(function(error) {
    //   console.log(error);
    // });
  },
  methods: {
    handleSelect(key, keyPath) {
      console.log(key, keyPath);
    },
    goTo(path) {
      this.$router.push(path);
    },
    getUsername() {
      axios.post('/employee/findByNumber', this.form).then(res => {
        this.username = res.data.name;
        this.type = res.data.type;
        if (this.type == 3) {
          this.isAdmin = true
        } else {
          this.isAdmin = false
        }
      })
    },
  }
}
</script>
<style scoped>
html {
  height: 100%;
}

body {
  height: 100%;
  margin: 0;
}

#app {
  height: 100%;
  text-align: center;
}

.flex,
.flex-c {
  display: flex;
}

.flex-c {
  flex-direction: column;
}

.flex-a {
  flex: auto;
}

.flex-s {
  flex-shrink: 0;
  border-top: 1px #ccc solid;
}

.navigation {
  font-size: 18px;
}
</style>
