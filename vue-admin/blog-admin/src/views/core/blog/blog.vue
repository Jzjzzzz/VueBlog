<template>
  <div class="app-container">
    <!--查询表单-->

    <el-button
      class="grid-content bg-purple"
      type="primary"
      icon="el-icon-edit"
      size="medium"
      @click="dialogFormVisible = true"
    >
      新增
    </el-button>
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
                list-type="picture-card"
                :file-list="fileList"
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
                v-model="form.blogSortId"
                size="small"
                placeholder="请选择"
              >
                <el-option
                  v-for="item in genreList"
                  :key="item.id"
                  :label="item.content"
                  :value="item.id"
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
            <el-form-item
              label="推荐等级"
              :label-width="maxLineLabelWidth"
              prop="level"
            >
              <el-select v-model="form.level" size="small" placeholder="请选择">
                <el-option
                  v-for="item in starsDict"
                  :key="item.id"
                  :label="item.name"
                  :value="item.id"
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
            <el-form-item
              label="是否发布"
              :label-width="lineLabelWidth"
              prop="status"
            >
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
      tagValue: [], //保存选中标签id(编辑时)
      tagList: {}, //标签列表
      genreList: {}, //分类列表
      starsDict: {}, //推荐等级字典数据
      orderList: {}, //是否原创字典数据
      commentList: {}, //是否开启评论字典数据
      dataList: {}, //是否上架字典数据
      uploadUrl: '/api/oss/file/upload', //图片上传地址
      BASE_API: process.env.VUE_APP_BASE_API, //获取后端接口地址
      fileList: 0,
      limitCount: 1,
      hideUpload: false
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
      location.reload()
    },
    //新增表单提交
    submitForm() {
      this.$refs.form.validate(valid => {
        if (!valid) {
          console.log('校验出错')
        } else {
          this.form.tagId = this.form.tagId.toString()
          blogApi.save(this.form).then(response => {
            this.dialogFormVisible = false
            this.$message.success(response.message)
            this.fetchData()
          })
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
    fetchData() {
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
<style scoped></style>
<style>
.hide .el-upload--picture-card {
  display: none;
}
</style>
