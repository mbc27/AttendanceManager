<template>
  <div class="pro-container">
    <div class="filter-container">
      <div class="right-items" style="text-align: left;">
        <el-button class="filter-item" style="margin-left: 10px;" type="primary" v-if="isAdmin" @click="handleCreate">
          发通知</el-button>
      </div>
    </div>
    <div style="margin-top: 10px;">
      <el-table :data="tableData" border fit highlight-current-row style="width: 100%">
        <el-table-column prop="title" label="标题"></el-table-column>
        <el-table-column prop="publisherName" label="发布人"></el-table-column>
        <el-table-column prop="details" label="详情"></el-table-column>
        <el-table-column prop="beginDate" label="开始日期"></el-table-column>
        <el-table-column prop="endDate" label="结束日期"></el-table-column>
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
          <el-input v-model="form.title" placeholder="请输入通知标题~" :readonly="isUpdate" />
        </el-form-item>
        <el-form-item label="开始时间" prop="beginDate">
          <el-date-picker v-model="form.beginDate" type="date" placeholder="选择开始日期" value-format="yyyy-MM-dd"
            style="width: 100%;" :readonly="isUpdate"></el-date-picker>
        </el-form-item>
        <el-form-item label="结束时间" prop="endDate">
          <el-date-picker v-model="form.endDate" type="date" placeholder="选择结束日期" value-format="yyyy-MM-dd"
            style="width: 100%;" :readonly="isUpdate"></el-date-picker>
        </el-form-item>
        <el-form-item label="发布人">
          <el-input v-model="form.publisherName" placeholder="请输入通知发布人~" readonly />
        </el-form-item>
        <el-form-item label="发布时间" v-if="isUpdate">
          <el-input v-model="form.publishTime" readonly />
        </el-form-item>
        <el-form-item label="详情">
          <el-input type="textarea" :rows="2" v-model="form.details" placeholder="请输入通知详情~" :readonly="isUpdate" />
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
      tableData: [{
        title: '这是一个通知',
        time: '之后一年时间',
        place: '公司之外的地方',
        participants: '全体员工',
        host: '董事长',
        details: '明天开始放年假，放一年',
        publisher: '系统管理员',
      }],
      dialogFormVisible: false,
      dialogTitle: '',
      form: {
        id: '',
        title: '',
        time: '',
        place: '',
        participants: '',
        host: '',
        details: '',
        publisherNumber: '',
        publisherName: '',
        publishTime: '',
        beginDate: '',
        endDate: '',
        type: '2',
      },
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
      rules: {
        title: [
          { required: true, message: '标题不能为空！', trigger: 'blur' }
        ],
        beginDate: [
          { validator: this.beginDate, trigger: 'change' }
        ],
        endDate: [
          { validator: this.endDate, trigger: 'change' }
        ],
      },
      isAdmin: false,
    }
  },
  created() {
    this.getData();
    this.getUsername();
  },
  methods: {
    getData() {
      axios.get("/meeting/getAllNotice").then(res => {
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
        host: '',
        details: '',
        publisherNumber: this.employeeNumber,
        publisherName: this.employeeName,
        publishTime: this.getTime(),
        beginDate: '',
        endDate: '',
        type: '2',
      }
    },
    resetForm() {
      this.form = {
        id: '',
        title: '',
        time: '',
        place: '',
        participants: '',
        host: '',
        details: '',
        publisherNumber: '',
        publisherName: '',
        publishTime: this.getTime(),
        beginDate: '',
        endDate: '',
        type: '2',
      }
      this.form.publisherNumber = this.employeeNumber;
      this.form.publisherName = this.employeeName;
      this.form.publishTime = this.getTime();
      this.form.type = '2';
    },
    handleCreate() {
      this.dialogTitle = "发布通知"
      this.isCreate = true
      this.isUpdate = false
      this.isShow = true
      this.dialogFormVisible = true
      this.resetForm()
    },
    handleUpdate(row) {
      //this.tempForm = this.$wtables.getSelectedRowData(this, "employeeList");
      this.form = Object.assign({}, row) // copy obj
      this.dialogTitle = "查看通知详情"
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
      this.$refs['form'].validate((valid) => {
        if (valid) {
          this.form.beginDate = this.rTime(this.form.beginDate)
          this.form.endDate = this.rTime(this.form.endDate)
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
    rTime(date) {
      var json_date = new Date(date).toJSON();
      return new Date(new Date(json_date) + 8 * 3600 * 1000).toISOString().replace(/T/g, ' ').replace(/\.[\d]{3}Z/, '')
    },
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
    compareDate(start, end) {
      return new Date(end).getTime() > new Date(start).getTime()
    },
    beginDate(rule, value, callback) {
      if (!value) {
        //callback(new Error('开始日期不能为空！'));
        callback()
      } else {
        if (!this.compareDate(this.getTime(), value)) {
          callback(new Error('不能早于当前日期请重新选择'))
        } else if (this.form.endDate) {
          if (!this.compareDate(value, this.form.endDate)) {
            callback(new Error('不能晚于结束日期请重新选择'))
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
        //callback(new Error('结束日期不能为空！'));
        callback()
      } else {
        if (!this.form.beginDate) {
          callback()
        } else {
          // 开始日期有
          if (this.compareDate(this.form.beginDate, value)) {
            callback()
          } else {
            callback(new Error('不能早于开始日期请重新选择'))
          }
        }
      }
    },
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