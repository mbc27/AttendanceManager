<template>
  <div>
    <el-card class="login-form-layout">
        <h2 class="login-title">请登录</h2>
      <el-form ref="form" :model="form">
          <el-form-item label="账号">
            <el-input v-model="form.username" clearable></el-input>
          </el-form-item>
          <el-form-item label="密码">
            <el-input v-model="form.password" show-password></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="onSubmit">登录</el-button>
            <!--<el-button>取消</el-button>-->
          </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>
 
<script>
export default {
  name: "login",
  data() {
    return {
      form: {
        username: "admin",
        password: "123456"
      },
      loading: false,
    };
  },
  methods: {
    onSubmit(){
        //this.$post("/login",this.form)
        this.$router.push("/list");
    }
  }
};
</script>
 
<style scoped>
.login-form-layout {
  position: absolute;
  left: 0;
  right: 0;
  width: 360px;
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
</style>