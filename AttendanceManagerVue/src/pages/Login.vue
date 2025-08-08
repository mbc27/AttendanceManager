<template>
  <div class="login">
    <el-card class="login-form-layout">
      <el-row :gutter="16">
        <el-col :span="6" style="text-align: right;">
          <img src="@/assets/01.png" style="
             width: 60px;
             height: 60px;
             margin: 5px 5px 0 0;
             -webkit-user-drag: none;
             -khtml-user-drag: none;
             -moz-user-drag: none;
             user-drag: none;
            " />
        </el-col>
        <el-col :span="17" style="font-size: 32px;text-align: left;margin: 15px 0 0 0;color: black;">

          <b class="login-title">企业考勤管理系统</b>


        </el-col>
      </el-row><br>
      <el-form ref="form" :model="form">
        <el-row :gutter="16">
          <el-col :span="3" style="padding-top: 12px;">
            <i class="iconfont icon-r-user2" style="font-size: 28px;color: grey;"></i>
          </el-col>
          <el-col :span="21"
            style="font-size: 24px;font-weight: 600;text-align: left;margin: 7px 0 0 0;color: rgb(64, 64, 64);">
            <el-input v-model="form.number" clearable placeholder="请输入账号"></el-input>
          </el-col>
        </el-row>
        <el-row :gutter="16">
          <el-col :span="3" style="padding-top: 12px;">
            <i class="iconfont icon-r-lock" style="font-size: 28px;color: grey;"></i>
          </el-col>
          <el-col :span="21"
            style="font-size: 24px;font-weight: 600;text-align: left;margin: 7px 0 0 0;color: rgb(64, 64, 64);">
          <el-input v-model="form.password" show-password placeholder="请输入密码"></el-input>
          </el-col>
        </el-row><br>
        <el-form-item>
          <el-button type="primary" @click="onSubmit"> 登录</el-button>
          <!--<el-button>取消</el-button>-->
        </el-form-item>
        <!-- <el-link type="primary" @click="goTo('/register')">还没有账号？去注册</el-link> -->
      </el-form>
    </el-card>
  </div>
</template>
 
<script>
import axios from 'axios'
export default {
  name: "login",
  data() {
    return {
      form: {
        number: "",
        password: ""
      },
      loading: false,
    };
  },
  methods: {
    onSubmit() {
      //this.$post("/login",this.form)
      //this.$router.push("/list");
      axios.post("/login/login", this.form).then(res => {

        document.cookie = "LOGIN=true; path=/";

        this.tableData = res.data;
        if (res.data.code == 200) {
          this.$message({ message: "登录成功", type: "success" });
          sessionStorage.setItem("username", this.form.number);
          this.$router.push("/home");
        } else if (res.data.code == 20004) {
          this.$message({ message: "账号不存在", type: "error" });
        } else if (res.data.code == 20002) {
          this.$message({ message: "密码错误", type: "error" });
        }
      }).catch((e) => {
        console.log(e);
        if (
          e.response == undefined ||
          e.response.data == undefined
        ) {
          this.$message({
            showClose: true,
            message: e,
            type: "error",
            duration: 20000,
          });
        } else {
          this.$message({
            showClose: true,
            message: e.response.data,
            type: "error",
            duration: 20000,
          });
        }
      })
    },
    goTo(path) {
      this.$router.push(path);
    }
  }
};
</script>
 
<style scoped>
.login-form-layout {
  position: absolute;
  left: 0;
  right: 0;
  width: 440px;
  margin: 140px auto;
  border-top: 10px solid #409eff;
}

.login-title {
  text-align: center;
}

.login-center-layout {
  background: #409eff;
  width: auto;
  height: auto;
  max-width: 100%;
  max-height: 100%;
  margin-top: 200px;
}

.login {
  /* background-image: url('../assets/gongsi.jpeg'); */
  background-color: aqua;
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  overflow-y: auto;
  height: 100%;
  background: url("../assets/04.jpg") center top / cover no-repeat;
}
</style>