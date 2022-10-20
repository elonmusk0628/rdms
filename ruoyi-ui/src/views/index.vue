<template>
  <div class="home-page">
    <el-row :gutter="10">
      <el-col :span="6">
        <el-card>
          <div slot="header">
            <span>邮件管理</span>
            <el-button
              style="float: right; padding: 3px 0"
              type="text"
              icon="el-icon-refresh-right"
              @click="refreshEmail()"
            ></el-button>
          </div>
          <el-table
            v-loading="emailLoading"
            :data="email"
            style="width: 100%"
          >
            <el-table-column label="序号" align="center" width="50">
              <template slot-scope="scope">
                {{ scope.$index + 1 }}
              </template>
            </el-table-column>
            <el-table-column label="邮箱类型" align="center" width="100">
              <template slot-scope="scope">
                <div>{{ scope.row.mailType=='163'?'163邮箱':(scope.row.mailType=='pearlwater'?'珠江委邮箱':'') }}</div>
              </template>
            </el-table-column>
            <el-table-column label="标题" align="center" prop="mailName" :show-overflow-tooltip="true" />
            <el-table-column label="发送地址" align="center" prop="address" :show-overflow-tooltip="true" />
          </el-table>
        </el-card>
      </el-col>

      <el-col :span="6">
        <el-card>
          <div slot="header">
            <span>传真管理</span>
            <el-button
              style="float: right; padding: 3px 0"
              type="text"
              icon="el-icon-refresh-right"
              @click="refreshFax()"
            ></el-button>
          </div>
          <el-table
            v-loading="faxLoading"
            :data="fax"
            style="width: 100%"
          >
            <el-table-column label="序号" align="center" width="50">
              <template slot-scope="scope">
                {{ scope.$index + 1 }}
              </template>
            </el-table-column>
            <el-table-column label="标题" align="center" prop="name" :show-overflow-tooltip="true" />
            <el-table-column label="编号" align="center" prop="fileNum" :show-overflow-tooltip="true" />
            <el-table-column label="附件" align="center" :show-overflow-tooltip="true">
              <template slot-scope="scope">
                <span>{{ scope.row.fileName == null ? '无' : scope.row.fileName }}</span>
              </template>
            </el-table-column>
            <el-table-column label="发文时间" align="center" width="180">
              <template slot-scope="scope">
                <span>{{ scope.row.draftDate }}</span>
              </template>
            </el-table-column>
          </el-table>
        </el-card>
      </el-col>

      <el-col :span="6">
        <el-card>
          <div slot="header">
            <span>短信管理</span>
            <el-button
              style="float: right; padding: 3px 0"
              type="text"
              icon="el-icon-refresh-right"
              @click="refreshMsm()"
            ></el-button>
          </div>
          <el-table
            v-loading="msmLoading"
            :data="msm"
            style="width: 100%"
          >
            <el-table-column label="序号" align="center" width="100">
              <template slot-scope="scope">
                {{ scope.$index + 1 }}
              </template>
            </el-table-column>
            <el-table-column label="收件人" align="center" :show-overflow-tooltip="true">
              <template slot-scope="scope">
                <span>{{ scope.row.remark == null ? '无' : scope.row.remark }}</span>
              </template>
            </el-table-column>
            <el-table-column label="署名" align="center" prop="signaTure" width="150" :show-overflow-tooltip="true" />
            <el-table-column label="发送内容" align="center" prop="content" :show-overflow-tooltip="true" />
            <el-table-column label="发送是否成功" align="center">
              <template slot-scope="scope">
                <el-tag size="small" :type="scope.row.success=='0'?'':'danger'">{{ scope.row.success == "0" ? "成功" : "失败" }}</el-tag>
              </template>
            </el-table-column>
            <el-table-column label="发送时间" align="center" width="180">
              <template slot-scope="scope">
                <span>{{ scope.row.sendTime }}</span>
              </template>
            </el-table-column>
          </el-table>
        </el-card>
      </el-col>

      <el-col :span="6">
        <el-card>
          <div slot="header">
            <span>电话管理</span>
            <el-button
              style="float: right; padding: 3px 0"
              type="text"
              icon="el-icon-refresh-right"
              @click="refreshTelephone()"
            ></el-button>
          </div>
          <el-table
            v-loading="telephoneLoading"
            :data="telephone"
            style="width: 100%"
          >
            <el-table-column label="序号" align="center" width="50">
              <template slot-scope="scope">
                {{ scope.$index + 1 }}
              </template>
            </el-table-column>
            <el-table-column label="来电单位" align="center" prop="theElectricityUnit" :show-overflow-tooltip="true" />
            <el-table-column label="来电号码" align="center" prop="tel" width="120" />
            <el-table-column label="来电姓名" align="center" prop="userName" />
            <el-table-column label="来电时间" align="center" prop="telTime" width="180">
              <template slot-scope="scope">
                <span>{{ scope.row.telTime }}</span>
              </template>
            </el-table-column>
            <el-table-column label="接话人" align="center" prop="answerPeople" />
            <el-table-column label="通话内容" align="center" prop="content" :show-overflow-tooltip="true" />
          </el-table>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import { listEmail } from "@/api/duty/operemail";
import { listFax } from "@/api/duty/operfax";
import { listMsm } from "@/api/duty/opermsm";
import { listTelephone } from "@/api/duty/opertelephone";

export default {
  name: "homePage",
  data() {
    return {
      email: [],
      fax: [],
      msm: [],
      telephone: [],
      emailLoading: true,
      faxLoading: true,
      msmLoading: true,
      telephoneLoading: true
    };
  },
  created() {
    this.getEmail();
    this.getFax();
    this.getMsm();
    this.getTelephone();
  },
  methods: {
    getEmail() {
      this.emailLoading = true;
      listEmail().then(response => {
        this.email = response.rows;
        this.emailLoading = false;
      });
    },
    getFax() {
      this.faxLoading = true;
      listFax().then(response => {
        this.fax = response.rows;
        this.faxLoading = false;
      });
    },
    getMsm() {
      this.msmLoading = true;
      listMsm().then(response => {
        this.msm = response.rows;
        this.msmLoading = false;
      });
    },
    getTelephone() {
      this.telephoneLoading = true;
      listTelephone().then(response => {
        this.telephone = response.rows;
        this.telephoneLoading = false;
      });
    },
    refreshEmail() {
      this.getEmail();
      this.$modal.msgSuccess("刷新邮件管理成功");
    },
    refreshFax() {
      this.getFax();
      this.$modal.msgSuccess("刷新传真管理成功");
    },
    refreshMsm() {
      this.getMsm();
      this.$modal.msgSuccess("刷新短信管理成功");
    },
    refreshTelephone() {
      this.getTelephone();
      this.$modal.msgSuccess("刷新电话管理成功");
    }
  }
};
</script>

<style scoped lang="scss">
.home-page {
  padding: 10px;
  .is-always-shadow {
    overflow: auto;
  }
}
</style>
