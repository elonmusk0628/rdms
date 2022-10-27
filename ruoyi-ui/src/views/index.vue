<template>
  <div class="home-page">
    <el-row :gutter="10">
      <el-card style="height: 260px;margin: 0 5px 10px;">
        <el-col :span="24" style="margin: 20px 0;">
          <span>今日值班概况</span>
        </el-col>
        <el-col :span="6">
          <el-card class="duty-card" @click.native="handleJump('email')">
            <svg-icon class="icon icon-email" icon-class="email" />
            <div class="num-detail">
              <div class="num-type">邮件数量</div>
              <span class="num">{{emailNum}}</span>
            </div>
          </el-card>
        </el-col>

        <el-col :span="6">
          <el-card class="duty-card" @click.native="handleJump('fax')">
            <svg-icon class="icon icon-fax" icon-class="fax" />
            <div class="num-detail">
              <div class="num-type">传真数量</div>
              <span class="num">{{faxNum}}</span>
            </div>
          </el-card>
        </el-col>

        <el-col :span="6">
          <el-card class="duty-card" @click.native="handleJump('msm')">
            <svg-icon class="icon icon-msm" icon-class="msm" />
            <div class="num-detail">
              <div class="num-type">短信数量</div>
              <span class="num">{{msmNum}}</span>
            </div>
          </el-card>
        </el-col>

        <el-col :span="6">
          <el-card class="duty-card" @click.native="handleJump('telephone')">
            <svg-icon class="icon icon-tel" icon-class="telephone" />
            <div class="num-detail">
              <div class="num-type">电话数量</div>
              <span class="num">{{telephoneNum}}</span>
            </div>
          </el-card>
        </el-col>
      </el-card>
      <el-col :span="12" class="pie-duty">
        <el-card>
          <div slot="header"><span>值班统计</span></div>
          <div class="el-table el-table--enable-row-hover el-table--medium">
            <div ref="dutyPie" style="height: 420px" />
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
      // 今日邮件数量
      emailNum: 0,
      // 今日传真数量
      faxNum: 0,
      // 今日短信数量
      msmNum: 0,
      // 今日电话数量
      telephoneNum: 0,
      // 值班统计信息
      dutyPie: null,
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
    /** 查询当日值班数量 */
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
    /** 查询所有值班数量 */
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
    /** 值班数量饼状图 */
    initdutyChart(val1,val2,val3,val4) {
      if(!this.emailLoading && !this.emailLoading && !this.emailLoading && !this.emailLoading) {
        this.dutyPie = echarts.init(this.$refs.dutyPie);
        this.dutyPie.setOption({
          tooltip: {
            trigger: "item",
            formatter: "{a} <br/>{b} : {c} ({d}%)",
          },
          legend: {
            left: 'center',
            top: 'bottom',
            data: [
              '邮件',
              '传真',
              '短信',
              '电话'
            ]
          },
          toolbox: {
            show: true,
            feature: {
              mark: { show: true },
              dataView: { show: true, readOnly: false },
              restore: { show: true },
              saveAsImage: { show: true }
            }
          },
          series: [
            {
              name: "值班",
              type: "pie",
              roseType: "radius",
              radius: [20, 190],
              center: ["50%", "50%"],
              itemStyle: {
                borderRadius: 5,
                normal: {
                  color: function (colors) {
                    let colorList = [
                      '#409EFF',
                      '#5470c6',
                      '#91cd77',
                      '#ef6567'
                    ];
                    return colorList[colors.dataIndex];
                  }
                }
              },
              emphasis: {
                label: {
                  show: true
                }
              },
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
    /** 用户登录情况柱状图 */
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
            grid:{
              top:"5%",
              left:"10%",
              right:"10%",
              bottom:"5%"
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
                itemStyle: {
                  color: '#409EFF'
                },
                backgroundStyle: {
                  color: 'rgba(180, 180, 180, 0.2)'
                }
              }
            ]
          });
        })
      )
    },
    /** 值班卡片跳转 */
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
    ::v-deep .el-card__body {
      display: flex;
      align-items: center;
      justify-content: space-around;
    }
    .icon-email {
      color: #409EFF;
    }
    .icon-fax {
      color: #5470c6;
    }
    .icon-msm {
      color: #91cd77;
    }
    .icon-tel {
      color: #ef6567;
    }
    .icon {
      font-size: 70px;
    }
    .num-detail {
      .num-type {
        font-size: 24px;
      }
      .num {
        font-weight: bold;
        font-size: 30px;
      }
    }
  }
}
</style>
