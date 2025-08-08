<template>
  <div class="pro-container">
    <div class="filter-container" style="text-align: left;">
      <div class="letf-items">
        <el-select v-model="form1.departmentID" clearable placeholder="请选择部门">
          <el-option v-for="item in departmentList" :key="item.id" :label="item.name" :value="item.id"></el-option>
        </el-select>
        <el-input v-model="form1.name" placeholder="请输入姓名" style="width: 200px;" class="filter-item"
          @keyup.enter.native="handleFilter" clearable />
        <br>
        <br>
        <el-button class="filter-item" type="primary" @click="onSubmit"> 查询</el-button>
        <el-button class="filter-item" style="margin-left: 10px;" type="success" @click="handleCreate"> 新增</el-button>
      </div>
    </div>
    <div>
      <el-table :data="tableData" border fit highlight-current-row style="width: 100%" ref="employeeList">
        <el-table-column prop="number" label="编号"></el-table-column>
        <el-table-column prop="name" label="姓名"></el-table-column>
        <el-table-column prop="sex" label="性别"></el-table-column>
        <el-table-column prop="birthday" label="出生日期"></el-table-column>
        <el-table-column prop="departmentName" label="所属部门"></el-table-column>
        <el-table-column prop="positionName" label="职位"></el-table-column>
        <el-table-column prop="entryDate" label="入职日期"></el-table-column>
        <el-table-column label="操作" width="260" fixed="right">
          <template slot-scope="scope">
            <el-button type="success" @click="handleUpdate(scope.row)"> 编辑</el-button>
            <el-button type="danger" @click="handleDelete(scope.row)"> 删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <el-dialog :title="dialogTitle" :visible.sync="dialogFormVisible">
      <el-form ref="form" :rules="rules" :model="form" label-position="left" label-width="120px"
        style="width: 420px; margin-left:50px;">
        <el-form-item label="编号" prop="number">
          <el-input v-model="form.number" placeholder="请输入编号" />
        </el-form-item>
        <el-form-item label="姓名" prop="name">
          <el-input v-model="form.name" placeholder="请输入姓名" />
        </el-form-item>
        <el-form-item label="性别">
          <el-select v-model="form.sex" clearable placeholder="请选择" style="width: 100%;">
            <el-option v-for="item in sexList" :key="item.id" :label="item.value" :value="item.value"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="出生日期">
          <el-date-picker type="date" placeholder="选择日期" v-model="form.birthday" style="width: 100%;"
            value-format="yyyy-MM-dd" :editable="false"></el-date-picker>
        </el-form-item>
        <el-form-item label="身份证号码" prop="idNumber">
          <el-input v-model="form.idNumber" placeholder="请输入身份证号码~" />
        </el-form-item>
        <el-form-item label="学历">
          <el-select v-model="form.education" clearable placeholder="请选择" style="width: 100%;">
            <el-option v-for="item in educationList" :key="item.id" :label="item.name" :value="item.name"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="婚姻状况">
          <el-select v-model="form.marriage" clearable placeholder="请选择" style="width: 100%;">
            <el-option v-for="item in marriageList" :key="item.id" :label="item.value" :value="item.value"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="所属部门">
          <el-select v-model="departmentID" clearable placeholder="请选择" style="width: 100%;">
            <el-option v-for="item in departmentList" :key="item.id" :label="item.name" :value="item.id"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="职位" prop="position">
          <el-select v-model="form.position" clearable placeholder="请选择" style="width: 100%;">
            <el-option v-for="item in positionList" :key="item.id" :label="item.name" :value="item.id"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="入职日期" prop="entryDate">
          <el-date-picker type="date" placeholder="选择日期" v-model="form.entryDate" style="width: 100%;"
            value-format="yyyy-MM-dd" :editable="false"></el-date-picker>
        </el-form-item>
        <el-form-item label="联系方式">
          <el-input v-model="form.phone" placeholder="请输入联系方式~" />
        </el-form-item>
        <el-form-item label="家庭住址">
          <el-input v-model="form.address" placeholder="请输入家庭住址~" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false"> 取 消</el-button>
        <el-button v-if="isCreate" type="primary" @click="createData()"> 新增</el-button>
        <el-button v-if="isUpdate" type="primary" @click="updateData()"> 修改</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import axios from 'axios'
export default {
  data() {
    return {
      tableData: [

      ],
      departmentList: [
      ],
      departmentName: '',
      dialogFormVisible: false,
      dialogTitle: '',
      form: {
        id: '',
        number: '',
        name: '',
        sex: '',
        birthday: '',
        //departmentName: '',
        departmentID: '',
        position: '',
        type: '',
        entryDate: '',
        phone: '',
        address: '',
        idNumber: '',
        education: '',
        employeeType: '',
        marriage: '',
        workStatus: '',
      },
      form1: {
        id: '',
        number: '',
        name: '',
        sex: '',
        birthday: '',
        //departmentName: '',
        departmentID: '',
        position: '',
        type: '',
        entryDate: '',
        phone: '',
        address: '',
        idNumber: '',
        education: '',
        employeeType: '',
        marriage: '',
        workStatus: '',
      },
      sexList: [
        {
          id: '1',
          value: '男',
        }, {
          id: '2',
          value: '女',
        },
      ],
      marriageList: [
        {
          id: '1',
          value: '未婚',
        }, {
          id: '2',
          value: '已婚',
        },
      ],
      id: '',
      isCreate: false,
      isUpdate: false,
      typeList: [],
      educationList: [
        {
          id: '1',
          name: '初中'
        }, {
          id: '2',
          name: '高中'
        }, {
          id: '3',
          name: '中专'
        }, {
          id: '4',
          name: '大专'
        }, {
          id: '5',
          name: '本科'
        }, {
          id: '6',
          name: '硕士'
        }, {
          id: '7',
          name: '博士及以上'
        },
      ],
      positionList: [

      ],
      departmentID: '',
      rules: {
        number: [
          { required: true, message: '请输入编号', trigger: 'blur' },
          { min: 3, max: 15, message: '长度在 3 到 15 个字符', trigger: 'blur' }
        ],
        name: [
          { required: true, message: '请输入姓名', trigger: 'blur' },
          { min: 2, max: 15, message: '长度在 2 到 15 个字符', trigger: 'blur' }
        ],
        entryDate: [
          { required: true, message: '请选择入职日期', trigger: 'change' },
        ],
        idNumber: [
          { required: true, message: '身份证号码不能为空', trigger: 'blur' },
        ],
        position: [
          { required: true, message: '职位不能为空', trigger: 'change' },
        ],
      }
    }
  },
  created() {
    this.getData();
    this.getDepartmentList();
    this.getTypeList();
  },
  watch: {
    departmentID: function (val) {
      if (this.isCreate) {
        this.form.position = '';
      }
      this.form.departmentID = val;
      this.getPosition();
    }
  },
  methods: {
    getData() {
      axios.get("/employee/list").then(res => {

        if (!document.cookie.split(';').some(c => c.trim().startsWith('LOGIN='))) {

          this.$router.push("/");
          this.$message({ message: "未登录或登录信息过期", type: "error" });
          return
        }
        this.tableData = res.data;
      })
    },
    getDepartmentList() {
      axios.get("/department/list").then(res => {
        this.departmentList = res.data;
      })
    },
    getTypeList() {
      axios.get("/employeeType/list").then(res => {
        this.typeList = res.data;
      })
    },
    resetTemp() {
      this.form = {
        id: '',
        number: '',
        name: '',
        sex: '',
        birthday: '',
        //departmentName: '',
        departmentID: '',
        position: '',
        type: '',
        entryDate: '',
        phone: '',
        address: '',
        idNumber: '',
        education: '',
        employeeType: '',
        marriage: '',
        workStatus: '',
      };
      this.departmentID = '';
    },
    resetForm() {
      this.form = {
        id: '',
        number: '',
        name: '',
        sex: '',
        birthday: '',
        //departmentName: '',
        departmentID: '',
        position: '',
        type: '',
        entryDate: '',
        phone: '',
        address: '',
        idNumber: '',
        education: '',
        employeeType: '',
        marriage: '',
        workStatus: '',
      };
      this.departmentID = '';
    },
    handleCreate() {
      //this.resetTemp()
      this.dialogTitle = "添加信息"
      this.isCreate = true
      this.isUpdate = false
      this.dialogFormVisible = true
      this.resetForm()
    },
    handleUpdate(row) {
      this.form = Object.assign({}, row) // copy obj
      this.departmentID = this.form.departmentID
      this.dialogTitle = "编辑信息"
      this.isUpdate = true
      this.isCreate = false
      this.dialogFormVisible = true
    },
    handleDelete(row) {
      if (row) {
        this.id = row.id
      }
      this.$confirm("确定删除 " + row.name + " ?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      }).then(() => {
        axios.post("/employee/deleteById", {
          id: this.id,
        }).then((res) => {
          console.log(res.data)
          if (res.data == 200) {
            this.$message({ message: "删除成功", type: "success" });
            this.getData();
          }
        });
      }).catch(() => {

      });
    },
    onSubmit() {
      axios.post('/employee/findByNameAndDepartment', this.form1).then(res => {
        this.tableData = res.data;
      })
    },
    createData() {
      this.$refs['form'].validate((valid) => {
        if (valid) {
          axios.post('/employee/insert', this.form).then(res => {
            if (res.data.code == 200) {
              this.dialogFormVisible = false;
              this.$message({ message: "添加成功", type: "success" });
              this.getData();
            } else if (res.data.code == 20005) {
              this.$message({ message: "该编号已存在", type: "error" });
            }
          })
        } else {
          //console.log('error submit!!');
          return false;
        }
      });
    },
    updateData() {
      axios.post('/employee/update', this.form).then(res => {
        if(res.data == "1") {

          this.dialogFormVisible = false;
          this.$message({ message: "修改成功", type: "success" });
          this.getData();
        }
      })
    },
    findByNameAndDepartment() {
      axios.post('/employee/findByNameAndDepartment', this.form).then(res => {
        this.tableData = res.data;
      })
    },
    getPosition() {
      axios.post('/position/findByDepartmentID', {
        departmentID: this.form.departmentID,
      }).then(res => {
        this.positionList = res.data;
      })
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
}

.filter-container {
  margin-bottom: 20px;
}</style>