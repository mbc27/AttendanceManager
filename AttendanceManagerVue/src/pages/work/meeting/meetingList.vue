<template>
  <div class="pro-container">
    <div class="filter-container">
      <div class="right-items" style="text-align: left;">
        <el-button v-if="isAdmin" class="filter-item" style="margin-left: 10px;" type="primary" @click="handleCreate"> 发起会议</el-button>
      </div>
    </div>
    <div style="margin-top: 10px;">
      <el-table :data="tableData" border fit highlight-current-row style="width: 100%">
        <el-table-column prop="title" label="标题"></el-table-column>
        <el-table-column prop="publisherName" label="发布人"></el-table-column>
        <el-table-column prop="beginTime" label="开始时间"></el-table-column>
        <el-table-column prop="endTime" label="结束时间"></el-table-column>
        <el-table-column prop="roomName" label="地点"></el-table-column>
        <el-table-column prop="details" label="详情"></el-table-column>
        <el-table-column label="操作" width="180" fixed="right">
          <template slot-scope="scope">
            <el-button type="success" @click="handleUpdate(scope.row)"> 查看详情</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <el-dialog :title="dialogTitle" :visible.sync="dialogFormVisible">
      <el-form ref="form" :rules="rules" :model="form" label-position="left" label-width="120px"
        style="width: 420px; margin-left:50px;">
        <el-form-item label="标题" prop="title">
          <el-input v-model="form.title" placeholder="请输入会议标题~" :readonly="isUpdate" />
        </el-form-item>
        <el-form-item label="开始时间" prop="beginTime" v-if="isCreate">
          <el-col :span="11">
            <el-form-item prop="date1">
              <el-date-picker type="date" placeholder="选择日期" v-model="form.date1" value-format="yyyy-MM-dd"
                style="width: 100%;" :readonly="isUpdate"></el-date-picker>
            </el-form-item>
          </el-col>
          <el-col class="line" :span="2">-</el-col>
          <el-col :span="11">
            <el-form-item prop="date2">
              <el-time-picker placeholder="选择时间" v-model="form.date2" value-format="HH:mm:ss" style="width: 100%;"
                :readonly="isUpdate"></el-time-picker>
            </el-form-item>
          </el-col>
        </el-form-item>
        <el-form-item label="结束时间" prop="endTime" v-if="isCreate">
          <el-col :span="11">
            <el-form-item prop="date3">
              <el-date-picker type="date" placeholder="选择日期" v-model="form.date3" value-format="yyyy-MM-dd"
                style="width: 100%;" :readonly="isUpdate"></el-date-picker>
            </el-form-item>
          </el-col>
          <el-col class="line" :span="2">-</el-col>
          <el-col :span="11">
            <el-form-item prop="date4">
              <el-time-picker placeholder="选择时间" v-model="form.date4" value-format="HH:mm:ss" style="width: 100%;"
                :readonly="isUpdate"></el-time-picker>
            </el-form-item>
          </el-col>
        </el-form-item>
        <el-form-item label="开始时间" v-if="isUpdate">
          <el-input v-model="form.beginTime" :readonly="isUpdate" />
        </el-form-item>
        <el-form-item label="结束时间" v-if="isUpdate">
          <el-input v-model="form.endTime" :readonly="isUpdate" />
        </el-form-item>
        <el-form-item label="会议室" prop="roomID">
          <el-select v-model="form.roomID" placeholder="请选择会议室" style="width: 100%;" :disabled="isUpdate">
            <el-option v-for="item in roomList" :key="item.id" :label="item.name" :value="item.id"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="发布人">
          <el-input v-model="form.publisherName" placeholder="请输入会议发布人~" readonly />
        </el-form-item>
        <el-form-item label="详情">
          <el-input type="textarea" :rows="2" v-model="form.details" placeholder="请输入会议详情~" :readonly="isUpdate" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false"> 取 消</el-button>
        <el-button type="primary" @click="createData()" v-if="isShow"> 确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import axios from 'axios'
export default {
  data() {
    return {
      tableData: [],
      dialogFormVisible: false,
      dialogTitle: '',
      form: {
        id: '',
        title: '',
        time: '',
        roomID: '',
        participants: '',
        host: '',
        details: '',
        publisherNumber: '',
        publisherName: '',
        publishTime: '',
        beginDate: '',
        endDate: '',
        beginTime: '',
        endTime: '',
        type: '1',
        date1: '',
        date2: '',
        date3: '',
        date4: '',
      },
      isAdmin: false,
      form1: {
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
      id: '',
      employeeName: '',
      employeeNumber: '',
      isCreate: false,
      isUpdate: false,
      isShow: false,
      roomList: [],
      rules: {
        title: [
          { required: true, message: '标题不能为空！', trigger: 'blur' }
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
        roomID: [
          { required: true, message: '请选择会议室', trigger: 'change' }
        ],
      },
    }
  },
  created() {
    this.getData();
    this.getUsername();
  },
  methods: {

    getData() {
      axios.get("/meeting/getAllMeeting").then(res => {
        this.getRoomList()
        this.tableData = res.data;
      })
    },
    resetTemp() {
      this.form = {
        id: '',
        title: '',
        time: '',
        place: '',
        participants: '',
        host: this.employeeName,
        details: '',
        publisherNumber: this.employeeNumber,
        publisherName: this.employeeName,
        publishTime: this.getTime(),
        beginDate: '',
        endDate: '',
        beginTime: '',
        endTime: '',
        type: '1',
        date1: '',
        date2: '',
        date3: '',
        date4: '',
      }
    },
    resetForm() {
      this.$refs['form'].resetFields();
      this.form.host = this.employeeName;
      this.form.publisherNumber = this.employeeNumber;
      this.form.publisherName = this.employeeName;
      this.form.publishTime = this.getTime('datetime');
      this.form.type = '1';
    },
    handleCreate() {
      //this.resetTemp()
      this.getRoomList()
      this.dialogTitle = "发布会议"
      this.isCreate = true
      this.isUpdate = false
      this.isShow = true
      this.dialogFormVisible = true
      this.form = {
        id: '',
        title: '',
        time: '',
        roomID: '',
        participants: '',
        host: '',
        details: '',
        publisherNumber: '',
        publisherName: '',
        publishTime: '',
        beginDate: '',
        endDate: '',
        beginTime: '',
        endTime: '',
        type: '1',
        date1: '',
        date2: '',
        date3: '',
        date4: '',
      }
    },
    handleUpdate(row) {
      //this.tempForm = this.$wtables.getSelectedRowData(this, "employeeList");
      this.form = Object.assign({}, row) // copy obj
      this.dialogTitle = "查看会议详情"
      this.isCreate = false
      this.isUpdate = true
      this.isShow = false
      this.dialogFormVisible = true
    },
    handleDelete(row) {
      if (row) {
        this.id = row.id
      }
      this.$confirm("确定删除？", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      }).then(() => {
        axios.post("/meeting/deleteById", {
          id: this.id,
        }).then((res) => {
          //if (res.data.code == 200) {
          this.$message({ message: "删除成功", type: "success" });
          this.getData();
          //}
        });
      });
    },
    onSubmit() {

    },
    createData() {
      //this.form.time = this.rTime(this.form.time1)
      // this.form.time = this.form.date1 + ' ' + this.form.date2
      this.$refs['form'].validate((valid) => {
        if (valid) {
          this.form.beginTime = this.form.date1 + ' ' + this.form.date2
          this.form.endTime = this.form.date3 + ' ' + this.form.date4
          axios.post('/meeting/insert', this.form).then(res => {
            this.dialogFormVisible = false;
            this.$message({ message: "发布成功", type: "success" });
            this.getData();
          })
        } else {
          console.log('error submit!!');
          return false;
        }
      });
    },
    // rTime(date){
    //   var json_date = new Date(date).toJSON();
    //   return new Date(new Date(json_date) + 8 * 3600 * 1000).toISOString().replace(/T/g, ' ').replace(/\.[\d]{3}Z/, '')
    // },
    getUsername() {
      axios.post('/employee/findByNumber', this.form1).then(res => {
        this.employeeName = res.data.name;
        this.employeeNumber = res.data.number;
        this.form.publisherName = res.data.name;
        this.form.publisherNumber = res.data.number;
        if (res.data.type == 3) {
          this.isAdmin = true
        } else {
          this.isAdmin = false
        }
      })
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
    getRoomList() {
      axios.get('/fixedasset/getRoomList').then(res => {
        this.roomList = res.data;
      })
    },
    compareDate(start, end) {
      return new Date(end).getTime() >= new Date(start).getTime()
    },
    beginDate(rule, value, callback) {
      if (!value) {
        callback(new Error('开始日期不能为空！'));
      } else {
        if (!this.compareDate(this.getTime('date'), value)) {
          callback(new Error('早于当前日期请重新选择'))
        } else if (this.form.date3) {
          if (!this.compareDate(value, this.form.date3)) {
            callback(new Error('晚于结束日期请重新选择'))
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
        if (!this.form.date1) {
          callback()
        } else {
          // 开始日期有
          if (this.compareDate(this.form.date1, value)) {
            callback()
          } else {
            callback(new Error('早于开始日期请重新选择'))
          }
        }
      }
    },
    beginTime(rule, value, callback) {
      if (!value) {
        callback(new Error('开始时间不能为空！'));
      } else {
        if (this.form.date1) {
          if (new Date(this.form.date1).getTime() == new Date(this.getTime('date')).getTime()) {
            if (this.compareDate(this.getTime('datetime'), this.form.date1 + ' ' + value)) {
              callback()
            } else {
              callback(new Error('早于当前时间请重新选择'))
            }
          } else if (this.form.date3) {
            if (new Date(this.form.date1).getTime() == new Date(this.form.date3).getTime()) {
              if (this.form.date4) {
                if (this.compareDate(this.form.date1 + ' ' + value, this.form.date3 + ' ' + this.form.date4)) {
                  callback()
                } else {
                  callback(new Error('晚于结束时间请重新选择'))
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
      }
    },
    endTime(rule, value, callback) {
      if (!value) {
        callback(new Error('结束时间不能为空！'));
      } else {
        if (new Date(this.form.date1).getTime() == new Date(this.form.date3).getTime()) {
          if (this.form.date2) {
            if (this.compareDate(this.form.date1 + ' ' + this.form.date2, this.form.date3 + ' ' + value)) {
              callback()
            } else {
              callback(new Error('早于开始时间请重新选择'))
            }
          } else {
            callback()
          }
        } else {
          callback()
        }
      }
    }
  }
}
</script>
<style scoped>
.pro-container {
  padding: 15px 32px;
  margin: 4px 2px;
}

.fenye {
  margin-top: 10px;
}</style>