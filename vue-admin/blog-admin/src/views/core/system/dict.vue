<template>
  <div class="app-container">
    <!--查询表单-->
    <el-form :inline="true" class="demo-form-inline">
      <el-form-item label="字典编码">
        <el-input v-model="searchObj.dict_code" placeholder="字典编码" />
      </el-form-item>

      <el-form-item label="字典名称">
        <el-input v-model="searchObj.name" placeholder="字典名称" />
      </el-form-item>

      <el-button type="primary" icon="el-icon-search" @click="fetchData()">
        查询
      </el-button>
      <el-button
        class="grid-content bg-purple"
        type="primary"
        icon="el-icon-edit"
        size="medium"
        @click="dialogVisible = true"
      >
        新增
      </el-button>
      <el-button
        @click="dialogFormVisible = true"
        type="primary"
        size="medium"
        icon="el-icon-download"
      >
        导入Excel
      </el-button>
      <el-button type="default" @click="resetData()">清空</el-button>
    </el-form>

    <!-- 表格 -->
    <el-table
      :data="list"
      border
      stripe
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="index" width="50" align="center" />
      <el-table-column prop="dictCode" label="字典编码" align="center" />
      <el-table-column prop="name" label="字典名称" align="center" />
      <el-table-column prop="remark" label="备注" align="center" />
      <el-table-column prop="createTime" label="创建时间" align="center" />
      <el-table-column prop="updateTime" label="修改时间" align="center" />
      <el-table-column prop="status" width="100" label="状态" align="center">
        <template slot-scope="scope">
          <template v-if="scope.row.status == 1">
            <span>正常</span>
          </template>
          <template v-if="scope.row.status == 0">
            <span>下架</span>
          </template>
        </template>
      </el-table-column>
      <el-table-column label="操作" width="300" align="center">
        <template slot-scope="scope">
          <el-button
            type="warning"
            size="mini"
            icon="el-icon-edit"
            @click="stickyBlogById(scope.row.id)"
          >
            列表
          </el-button>
          <el-button
            type="primary"
            size="mini"
            icon="el-icon-edit"
            @click="approvalShow(scope.row.id)"
          >
            修改
          </el-button>
          <el-button
            type="danger"
            size="mini"
            icon="el-icon-delete"
            @click="removeByIdTop(scope.row.id)"
          >
            删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>
    <!-- 分页组件 -->
    <el-pagination
      :current-page="page"
      :total="total"
      :page-size="limit"
      :page-sizes="[5, 10]"
      style="padding: 30px 0; "
      layout="total, sizes, prev, pager, next, jumper"
      @size-change="changePageSize"
      @current-change="changeCurrentPage"
    />
    <!-- 字典导入弹窗 -->
    <el-dialog
      title="数据字典导入"
      :visible.sync="dialogFormVisible"
      width="30%"
    >
      <el-form>
        <el-form-item label="请选择Excel文件">
          <el-upload
            :auto-upload="true"
            :multiple="false"
            :limit="1"
            :on-exceed="fileUploadExceed"
            :on-success="fileUploadSuccess"
            :on-error="fileUploadError"
            :action="BASE_API + '/admin/core/dict/import'"
            name="file"
            accept="application/vnd.ms-excel,application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
          >
            <el-button size="small" type="primary">点击上传</el-button>
          </el-upload>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">
          取消
        </el-button>
      </div>
    </el-dialog>
    <!-- 添加或修改对话框 -->
    <el-dialog
      title="新增字典顶级节点"
      :visible.sync="dialogVisible"
      width="30%"
      @close="closeDialog"
    >
      <el-form :model="form" :rules="rules" ref="form">
        <el-form-item label="字典名" :label-width="formLabelWidth" prop="name">
          <el-input v-model="form.name" auto-complete="off"></el-input>
        </el-form-item>

        <el-form-item
          label="字典编码"
          :label-width="formLabelWidth"
          prop="dictCode"
        >
          <el-input v-model="form.dictCode" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="备注" :label-width="formLabelWidth" prop="remark">
          <el-input
            type="textarea"
            v-model="form.remark"
            auto-complete="off"
          ></el-input>
        </el-form-item>
        <el-form-item
          label="发布状态"
          :label-width="formLabelWidth"
          prop="isPublish"
        >
          <el-radio-group v-model="form.status" size="small">
            <el-radio label="1" border>上架</el-radio>
            <el-radio label="0" border>下架</el-radio>
          </el-radio-group>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">
          取消
        </el-button>
        <el-button type="primary" @click="approvalSubmit">
          确定
        </el-button>
      </div>
    </el-dialog>
  </div>
</template>
<script>
import dictApi from '@/api/core/dict'
export default {
  // 定义数据模型
  data() {
    return {
      list: [], // 数据列表
      total: 0, // 数据库中的总记录数
      page: 1, // 默认页码
      limit: 5, // 每页记录数
      searchObj: {}, // 查询条件
      options: [
        {
          value: '1',
          label: '正常'
        },
        {
          value: '0',
          label: '下架'
        }
      ], //标签状态字典
      formLabelWidth: '120px',
      dialogVisible: false, //新增字典弹窗
      dialogFormVisible: false, //导入字典弹窗
      BASE_API: process.env.VUE_APP_BASE_API, //获取后端接口地址

      form: {} //新增
    }
  },
  // 页面渲染成功后获取数据
  created() {
    this.fetchData()
  },
  // 定义方法
  methods: {
    //关闭dialog时清空数据
    closeDialog() {
      this.form = {}
    },
    //点击修改通过ID获取字典model
    approvalShow(row) {
      this.dialogVisible = true
      dictApi.getById(row).then(response => {
        this.form = response.data.model
        if (this.form.status) {
          this.form.status = '1'
        } else {
          this.form.status = '0'
        }
      })
    },
    // 重置表单
    resetData() {
      this.searchObj = {}
      this.fetchData()
    },
    approvalSubmit() {
      if (this.form.id != null) {
        dictApi.updateById(this.form).then(response => {
          this.dialogVisible = false
          this.$message.success(response.message)
          this.fetchData()
        })
      } else {
        dictApi.saveTop(this.form).then(response => {
          this.dialogVisible = false
          this.$message.success(response.message)
          this.fetchData()
        })
      }
    },
    fetchData() {
      dictApi.listPage(this.page, this.limit, this.searchObj).then(response => {
        this.list = response.data.listPage.records
        this.total = response.data.listPage.total
      })
    },
    // 上传多于一个文件时
    fileUploadExceed() {
      this.$message.warning('只能选取一个文件')
    },
    //上传成功回调
    fileUploadSuccess(response) {
      if (response.code === 0) {
        this.$message.success('数据导入成功')
        this.dialogVisible = false
      } else {
        this.$message.error(response.message)
      }
    },
    //上传失败回调
    fileUploadError(error) {
      this.$message.error('数据导入失败')
    },
    changePageSize(size) {
      this.limit = size
      this.fetchData()
    },
    changeCurrentPage(page) {
      this.page = page
      this.fetchData()
    },
    //删除顶级节点
    removeByIdTop(id) {
      dictApi.removeByIdTop(id).then(response => {
        this.$message.success(response.message)
        this.fetchData()
      })
    }
  }
}
</script>
