<template>
  <div class="app-container">
    <!--查询表单-->
    <el-form :inline="true" class="demo-form-inline">
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
      <el-button type="default" @click="resetData()">清空</el-button>
      <el-button type="warning" @click="deleteCache()">清空缓存</el-button>
    </el-form>

    <!-- 表格 -->
    <el-table
      :data="list"
      border
      stripe
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="index" width="50" align="center" />

      <el-table-column prop="name" label="字典名称" align="center" />
      <el-table-column label="值" width="100" align="center">
        <template slot-scope="scope">
          <el-tag v-if="scope.$index % 5 == 0" type="warning">
            {{ scope.row.value }}
          </el-tag>
          <el-tag v-if="scope.$index % 5 == 1" type="success">
            {{ scope.row.value }}
          </el-tag>
          <el-tag v-if="scope.$index % 5 == 2" type="info">
            {{ scope.row.value }}
          </el-tag>
          <el-tag v-if="scope.$index % 5 == 3" type="danger">
            {{ scope.row.value }}
          </el-tag>
          <el-tag v-if="scope.$index % 5 == 4">
            {{ scope.row.value }}
          </el-tag>
        </template>
      </el-table-column>
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

    <!-- 添加或修改对话框 -->
    <el-dialog
      title="新增字典下级节点"
      :visible.sync="dialogVisible"
      width="30%"
      @close="closeDialog"
    >
      <el-form :model="form" :rules="rules" ref="form">
        <el-form-item label="字典名" :label-width="formLabelWidth" prop="name">
          <el-input v-model="form.name" auto-complete="off"></el-input>
        </el-form-item>

        <el-form-item label="值" :label-width="formLabelWidth" prop="value">
          <el-input v-model="form.value" auto-complete="off"></el-input>
        </el-form-item>

        <el-form-item label="备注" :label-width="formLabelWidth" prop="remark">
          <el-input
            type="textarea"
            v-model="form.remark"
            auto-complete="off"
          ></el-input>
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
      dictTypeUid: '', // 从SysDictType传递过来的
      dict: [], //数据字典
      formLabelWidth: '120px',
      dialogVisible: false, //新增字典弹窗
      BASE_API: process.env.VUE_APP_BASE_API, //获取后端接口地址
      form: {}, //新增
      rules: {
        name: [
          { required: true, message: '字典名不能为空', trigger: 'blur' },
          { min: 1, max: 20, message: '长度在1到20个字符' }
        ],
        value: [
          { required: true, message: '字典值不能为空', trigger: 'blur' },
          { pattern: /^[0-9]\d*$/, message: '排序字段只能为自然数' }
        ],
        status: [{ required: true, message: '状态不能为空', trigger: 'blur' }]
      }
    }
  },
  // 页面渲染成功后获取数据
  created() {
    //传递过来的dictTypeUid
    this.dictTypeUid = this.$route.query.dictTypeUid
    this.fetchData()
  },
  // 定义方法
  methods: {
    //清空redis缓存
    deleteCache() {
      dictApi.removeDictRedis().then(response => {
        this.$message.success(response.message)
        this.fetchData()
      })
    },
    //点击修改通过ID获取字典model
    approvalShow(row) {
      this.dialogVisible = true
      dictApi.getById(row).then(response => {
        this.form = response.data.model
      })
    },
    //关闭dialog时清空数据
    closeDialog() {
      this.form = {}
      this.$refs.form.clearValidate() //移除该表单项的校验结果
    },
    fetchData() {
      dictApi
        .listByParentId(this.page, this.limit, this.searchObj, this.dictTypeUid)
        .then(response => {
          this.list = response.data.listPage.records
          this.total = response.data.listPage.total
        })
      //字典数据
      dictApi.dict().then(response => {
        this.dict = response.data.dict
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
    // 重置表单
    resetData() {
      this.searchObj = {}
      this.fetchData()
    },
    //删除节点
    removeByIdTop(id) {
      dictApi.removeByIdTop(id).then(response => {
        this.$message.success(response.message)
        this.fetchData()
      })
    },
    approvalSubmit() {
      this.$refs.form.validate(valid => {
        if (!valid) {
          console.log('校验出错')
        } else {
          if (this.form.id != null) {
            dictApi.updateById(this.form).then(response => {
              this.dialogVisible = false
              this.$message.success(response.message)
              this.fetchData()
            })
          } else {
            dictApi.saveSun(this.form, this.dictTypeUid).then(response => {
              this.dialogVisible = false
              this.$message.success(response.message)
              this.fetchData()
            })
          }
        }
      })
    }
  }
}
</script>
