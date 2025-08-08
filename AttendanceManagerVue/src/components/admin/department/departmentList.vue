<template>
  <el-table
    :data="tableData"
    border
    style="width: 100%">
    <el-table-column
      prop="number"
      label="部门编号"
      width="180">
    </el-table-column>
    <el-table-column
      prop="name"
      label="部门名称"
      width="180">
    </el-table-column>
    <el-table-column
      prop="quantity"
      label="部门人数"
      width="180">
    </el-table-column>
  </el-table>
</template>

<script>
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
        }]
      }
    }
  }
</script>