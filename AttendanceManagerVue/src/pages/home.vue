<template>
  <div class="block" style="width: 100%;">
    <el-row :gutter="16">
      <el-col :span="12">

        <el-card class="box-card" style="height: 385px; margin: 2px;">
          <div slot="header" class="clearfix" style="text-align: left;">
            <span class="to-attendance"> 签到打卡</span>
          </div>
          <div class="text item">
            <el-row>
              <el-col :span="12">
                <div style="background-color: lightgrey;height: 50px; width: 120px;border-radius: 10px;">
                  <div>
                    <span style="font-size: 18px; font-weight:bold">上班{{onTime}}</span>
                  </div>
                  <div>
                    <b>
                      <span style="color: #FFCC00" v-if="isLeave">请假</span>
                      <span style="color: green" v-else-if="isOn">已打卡</span>
                      <span style="color: red" v-else>未打卡</span>
                    </b>
                  </div>
                </div>
              </el-col>
              <el-col :span="12" style="display: flex; justify-content: flex-end;">
                <div style="background-color: lightgrey;height: 50px; width: 120px;border-radius: 10px;">
                  <div>
                    <span style="font-size: 18px; font-weight:bold">下班{{offTime}}</span>
                  </div>
                  <div>
                    <b>

                      <span style="color: #FFCC00" v-if="isLeave">请假</span>
                      <span style="color: green" v-else-if="isOff">已打卡</span>
                      <span style="color: red" v-else>未打卡</span>
                    </b>
                  </div>
                </div>
              </el-col>
              <el-col :span="24" style="text-align: center;">
                <div class="text item" style="margin-top: 30px;">
                  <el-button type="success" style="width: 150px; height: 150px; border-radius: 50%;border: none"
                    v-if="!isOn" @click="checkOn()">
                    <div class="click-now">
                      上班打卡
                    </div>
                    <div style="font-size: 16px">
                      {{ currentTime }}
                    </div>
                  </el-button>
                  <el-button type="danger" style="width: 150px; height: 150px; border-radius: 50%;border: none"
                    v-if="isOn && !isOff" @click="checkOff()">
                    <div class="click-now">
                      下班打卡
                    </div>
                    <div style="font-size: 16px">
                      {{ currentTime }}
                    </div>

                  </el-button>
                  <el-button type="info" disabled style="width: 150px; height: 150px; border-radius: 50%;border: none"
                    v-if="isOn && isOff" @click="checkOff()">
                    <div class="click-now">
                      打卡结束
                    </div>

                  </el-button>
                </div>
                <div style="float: right; margin-right: 10px;">
                  <el-button type="text" @click="goTo('/check')">我的企业考勤>></el-button>
                </div>
              </el-col>
            </el-row>
          </div>
        </el-card>
        <br>

        <el-card class="box-card" style="height: 307px; margin: 2px;">
          <div slot="header" class="clearfix" style="text-align: left;">
            <span class="announcement"> 通知公告</span>
            <el-button style="float: right;" type="success" @click="goTo('/notice')"> 更多</el-button>
          </div>
          <div class="text item">
            <div v-for="item in noticeList" :key="item.id"
              style="height: 35px; display: flex; justify-content: space-between;">
              <div>
                <span style="font-size: 14px; color: gray;"><el-link @click="showAnnouncement(item)">{{ item.title
                }}</el-link></span>
              </div>
              <div>
                <span style="font-size: 14px; color: gray;">{{ item.date }}</span>
              </div>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="12">
        <div class="welcome">
          <b>

            欢迎您，{{ this.username }}
          </b>
        </div>
        <hr>
        <el-calendar v-model="value"></el-calendar>
      </el-col>
      <el-col :span="12">
        <br>
        <el-carousel height="280px">
          <el-carousel-item>
            <img src="../assets/001.jpg" class="image" style="width: 100%; height: 100%">
          </el-carousel-item>
          <el-carousel-item>
            <img src="../assets/002.jpg" class="image" style="width: 100%; height: 100%">
          </el-carousel-item>
        </el-carousel>
      </el-col>
      <el-col :span="12">
        <el-card class="box-card" style="height: 298px; margin: 2px;">
          <div slot="header" class="clearfix" style="text-align: left;">
            <span class="today-tips"> 每日金句</span>
          </div>
          <div class="text item" style="line-height: 60px">
            <h3>{{ this.jitangList[this.jitangNumber].jitang }}</h3>
          </div>
        </el-card>
      </el-col>
    </el-row>

    <el-row :gutter="16">
    </el-row>

    <!-- 通知提示框-->
    <el-dialog :title="'通知'" :visible.sync="dialogFormVisible">
      <el-form ref="form" :model="form" label-position="left" label-width="120px"
        style="width: 420px; margin-left:50px;">
        <el-form-item label="标题" prop="title">
          <el-input v-model="form.title" placeholder="请输入通知标题~" :readonly="true" />
        </el-form-item>
        <el-form-item label="开始时间" prop="beginDate">
          <el-date-picker v-model="form.beginDate" type="date" placeholder="选择开始日期" value-format="yyyy-MM-dd"
            style="width: 100%;" :readonly="true"></el-date-picker>
        </el-form-item>
        <el-form-item label="结束时间" prop="endDate">
          <el-date-picker v-model="form.endDate" type="date" placeholder="选择结束日期" value-format="yyyy-MM-dd"
            style="width: 100%;" :readonly="true"></el-date-picker>
        </el-form-item>
        <el-form-item label="发布人">
          <el-input v-model="form.publisherName" placeholder="请输入通知发布人~" readonly />
        </el-form-item>
        <el-form-item label="发布时间">
          <el-input v-model="form.publishTime" readonly />
        </el-form-item>
        <el-form-item label="详情">
          <el-input type="textarea" :rows="2" v-model="form.details" placeholder="请输入通知详情~" :readonly="true" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false"> 取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import axios from 'axios'
export default {
  data() {
    return {
      username: '',
      dialogFormVisible: false,
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
      currentTime: '',
      value: new Date(),
      isOn: false,
      isOff: false,
      isCheck: true,
      noticeList: [],
      checkForm: {
        id: '',
        employeeID: sessionStorage.getItem("username"),
        date: '',
        remarks: '',
        time: '',
        checkOnTime: '',
        checkOffTime: ' ',
        checkOnStatus: '',
        checkOffStatus: '',
      },
      isLeave: false,
      jitangList: [
        { jitang: "星光不问赶路人，时光不负有心人。" },
        // {jitang:"生活不是等着暴风雨过去，而是学会在风雨中跳舞。"},
        { jitang: "不要等待机会，而要创造机会。" },
        { jitang: "没有伞的孩子，必须努力奔跑。" },
        { jitang: "不为失败找理由，要为成功找方法。" },
        // {jitang:"一切伟大的行动和思想，都有一个微不足道的开始。"},
      ],
      jitangNumber: 0,
      isDuoyun: false,
      isXiayu: false,
      isXiaxue: false,
      isYin: false,
      isSunny: false,
      onTime: "08:00",
      offTime: "17:30"
    };
  },
  created() {
    this.getUsername();
    this.getNotice();
    this.getCheckOn();
    this.getCheckOff();
    this.getJiTang();
    this.updateTime();
    // 每秒更新时间
    setInterval(this.updateTime, 1000);
    this.getAttendanceTime()
  },
  methods: {
    getAttendanceTime() {
      // 获取出勤时间
      axios.get('/enum/getAttendanceTime').then(res => {
        this.onTime = res.data.split("-")[0]
        this.offTime = res.data.split("-")[1]
      }).catch((e) => {
        console.log(e)
      })
    },
    showAnnouncement(row) {
      // 查询公告
      this.form = Object.assign({}, row) // copy obj
      this.dialogFormVisible = true;
    },
    updateTime() {
      const date = new Date();
      const hours = this.formatTime(date.getHours());
      const minutes = this.formatTime(date.getMinutes());
      const seconds = this.formatTime(date.getSeconds());
      this.currentTime = `${hours}:${minutes}:${seconds}`;
    },
    formatTime(time) {
      return time < 10 ? `0${time}` : time;
    },
    getUsername() {
      axios.post('/employee/findByNumber', this.form).then(res => {

        if (!document.cookie.split(';').some(c => c.trim().startsWith('LOGIN='))) {

          this.$router.push("/");
          this.$message({ message: "未登录或登录信息过期", type: "error" });
          return
        }
        this.username = res.data.name;
      }).catch((e) => {
        console.log(e)
      })
    },
    goTo(path) {
      this.$router.push(path);
    },
    checkOn() {
      if (this.isLeave) {
        this.$message({ message: "请假无需打卡", type: "error" });
      } else if (this.isOn) {
        this.$message({ message: "请勿重复打卡", type: "error" });
      } else {
        this.checkForm.checkOnTime = this.getTime();
        axios.post("/check/checkOn", this.checkForm).then(res => {
          this.$message({ message: "上班打卡成功", type: "success" });
          this.isOn = true;
          this.isCheck = false;
        })
      }
    },
    checkOff() {
      if (this.isOff) {
        this.$message({ message: "请勿重复打卡", type: "error" });
      } else {
        this.checkForm.checkOffTime = this.getTime();
        axios.post("/check/checkOff", this.checkForm).then(res => {
          this.$message({ message: "下班打卡成功", type: "success" });
          this.isOff = true;
          this.isCheck = true;
        })
      }
    },
    getCheckOn() {
      // 查询是否已经上班打卡
      this.checkForm.checkOnTime = this.getTime();
      axios.post("/check/getCheckOn", this.checkForm).then(res => {
        if (res.data == '0') {
          this.isOn = false;
        } else if (res.data == '1') {
          this.isOn = true;
          this.isCheck = false;
        } else if (res.data == '2') {
          this.isLeave = true;
        }
        this.resetCheckForm();
      })
    },
    getCheckOff() {
      // 查询是否已经下班打卡
      this.checkForm.checkOffTime = this.getTime();
      axios.post("/check/getCheckOff", this.checkForm).then(res => {
        if (res.data == '0') {
          this.isOff = false;
        } else if (res.data == '1') {
          // 下班已打卡

          this.isOff = true;
          this.isCheck = true;
        }
        this.resetCheckForm();
      })
    },
    getNotice() {
      axios.get("/meeting/getNotice").then(res => {
        this.noticeList = res.data;
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
    resetCheckForm() {
      this.checkForm = {
        id: '',
        employeeID: sessionStorage.getItem("username"),
        date: '',
        remarks: '',
        time: '',
        checkOnTime: '',
        checkOffTime: '',
        checkOnStatus: '',
        checkOffStatus: '',
      }
    },
    getJiTang() {
      this.jitangNumber = Math.round(Math.random() * 3)
    }
  }
}
</script>

<style>
.today-tips {

  font-weight: bold;
}

.el-carousel__item h3 {
  color: #475669;
  font-size: 14px;
  opacity: 0.75;
  line-height: 500px;
  margin: 0;
}

.click-now {
  margin-top: 10px;
}

.el-carousel__item:nth-child(2n) {
  background-color: #99a9bf;
}

.el-carousel__item:nth-child(2n+1) {
  background-color: #d3dce6;
}

.announcement,
.to-attendance {
  font-weight: bold;
}

.welcome {
  font-size: 32px;
}
</style>
