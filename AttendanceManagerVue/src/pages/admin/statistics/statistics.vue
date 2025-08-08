<template>
  <div>
    <el-row>
      <el-col :span="12">

        <div style="width: 600px; margin: 10px;">
          <div id="education"></div>
          <h3>员工学历分布</h3>
        </div>
      </el-col>
      <el-col :span="12">

        <div style="width: 50%; margin: 10px 10px 10px 100px;">
          <div id="age"></div>
          <h3>员工年龄分布</h3>
        </div>
      </el-col>
      <!-- <el-col :span="24">

        <div style="width: 400px; margin: 10px;">
          <div id="new"></div>
          <h3>近半年每月员工入职情况</h3>
        </div>
      </el-col> -->

    </el-row>
  </div>
</template>
<script>
import { Chart } from "@antv/g2";
import axios from 'axios'
export default {
  data() {
    return {
      new: [
        { time: '2023-11', value: 4 },
        { time: '2023-12', value: 3 },
        { time: '2024-01', value: 9 },
        { time: '2024-02', value: 5 },
        { time: '2024-03', value: 3 },
        { time: '2024-04', value: 7 }
      ],
      age: [
        { type: '18-24 岁', value: 22, percent: 0.22 },
        { type: '25-29 岁', value: 26, percent: 0.26 },
        { type: '30-39 岁', value: 28, percent: 0.28 },
        { type: '40-49 岁', value: 14, percent: 0.14 },
        { type: '50 岁以上', value: 6, percent: 0.06 },
      ],
      education: [
        { item: '初中', count: 6, percent: 0.06 },
        { item: '高中', count: 21, percent: 0.21 },
        { item: '中专', count: 13, percent: 0.13 },
        { item: '大专', count: 17, percent: 0.17 },
        { item: '本科', count: 40, percent: 0.4 },
        { item: '硕士', count: 2, percent: 0.02 },
        { item: '博士及以上', count: 1, percent: 0.01 },
      ]
    }
  },
  mounted() {
    this.getEducation();
    this.getNew();
    this.getAge();
    //this.initNew();
    //this.initAge();
    //this.initEducation();
  },
  methods: {
    getNew() {
      axios.get("/employee/getNew").then(res => {
        this.new = res.data;
        this.initNew();
      })
    },
    getAge() {
      axios.get("/employee/getAge").then(res => {
        this.age = res.data;
        this.initAge();
      })
    },
    getEducation() {
      axios.get("/employee/getEducation").then(res => {
        this.education = res.data;
        this.initEducation();
      })
    },
    initNew() {
      const chart = new Chart({
        container: 'new',
        autoFit: true,
        height: 500,
      });
      chart.data(this.new);
      chart.scale('value', {
        alias: '销售额(万)',
        nice: true,
      });
      chart.axis('time', {
        tickLine: null
      });
      chart.tooltip({
        showMarkers: false
      });
      chart.interaction('active-region');
      chart.interval().position('time*value')
        .style('time', val => {
          return {
            fillOpacity: 1,
            lineWidth: 0,
            stroke: '#636363',
            lineDash: [3, 2]
          };
        });
      chart.render();
    },
    initAge() {
      const chart = new Chart({
        container: 'age',
        autoFit: true,
        height: 500,
        padding: [50, 20, 50, 20],
      });
      chart.data(this.age);
      chart.scale('value', {
        alias: '销售额(万)',
      });
      chart.axis('type', {
        tickLine: {
          alignTick: false,
        },
      });
      chart.axis('value', false);
      chart.tooltip({
        showMarkers: false,
      });
      chart.interval().position('type*value');
      chart.interaction('element-active');
      // 添加文本标注
      this.age.forEach((item) => {
        chart
          .annotation()
          .text({
            position: [item.type, item.value],
            content: item.value,
            style: {
              textAlign: 'center',
            },
            offsetY: -30,
          })
          .text({
            position: [item.type, item.value],
            content: (item.percent * 100).toFixed(0) + '%',
            style: {
              textAlign: 'center',
            },
            offsetY: -12,
          });
      });
      chart.render();
    },
    initEducation() {
      const chart = new Chart({
        container: 'education',
        autoFit: true,
        height: 500,
      });
      chart.coordinate('theta', {
        radius: 0.75,
      });
      chart.data(this.education);
      chart.scale('percent', {
        formatter: (val) => {
          val = val * 100 + '%';
          return val;
        },
      });
      chart.tooltip({
        showTitle: false,
        showMarkers: false,
      });
      chart
        .interval()
        .position('percent')
        .color('item')
        .label('percent', {
          content: (data) => {
            return `${data.item}: ${data.percent * 100}%`;
          },
        })
        .adjust('stack');
      chart.interaction('element-active');
      chart.render();
    }
  },
}
</script>
