<template>
  <div class="home-page">
    <el-row :gutter="10">
      <el-card style="height: 260px;margin: 0 5px 10px;">
        <el-col :span="24" style="margin: 20px 0;">
          <span>今日值班概况</span>
        </el-col>
        <el-col :span="6">
          <el-card class="duty-card" @click.native="handleJump('email')">
            <svg-icon class="icon" icon-class="email" />
            <div>邮件数量</div>
            <span>{{emailNum}}</span>
          </el-card>
        </el-col>

        <el-col :span="6">
          <el-card class="duty-card" @click.native="handleJump('fax')">
            <svg-icon class="icon" icon-class="fax" />
            <div>传真数量</div>
            <span>{{faxNum}}</span>
          </el-card>
        </el-col>

        <el-col :span="6">
          <el-card class="duty-card" @click.native="handleJump('msm')">
            <svg-icon class="icon" icon-class="msm" />
            <div>短信数量</div>
            <span>{{msmNum}}</span>
          </el-card>
        </el-col>

        <el-col :span="6">
          <el-card class="duty-card" @click.native="handleJump('telephone')">
            <svg-icon class="icon" icon-class="telephone" />
            <div>电话数量</div>
            <span>{{telephoneNum}}</span>
          </el-card>
        </el-col>
      </el-card>
      <el-col :span="12" class="pie-duty">
        <el-card>
          <div slot="header"><span>值班统计</span></div>
          <div class="el-table el-table--enable-row-hover el-table--medium">
            <div ref="dutyCompare" style="height: 420px" />
          </div>
        </el-card>
      </el-col>

      <el-col :span="12" class="bar-login">
        <el-card>
          <div slot="header"><span>登录信息</span></div>
          <div class="el-table el-table--enable-row-hover el-table--medium">
            <div ref="loginBar" style="height: 420px" />
          </div>
        </el-card>
      </el-col>
      <el-col :span="24">
        <map-pearl-river />
      </el-col>
    </el-row>
  </div>
</template>

<script>
import { todayEmailNum, listEmail } from "@/api/duty/operemail";
import { todayFaxNum, listFax } from "@/api/duty/operfax";
import { todayMsmNum, listMsm } from "@/api/duty/opermsm";
import { todayTelephoneNum, listTelephone } from "@/api/duty/opertelephone";
import { list } from "@/api/monitor/logininfor";
import echarts from "echarts";
import MapPearlRiver from "./MapPearlRiver";
import axios from 'axios'

export default {
  name: "homePage",
  components: { MapPearlRiver },
  data() {
    return {
      emailNum: '',
      faxNum: '',
      msmNum: '',
      telephoneNum: '',
      // 值班统计信息
      dutyCompare: null,
      // 登录信息
      loginBar: null
    };
  },
  created() {
    this.getTodayDutyNum();
    this.getAllDutyNum();
    this.initUserChart();
  },
  methods: {
    getTodayDutyNum() {
      axios.all([
        todayEmailNum().then(res => res.data),
        todayFaxNum().then(res => res.data),
        todayMsmNum().then(res => res.data),
        todayTelephoneNum().then(res => res.data)
      ]).then(
        axios.spread((val1,val2,val3,val4) => {
          // val 是数组中每个接口返回的值 res.data
          this.emailNum = val1;
          this.faxNum = val2;
          this.msmNum = val3;
          this.telephoneNum = val4;
        })
      )
    },
    getAllDutyNum() {
      axios.all([
        listEmail().then(res => res.total),
        listFax().then(res => res.total),
        listMsm().then(res => res.total),
        listTelephone().then(res => res.total)
      ]).then(
        axios.spread((val1,val2,val3,val4) => {
          // val 是数组中每个接口返回的值 res.total
          this.initdutyChart(val1,val2,val3,val4)
        })
      )
    },
    initdutyChart(val1,val2,val3,val4) {
      if(!this.emailLoading && !this.emailLoading && !this.emailLoading && !this.emailLoading) {
        this.dutyCompare = echarts.init(this.$refs.dutyCompare, "macarons");
        this.dutyCompare.setOption({
          tooltip: {
            trigger: "item",
            formatter: "{a} <br/>{b} : {c} ({d}%)",
          },
          series: [
            {
              name: "值班",
              type: "pie",
              roseType: "radius",
              radius: [15, 150],
              center: ["50%", "50%"],
              data: [
                { value: val1, name: '邮件' },
                { value: val2, name: '传真' },
                { value: val3, name: '短信' },
                { value: val4, name: '电话' }
              ],
              animationEasing: "cubicInOut",
              animationDuration: 1000,
            }
          ]
        });
      }
    },
    initUserChart() {
      let myDate = new Date(); //获取今天日期
      myDate.setDate(myDate.getDate() - 6);
      const year = myDate.getFullYear();
      let dateArray = [];
      let dateTemp; 
      let flag = 1;
      for (let i = 0; i < 7; i++) {
          dateTemp = (myDate.getMonth()+1)+"-"+myDate.getDate();
          dateArray.push(dateTemp);
          myDate.setDate(myDate.getDate() + flag);
      };
      axios.all([
        list({params: {beginTime: year+"-"+dateArray[0], endTime: year+"-"+dateArray[0]}}).then(res => res.total),
        list({params: {beginTime: year+"-"+dateArray[1], endTime: year+"-"+dateArray[1]}}).then(res => res.total),
        list({params: {beginTime: year+"-"+dateArray[2], endTime: year+"-"+dateArray[2]}}).then(res => res.total),
        list({params: {beginTime: year+"-"+dateArray[3], endTime: year+"-"+dateArray[3]}}).then(res => res.total),
        list({params: {beginTime: year+"-"+dateArray[4], endTime: year+"-"+dateArray[4]}}).then(res => res.total),
        list({params: {beginTime: year+"-"+dateArray[5], endTime: year+"-"+dateArray[5]}}).then(res => res.total),
        list({params: {beginTime: year+"-"+dateArray[6], endTime: year+"-"+dateArray[6]}}).then(res => res.total)
      ]).then(
        axios.spread((val1,val2,val3,val4,val5,val6,val7) => {
          // val 是数组中每个接口返回的值 res.total
          const numArray = [val1,val2,val3,val4,val5,val6,val7];
          this.loginBar = echarts.init(this.$refs.loginBar);
          this.loginBar.setOption({
            tooltip: {
              trigger: 'axis',
              axisPointer: {
                type: 'shadow'
              }
            },
            xAxis: {
              type: 'category',
              data: dateArray
            },
            yAxis: {
              type: 'value'
            },
            series: [
              {
                data: numArray,
                type: 'bar',
                showBackground: true,
                backgroundStyle: {
                  color: 'rgba(180, 180, 180, 0.2)'
                }
              }
            ]
          });
        })
      )
    },
    handleJump(page) {
      this.$router.push("/duty/" + page );
    }
  }
};
</script>

<style scoped lang="scss">
.home-page {
  padding: 10px;
  .duty-card {
    text-align: center;
    line-height: 2;
    .icon {
      color: rgb(64, 158, 255)
    }
  }
}
</style>
