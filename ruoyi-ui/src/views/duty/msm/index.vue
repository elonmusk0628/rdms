<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="署名" prop="signature">
        <el-input
          v-model="queryParams.signature"
          placeholder="请输入短信署名"
          clearable
          style="width: 240px;"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="发送内容" prop="content">
        <el-input
          v-model="queryParams.content"
          placeholder="请输入发送内容"
          clearable
          style="width: 240px;"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="是否成功" prop="success">
        <el-select
          v-model="queryParams.success"
          placeholder="发送是否成功"
          clearable
          style="width: 240px"
        >
          <el-option
            v-for="item in msmSuccess"
            :key="item.id"
            :label="item.name"
            :value="item.id"
          />
        </el-select>
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

    <el-table ref="tables" v-loading="loading" :data="list" @selection-change="handleSelectionChange" :default-sort="defaultSort" @sort-change="handleSortChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="短信编号" align="center" prop="sendInfoId" />
      <el-table-column label="短信署名" align="center" prop="signature" />
      <el-table-column label="发送内容" align="center" prop="content" :show-overflow-tooltip="true" />
      <el-table-column label="发送人数" align="center" prop="sendCount" />
      <el-table-column label="失败人数" align="center" prop="failCount" />
      <el-table-column label="发送是否成功" align="center" prop="success">
        <template slot-scope="scope">
          <div>
            {{ scope.row.success == "0" ? "失败" : "成功" }}
          </div>
        </template>
      </el-table-column>
      <el-table-column label="发送时间" align="center" prop="sendTime" sortable="custom" :sort-orders="['descending', 'ascending']" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.sendTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-view"
            @click="handleView(scope.row.sendInfoId)"
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
      <el-form ref="form" :model="form" label-width="100px" size="mini">
        <el-row>
          <el-col :span="24">
            <el-form-item label="发送内容：" v-if="form.content">{{ form.content }}</el-form-item>
          </el-col>
          <el-col :span="24" class="card-box">
            <!-- <el-card v-if="form.receiverList"> -->
            <el-form-item label="接收人列表：" v-if="form.operParam">
              <el-card>
                <div class="el-table el-table--enable-row-hover el-table--medium">
                  <table cellspacing="0" style="width: 100%;">
                    <thead>
                      <tr>
                        <th class="el-table__cell is-leaf"><div class="cell">收件人</div></th>
                        <th class="el-table__cell is-leaf"><div class="cell">发送状态</div></th>
                        <th class="el-table__cell is-leaf"><div class="cell">电话号码</div></th>
                      </tr>
                    </thead>
                    <tbody>
                      <tr>
                      <!-- <tr v-for="(receiver, index) in form.receiverList" :key="index"> -->
                        <td class="el-table__cell is-leaf"><div class="cell">陈基海</div></td>
                        <td class="el-table__cell is-leaf"><div class="cell">成功</div></td>
                        <td class="el-table__cell is-leaf"><div class="cell">18826417127</div></td>
                      </tr>
                      <tr>
                        <td class="el-table__cell is-leaf"><div class="cell">陈名</div></td>
                        <td class="el-table__cell is-leaf"><div class="cell">成功</div></td>
                        <td class="el-table__cell is-leaf"><div class="cell">18826417127</div></td>
                      </tr>
                    </tbody>
                  </table>
                </div>
              </el-card>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="open = false">关 闭</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { list, getMsmInfoById } from "@/api/duty/opermsm";

export default {
  name: "opermsm",
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 表格数据
      list: [],
      // 是否显示弹出层
      open: false,
      // 日期范围
      dateRange: [],
      // 默认排序
      defaultSort: {prop: 'sendTime', order: 'descending'},
      // 表单参数
      form: {},
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        signature: undefined,
        content: undefined,
        success: undefined
      },
      msmSuccess: [{
        id: 0,
        name: '失败'
      },{
        id: 1,
        name: '成功'
      }]
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询短信列表 */
    getList() {
      this.loading = true;
      list({ MsmParam: this.queryParams }).then( response => {
      // list(this.addDateRange(this.queryParams, this.dateRange)).then( response => {
          console.log(response)
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
    /** 多选框选中数据 */
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.operId)
      this.multiple = !selection.length
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