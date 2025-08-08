<template>
  <div>
    <div style="text-align: left; margin-left: 20px;">
      <el-button class="filter-item" style="margin-left: 10px;" type="primary" @click="handleCreate()"> 发起申请</el-button>
    </div>
    <div style="display: flex; flex-direction: row; flex-wrap: wrap;">
      <el-card class="box-card" v-for="item in tableData" :key="item.id" style="width: 450px; margin: 20px;">
        <template #header>
          <div class="card-header">
            <span style="color: green; float: left;" v-if="item.isPass">{{ item.statusName }}</span>
            <span style="color: gray; float: left;" v-else-if="item.isNotApproval">{{ item.statusName }}</span>
            <span style="color: red; float: left;" v-else>{{ item.statusName }}</span>
            <span>{{ ruleForm.employeeName }}提交的固定资产申请</span>
            <el-button class="button" type="text" style="float: right; padding: 3px 0"
              @click="handleUpdate(item)">查看详情</el-button>
          </div>
        </template>
        <div class="text item">
          {{ '类型：' + item.typeName }}
        </div>
        <div class="text item">
          {{ '申请时间：' + item.applyTime }}
        </div>
      </el-card>
    </div>
    <el-dialog :title="dialogTitle" :visible.sync="dialogFormVisible">
      <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm"
        style="width:100%;" v-if="isshowForm">
        <el-form-item label="申请人" prop="employeeName">
          <el-input v-model="ruleForm.employeeName" readonly></el-input>
        </el-form-item>
        <el-form-item label="申请时间" prop="applyTime" v-if="isUpdate">
          <el-input v-model="ruleForm.applyTime" readonly></el-input>
        </el-form-item>
        <el-form-item label="编号" prop="number">
          <el-input v-model="ruleForm.number" placeholder="请输入固定资产编号" :readonly="isUpdate"></el-input>
        </el-form-item>
        <el-form-item label="名称" prop="name">
          <el-input v-model="ruleForm.name" placeholder="请输入固定资产名称" :readonly="isUpdate"></el-input>
        </el-form-item>
        <el-form-item label="价格" prop="price">
          <el-input v-model.number="ruleForm.price" placeholder="请输入固定资产价格" :readonly="isUpdate"></el-input>
        </el-form-item>
        <el-form-item label="类别" prop="typeID">
          <el-select v-model="ruleForm.typeID" placeholder="请选择固定资产类别" :disabled="isUpdate" style="width: 100%;">
            <el-option v-for="item in typeList" :key="item.id" :label="item.name" :value="item.id"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="审批人" prop="approvalName" v-if="isApproval">
          <el-input v-model="ruleForm.approvalName" readonly></el-input>
        </el-form-item>
        <el-form-item label="审批时间" prop="approvalTime" v-if="isApproval">
          <el-input v-model="ruleForm.approvalTime" readonly></el-input>
        </el-form-item>
        <el-form-item label="审批状态" prop="course" v-if="isUpdate">
          <el-input v-model="ruleForm.statusName" :readonly="isUpdate"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button @click="dialogFormVisible = false"> 取 消</el-button>
          <el-button type="primary" @click="submitForm('ruleForm')" v-if="isCreate"> 立即申请</el-button>
          <el-button type="warning" @click="cancelAsset" v-if="isUpdate && ruleForm.status === '0'"> 撤回申请</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>
  </div>
</template>

<script>
import axios from 'axios'

export default {
  data() {
    return {
      tableData: [],
      ruleForm: {
        employeeNumber: sessionStorage.getItem("username"),
        employeeName: '',
        number: '',
        name: '',
        typeID: '',
        price: '',
        applyTime: this.getTime(),
        taskTypeID: '2',
        status: '',
        approvalID: '',
      },
      rules: {
        number: [
          { required: true, message: '请输入固定资产编号', trigger: 'blur' },
          { min: 3, max: 15, message: '长度在 3 到 15 个字符', trigger: 'blur' }
        ],
        name: [
          { required: true, message: '请输入固定资产名称', trigger: 'blur' },
          { min: 3, max: 15, message: '长度在 3 到 15 个字符', trigger: 'blur' }
        ],
        typeID: [
          { required: true, message: '请选择固定资产类别', trigger: 'change' }
        ],
        price: [
          { required: true, message: '价格不能为空' },
          { type: 'number', message: '价格必须为数字值' }
        ],
      },
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
      isshowForm: true,
      isshowResult: false,
      dialogFormVisible: false,
      dialogTitle: '',
      isPass: false,
      isNotApproval: false,
      isCreate: false,
      isUpdate: false,
      isApproval: false,
      employeeName: '',
      employeeNumber: '',
      typeList: [],
    };
  },
  created() {
    this.getData();
    this.getUsername();
  },
  methods: {
    getData() {
      axios.post('/fixedasset/findByEmployeeNumber', {
        employeeNumber: sessionStorage.getItem("username"),
      }).then(res => {
        this.tableData = res.data;
        this.tableData.forEach(item => {
          if (item.status == '0') {
            item.isNotApproval = true
          } else if (item.status == '1') {
            item.isPass = true
          }
        })
      })
    },
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          const loading = this.$loading({
            lock: true,
            text: '提交申请中...',
            spinner: 'el-icon-loading',
            background: 'rgba(0, 0, 0, 0.7)'
          });
          axios.post('/fixedasset/insert', this.ruleForm).then(res => {
            loading.close();
            if (res.data.code == 200) {
              this.dialogFormVisible = false;
              this.$message({ message: "申请成功", type: "success" });
              this.$refs[formName].resetFields();
              this.ruleForm.employeeName = this.employeeName;
              this.ruleForm.applyTime = this.getTime();
              this.getData();
            } else if (res.data.code == 20005) {
              this.$message({ message: "该编号已存在", type: "error" });
            } else {
              this.$message({ message: "申请提交失败，请联系管理员", type: "error" });
            }
          }).catch(error => {
            loading.close();
            this.$message({ message: "申请提交失败，系统错误", type: "error" });
            console.error(error);
          });
        } else {
          //console.log('error submit!!');
          return false;
        }
      });
    },
    resetForm() {
      this.$refs['ruleForm'].resetFields();
      this.ruleForm.employeeName = this.employeeName;
      this.ruleForm.applyTime = this.getTime();
      this.employeeNumber = sessionStorage.getItem("username");
      this.taskTypeID = '2';
    },
    getUsername() {
      axios.post('/employee/findByNumber', this.form).then(res => {
        this.employeeName = res.data.name;
        this.employeeNumber = res.data.number;
        this.ruleForm.employeeName = res.data.name;
        this.ruleForm.employeeNumber = res.data.number;
      })
    },
    getTime() {
      var date = new Date();
      var seperator1 = "-";
      var seperator2 = ":";
      //以下代码依次是获取当前时间的年月日时分秒
      var year = date.getFullYear();
      var month = date.getMonth() + 1;
      var strDate = date.getDate();
      var minute = date.getMinutes();
      var hour = date.getHours();
      var second = date.getSeconds();
      //固定时间格式
      if (month >= 1 && month <= 9) {
        month = "0" + month;
      }
      if (strDate >= 0 && strDate <= 9) {
        strDate = "0" + strDate;
      }
      if (hour >= 0 && hour <= 9) {
        hour = "0" + hour;
      }
      if (minute >= 0 && minute <= 9) {
        minute = "0" + minute;
      }
      if (second >= 0 && second <= 9) {
        second = "0" + second;
      }
      var currentdate = year + seperator1 + month + seperator1 + strDate
        + " " + hour + seperator2 + minute + seperator2 + second;
      return currentdate;
    },
    handleCreate() {
      //this.resetForm(this.ruleForm)
      //this.getTypeList()
      //this.resetTemp()
      this.getTypeList()
      this.dialogTitle = "发送申请"
      this.isCreate = true
      this.isUpdate = false
      this.isApproval = false
      this.dialogFormVisible = true
      this.resetForm()
    },
    handleUpdate(row) {
      
      this.ruleForm = Object.assign({}, row) // copy obj
      this.ruleForm.employeeName = this.employeeName;
      if (this.ruleForm.status == '0') {
        this.isApproval = false
      } else if (this.ruleForm.status == '1' || this.ruleForm.status == '2') {
        this.isApproval = true
      }
      this.dialogTitle = "申请详情"
      this.isUpdate = true
      this.isCreate = false
      this.dialogFormVisible = true
    },
    cancelAsset() {
      this.$confirm('确认撤回该申请吗?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        const loading = this.$loading({
          lock: true,
          text: '撤回申请中...',
          spinner: 'el-icon-loading',
          background: 'rgba(0, 0, 0, 0.7)'
        });
        
        axios.post('/fixedasset/cancel', {
          id: this.ruleForm.id
        }).then(res => {
          loading.close();
          if (res.data.code === 200) {
            this.dialogFormVisible = false;
            this.$message({ message: "申请已撤回", type: "success" });
            this.getData(); // 刷新列表
          } else {
            this.$message({ message: "撤回失败，申请可能已被处理", type: "error" });
          }
        }).catch(error => {
          loading.close();
          this.$message({ message: "系统错误", type: "error" });
          console.error(error);
        });
      }).catch(() => {
        this.$message({ message: "已取消操作", type: "info" });
      });
    },
    getTypeList() {
      axios.get('/fixedassetType/list').then(res => {
        this.typeList = res.data;
      })
    }
  }
}
</script>
