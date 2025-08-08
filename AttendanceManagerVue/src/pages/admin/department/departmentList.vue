<template>
<div class="pro-container">
  <div class="filter-container" style="display: flex;">
    <div class="letf-items">
	    <el-button class="filter-item" style="margin-left: 10px;" type="primary"  @click="handleCreate"> 新增部门</el-button>
    </div>
    <div class="right-items">
	    <el-button class="filter-item" style="margin-left: 10px;" type="primary"  @click="handleCreate2"> 新增岗位</el-button>
    </div>
  </div>
  <div style="margin-top: 10px;">
    <el-table :data="tableData" border fit highlight-current-row style="width: 100%">
      <el-table-column prop="number" label="部门编号"></el-table-column>
      <el-table-column prop="name" label="部门名称"></el-table-column>
      <el-table-column prop="quantity" label="部门人数"></el-table-column>
      <el-table-column prop="posNum" label="岗位数量"></el-table-column>
      <el-table-column label="操作" width="430" fixed="right">
	      <template slot-scope="scope">
          <el-button type="primary" @click="getPosition(scope.row)"> 查看岗位</el-button>
	        <el-button type="success" @click="handleUpdate(scope.row)"> 编辑</el-button>
	        <el-button type="danger" @click="handleDelete(scope.row)"> 删除</el-button>
	      </template>
	    </el-table-column>
    </el-table>
  </div>
<el-dialog :title="dialogTitle" :visible.sync="dialogFormVisible">
	<el-form ref="form" :rules="rules" :model="form" label-position="left" label-width="120px" style="width: 420px; margin-left:50px;">
		<el-form-item label="编号" prop="number">
		  <el-input v-model="form.number" placeholder="请输入部门编号~"/>
		</el-form-item>
		<el-form-item label="名称" prop="name">
		  <el-input v-model="form.name" placeholder="请输入部门名称~"/>
		</el-form-item>
    <el-form-item label="部门人数" v-if="isUpdate" >
		  <el-input v-model="form.quantity" :readonly="isUpdate"/>
		</el-form-item>
    <el-form-item label="岗位数量" v-if="isUpdate">
		  <el-input v-model="form.posNum" :readonly="isUpdate"/>
		</el-form-item>
	</el-form>
	<div slot="footer" class="dialog-footer">
		<el-button @click="dialogFormVisible = false"> 取消</el-button>
		<el-button v-if="isCreate" type="success" @click="createData()"> 新增</el-button>
    <el-button v-if="isUpdate" type="success" @click="updateData()"> 修改</el-button>
	</div>
</el-dialog>
<el-dialog :title="dialogTitle2" :visible.sync="dialogFormVisible2">
	<el-form ref="form2" :rules="rules2" :model="form2" label-position="left" label-width="120px" style="width: 420px; margin-left:50px;">
		<el-form-item label="编号" prop="number">
		  <el-input v-model="form2.number" placeholder="请输入岗位编号~"/>
		</el-form-item>
    <el-form-item label="岗位类别" prop="typeID">
		  <el-select v-model="form2.typeID" clearable placeholder="请选择" style="width: 100%;">
        <el-option v-for="item in typeList" :key="item.id" :label="item.name" :value="item.id"></el-option>
      </el-select>
		</el-form-item>
    <el-form-item label="所属部门" prop="departmentID">
		  <el-select v-model="form2.departmentID" clearable placeholder="请选择" style="width: 100%;">
        <el-option v-for="item in tableData" :key="item.id" :label="item.name" :value="item.id"></el-option>
      </el-select>
		</el-form-item>
		<el-form-item label="名称" prop="name">
		  <el-input v-model="form2.name" placeholder="请输入岗位名称~"/>
		</el-form-item>
    <el-form-item label="薪资" prop="monthlySalary">
		  <el-input v-model="form2.monthlySalary" placeholder="请输入岗位薪资~"/>
		</el-form-item>
	</el-form>
	<div slot="footer" class="dialog-footer">
		<el-button @click="dialogFormVisible2 = false"> 取消</el-button>
		<el-button v-if="isCreate" type="success" @click="createData2()"> 新增</el-button>
    <el-button v-if="isUpdate" type="success" @click="updateData2()"> 修改</el-button>
	</div>
</el-dialog>
<el-dialog title="岗位列表" :visible.sync="dialogFormVisible3">
  <el-table :data="positionList" border fit highlight-current-row style="width: 100%">
    <el-table-column prop="number" label="岗位编号"></el-table-column>
    <el-table-column prop="name" label="岗位名称"></el-table-column>
    <el-table-column prop="monthlySalary" label="岗位薪资"></el-table-column>
    <el-table-column prop="quantity" label="在职人数"></el-table-column>
    <el-table-column label="操作" width="260" fixed="right">
	    <template slot-scope="scope">
        <el-button type="success" @click="handleUpdate2(scope.row)"> 编辑</el-button>
        <el-button type="danger" @click="handleDelete2(scope.row)"> 删除</el-button>
	    </template>
	  </el-table-column>
  </el-table>
</el-dialog>
</div>
</template>

<script>
import axios from 'axios'
  export default {
    data() {
      return {
        tableData: [{
          number: '2016-05-02',
          name: '王小虎',
          quantity: '上海市普陀区金沙江路 1518 弄'
        }, {
          number: '2016-05-04',
          name: '王小虎',
          quantity: '上海市普陀区金沙江路 1517 弄'
        }, {
          number: '2016-05-01',
          name: '王小虎',
          quantity: '上海市普陀区金沙江路 1519 弄'
        }, {
          number: '2016-05-03',
          name: '王小虎',
          quantity: '上海市普陀区金沙江路 1516 弄'
        }],
        dialogFormVisible: false,//新增编辑部门的弹窗
        dialogTitle:'',
        form: {
          id: '',
          number: '',
          name: '',
          quantity: '',
          posNum: '',
        },
        dialogFormVisible2: false,//新增岗位的弹窗
        dialogTitle2:'',
        form2: {
          id: '',
          number: '',
          name: '',
          quantity: '',
          monthlySalary: '',
          departmentID: '',
          typeID: '',
        },
        id: '',
        isCreate: false,
        isUpdate: false,
        dialogFormVisible3: false,//查看岗位的弹窗
        positionList: [],
        typeList: [],
        rules: {
          number: [
            { required: true, message: '请输入部门编号', trigger: 'blur' },
            { min: 3, max: 5, message: '长度在 3 到 15 个字符', trigger: 'blur' }
          ],
          name: [
            { required: true, message: '请输入部门名称', trigger: 'blur' },
            { min: 3, max: 5, message: '长度在 3 到 15 个字符', trigger: 'blur' }
          ],
        },
        rules2: {
          number: [
            { required: true, message: '请输入岗位编号', trigger: 'blur' },
            { min: 3, max: 5, message: '长度在 3 到 15 个字符', trigger: 'blur' }
          ],
          name: [
            { required: true, message: '请输入岗位名称', trigger: 'blur' },
            { min: 3, max: 5, message: '长度在 3 到 15 个字符', trigger: 'blur' }
          ],
          typeID: [
            { required: true, message: '请选择岗位类型', trigger: 'change' },
          ],
          departmentID: [
            { required: true, validator: this.checkDepartment, trigger: 'change' },
          ],
          monthlySalary: [
            { required: true, message: '薪资不能为空', trigger: 'blur' }
          ],
        }
      }
    },
    created() {
      this.getData();
      this.getTypeList();
    },
    methods: {
      getData(){
        axios.get("/department/list").then(res => {
          this.tableData = res.data;
        })
      },
      getTypeList(){
        axios.get("/employeeType/list").then(res => {
          this.typeList = res.data;
        })
      },
      resetForm1() {
	      this.form = {
          id: '',
          number: '',
          name: '',
          quantity: '',
          posNum: '',
        }
      },
      resetForm2() {
	      this.form2 = {
          id: '',
          number: '',
          name: '',
          quantity: '',
          monthlySalary: '',
          departmentID: '',
          typeID: '',
        }
      },
      handleCreate(){
        //this.resetTemp()
        this.dialogTitle = "添加部门"
        this.isCreate = true
        this.isUpdate = false
        this.dialogFormVisible = true
        this.resetForm1()
      },
      handleCreate2(){
        //this.resetTemp2()
        this.dialogTitle2 = "新增岗位"
        this.isCreate = true
        this.isUpdate = false
        this.dialogFormVisible2 = true
        this.resetForm2()
      },
      handleUpdate(row){
        //this.tempForm = this.$wtables.getSelectedRowData(this, "employeeList");
        this.form = Object.assign({}, row) // copy obj
        this.dialogTitle = "编辑部门信息"
        this.isUpdate = true
        this.isCreate = false
        this.dialogFormVisible = true
      },
      handleUpdate2(row){
        //this.tempForm = this.$wtables.getSelectedRowData(this, "employeeList");
        this.form2 = Object.assign({}, row) // copy obj
        this.dialogTitle2 = "编辑岗位信息"
        this.isUpdate = true
        this.isCreate = false
        this.dialogFormVisible2 = true
      },
      handleDelete(row){
        if(row){
          this.id = row.id
        }
        this.$confirm("确定删除该部门及以下所有岗位吗？", "提示", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        }).then(() => {
            axios.post("/department/deleteById", {
                id: this.id,
            }).then((res) => {
                if (res.data.code == 200) {
                  this.$message({ message: "删除成功", type: "success" });
                }else if(res.data.code == 50003){
                  this.$message({ message: "该部门还有员工不能删除", type: "error" });
                }
                this.getData();
              });
          });
      },
      handleDelete2(row){
        if(row){
          this.id = row.id
        }
        this.$confirm("确定删除该岗位吗？", "提示", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        }).then(() => {
            axios.post("/position/deleteById", {
                id: this.id,
            }).then((res) => {
                if (res.data.code == 200) {
                  this.$message({ message: "删除成功", type: "success" });
                }else if(res.data.code == 50003){
                  this.$message({ message: "该岗位还有员工不能删除", type: "error" });
                }
                this.getData();
              });
          });
      },
      onSubmit(){

      },
      createData(){
        this.$refs['form'].validate((valid) => {
          if (valid) {
            axios.post('/department/insert',this.form).then(res => {
              if(res.data.code == 200){
                this.dialogFormVisible = false;
                this.$message({ message: "添加成功", type: "success" });
                this.getData();
              }else if(res.data.code == 20005){
                this.$message({ message: "该编号已存在", type: "error" });
              }
            })
        } else {
            //console.log('error submit!!');
            return false;
          }
        });
      },
      updateData(){
        axios.post('/department/update',this.form).then(res => {
          //if(res.data.code == 200){
            this.dialogFormVisible = false;
            this.$message({ message: "修改成功", type: "success" });
            this.getData();
          //}else if(res.data.code == 20005){
            //this.$message({ message: "该编号已存在", type: "error" });
          //}
        })
      },
      createData2(){
        this.$refs['form2'].validate((valid) => {
          if (valid) {
            axios.post('/position/insert',this.form2).then(res => {
              if(res.data.code == 200){
                this.dialogFormVisible2 = false;
                this.dialogFormVisible3 = false;
                this.$message({ message: "添加成功", type: "success" });
                this.getData();
              }else if(res.data.code == 20005){
                this.$message({ message: "该编号已存在", type: "error" });
              }
            })
        } else {
            //console.log('error submit!!');
            return false;
          }
        });
      },
      updateData2(){
        axios.post('/position/update',this.form2).then(res => {
          //if(res.data.code == 200){
            this.dialogFormVisible2 = false;
            this.dialogFormVisible3 = false;
            this.$message({ message: "修改成功", type: "success" });
            this.getData();
          //}else if(res.data.code == 20005){
            //this.$message({ message: "该编号已存在", type: "error" });
          //}
        })
      },
      getPosition(row){
        axios.post('/position/findByDepartmentID',{
          departmentID : row.id,
        }).then(res => {
          this.positionList = res.data;
          this.dialogFormVisible3 = true;
          this.dialogTitle3 = "岗位列表"
        })
      },
      checkDepartment(rule, value, callback){
        if(this.form2.typeID){
          
          if(!value){
              callback(new Error('部门不能为空！'));
            }else{
              callback()
            }
          // if(this.form2.typeID == '1' || this.form2.typeID == '2'){
          //   if(!value){
          //     callback(new Error('部门不能为空！'));
          //   }else{
          //     callback()
          //   }
          // }else{
          //   if(value){
          //     callback(new Error('公司高层不能选择部门！'));
          //   }else{
          //     callback()
          //   }
          // }
        }else{
          if(!value){
            callback(new Error('部门不能为空1！'));
          }else{
            callback()
          }
        }
      }
    }
  }
</script>
</script>
<style scoped>
  .pro-container {
    padding: 15px 32px;
    margin: 4px 2px;
  }
  .fenye {
	  margin-top: 10px;
  }
</style>
