<template>
  <div style="margin-left: -20px;">
    <div style="margin: 0 0 -12px 40px;">
      <el-tabs v-model="activeName">
        <el-tab-pane label="固定资产" name="fixed">
          <div style="margin-left: 20px;">
            <div style="text-align: right;">
              <el-button class="filter-item" type="primary" @click="createFixed()"> 购置固定资产</el-button>
            </div>
            <div style="margin-top: 10px;">
              <el-table :data="fixedList" border fit highlight-current-row style="width: 100%">
                <el-table-column prop="number" label="固定资产编号"></el-table-column>
                <el-table-column prop="name" label="名称"></el-table-column>
                <el-table-column prop="typeName" label="类别"></el-table-column>
                <el-table-column prop="price" label="价格"></el-table-column>
                <el-table-column prop="employeeName" label="申请人"></el-table-column>
                <el-table-column prop="statusName" label="状态">
                  <template slot-scope="scope">
                    <span style="color: green;" v-if="scope.row.status === '1'">{{ scope.row.statusName }}</span>
                    <span style="color: gray;" v-else-if="scope.row.status === '0'">{{ scope.row.statusName }}</span>
                    <span style="color: red;" v-else>{{ scope.row.statusName }}</span>
                  </template>
                </el-table-column>
                <el-table-column prop="applyTime" label="申请时间"></el-table-column>
                <el-table-column label="操作" width="360" fixed="right">
                  <template slot-scope="scope1">
                    <el-button type="success" @click="updateFixed(scope1.row)"> 查看详情</el-button>
                    <el-button type="danger" @click="deleteFixed(scope1.row)"> 报废处理</el-button>
                  </template>
                </el-table-column>
              </el-table>
            </div>
          </div>
        </el-tab-pane>
        <el-tab-pane label="工资发放" name="check">
          <div style="margin-left: 15px; margin-top: 20px;">
            <div>
              <div class="block">
                <span class="demonstration">选择月份</span>
                <el-date-picker v-model="checkMonth" type="month" placeholder="选择月"
                  value-format="yyyy-MM"></el-date-picker>

              </div><br>
              <div class="block">
                <el-button type="primary" @click="getCheckList()"> 查询</el-button>
                <el-button type="primary" v-print="'#check'"> 打印报表</el-button>
                <el-button type="primary" @click="exportCheck()"> 导出报表</el-button>
              </div>
            </div>
            <div style="margin-top: 10px;" id="check">
              <el-table :data="checkList" border fit highlight-current-row style="width: 100%">
                <el-table-column prop="employeeID" label="员工编号"></el-table-column>
                <!-- <el-table-column prop="employeeName" label="员工姓名"></el-table-column> -->
                <el-table-column prop="workDays" label="应出勤（天）"></el-table-column>
                <el-table-column prop="checkDays" label="实际出勤（天）"></el-table-column>
                <el-table-column prop="leaveDays" label="请假（天）"></el-table-column>
                <el-table-column prop="lateDays" label="迟到（次）"></el-table-column>
                <el-table-column prop="leaveEarlyDays" label="早退（次）"></el-table-column>
                <el-table-column prop="leaveDays" label="缺勤（次）"></el-table-column>
                <el-table-column label="操作" width="260" fixed="right">
                  <template slot-scope="scope2">
                    <el-button type="success" @click="payOff(scope2.row)"> 发放工资条</el-button>
                  </template>
                </el-table-column>
              </el-table>
            </div>
          </div>
        </el-tab-pane>
      </el-tabs>
    </div>
    <el-dialog :title="dialogTitle" :visible.sync="dialogFormVisible">
      <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm"
        style="width:100%;">
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
            <el-option v-for="item in fixedTypeList" :key="item.id" :label="item.name" :value="item.id"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="审批人" prop="approvalName" v-if="isApproval">
          <el-input v-model="ruleForm.employeeName" readonly></el-input>
        </el-form-item>
        <el-form-item label="审批时间" prop="approvalTime" v-if="isApproval">
          <el-input v-model="ruleForm.approvalTime" readonly></el-input>
        </el-form-item>
        <el-form-item label="审批状态" prop="course" v-if="isUpdate">
          <el-input v-model="ruleForm.statusName" :readonly="isUpdate"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button @click="dialogFormVisible = false"> 取 消</el-button>
          <el-button type="primary" @click="insertFixed('ruleForm')" v-if="isCreate"> 立即购置</el-button>
          <el-button type="success" @click="approveAsset('yes')" v-if="isUpdate && ruleForm.status === '0'"> 同 意</el-button>
          <el-button type="danger" @click="approveAsset('no')" v-if="isUpdate && ruleForm.status === '0'"> 驳 回</el-button>
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
      isFixed: false,
      fixedList: [],
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
      dialogTitle: "",
      dialogFormVisible: false,
      fixedTypeList: [],
      isCreate: false,
      isUpdate: false,
      employeeName: '',
      employeeNumber: '',
      isApproval: false,
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
      checkList: [],
      checkMonth: new Date(),
      activeName: 'fixed',
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
    }
  },
  created() {
    this.getUsername();
    this.getFixedList();
    this.getCheckList();
    this.getTypeList();
  },
  methods: {
    getFixedList() {
      axios.get("/fixedasset/list").then(res => {
        this.fixedList = res.data;
      })
    },
    resetForm() {
      this.$refs['ruleForm'].resetFields();
      this.ruleForm.employeeName = this.employeeName;
      this.ruleForm.applyTime = this.getTime();
      this.employeeNumber = sessionStorage.getItem("username");
      this.taskTypeID = '2';
    },
    createFixed() {
      //this.resetForm(this.ruleForm)
      //this.getTypeList()
      //this.resetTemp()
      this.dialogTitle = "购置固定资产"
      this.isCreate = true
      this.isUpdate = false
      this.isApproval = false
      this.dialogFormVisible = true
      this.resetForm()
      this.getTypeList()
    },
    updateFixed(row) {
      this.ruleForm = Object.assign({}, row) // copy obj
      this.ruleForm.employeeName = row.employeeName || this.employeeName;
      if (this.ruleForm.status == '0') {
        this.isApproval = false
      } else if (this.ruleForm.status == '1' || this.ruleForm.status == '2') {
        this.isApproval = true
      }
      this.dialogTitle = "资产详情"
      this.isUpdate = true
      this.isCreate = false
      this.dialogFormVisible = true
    },
    // resetTemp() {
    //   this.ruleForm = {
    //     employeeNumber: sessionStorage.getItem("username"),
    //     employeeName: this.employeeName,
    //     number:'',
    //     name: '',
    //     typeID: '',
    //     price: '',
    //     applyTime: this.getTime(),
    //     taskTypeID: '2',
    //     status: '',
    //     approvalID: '',
    //   }
    // },
    getUsername() {
      axios.post('/employee/findByNumber', this.form).then(res => {
        this.employeeName = res.data.name;
        this.employeeNumber = res.data.number;
        this.ruleForm.employeeName = res.data.name;
        this.ruleForm.employeeNumber = res.data.number;
      })
    },
    getTypeList() {
      axios.get('/fixedassetType/list').then(res => {
        this.fixedTypeList = res.data;
      })
    },
    insertFixed(formName) {
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
              this.$message({ message: "购置成功", type: "success" });
              this.$refs[formName].resetFields();
              this.ruleForm.employeeName = this.employeeName;
              this.ruleForm.applyTime = this.getTime();
              this.getFixedList();
            } else if (res.data.code == 20005) {
              this.$message({ message: "该编号已存在", type: "error" });
            } else {
              this.$message({ message: "购置失败，请联系管理员", type: "error" });
            }
          }).catch(error => {
            loading.close();
            this.$message({ message: "系统错误", type: "error" });
            console.error(error);
          });
        } else {
          //console.log('error submit!!');
          return false;
        }
      });
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
    deleteFixed(row) {
      this.$confirm('确认报废该固定资产吗?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        axios.post('/fixedasset/deleteById', {
          id: row.id
        }).then(res => {
          this.$message({
            type: 'success',
            message: '报废成功!'
          });
          this.getFixedList();
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        });
      });
    },
    getCheckList() {
      axios.post('/check/getCheckList', {
        date: this.checkMonth,
        month: this.checkMonth,
      }).then(res => {
        this.checkList = res.data;
      })
    },
    payOff(row) {
      row.month = this.checkMonth;
      // 创建一个表示当前日期的 Date 对象
      let currentDate = new Date();
      // 创建一个表示给定日期的 Date 对象
      let givenDate = new Date(row.month);
      // 比较月份
      if (currentDate.getFullYear() === givenDate.getFullYear() && currentDate.getMonth() === givenDate.getMonth()) {

        this.$message({ message: "无法发放当月工资条", type: "error" });
        return
      }
      axios.post('/salary/payOff', row).then(res => {
        if (res.data.code == 200) {
          this.$message({ message: "发放成功", type: "success" });
        } else if (res.data.code == 50003) {
          this.$message({ message: "请勿重复发放", type: "error" });
        }
        this.getCheckList();
      })
    },
    exportCheck() {
      // 获取年、月和日
      let date = new Date(this.checkMonth);
      let year = date.getFullYear();
      let month = (date.getMonth() + 1).toString().padStart(2, '0'); // 月份从 0 开始，需要加 1，并且月份不足两位要补零
      let day = date.getDate().toString().padStart(2, '0'); // 日不足两位要补零

      window.open('http://localhost:9331/check/exportExcel?month=' + `${year}-${month}-${day}`)
    },
    approveAsset(advice) {
      const loading = this.$loading({
        lock: true,
        text: '处理中...',
        spinner: 'el-icon-loading',
        background: 'rgba(0, 0, 0, 0.7)'
      });
      
      // 先获取资产对应的任务ID
      axios.post('/fixedasset/getTaskByFixedassetID', {
        id: this.ruleForm.id
      }).then(res => {
        if (res.data.code === 200 && res.data.data) {
          const taskId = res.data.data;
          
          // 提交审批
          const approvalData = {
            id: taskId,
            applyID: this.ruleForm.id,
            typeID: this.ruleForm.taskTypeID || '2', // 默认使用固定资产申请类型
            advice: advice,
            approvalTime: this.getTime(),
            approvalNumber: sessionStorage.getItem("username")
          };
          
          axios.post('/task/approval', approvalData).then(response => {
            loading.close();
            if (response.data.code === 200) {
              this.dialogFormVisible = false;
              this.$message({ message: "审批成功", type: "success" });
              this.getFixedList();
            } else {
              this.$message({ message: "审批失败，请重试", type: "error" });
            }
          }).catch(error => {
            loading.close();
            this.$message({ message: "系统错误", type: "error" });
            console.error(error);
          });
        } else {
          loading.close();
          this.$message({ message: "找不到相关任务或已被处理", type: "warning" });
        }
      }).catch(error => {
        loading.close();
        this.$message({ message: "获取任务信息失败", type: "error" });
        console.error(error);
      });
    },
  },
}
</script>
