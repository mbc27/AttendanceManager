<template>
  <div>
    <div style="text-align: left; margin-left: 20px;">
      <el-button class="filter-item" style="margin-left: 10px;" type="primary" @click="handleCreate"> 发起申请</el-button>
    </div>
    <div style="display: flex; flex-direction: row; flex-wrap: wrap;">
      <el-card class="box-card" v-for="item in tableData" :key="item.id" style="width: 328px; margin: 20px;">
        <template #header>
          <div class="card-header">
            <span style="color: green; float: left;" v-if="item.isPass">{{ item.statusName }}</span>
            <span style="color: gray; float: left;" v-else-if="item.isNotApproval">{{ item.statusName }}</span>
            <span style="color: red; float: left;" v-else>{{ item.statusName }}</span>
            <span>{{ ruleForm.applyName }}提交的请假申请</span>
            <!-- <el-button class="button" type="text">撤销</el-button>
          <el-button class="button" type="text" @click="handleUpdate">修改</el-button> -->
          </div>
        </template>
        <!-- <div v-for="o in 4" :key="o" class="text item"> -->
        <div class="text item">
          {{ '请假类型：' + item.typeName }}
        </div>
        <!-- <div class="text item">
        {{'开始时间：'+item.beginTime}}
      </div>
      <div class="text item">
        {{'结束时间：'+item.endTime}}
      </div> -->
        <div class="text item">
          {{ '申请时间：' + item.applyTime }}
        </div>
        <div class="text item" style="text-align: center;">
          <el-button class="button" type="text" @click="handleUpdate(item)"> 查看详情</el-button>
        </div>
      </el-card>
    </div>
    <el-dialog :title="dialogTitle" :visible.sync="dialogFormVisible" >
      <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm"
        style="width:100%;">
        <el-form-item label="申请人" prop="name">
          <el-input v-model="ruleForm.applyName" readonly></el-input>
        </el-form-item>
        <el-form-item label="申请时间" prop="applyTime" v-if="isUpdate">
          <el-input v-model="ruleForm.applyTime" readonly></el-input>
        </el-form-item>
        <el-form-item label="请假类型" prop="typeID">
          <el-select v-model="ruleForm.typeID" clearable placeholder="请选择" style="width: 100%;" :disabled="isUpdate">
            <el-option v-for="item in typeList" :key="item.id" :label="item.name" :value="item.id"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="开始时间" prop="beginTime" v-if="isCreate">
          <!-- <el-date-picker v-model="form.beginTime" type="datetime" placeholder="选择开始时间" style="width: 100%;"></el-date-picker> -->
          <el-col :span="11">
            <el-form-item prop="date1">
              <el-date-picker type="date" placeholder="选择日期" v-model="ruleForm.date1" value-format="yyyy-MM-dd"
                style="width: 100%;" :readonly="isUpdate"></el-date-picker>
            </el-form-item>
          </el-col>
          <el-col class="line" :span="2">-</el-col>
          <el-col :span="11">
            <el-form-item prop="date2">
              <el-time-picker placeholder="选择时间" v-model="ruleForm.date2" value-format="HH:mm:ss" style="width: 100%;"
                :readonly="isUpdate"></el-time-picker>
            </el-form-item>
          </el-col>
        </el-form-item>
        <el-form-item label="开始时间" prop="beginTime" v-if="isUpdate">
          <el-input v-model="ruleForm.beginTime" :readonly="isUpdate"></el-input>
        </el-form-item>
        <el-form-item label="结束时间" prop="endTime" v-if="isCreate">
          <!-- <el-date-picker v-model="form.endTime" type="datetime" placeholder="选择结束时间" style="width: 100%;"></el-date-picker> -->
          <el-col :span="11">
            <el-form-item prop="date3">
              <el-date-picker type="date" placeholder="选择日期" v-model="ruleForm.date3" value-format="yyyy-MM-dd"
                style="width: 100%;" :readonly="isUpdate"></el-date-picker>
            </el-form-item>
          </el-col>
          <el-col class="line" :span="2">-</el-col>
          <el-col :span="11">
            <el-form-item prop="date4">
              <el-time-picker placeholder="选择时间" v-model="ruleForm.date4" value-format="HH:mm:ss" style="width: 100%;"
                :readonly="isUpdate"></el-time-picker>
            </el-form-item>
          </el-col>
        </el-form-item>
        <el-form-item label="结束时间" prop="endTime" v-if="isUpdate">
          <el-input v-model="ruleForm.endTime" :readonly="isUpdate"></el-input>
        </el-form-item>
        <el-form-item label="时长" prop="duration" v-if="isCreate">
          <!-- <p>{{duration}}</p> -->
          <el-input v-model="ruleForm.duration" :placeholder=duration readonly></el-input>
        </el-form-item>
        <el-form-item label="时长" prop="duration" v-if="isUpdate">
          <!-- <p>{{duration}}</p> -->
          <el-input v-model="ruleForm.duration" readonly></el-input>
        </el-form-item>
        <el-form-item label="请假事由" prop="reason">
          <el-input type="textarea" :rows="2" v-model="ruleForm.reason" placeholder="请输入请假事由"
            :readonly="isUpdate"></el-input>
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
          <el-button type="primary" @click="submitForm()" v-if="isCreate"> 提 交</el-button>
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
        applyNumber: '',
        applyName: '',
        beginTime: '',
        endTime: '',
        duration: '',
        reason: '',
        typeID: '',
        status: '',
        approvalID: '',
        taskTypeID: '1',
        date1: '',
        date2: '',
        date3: '',
        date4: '',
        applyTime: this.getTime('datetime'),
        statusName: '',
        approvalName: '',
        approvalTime: '',
        typeName: '',
      },
      rules: {
        typeID: [
          { required: true, validator: this.leaveType, trigger: 'change' }
        ],
        date1: [
          { required: true, validator: this.beginDate, trigger: 'change' }
        ],
        date2: [
          { required: true, validator: this.beginTime, trigger: 'change' }
        ],
        date3: [
          { required: true, validator: this.endDate, trigger: 'change' }
        ],
        date4: [
          { required: true, validator: this.endTime, trigger: 'change' }
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
      employeeNumber: '',
      employeeName: '',
      dialogFormVisible: false,
      dialogTitle: '',
      isCreate: false,
      isUpdate: false,
      isApproval: false,
      typeList: [],
      isPass: false,
      isNotApproval: false,
    };
  },
  computed: {
    duration: function () {
      let beginTime = this.ruleForm.date1 + ' ' + this.ruleForm.date2
      let beginTime1 = new Date(beginTime.replace(/-/g, "/"));
      let endTime = this.ruleForm.date3 + ' ' + this.ruleForm.date4
      let endTime1 = new Date(endTime.replace(/-/g, "/"));
      var date = endTime1.getTime() - beginTime1.getTime();
      this.ruleForm.duration = Math.floor(date / (3600 * 1000));
      return Math.floor(date / (3600 * 1000));
    },
  },
  created() {
    this.getData();
    this.getUsername();
  },
  methods: {
    getData() {
      console.log("正在请求请假记录，用户名:", sessionStorage.getItem("username"));
      axios.post('/leave/list', {
        applyNumber: sessionStorage.getItem("username"),
      }).then(res => {
        console.log("请假记录请求结果:", res.data);
        this.tableData = res.data;
        if (!this.tableData || this.tableData.length === 0) {
          console.log("未找到请假记录");
        } else {
          console.log("找到", this.tableData.length, "条请假记录");
        }
        this.tableData.forEach(item => {
          if (item.status == '0') {
            item.statusName = '待审批'
            item.isNotApproval = true
          } else if (item.status == '1') {
            item.statusName = '已通过'
            item.isPass = true
          } else if (item.status == '2') {
            item.statusName = '已驳回'
          } else if (item.status == '3') {
            item.statusName = '已撤销'
          }
        })
      }).catch(error => {
        console.error("请求请假记录失败:", error);
      });
    },
    submitForm() {
      this.$refs['ruleForm'].validate((valid) => {
        if (valid) {
          this.ruleForm.beginTime = this.ruleForm.date1 + ' ' + this.ruleForm.date2
          this.ruleForm.endTime = this.ruleForm.date3 + ' ' + this.ruleForm.date4
          axios.post('/leave/insert', this.ruleForm).then(res => {
            this.$message({ message: "提交成功", type: "success" });
            this.resetTemp();
            this.dialogFormVisible = false;
            this.getData();
          })
        } else {
          console.log('error submit!!');
          return false;
        }
      });
    },
    getUsername() {
      axios.post('/employee/findByNumber', this.form).then(res => {
        this.employeeName = res.data.name;
        this.employeeNumber = res.data.number;
        this.ruleForm.applyName = res.data.name;
        this.ruleForm.applyNumber = res.data.number;
      })
    },
    resetTemp() {
      this.ruleForm = {
        applyNumber: this.employeeNumber,
        applyName: this.employeeName,
        beginTime: '',
        endTime: '',
        duration: '',
        reason: '',
        typeID: '',
        status: '',
        approvalID: '',
        taskTypeID: '1',
        date1: '',
        date2: '',
        date3: '',
        date4: '',
        applyTime: this.getTime('datetime'),
        statusName: '',
        approvalName: '',
        approvalTime: '',
        typeName: '',
      }
    },
    resetForm() {
      this.ruleForm = {
        applyNumber: '',
        applyName: '',
        beginTime: '',
        endTime: '',
        duration: '',
        reason: '',
        typeID: '',
        status: '',
        approvalID: '',
        taskTypeID: '1',
        date1: '',
        date2: '',
        date3: '',
        date4: '',
        applyTime: this.getTime('datetime'),
        statusName: '',
        approvalName: '',
        approvalTime: '',
        typeName: '',
      };
      this.ruleForm.applyNumber = this.employeeNumber;
      this.ruleForm.applyName = this.employeeName;
      this.ruleForm.applyTime = this.getTime('datetime');
      this.taskTypeID = '1';
    },
    getTime(type) {
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
      if (type == 'date') {
        var currentdate = year + seperator1 + month + seperator1 + strDate
      } else if (type == 'time') {
        var currentdate = hour + seperator2 + minute + seperator2 + second;
      } else {
        var currentdate = year + seperator1 + month + seperator1 + strDate
          + " " + hour + seperator2 + minute + seperator2 + second;
      }

      return currentdate;
    },
    handleCreate() {
      this.dialogTitle = "发送申请"
      this.isCreate = true
      this.isUpdate = false
      this.isApproval = false
      this.dialogFormVisible = true
      this.getTypeList()
      this.resetForm()
    },
    handleUpdate(row) {
      this.ruleForm = Object.assign({}, row) // copy obj
      this.ruleForm.applyName = this.employeeName;
      this.getDuration();
      if (this.ruleForm.status == '0' || this.ruleForm.status == '3') {
        this.isApproval = false
      } else if (this.ruleForm.status == '1' || this.ruleForm.status == '2') {
        this.isApproval = true
      }
      this.dialogTitle = "请假详情"
      this.isUpdate = true
      this.isCreate = false
      this.dialogFormVisible = true
    },
    getTypeList() {
      axios.get('/leaveType/list').then(res => {
        this.typeList = res.data;
      })
    },
    getDuration() {
      let beginTime = new Date(this.ruleForm.beginTime.replace(/-/g, "/"));
      let endTime = new Date(this.ruleForm.endTime.replace(/-/g, "/"));
      var date = endTime.getTime() - beginTime.getTime();
      this.ruleForm.duration = Math.floor(date / (3600 * 1000));
    },
    compareDate(start, end) {
      return new Date(end).getTime() >= new Date(start).getTime()
    },
    beginDate(rule, value, callback) {
      if (!value) {
        callback(new Error('开始日期不能为空！'));
      } else {
        if (!this.compareDate(this.getTime('date'), value)) {
          callback(new Error('开始日期不能早于当前日期，请重新选择'))
        } else if (this.ruleForm.date3) {
          if (!this.compareDate(value, this.ruleForm.date3)) {
            callback(new Error('开始日期不能在结束日期之后，请重新选择'))
          } else {
            callback()
          }
        } else {
          callback()
        }
      }
    },
    endDate(rule, value, callback) {
      if (!value) {
        callback(new Error('结束日期不能为空！'));
      } else {
        if (!this.ruleForm.date1) {
          callback()
        } else {
          // 开始日期有
          if (this.compareDate(this.ruleForm.date1, value)) {
            callback()
          } else {
            callback(new Error('结束日期不能早于开始日期，请重新选择'))
          }
        }
      }
    },
    beginTime(rule, value, callback) {
      if (!value) {
        callback(new Error('开始时间不能为空！'));
      } else {
        if (this.ruleForm.date1) {
          if (new Date(this.ruleForm.date1).getTime() == new Date(this.getTime('date')).getTime()) {
            if (this.compareDate(this.getTime('datetime'), this.ruleForm.date1 + ' ' + value)) {
              callback()
            } else {
              callback(new Error('开始时间不能早于当前时间，请重新选择'))
            }
          } else if (this.ruleForm.date3) {
            if (new Date(this.ruleForm.date1).getTime() == new Date(this.ruleForm.date3).getTime()) {
              if (this.ruleForm.date4) {
                if (this.compareDate(this.ruleForm.date1 + ' ' + value, this.ruleForm.date3 + ' ' + this.ruleForm.date4)) {
                  callback()
                } else {
                  callback(new Error('开始时间不能晚于结束时间，请重新选择'))
                }
              } else {
                callback()
              }
            } else {
              callback()
            }
          } else {
            callback()
          }
        } else {
          callback()
        }
      }
    },
    endTime(rule, value, callback) {
      if (!value) {
        callback(new Error('结束时间不能为空！'));
      } else {
        if (new Date(this.ruleForm.date1).getTime() == new Date(this.ruleForm.date3).getTime()) {
          if (this.ruleForm.date2) {
            if (this.compareDate(this.ruleForm.date1 + ' ' + this.ruleForm.date2, this.ruleForm.date3 + ' ' + value)) {
              callback()
            } else {
              callback(new Error('结束时间不能早于开始时间，请重新选择'))
            }
          } else {
            callback()
          }
        } else {
          callback()
        }
      }
    },
    leaveType(rule, value, callback) {
      if (!value) {
        callback(new Error('请选择请假类型'));
      } else {
        callback()
      }
    }
  }
}
</script>

<style></style>
