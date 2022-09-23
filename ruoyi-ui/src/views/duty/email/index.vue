<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="100px">
      <el-form-item label="邮箱类型" prop="mailType">
        <el-select
          v-model="queryParams.mailType"
          placeholder="请选择"
          clearable
          style="width: 240px"
        >
          <el-option
            v-for="item in mailType"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="标题" prop="mailName">
        <el-input
          v-model="queryParams.mailName"
          placeholder="请输入"
          clearable
          style="width: 240px;"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="接收开始时间">
        <el-date-picker
          v-model="queryParams.startDate"
          value-format="yyyy-MM-dd"
          type="date"
          placeholder="开始时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="接收结束时间">
        <el-date-picker
          v-model="queryParams.endDate"
          value-format="yyyy-MM-dd"
          type="date"
          placeholder="结束时间">
        </el-date-picker>
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
      <el-table-column label="邮箱类型" align="center">
        <template slot-scope="scope">
          <div>{{ scope.row.mailType=='163'?'163邮箱':(scope.row.mailType=='pearlwater'?'珠江委邮箱':'') }}</div>
        </template>
      </el-table-column>
      <el-table-column label="标题" align="center" prop="mailName" :show-overflow-tooltip="true" />
      <el-table-column label="附件数量" align="center" prop="fileSize" />
      <el-table-column label="发送地址" align="center" prop="address" :show-overflow-tooltip="true" />
      <el-table-column label="接收时间" align="center" prop="receivedDate" sortable="custom" :sort-orders="['descending', 'ascending']" width="180">
        <template slot-scope="scope">
          <span>{{ scope.row.receivedDate }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
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

    <!-- 邮件详细 -->
    <el-dialog title="邮件详细" :visible.sync="open" width="700px" append-to-body>
      <el-descriptions class="margin-top" :column="1">
        <el-descriptions-item label="创建时间" v-if="form.createTime">{{ form.createTime }}</el-descriptions-item>
        <el-descriptions-item label="标题" v-if="form.mailName">{{ form.mailName }}</el-descriptions-item>
        <el-descriptions-item label="附件" v-if="form.mailInfoList&&form.mailInfoList.length>0">
          <div>
            <div v-for="(item, index) in form.mailInfoList" :key="index" style="padding-bottom: 10px">
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
import { list, getEmailInfoById } from "@/api/duty/operemail";

export default {
  name: "operemail",
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
      // 默认排序
      defaultSort: {prop: 'receivedDate', order: 'descending'},
      // 表单参数
      form: {},
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        startDate: undefined,
        endDate: undefined,
        mailName: undefined,
        mailType: undefined
      },
      mailType: [{
        value: '163',
        label: '163邮箱'
      },{
        value: 'pearlwater',
        label: '珠江委邮箱'
      }]
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询邮件列表 */
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
      getEmailInfoById(id).then( res => {
          this.form = res.data;
          this.open = true;
        }
      );
    }
  }
};
</script>