<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="来电单位" prop="theElectricityUnit">
        <el-input
          v-model="queryParams.theElectricityUnit"
          placeholder="请输入"
          clearable
          style="width: 240px;"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="来电号码" prop="tel">
        <el-input
          v-model="queryParams.tel"
          placeholder="请输入"
          clearable
          style="width: 240px;"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="来电姓名" prop="userName">
        <el-input
          v-model="queryParams.userName"
          placeholder="请输入"
          clearable
          style="width: 240px;"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="开始时间">
        <el-date-picker
          v-model="queryParams.startDate"
          value-format="yyyy-MM-dd HH:mm:ss"
          type="datetime"
          placeholder="开始时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="结束时间">
        <el-date-picker
          v-model="queryParams.endDate"
          value-format="yyyy-MM-dd HH:mm:ss"
          type="datetime"
          placeholder="结束时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="接话人" prop="answerPeople">
        <el-input
          v-model="queryParams.answerPeople"
          placeholder="请输入"
          clearable
          style="width: 240px;"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-table ref="tables" v-loading="loading" :data="list">
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
          <span>{{ parseTime(scope.row.telTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="接话人" align="center" prop="answerPeople" />
      <el-table-column label="通话内容" align="center" prop="content" :show-overflow-tooltip="true" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width" width="200">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-view"
            @click="handleView(scope.row.id)"
          >详细</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 电话详细 -->
    <el-dialog title="电话详细" :visible.sync="open" width="700px" append-to-body>
      <el-descriptions class="margin-top" :column="2" border>
        <el-descriptions-item label="来电单位" :labelStyle="labelStyle">{{ form.theElectricityUnit }}</el-descriptions-item>
        <el-descriptions-item label="来电电话" :labelStyle="labelStyle">{{ form.tel }}</el-descriptions-item>
        <el-descriptions-item label="来电姓名" :labelStyle="labelStyle">{{ form.userName }}</el-descriptions-item>
        <el-descriptions-item label="来电时间" :labelStyle="labelStyle">{{ parseTime(form.telTime) }}</el-descriptions-item>
        <el-descriptions-item label="接话人" :labelStyle="labelStyle">{{ form.answerPeople }}</el-descriptions-item>
        <el-descriptions-item label="联系电话" :labelStyle="labelStyle">{{ form.phone }}</el-descriptions-item>
        <el-descriptions-item label="通话标题" :labelStyle="labelStyle" :span="2">{{ form.title }}</el-descriptions-item>
        <el-descriptions-item label="通话内容" :labelStyle="labelStyle" :span="2">{{ form.content }}</el-descriptions-item>
        <el-descriptions-item label="处理意见" :labelStyle="labelStyle">{{ form.suggestion }}</el-descriptions-item>
      </el-descriptions>
      <div slot="footer" class="dialog-footer">
        <el-button @click="open = false">关 闭</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { list, getTelInfoById } from "@/api/duty/opertelephone";

export default {
  name: "opertelephone",
  data() {
    return {
      // 遮罩层
      loading: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 表格数据
      list: [],
      // 是否显示弹出层
      open: false,
      // 表单参数
      form: {},
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        startDate: undefined,
        endDate: undefined,
        theElectricityUnit: undefined,
        tel: undefined,
        userName: undefined,
        answerPeople: undefined
      },
      labelStyle: {
        'width': '80px'
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询电话列表 */
    getList() {
      this.loading = true;
      list(this.queryParams).then( response => {
          this.list = response.rows;
          this.total = response.total;
          this.loading = false;
        }
      );
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.queryParams.startDate = undefined;
      this.queryParams.endDate = undefined;
      this.resetForm("queryForm");
      this.handleQuery();
    },
    /** 详细按钮操作 */
    handleView(id) {
      getTelInfoById(id).then( res => {
          this.form = res.data;
          this.open = true;
        }
      );
    }
  }
};
</script>