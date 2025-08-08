<template>
  <div style="margin-left: -20px;">
    <div style="margin-top: 20px;">
      <div>
        <div style="margin:-10px 0 -12px 20px; text-align: left; display: flex; justify-content: space-between;">
          <div>
            <el-button type="primary" @click="getAll()"> 考勤记录</el-button>
            <el-button type="primary" @click="findSalary()"> 查看工资条</el-button>
          </div>
          <div class="block">
            <span class="demonstration">选择月份</span>
            <el-date-picker v-model="checkMonth" type="month" placeholder="选择月" value-format="yyyy-MM"></el-date-picker>
            <el-button type="primary" @click="getCheckInfo()"> 查询</el-button>
          </div>
        </div>
        <el-divider></el-divider>
        <div style="margin:-12px 0 -12px 10px; text-align: left; color: blue;">
          <el-divider direction="vertical"></el-divider>
          <span style="font-size: 20px;">出勤（天）</span>
        </div>
        <el-divider></el-divider>
        <div style="display: flex; margin:-25px 0 -25px 0;">
          <div style="background-color: #fafafa; width:180px; height: 50px; line-height:50px;">
            <span style="font-size: 18px;">应出勤</span>
          </div>
          <div style="width:420px; height: 50px; line-height:50px;">
            <span style="font-size: 18px;">{{ this.checkForm.workDays }}</span>
          </div>
          <div style="background-color: #fafafa; width:180px; height: 50px; line-height:50px;">
            <span style="font-size: 18px;">实际出勤</span>
          </div>
          <div style="width:420px; height: 50px; line-height:50px;">
            <span style="font-size: 18px;">{{ this.checkForm.checkDays }}</span>
          </div>
        </div>
        <el-divider></el-divider>
        <div style="margin:-12px 0 -12px 10px; text-align: left; color: blue;">
          <el-divider direction="vertical"></el-divider>
          <span style="font-size: 20px;">请假（小时）</span>
        </div>
        <el-divider></el-divider>
        <div style="display: flex; margin:-25px 0 -25px 0;">
          <div style="background-color: #fafafa; width:180px; height: 50px; line-height:50px;">
            <span style="font-size: 18px;">事假</span>
          </div>
          <div style="width:420px; height: 50px; line-height:50px;">
            <span style="font-size: 18px;">{{ this.checkForm.leaveDays1 }}</span>
          </div>
          <div style="background-color: #fafafa; width:180px; height: 50px; line-height:50px;">
            <span style="font-size: 18px;">婚假</span>
          </div>
          <div style="width:420px; height: 50px; line-height:50px;">
            <span style="font-size: 18px;">{{ this.checkForm.leaveDays2 }}</span>
          </div>
        </div>
        <el-divider></el-divider>
        <div style="display: flex; margin:-25px 0 -25px 0;">
          <div style="background-color: #fafafa; width:180px; height: 50px; line-height:50px;">
            <span style="font-size: 18px;">病假</span>
          </div>
          <div style="width:420px; height: 50px; line-height:50px;">
            <span style="font-size: 18px;">{{ this.checkForm.leaveDays6 }}</span>
          </div>
          <div style="background-color: #fafafa; width:180px; height: 50px; line-height:50px;">
            <span style="font-size: 18px;">丧假</span>
          </div>
          <div style="width:420px; height: 50px; line-height:50px;">
            <span style="font-size: 18px;">{{ this.checkForm.leaveDays3 }}</span>
          </div>
        </div>
        <el-divider></el-divider>
        <div style="display: flex; margin:-25px 0 -25px 0;">
          <div style="background-color: #fafafa; width:180px; height: 50px; line-height:50px;">
            <span style="font-size: 18px;">产假</span>
          </div>
          <div style="width:420px; height: 50px; line-height:50px;">
            <span style="font-size: 18px;">{{ this.checkForm.leaveDays4 }}</span>
          </div>
          <div style="background-color: #fafafa; width:180px; height: 50px; line-height:50px;">
            <span style="font-size: 18px;">陪产假</span>
          </div>
          <div style="width:420px; height: 50px; line-height:50px;">
            <span style="font-size: 18px;">{{ this.checkForm.leaveDays5 }}</span>
          </div>
        </div>
        <el-divider></el-divider>
        <div style="margin:-12px 0 -12px 10px; text-align: left; color: blue;">
          <el-divider direction="vertical"></el-divider>
          <span style="font-size: 20px;">异常（次）</span>
        </div>
        <el-divider></el-divider>
        <div style="display: flex; margin:-25px 0 -25px 0;">
          <div style="background-color: #fafafa; width:180px; height: 50px; line-height:50px;">
            <span style="font-size: 18px;">迟到</span>
          </div>
          <div style="width:420px; height: 50px; line-height:50px;">
            <span style="font-size: 18px;">{{ this.checkForm.lateDays }}</span>
          </div>
          <div style="background-color: #fafafa; width:180px; height: 50px; line-height:50px;">
            <span style="font-size: 18px;">早退</span>
          </div>
          <div style="width:420px; height: 50px; line-height:50px;">
            <span style="font-size: 18px;">{{ this.checkForm.leaveEarlyDays }}</span>
          </div>
        </div>
        <el-divider></el-divider>
        <span v-if="!currentMonth">
          <div style="display: flex; margin:-25px 0 -25px 0;">
            <div style="background-color: #fafafa; width:155px; height: 50px; line-height:50px;">
              <span style="font-size: 18px;">缺勤</span>
            </div>
            <div style="width:400px; height: 50px; line-height:50px;">
              <span style="font-size: 18px;">{{ this.checkForm.workDays - this.checkForm.checkDays }}</span>
            </div>
          </div>
          <el-divider></el-divider>
        </span>
      </div>
    </div>
    <el-dialog title="全部记录" :visible.sync="dialogFormVisible" width="50%">
      <el-table :data="tableData" border fit highlight-current-row style="width: 100%">
        <el-table-column prop="date" label="日期"></el-table-column>
        <el-table-column prop="checkOnTime" label="签到时间"></el-table-column>
        <el-table-column prop="checkOnStatus" label="签到状态"></el-table-column>
        <el-table-column prop="checkOffTime" label="签退时间"></el-table-column>
        <el-table-column prop="checkOffStatus" label="签退状态"></el-table-column>
      </el-table>
    </el-dialog>
    <el-dialog title="工资条" :visible.sync="salaryShow" width="50%">
      <el-table :data="salaryTable" border fit highlight-current-row style="width: 100%">
        <el-table-column prop="month" label="月份"></el-table-column>
        <el-table-column prop="monthlySalary" label="应发工资"></el-table-column>
        <el-table-column prop="workDays" label="应工作天数"></el-table-column>
        <el-table-column prop="checkDays" label="实际工作天数"></el-table-column>
        <el-table-column prop="leaveDays" label="请假天数"></el-table-column>
        <el-table-column prop="lateTimes" label="迟到次数"></el-table-column>
        <el-table-column prop="leaveEarlyTimes" label="早退次数"></el-table-column>
        <el-table-column prop="salary" label="实发工资"></el-table-column>
      </el-table>
    </el-dialog>
  </div>
</template>

<script>
import axios from 'axios'
export default {
  data() {
    return {
      dialogFormVisible: false,
      tableData: [],
      salaryTable: [],
      checkMonth: new Date(),
      form: {
        id: '',
        employeeID: sessionStorage.getItem("username"),
        date: '',
        remarks: '',
        time: '',
        checkOnTime: '',
        checkOffTime: ' ',
        checkOnStatus: '',
        checkOffStatus: '',
        month: '',
        workDays: '',
        checkDays: '',
        lateDays: '',
        leaveEarlyDays: '',
        leaveDays1: '',
        leaveDays2: '',
        leaveDays3: '',
        leaveDays4: '',
        leaveDays5: '',
        leaveDays6: '',
      },
      checkForm: {},
      salaryShow: false,
      currentMonth: false,
    }
  },
  mounted() {
    this.getCheckInfo();
  },
  methods: {
    getCheckInfo() {
      this.form.month = this.checkMonth;
      this.form.date = this.checkMonth;
      axios.post("/check/getCheckInfo", this.form).then(res => {
        this.checkForm = res.data;
        this.isCurrentMonth()
      })
    },
    getAll() {
      axios.post("/check/findByNumber", this.form).then(res => {
        this.tableData = res.data;
        this.dialogFormVisible = true;
      })
    },
    findSalary() {
      axios.post("/salary/findByNumberAndMonth", {
        employeeID: this.form.employeeID,
        month: this.checkMonth,
      }).then(res => {

        this.salaryTable = [res.data];
        this.salaryShow = true;
      })
    },
    isCurrentMonth() {

      let date = new Date(this.checkMonth);
      let currentMonth = new Date().getMonth() + 1; // 获取当前月份，需要加1
      let dateMonth = date.getMonth() + 1; // 获取日期对象的月份，也需要加1
      if (dateMonth === currentMonth) {
        this.currentMonth = true

      } else {
        this.currentMonth = false
      }
    }
  }
}
</script>