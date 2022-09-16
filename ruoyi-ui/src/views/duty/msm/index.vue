<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="署名" prop="signaTure">
        <el-input
          v-model="queryParams.signaTure"
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
            :key="item.value"
            :label="item.label"
            :value="item.value"
          />
        </el-select>
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
      <el-table-column label="署名" align="center" prop="signaTure" />
      <el-table-column label="发送内容" align="center" prop="content" :show-overflow-tooltip="true" />
      <el-table-column label="发送是否成功" align="center" prop="success">
        <template slot-scope="scope">
          <el-tag size="small" :type="scope.row.success=='0'?'':'danger'">{{ scope.row.success == "0" ? "成功" : "失败" }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="发送时间" align="center" prop="sendTime" sortable :sort-orders="['descending', 'ascending']" width="180">
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
      <el-form ref="form" :model="form" label-width="100px" size="mini">
        <el-row>
          <el-col :span="24">
            <el-form-item label="发送内容：" v-if="form.content">{{ form.content }}</el-form-item>
          </el-col>
          <el-col :span="24" class="card-box">
            <el-form-item label="接收人列表：" v-if="form.msmInfoList&&form.msmInfoList.length>0">
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
                      <tr v-for="(item, index) in form.msmInfoList" :key="index">
                        <td class="el-table__cell is-leaf"><div class="cell">{{ item.userName }}</div></td>
                        <td class="el-table__cell is-leaf"><div class="cell"><el-tag size="small">{{ item.success == "0" ? "成功" : "失败" }}</el-tag></div></td>
                        <td class="el-table__cell is-leaf"><div class="cell">{{ item.phone }}</div></td>
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
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 表格数据
      list: [],
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
        startDate: undefined,
        endDate: undefined,
        signaTure: undefined,
        content: undefined,
        success: undefined
      },
      msmSuccess: [{
        value: 0,
        label: '成功'
      },{
        value: 1,
        label: '失败'
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