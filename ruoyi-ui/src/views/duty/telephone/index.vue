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
      <el-form-item label="来电时间">
        <el-date-picker
          v-model="datetimerange"
          style="width: 350px"
          value-format="yyyy-MM-dd HH:mm:ss"
          type="datetimerange"
          range-separator="-"
          start-placeholder="开始时间"
          end-placeholder="结束时间"
        ></el-date-picker>
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

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
        >新增</el-button>
      </el-col>
    </el-row>

    <el-table ref="tables" v-loading="loading" :data="list">
      <el-table-column label="序号" align="center" width="50">
        <template slot-scope="scope">
          {{ scope.$index + 1 }}
        </template>
      </el-table-column>
      <el-table-column label="来电单位" align="center" prop="theElectricityUnit" />
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
            @click="handleView(scope.row.telRecordId)"
          >详细</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
          >删除</el-button>
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
        <el-descriptions-item label="来电单位">{{ form.theElectricityUnit }}</el-descriptions-item>
        <el-descriptions-item label="来电电话">{{ form.tel }}</el-descriptions-item>
        <el-descriptions-item label="来电姓名">{{ form.userName }}</el-descriptions-item>
        <el-descriptions-item label="来电时间">{{ form.telTime }}</el-descriptions-item>
        <el-descriptions-item label="接话人">{{ form.answerPeople }}</el-descriptions-item>
        <el-descriptions-item label="联系电话">{{ form.phone }}</el-descriptions-item>
        <el-descriptions-item label="通话标题" :span="2">{{ form.title }}</el-descriptions-item>
        <el-descriptions-item label="通话内容" :span="2">{{ form.content }}</el-descriptions-item>
        <el-descriptions-item label="处理意见">{{ form.suggestion }}</el-descriptions-item>
      </el-descriptions>
      <div slot="footer" class="dialog-footer">
        <el-button @click="open = false">关 闭</el-button>
      </div>
    </el-dialog>

    <!-- 添加或修改电话 -->
    <el-dialog :title="addUpdate.title" :visible.sync="addUpdate.open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="addUpdate.rules" label-width="80px">
        <el-form-item label="来电单位" prop="theElectricityUnit">
          <el-input v-model="form.theElectricityUnit" placeholder="请输入来电单位" />
        </el-form-item>
        <el-form-item label="来电号码" prop="tel">
          <el-input v-model="form.tel" placeholder="请输入来电号码" />
        </el-form-item>
        <el-form-item label="来电姓名" prop="userName">
          <el-input v-model="form.userName" placeholder="请输入来电姓名" />
        </el-form-item>
        <el-form-item label="来电时间" prop="telTime">
          <el-date-picker
            v-model="form.telTime"
            type="datetime"
            placeholder="请选择">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="接话人" prop="answerPeople">
          <el-input v-model="form.answerPeople" placeholder="请输入来电姓名" />
        </el-form-item>
        <el-form-item label="通话内容" prop="content">
          <el-input v-model="form.content" type="textarea" placeholder="请输入通话内容"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { list, getTelInfoById, delTel, updateTel, addTel } from "@/api/duty/opertelephone";

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
      // 日期时间范围
      datetimerange: [],
      // 表单参数
      form: {},
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        theElectricityUnit: undefined,
        tel: undefined,
        userName: undefined,
        answerPeople: undefined
      },
      // 添加或修改电话参数
      addUpdate: {
        // 是否显示弹出层
        open: false,
        // 弹出层标题
        title: "",
        // 表单校验
        rules: {
          type: [
            { required: true, message: "来电号码不能为空", trigger: "blur" }
          ],
          remark: [
            { required: true, message: "通话内容不能为空", trigger: "blur" }
          ]
        },
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
      list(this.addDateRange(this.queryParams, this.datetimerange)).then( response => {
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
      this.datetimerange = [];
      this.resetForm("queryForm");
      this.handleQuery();
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.addUpdate.open = true;
      this.addUpdate.title = "添加电话";
    },
    /** 详细按钮操作 */
    handleView(telRecordId) {
      getTelInfoById(telRecordId).then( res => {
          this.form = res.data;
          this.open = true;
        }
      );
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      this.form = row;
      this.addUpdate.open = true;
      this.addUpdate.title = "修改电话";
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const telRecordId = row.telRecordId;
      this.$modal.confirm('是否确认删除来电号码为"' + row.tel + '"的数据项？').then(function() {
        return delTel(telRecordId);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    // 表单重置
    reset() {
      this.form = {
        telRecordId: undefined,
        theElectricityUnit: undefined,
        tel: undefined,
        userName: undefined,
        telTime: undefined,
        answerPeople: undefined,
        content: undefined
      };
      this.resetForm("form");
    },
    // 取消按钮
    cancel() {
      this.addUpdate.open = false;
      this.reset();
    },
    /** 提交按钮 */
    submitForm: function() {
      this.$refs["form"].validate(valid => {
        if (valid) {
         if (this.form.telRecordId != undefined) {
            updateTel(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addTel(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    }
  }
};
</script>