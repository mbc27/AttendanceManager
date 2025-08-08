<template>
  <el-table
    :data="tableData"
    border
    style="width: 100%">
    <el-table-column
      prop="number"
      label="员工编号"
      width="180">
    </el-table-column>
    <el-table-column
      prop="name"
      label="姓名"
      width="180">
    </el-table-column>
    <el-table-column
      prop="sex"
      label="性别"
      width="180">
    </el-table-column>
    <el-table-column
      prop="age"
      label="年龄"
      width="180">
    </el-table-column>
    <el-table-column
      prop="departName"
      label="所属部门"
      width="180">
    </el-table-column>
    <el-table-column
      prop="position"
      label="职位"
      width="180">
    </el-table-column>
    <el-table-column
      prop="type"
      label="类别"
      width="180">
    </el-table-column>
    <el-table-column
      prop="phone"
      label="联系方式"
      width="180">
    </el-table-column>
    <el-table-column
      prop="address"
      label="家庭住址">
    </el-table-column>
  </el-table>
</template>

<script>
  export default {
    data() {
      return {
        tableData: [
        //   {
        //   date: '2016-05-02',
        //   name: '王小虎',
        //   address: '上海市普陀区金沙江路 1518 弄'
        // }, {
        //   date: '2016-05-04',
        //   name: '王小虎',
        //   address: '上海市普陀区金沙江路 1517 弄'
        // }, {
        //   date: '2016-05-01',
        //   name: '王小虎',
        //   address: '上海市普陀区金沙江路 1519 弄'
        // }, {
        //   date: '2016-05-03',
        //   name: '王小虎',
        //   address: '上海市普陀区金沙江路 1516 弄'
        // }
        ]
      }
    },
    created() {
      this.getData();
    },
    methods: {
      getData(){
        this.$http.get("/employee/list").then(res => {
          this.tableData = res.data.data;
        })
      }
    }
  }
</script>