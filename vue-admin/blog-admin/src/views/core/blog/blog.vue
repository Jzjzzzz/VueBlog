<template>
  <div class="app-container">
    <!--查询表单-->
    <el-form :inline="true" class="demo-form-inline">
      <el-form-item label="博客标题">
        <el-input v-model="searchObj.title" placeholder="博客标题" />
      </el-form-item>

      <el-form-item label="分类" prop="blogSortName">
        <el-select
          v-model="searchObj.blogSortName"
          placeholder="请选择"
          clearable
        >
          <el-option
            v-for="item in genreList"
            :key="item.id"
            :label="item.content"
            :value="item.content"
          ></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="标签" prop="tagId">
        <el-select v-model="searchObj.tagId" placeholder="请选择" clearable>
          <el-option
            v-for="item in tagList"
            :key="item.id"
            :label="item.content"
            :value="item.id"
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
        @click="dialogFormVisible = true"
      >
        新增
      </el-button>

      <el-button type="default" @click="resetData()">清空</el-button>
    </el-form>

    <!-- 表格 -->
    <el-table :data="list" border stripe>
      <el-table-column type="index" width="50" align="center" />
      <el-table-column prop="title" label="博客标题" align="center" />
      <el-table-column prop="fileId" label="博客图片" align="center">
        <template slot-scope="scope">
          <el-image
            style="width: 100px; height: 100px"
            :src="scope.row.fileId"
            :preview-src-list="srcList"
            @click="srclistBig(scope.row.fileId)"
          ></el-image>
        </template>
      </el-table-column>
      <el-table-column prop="blogSortName" label="博客分类" align="center">
        <template slot-scope="scope">
          <el-tag type="success">{{ scope.row.blogSortName }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="tagName" label="博客标签" align="center">
        <template slot-scope="scope">
          <el-tag effect="plain">{{ scope.row.tagName }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="clickCount" label="博客点击数" align="center" />
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

    <!-- 图片缩略图弹出框 -->
    <el-dialog :visible.sync="dialogVisible">
      <img width="100%" :src="dialogImageUrl" alt="" />
    </el-dialog>
    <!-- 添加或修改对话框 -->
    <el-dialog
      title="新增博客"
      :visible.sync="dialogFormVisible"
      @close="closeDialog"
      fullscreen
    >
      <el-form :model="form" :rules="rules" ref="form">
        <el-row>
          <el-col :span="12">
            <el-form-item
              label="标题"
              :label-width="formLabelWidth"
              prop="title"
            >
              <el-input type="textarea" v-model="form.title"></el-input>
            </el-form-item>
            <el-form-item
              style="margin-top:39px"
              label="简介"
              :label-width="formLabelWidth"
            >
              <el-input type="textarea" v-model="form.summary"></el-input>
            </el-form-item>
          </el-col>
          <el-col style="margin-left:40px" :span="6">
            <el-form-item label="标题图">
              <el-upload
                :class="{ hide: hideUpload }"
                :action="handleBeforeUploadImg()"
                :on-success="onUploadSuccessImg"
                :on-remove="onUploadRemove"
                :on-preview="handlePictureCardPreview"
                :on-change="handleChange"
                :multiple="false"
                :data="{ module: 'blogTitleImg' }"
                :limit="1"
                :file-list="fileListurl"
                list-type="picture-card"
              >
                <i class="el-icon-plus"></i>
              </el-upload>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row>
          <el-col :span="4">
            <el-form-item
              label="分类"
              :label-width="formLabelWidth"
              prop="blogSortId"
            >
              <el-select
                v-model="form.blogSortName"
                size="small"
                placeholder="请选择"
                @change="blogSortValue"
              >
                <el-option
                  v-for="item in genreList"
                  :key="item.id"
                  :label="item.content"
                  :value="item"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>

          <el-col :span="4">
            <el-form-item label="标签" label-width="80px" prop="tagId">
              <el-select
                v-model="form.tagId"
                multiple
                size="small"
                placeholder="请选择"
                filterable
              >
                <el-option
                  v-for="item in tagList"
                  :key="item.id"
                  :label="item.content"
                  :value="item.id"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="4">
            <el-form-item label="推荐等级" prop="level">
              <el-select v-model="form.level" size="small" placeholder="请选择">
                <el-option
                  v-for="item in starsDict"
                  :key="item.id"
                  :label="item.name"
                  :value="item.value"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="4">
            <el-form-item
              label="是否原创"
              :label-width="formLabelWidth"
              prop="original"
            >
              <el-radio-group v-model="form.original" size="small">
                <el-radio
                  v-for="item in orderList"
                  :key="item.id"
                  :label="item.value"
                  border
                >
                  {{ item.name }}
                </el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
          <el-col :span="5">
            <el-form-item
              label="文章评论"
              :label-width="formLabelWidth"
              prop="openComment"
            >
              <el-radio-group v-model="form.openComment" size="small">
                <el-radio
                  v-for="item in commentList"
                  :key="item.id"
                  :label="item.value"
                  border
                >
                  {{ item.name }}
                </el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
          <el-col :span="5">
            <el-form-item label="是否发布" prop="status">
              <el-radio-group v-model="form.status" size="small">
                <el-radio
                  v-for="item in dataList"
                  :key="item.id"
                  :label="item.value"
                  border
                >
                  {{ item.name }}
                </el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item
          label="作者"
          :label-width="formLabelWidth"
          v-if="form.original == 2"
          prop="author"
        >
          <el-input v-model="form.author"></el-input>
        </el-form-item>

        <el-form-item
          label="文章出处"
          :label-width="formLabelWidth"
          v-if="form.original == 2"
        >
          <el-input v-model="form.articlesPart"></el-input>
        </el-form-item>

        <el-form-item label="内容" :label-width="formLabelWidth" prop="content">
          <mavon-editor v-model="form.content" />
        </el-form-item>

        <el-form-item
          style="float: right; margin-right: 20px; margin-top: 20px;"
        >
          <el-button @click="dialogFormVisible = false">取 消</el-button>
          <el-button type="primary" @click="submitForm">确 定</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>
  </div>
</template>
<script>
import webConfigApi from '@/api/core/webConfig'
import blogApi from '@/api/core/blog'
import MarkdownEditor from '@/components/MarkdownEditor'

export default {
  components: {
    MarkdownEditor
  },
  // 定义数据模型
  data() {
    return {
      srcList: [],
      list: [],
      total: 0, // 数据库中的总记录数
      page: 1, // 默认页码
      limit: 5, // 每页记录数
      dialogImageUrl: '',
      dialogVisible: false, //图片缩略图弹出框状态
      dialogFormVisible: false, //新增博客弹出框状态
      formLabelWidth: '120px',
      form: {},
      rules: {
        title: [
          { required: true, message: '博客标题不能为空', trigger: 'blur' },
          { min: 1, max: 30, message: '长度在1到30个字符' }
        ],
        blogSortId: [
          { required: true, message: '分类不能为空', trigger: 'blur' }
        ],
        tagId: [{ required: true, message: '标签不能为空', trigger: 'blur' }],
        original: [
          { required: true, message: '是否原创不能为空', trigger: 'blur' }
        ],
        openComment: [
          { required: true, message: '是否开启评论不能为空', trigger: 'blur' }
        ],
        status: [
          { required: true, message: '是否发布不能为空', trigger: 'blur' }
        ],
        content: [{ required: true, message: '内容不能为空', trigger: 'blur' }]
      },
      tagList: {}, //标签列表
      genreList: {}, //分类列表
      starsDict: {}, //推荐等级字典数据
      orderList: {}, //是否原创字典数据
      commentList: {}, //是否开启评论字典数据
      dataList: {}, //是否上架字典数据
      uploadUrl: '/api/oss/file/upload', //图片上传地址
      BASE_API: process.env.VUE_APP_BASE_API, //获取后端接口地址
      fileList: 0,
      fileListurl: [],
      limitCount: 1,
      hideUpload: false,
      searchObj: {} //查询集合
    }
  },

  // 页面渲染成功后获取数据
  created() {
    this.fetchData()
  },
  // 定义方法
  methods: {
    approvalShow(row) {
      this.dialogFormVisible = true
      blogApi.getById(row).then(response => {
        this.form = response.data.blog
        //表单数据类型转换
        var tag = new Array()
        tag = this.form.tagId.split(',')
        tag = tag.map(function(data) {
          return +data
        })
        this.form.tagId = tag
        this.form.original = this.form.original == true ? 1 : 2
        this.form.openComment = this.form.openComment == true ? 1 : 2

        this.fileListurl.push({
          name: 'blogTitleImg',
          url: this.form.fileId
        })
        this.fileList = 1
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
          blogApi.removeById(id).then(response => {
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
    srclistBig(url) {
      this.srcList.push(url)
    },
    //选中分类下拉后给表单赋值
    blogSortValue(item) {
      this.form.blogSortId = item.id
      this.form.blogSortName = item.content
    },
    //关闭dialog时清空数据
    closeDialog() {
      location.reload()
    },
    //新增表单提交
    submitForm() {
      this.$refs.form.validate(valid => {
        if (!valid) {
          console.log('校验出错')
        } else {
          if (this.form.id != null) {
            this.form.tagId = this.form.tagId.toString()
            blogApi.updateById(this.form).then(response => {
              this.dialogFormVisible = false
              this.$message.success(response.message)
              this.fetchData()
            })
          } else {
            this.form.tagId = this.form.tagId.toString()
            blogApi.save(this.form).then(response => {
              this.dialogFormVisible = false
              this.$message.success(response.message)
              this.fetchData()
            })
          }
        }
      })
    },
    handleChange() {
      this.hideUpload = this.fileList >= this.limitCount //this.limitCount就是图片数量
    },
    handlePictureCardPreview(file) {
      this.dialogImageUrl = file.url
      this.dialogVisible = true
    },
    onUploadSuccessImg(response, file) {
      this.onUploadSuccess(response, file, 'blogTitleImg')
    },
    onUploadSuccess(response, file, type) {
      // debugger
      if (response.code !== 0) {
        this.$message.error(response.message)
        return
      }
      // 填充上传文件
      this.form.fileId = response.data.url
      this.fileList = 1
    },
    onUploadRemove(file, fileList) {
      //删除oss服务器上的内容
      this.url = file.response.data.url
      webConfigApi.delete(this.url).then(response => {
        this.$message.success(response.message)
        this.fileList = 0
        this.hideUpload = false
      })
    },
    //拼接图片上传路径
    handleBeforeUploadImg: function() {
      return this.BASE_API + this.uploadUrl
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
      blogApi.list(this.page, this.limit, this.searchObj).then(response => {
        this.list = response.data.listPage.records
        this.total = response.data.listPage.total
      })
      //获取标签和分类的数据
      blogApi.getCategoryLabels().then(response => {
        this.tagList = response.data.categoryLabelsVo.tagList
        this.genreList = response.data.categoryLabelsVo.genreList
      })
      //获取字典数据
      blogApi.dict().then(response => {
        this.starsDict = response.data.dict.starsList
        this.orderList = response.data.dict.orderList
        this.commentList = response.data.dict.commentList
        this.dataList = response.data.dict.dataList
      })
    }
  }
}
</script>
<style>
.hide .el-upload--picture-card {
  display: none;
}
</style>
