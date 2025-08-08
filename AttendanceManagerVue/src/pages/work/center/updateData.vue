<template>
    <el-form :model="ruleForm" status-icon :rules="rules" ref="ruleForm" label-width="125px" class="demo-ruleForm" style="margin-top:50px;width:40%;">
  <el-form-item label="新密码" prop="password">
    <el-input type="password" v-model="ruleForm.password" autocomplete="off" placeholder="请输入新密码"></el-input>
  </el-form-item>
  <el-form-item label="确认密码" prop="checkPass">
    <el-input type="password" v-model="ruleForm.checkPass" autocomplete="off" placeholder="请再输入一次"></el-input>
  </el-form-item>
  <el-form-item>
    <el-button type="primary" @click="submitForm('ruleForm')"> 提交</el-button>
    <el-button type="warning" @click="resetForm('ruleForm')"> 重置</el-button>
  </el-form-item>
</el-form>
</template>

<script>
  import axios from 'axios'
  export default {
    data() {
      var validatePass = (rule, value, callback) => {
        if (value === '') {
          callback(new Error('请输入密码'));
        } else {
          if (this.ruleForm.checkPass !== '') {
            this.$refs.ruleForm.validateField('checkPass');
          }
          callback();
        }
      };
      var validatePass2 = (rule, value, callback) => {
        if (value === '') {
          callback(new Error('请再次输入密码'));
        } else if (value !== this.ruleForm.password) {
          callback(new Error('两次输入密码不一致!'));
        } else {
          callback();
        }
      };
      return {
        ruleForm: {
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
        rules: {
          pass: [
            { validator: validatePass, trigger: 'blur' }
          ],
          checkPass: [
            { validator: validatePass2, trigger: 'blur' }
          ],
        }
      };
    },
    methods: {
      submitForm(formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            axios.post('/employee/updatePassword',this.ruleForm).then(res => {
            this.$message({ message: "修改成功", type: "success" });
            this.$refs[formName].resetFields();
            })
          } else {
            //console.log('error submit!!');
            return false;
          }
        });
      },
      resetForm(formName) {
        this.$refs[formName].resetFields();
      }
    }
  }
</script>

<style>

</style>
