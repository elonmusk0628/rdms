<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="收件人" prop="remark">
        <el-input
          v-model="queryParams.remark"
          placeholder="请输入"
          clearable
          style="width: 240px;"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="署名" prop="signaTure">
        <el-input
          v-model="queryParams.signaTure"
          placeholder="请输入"
          clearable
          style="width: 240px;"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="发送内容" prop="content">
        <el-input
          v-model="queryParams.content"
          placeholder="请输入"
          clearable
          style="width: 240px;"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="发送时间">
        <el-date-picker
          v-model="dateRange"
          style="width: 240px"
          value-format="yyyy-MM-dd"
          type="daterange"
          range-separator="-"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
        ></el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-table ref="tables" v-loading="loading" :data="list" :default-sort="defaultSort" @sort-change="handleSortChange">
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
      <el-table-column label="发送时间" align="center" prop="sendTime" sortable :sort-orders="['descending', 'ascending']" width="180">
        <template slot-scope="scope">
          <span>{{ scope.row.sendTime }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-view"
            @click="handleView(scope.row.sendInfoId)"
            v-hasPermi="['msm:message:getInfoById']"
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

    <!-- 短信详细 -->
    <el-dialog title="短信详细" :visible.sync="open" width="700px" append-to-body>
      <el-descriptions class="margin-top" :column="1" border>
        <el-descriptions-item label="发送内容" :labelStyle="labelStyle" v-if="form.content">{{ form.content }}</el-descriptions-item>
        <el-descriptions-item label="接收人列表" :labelStyle="labelStyle" v-if="form.msmInfoList&&form.msmInfoList.length>0">
          <el-table
            :data="form.msmInfoList"
            stripe
            style="width: 100%">
            <el-table-column
              prop="userName"
              label="收件人"
              width="180">
            </el-table-column>
            <el-table-column
              label="发送状态"
              width="180">
              <template slot-scope="scope">
                <el-tag size="small" :type="scope.row.success=='0'?'':'danger'">{{ scope.row.success == "0" ? "成功" : "失败" }}</el-tag>
              </template>
            </el-table-column>
            <el-table-column
              prop="phone"
              label="电话号码">
            </el-table-column>
          </el-table>
        </el-descriptions-item>
      </el-descriptions>
      <div slot="footer" class="dialog-footer">
        <el-button @click="open = false">关 闭</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listMsm, getMsmInfoById } from "@/api/duty/opermsm";

export default {
  name: "opermsm",
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
      // 日期范围
      dateRange: [],
      // 是否显示弹出层
      open: false,
      // 默认排序
      defaultSort: {prop: 'sendTime', order: 'descending'},
      // 表单参数
      form: {},
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        remark: undefined,
        signaTure: undefined,
        content: undefined,
        success: undefined
      },
      labelStyle: {
        'width': '100px'
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询短信列表 */
    getList() {
      this.loading = true;
      this.queryParams.startDate = this.dateRange[0];
      this.queryParams.endDate = this.dateRange[1];
      listMsm(this.queryParams).then( response => {
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
      this.dateRange = [];
      this.resetForm("queryForm");
      this.$refs.tables.sort(this.defaultSort.prop, this.defaultSort.order)
      this.handleQuery();
    },
    /** 排序触发事件 */
    handleSortChange(column, prop, order) {
      this.queryParams.orderByColumn = column.prop;
      this.queryParams.isAsc = column.order;
      this.getList();
    },
    /** 详细按钮操作 */
    handleView(sendInfoId) {
      getMsmInfoById(sendInfoId).then( res => {
          this.form = res.data;
          this.open = true;
        }
      );
    }
  }
};
</script>