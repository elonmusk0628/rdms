<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="标题" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入关键字"
          clearable
          style="width: 240px;"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="编号" prop="fileNum">
        <el-input
          v-model="queryParams.fileNum"
          placeholder="请输入"
          clearable
          style="width: 240px;"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="发文时间">
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
      <el-table-column label="标题" align="center" prop="name" :show-overflow-tooltip="true" />
      <el-table-column label="编号" align="center" prop="fileNum" :show-overflow-tooltip="true" />
      <el-table-column label="附件" align="center" :show-overflow-tooltip="true">
        <template slot-scope="scope">
          <span>{{ scope.row.fileName == null ? '无' : scope.row.fileName }}</span>
        </template>
      </el-table-column>
      <el-table-column label="发文时间" align="center" prop="draftDate" sortable="custom" :sort-orders="['descending', 'ascending']" width="180">
        <template slot-scope="scope">
          <span>{{ scope.row.draftDate }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-view"
            @click="handleView(scope.row.id)"
            v-hasPermi="['fax:message:getFaxInfoById']"
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

    <!-- 传真详细 -->
    <el-dialog title="传真详细" :visible.sync="open" width="700px" append-to-body>
      <el-descriptions class="margin-top" :column="1" border>
        <el-descriptions-item label="创建时间" v-if="form.createTime" :labelStyle="labelStyle">{{ form.createTime }}</el-descriptions-item>
        <el-descriptions-item label="标题" v-if="form.name" :labelStyle="labelStyle">{{ form.name }}</el-descriptions-item>
        <el-descriptions-item label="文件名" v-if="form.faxInfoEntityList&&form.faxInfoEntityList.length>0" :labelStyle="labelStyle">
          <div>
            <div v-for="(item, index) in form.faxInfoEntityList" :key="index" style="padding-bottom: 10px">
              {{ item.fileName }}
            </div>
          </div>
        </el-descriptions-item>
      </el-descriptions>
      <div slot="footer" class="dialog-footer">
        <el-button @click="open = false">关 闭</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { list, getFaxInfoById } from "@/api/duty/operfax";

export default {
  name: "operfax",
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
      defaultSort: {prop: 'draftDate', order: 'descending'},
      // 表单参数
      form: {},
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        name: undefined,
        fileNum: undefined
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
    /** 查询传真列表 */
    getList() {
      this.loading = true;
      this.queryParams.startDate = this.dateRange[0];
      this.queryParams.endDate = this.dateRange[1];
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
    handleView(id) {
      getFaxInfoById(id).then( res => {
          this.form = res.data;
          this.open = true;
        }
      );
    }
  }
};
</script>