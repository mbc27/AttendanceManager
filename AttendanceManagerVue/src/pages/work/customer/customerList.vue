<template>
<div class="pro-container">
  <div style="text-align: left;">
    <div>
	    <el-select v-model="form1.type" clearable placeholder="请选择客户类别">
            <el-option v-for="item in customerTypeList" :key="item.id" :label="item.value" :value="item.value"></el-option>
        </el-select>
	    <el-input v-model="form1.name" placeholder="请输入名称" style="width: 200px;" class="filter-item" @keyup.enter.native="handleFilter" clearable/>
      <br>
      <br>
    </div>
    <el-row>
      <el-button class="filter-item" type="primary"  @click="onSubmit"> 查询</el-button>  	 	
      <el-button class="filter-item" style="margin-left: 10px;" type="primary"  @click="handleCreate"> 新增</el-button>

    </el-row>
    <div class="right-items">
        <!-- <el-button class="filter-item" style="margin-left: 10px;" type="primary"  >修   改</el-button>
	    <el-button class="filter-item" style="margin-left: 10px;" type="primary"  >删  除</el-button> -->
    </div>
  </div>
  <div style="margin-top: 20px;">
    <el-table :data="tableData" border fit highlight-current-row style="width: 100%">
      <el-table-column prop="number" label="客户编号"></el-table-column>
      <el-table-column prop="name" label="姓名"></el-table-column>
      <el-table-column prop="phone" label="联系方式"></el-table-column>
      <el-table-column prop="address" label="地址"></el-table-column>
      <el-table-column prop="type" label="类别"></el-table-column>
      <el-table-column prop="remarks" label="备注"></el-table-column>
      <el-table-column label="操作" width="260" fixed="right">
	      <template slot-scope="scope">
	        <el-button type="success" @click="handleUpdate(scope.row)"> 编辑</el-button>
	        <el-button type="danger" @click="handleDelete(scope.row)"> 删除</el-button>
	      </template>
	  </el-table-column>
    </el-table>
  </div>
<el-dialog :title="dialogTitle" :visible.sync="dialogFormVisible" >
	<el-form ref="form" :rules="rules" :model="form" label-position="left" label-width="120px" style="width: 420px; margin-left:50px;">
		<el-form-item label="编号" prop="number">
		  <el-input v-model="form.number" placeholder="请输入客户编号~"/>
		</el-form-item>
		<el-form-item label="姓名名称" prop="name">
		  <el-input v-model="form.name" placeholder="请输入客户姓名或名称~"/>
		</el-form-item>
        <el-form-item label="类别" prop="type">
		  <el-select v-model="form.type" clearable placeholder="请选择" style="width: 100%;">
            <el-option v-for="item in customerTypeList" :key="item.id" :label="item.value" :value="item.value"></el-option>
          </el-select>
		</el-form-item>
        <el-form-item label="联系方式">
		  <el-input v-model="form.phone" placeholder="请输入联系方式~"/>
		</el-form-item>
        <el-form-item label="地址">
		  <el-input v-model="form.address" placeholder="请输入地址~"/>
		</el-form-item>
        <el-form-item label="备注">
		  <el-input v-model="form.remarks" placeholder="请输入备注~"/>
		</el-form-item>
	</el-form>
	<div slot="footer" class="dialog-footer">
		<el-button @click="dialogFormVisible = false"> 取 消</el-button>
		<el-button v-if="isCreate" type="primary" @click="createData()"> 新增</el-button>
    <el-button v-if="isUpdate" type="primary" @click="updateData()">修 改</el-button>
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
        customerType:'',
        customerTypeList:[
            {
                id: '1',
                value: '个人',
            },{
                id: '2',
                value: '公司',
            },
        ],
        dialogFormVisible: false,
        dialogTitle:'',
        form: {
          id: '',
          number: '',
          name: '',
          type: '',
          phone: '',
          address: '',
          remarks: '',
          applyNumber: '',
        },
        form1: {
          id: '',
          number: '',
          name: '',
          type: '',
          phone: '',
          address: '',
          remarks: '',
          applyNumber: '',
        },
        id: '',
        isCreate: false,
        isUpdate: false,
        number: sessionStorage.getItem("username"),
        employeeType: '',
        rules: {
          number: [
            { required: true, message: '客户编号不能为空', trigger: 'blur' }
          ],
          name: [
            { required: true, message: '客户名称不能为空', trigger: 'blur' }
          ],
          type: [
            { required: true, message: '请选择客户类型', trigger: 'change' }
          ],
        }
      }
    },
    created() {
      this.getData();
    },
    methods: {
      getData(){
        axios.post("/employee/findByNumber",{
          number: this.number,
        }).then(res => {
          if(res.data.type == "3"){
            axios.get("/customer/list").then(res => {
              this.tableData = res.data;
            })
          }else{
            axios.post("/customer/findByApplyNumber",{
              applyNumber: this.number,
            }).then(res => {
              this.tableData = res.data;
            })
          }
        })
      },
      resetTemp() {
	      this.form = {
	        id: '',
          number: '',
          name: '',
          type: '',
          phone: '',
          address: '',
          remarks: '',
	      }
      },
      resetForm() {
        this.form = {
          id: '',
          number: '',
          name: '',
          type: '',
          phone: '',
          address: '',
          remarks: '',
          applyNumber: '',
        }
      },
      handleCreate(){
        //this.resetTemp()
        this.dialogTitle = "添加客户"
        this.isCreate = true
        this.isUpdate = false
        this.dialogFormVisible = true
        this.resetForm()
      },
      handleUpdate(row){
        //this.tempForm = this.$wtables.getSelectedRowData(this, "employeeList");
        this.form = Object.assign({}, row) // copy obj
        this.dialogTitle = "编辑客户信息"
        this.isUpdate = true
        this.isCreate = false
        this.dialogFormVisible = true
      },
      handleDelete(row){
        if(row){
          this.id = row.id
        }
        this.$confirm("确定删除？"+row.id, "提示", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        }).then(() => {
            axios.post("/customer/deleteById", {
                id: this.id,
            }).then((res) => {
                //if (res.data.code == 200) {
                  this.$message({ message: "删除成功", type: "success" });
                  this.getData();
                //}
              });
          });
      },
      onSubmit(){
        axios.post('/customer/findByNameAndType',this.form1).then(res => {
          this.tableData = res.data;
        })
      },
      createData(){
        this.$refs['form'].validate((valid) => {
          if (valid) {
            this.form.applyNumber = this.number;
            axios.post('/customer/insert',this.form).then(res => {
              if(res.data.code == 200){
                this.dialogFormVisible = false;
                this.$message({ message: "添加成功", type: "success" });
                this.getData();
              }else if(res.data.code == 20005){
                this.$message({ message: "该编号已存在", type: "error" });
              }
            })
          } else {
            console.log('error submit!!');
            return false;
          }
        });
      },
      updateData(){
        axios.post('/customer/update',this.form).then(res => {
          this.dialogFormVisible = false;
          this.$message({ message: "修改成功", type: "success" });
          this.getData();
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
</style>