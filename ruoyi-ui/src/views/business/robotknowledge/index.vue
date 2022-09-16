<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="类型" prop="type">
        <el-select
          v-model="queryParams.type"
          placeholder="请选择"
          clearable
          style="width: 240px"
        >
          <el-option
            v-for="item in waterType"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="关键字" prop="dictName">
        <el-input
          v-model="queryParams.dictName"
          placeholder="请输入关键字"
          clearable
          style="width: 240px"
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
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="info"
          plain
          icon="el-icon-upload2"
          size="mini"
          @click="handleImport"
        >导入</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="typeList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="编号" width="100" align="center" prop="id" />
      <el-table-column label="类型" align="center" prop="type" />
      <el-table-column label="关键字" align="center" prop="dictName" :show-overflow-tooltip="true" />
      <el-table-column label="水位" align="center" prop="dictName" :show-overflow-tooltip="true" />
      <el-table-column label="状态" align="center" prop="status">
        <template slot-scope="scope">
          <el-tag size="small" :type="scope.row.status=='告警'?'danger':''">{{ scope.row.status}}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="时间" align="center" prop="createTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-view"
            @click="handleView(scope.row)"
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

    <!-- 新增水情信息或者详细弹框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px" v-if="this.dialog==='add' || this.dialog==='modify'">
        <el-form-item label="类型" prop="type">
          <el-select
            v-model="form.type"
            placeholder="请选择"
            clearable
            style="width: 240px"
          >
            <el-option
              v-for="item in waterType"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="关键字" prop="remark">
          <el-input v-model="form.remark" type="textarea" placeholder="请输入关键字"></el-input>
        </el-form-item>
      </el-form>
      <el-form :model="form" label-width="100px" size="mini" v-if="this.dialog==='detail'">
        <el-descriptions class="margin-top" :column="2">
          <el-descriptions-item label="关键字">{{ form.createTime }}</el-descriptions-item>
          <el-descriptions-item label="时间">{{ form.theElectricityUnit }}</el-descriptions-item>
          <el-descriptions-item label="水位">{{ form.tel }}</el-descriptions-item>
          <el-descriptions-item label="汛限水位" v-if="form.type=='1'">{{ form.telTime }}</el-descriptions-item>
          <el-descriptions-item label="警戒水位" v-if="form.type=='2'">{{ form.telTime }}</el-descriptions-item>
          <el-descriptions-item label="入库" v-if="form.type=='1'">{{ form.phone }}</el-descriptions-item>
          <el-descriptions-item label="出库" v-if="form.type=='1'">{{ form.answerPeople }}</el-descriptions-item>
          <el-descriptions-item label="河道流量" v-if="form.type=='2'">{{ form.phone }}</el-descriptions-item>
          <el-descriptions-item label="水势" v-if="form.type=='2'">{{ form.answerPeople }}</el-descriptions-item>
          <el-descriptions-item label="状态">{{ form.title }}</el-descriptions-item>
        </el-descriptions>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm" v-if="this.dialog==='add'">确 定</el-button>
        <el-button @click="cancel" v-if="this.dialog==='add'">取 消</el-button>
        <el-button @click="open = false" v-if="this.dialog==='detail'">关 闭</el-button>
      </div>
    </el-dialog>

    <!-- 水情信息导入弹框 -->
    <el-dialog :title="upload.title" :visible.sync="upload.open" width="400px" append-to-body>
      <el-upload
        ref="upload"
        :limit="1"
        accept=".xlsx, .xls"
        :headers="upload.headers"
        :action="upload.url + '?updateSupport=' + upload.updateSupport"
        :disabled="upload.isUploading"
        :on-progress="handleFileUploadProgress"
        :on-success="handleFileSuccess"
        :auto-upload="false"
        drag
      >
        <i class="el-icon-upload"></i>
        <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
        <div class="el-upload__tip text-center" slot="tip">
          <div class="el-upload__tip" slot="tip">
            <el-checkbox v-model="upload.updateSupport" /> 是否更新已经存在的水情数据
          </div>
          <span>仅允许导入xls、xlsx格式文件。</span>
          <el-link type="primary" :underline="false" style="font-size:12px;vertical-align: baseline;" @click="importTemplate">下载模板</el-link>
        </div>
      </el-upload>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitFileForm">确 定</el-button>
        <el-button @click="upload.open = false">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listWord, delWord, addWord, updateWord, getWord } from "@/api/business/robot";
import { getToken } from "@/utils/auth";

export default {
  name: "robotknowledge",
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 水情信息数据
      typeList: [],
      // 弹出层标题
      title: "",
      // 弹出框类型
      dialog: 'add',
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        startDate: undefined,
        endDate: undefined,
        type: undefined,
        dictName: undefined
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        type: [
          { required: true, message: "类型不能为空", trigger: "blur" }
        ],
        remark: [
          { required: true, message: "关键字不能为空", trigger: "blur" }
        ]
      },
      waterType: [{
        value: '1',
        label: '水库'
      },{
        value: '2',
        label: '河道'
      },{
        value: '3',
        label: '机构'
      }],
      // 水情导入参数
      upload: {
        // 是否显示弹出层（水情导入）
        open: false,
        // 弹出层标题（水情导入）
        title: "",
        // 是否禁用上传
        isUploading: false,
        // 是否更新已经存在的水情数据
        updateSupport: 0,
        // 设置上传的请求头部
        headers: { Authorization: "Bearer " + getToken() },
        // 上传的地址
        url: process.env.VUE_APP_BASE_API + "/system/user/importData"
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询水情信息列表 */
    getList() {
      this.loading = true;
      listWord(this.queryParams).then(response => {
          this.typeList = response.rows;
          this.total = response.total;
          this.loading = false;
        }
      );
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        id: undefined,
        type: undefined,
        dictName: undefined,
        remark: undefined
      };
      this.resetForm("form");
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
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加水文水情信息";
      this.dialog = "add";
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length!=1
      this.multiple = !selection.length
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const waterId = row.id || this.ids
      getWord(waterId).then(response => {
        this.form = response.data;
        this.open = true;
        this.dialog = 'modify';
        this.title = "修改水文水情信息";
      });
    },
    /** 提交按钮 */
    submitForm: function() {
      this.$refs["form"].validate(valid => {
        if (valid) {
         if (this.form.id != undefined) {
            updateWord(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addWord(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 详细按钮操作 */
    handleView(row) {
      this.waterType.forEach(item => {
        if (item.value == row.type) {
          this.title = item.label + "详细";
          return;
        }
      })
      this.title = "详细";
      this.dialog = 'detail';
      this.open = true;
      this.form = row;
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const waterIds = row.id || this.ids;
      this.$modal.confirm('是否确认删除编号为"' + waterIds + '"的数据项？').then(function() {
        return delWord(waterIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导入按钮操作 */
    handleImport() {
      this.upload.title = "导入水文水情信息";
      this.upload.open = true;
    },
    /** 下载模板操作 */
    importTemplate() {
      this.download('system/user/importTemplate', {
      }, `water_template_${new Date().getTime()}.xlsx`)
    },
    // 文件上传中处理
    handleFileUploadProgress(event, file, fileList) {
      this.upload.isUploading = true;
    },
    // 文件上传成功处理
    handleFileSuccess(response, file, fileList) {
      this.upload.open = false;
      this.upload.isUploading = false;
      this.$refs.upload.clearFiles();
      this.$alert("<div style='overflow: auto;overflow-x: hidden;max-height: 70vh;padding: 10px 20px 0;'>" + response.msg + "</div>", "导入结果", { dangerouslyUseHTMLString: true });
      this.getList();
    },
    // 提交上传文件
    submitFileForm() {
      this.$refs.upload.submit();
    }
  }
};
</script>