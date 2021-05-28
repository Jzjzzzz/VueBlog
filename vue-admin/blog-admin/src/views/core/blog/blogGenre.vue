<template>
  <div class="app-container">
    <!--查询表单-->
    <el-form :inline="true" class="demo-form-inline">
      <el-form-item label="分类名">
        <el-input v-model="searchObj.content" placeholder="分类名" />
      </el-form-item>

      <el-form-item label="分类状态">
        <el-select
          @change="fetchData()"
          @clear="resetData()"
          v-model="searchObj.status"
          placeholder="请选择"
          clearable
        >
          <el-option
            v-for="item in dict"
            :key="item.value"
            :label="item.name"
            :value="item.value"
          ></el-option>
        </el-select>
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
        class="filter-item"
        type="danger"
        size="medium"
        @click="handleDeleteBatch"
        icon="el-icon-delete"
      >
        删除选中
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
      <el-table-column type="selection"></el-table-column>
      <el-table-column type="index" width="50" align="center" />
      <el-table-column prop="content" label="博客分类名称" align="center" />
      <el-table-column
        width="100"
        align="center"
        sortable="custom"
        prop="sort"
        label="排序"
      >
        <template slot-scope="scope">
          <el-tag type="warning">{{ scope.row.sort }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column
        prop="clickcount"
        width="300"
        label="点击数"
        align="center"
      />
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
            置顶
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
            @click="removeById(scope.row.id)"
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
    <!-- 添加或修改对话框 -->
    <el-dialog
      title="新增博客分类"
      :visible.sync="dialogVisible"
      width="30%"
      @close="closeDialog"
    >
      <el-form :model="form" :rules="rules" ref="form">
        <el-form-item
          label="分类名"
          :label-width="formLabelWidth"
          prop="content"
        >
          <el-input v-model="form.content" auto-complete="off"></el-input>
        </el-form-item>

        <el-form-item label="排序" :label-width="formLabelWidth" prop="sort">
          <el-input v-model="form.sort" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="状态" :label-width="formLabelWidth" prop="status">
          <el-select v-model="form.status" placeholder="请选择">
            <el-option
              v-for="item in dict"
              :key="item.value"
              :label="item.name"
              :value="item.value"
            ></el-option>
          </el-select>
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
import genreApi from '@/api/core/genre'
export default {
  // 定义数据模型
  data() {
    return {
      multipleSelection: [], //多选，用于批量删除
      list: [], // 数据列表
      total: 0, // 数据库中的总记录数
      page: 1, // 默认页码
      limit: 5, // 每页记录数
      searchObj: {}, // 查询条件
      dict: [], //字典数据
      formLabelWidth: '120px',
      dialogVisible: false,
      form: {}, //新增
      rules: {
        content: [
          { required: true, message: '博客分类名不能为空', trigger: 'blur' },
          { min: 1, max: 20, message: '长度在1到20个字符' }
        ],
        status: [{ required: true, message: '状态不能为空', trigger: 'blur' }],
        sort: [
          { required: true, message: '排序字段不能为空', trigger: 'blur' },
          { pattern: /^[0-9]\d*$/, message: '排序字段只能为自然数' }
        ]
      }
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
      this.$refs.form.clearValidate() //移除该表单项的校验结果
    },
    approvalShow(row) {
      this.dialogVisible = true
      genreApi.getById(row).then(response => {
        this.form = response.data.model
      })
    },

    approvalSubmit() {
      this.$refs.form.validate(valid => {
        if (!valid) {
          console.log('校验出错')
        } else {
          if (this.form.id != null) {
            genreApi.updateById(this.form).then(response => {
              this.dialogVisible = false
              this.$message.success(response.message)
              this.fetchData()
            })
          } else {
            genreApi.approval(this.form).then(response => {
              this.dialogVisible = false
              this.$message.success(response.message)
              this.fetchData()
            })
          }
        }
      })
    },
    changePageSize(size) {
      this.limit = size
      this.fetchData()
    },
    changeCurrentPage(page) {
      this.page = page
      this.fetchData()
    },
    fetchData() {
      // 调用api
      genreApi.list(this.page, this.limit, this.searchObj).then(response => {
        this.list = response.data.listPage.records
        this.total = response.data.listPage.total
      })
      //字典数据
      genreApi.dict().then(response => {
        this.dict = response.data.dict
      })
    },
    //根据id置顶数据
    stickyBlogById(id) {
      this.$confirm('此操作将置顶该记录，是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        genreApi.stickyBlogById(id).then(response => {
          this.$message.success(response.message)
          this.fetchData()
        })
      })
    },
    // 根据id删除数据
    removeById(id) {
      this.$confirm('此操作将永久删除该记录, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(() => {
          genreApi.removeById(id).then(response => {
            this.$message.success(response.message)
            this.fetchData()
          })
        })
        .catch(error => {
          this.$message.info('取消删除')
        })
    },
    // 重置表单
    resetData() {
      this.searchObj = {}
      this.fetchData()
    },
    // 改变多选
    handleSelectionChange(val) {
      this.multipleSelection = val
    },
    handleDeleteBatch: function() {
      var that = this
      var that = this
      if (that.multipleSelection.length <= 0) {
        this.$commonUtil.message.error('请先选中需要删除的内容')
        return
      }
      this.$confirm('此操作将把选中的分类删除, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(() => {
          genreApi.deleteBatchTag(that.multipleSelection).then(response => {
            this.$message.success(response.message)
            this.fetchData()
          })
        })
        .catch(() => {
          this.$commonUtil.message.info('已取消删除')
        })
    }
  }
}
</script>
