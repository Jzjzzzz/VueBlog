<template>
  <div class="app-container">
    <el-tabs type="border-card">
      <el-tab-pane label="网站信息">
        <span slot="label">
          <i class="el-icon-date"></i>
          网站信息
        </span>

        <el-form ref="form" :model="form" label-width="80px">
          <el-form-item label="LOGO">
            <el-upload
              :action="handleBeforeUploadImg()"
              :on-success="onUploadSuccessLOGO"
              :on-remove="onUploadRemove"
              :multiple="false"
              :on-change="handleEditChange"
              :class="{ hide: hideUpload }"
              :data="{ module: 'LOGO' }"
              :limit="1"
              :file-list="fileList"
              list-type="picture-card"
            >
              <i class="el-icon-plus"></i>
            </el-upload>
          </el-form-item>
          <el-row :gutter="24">
            <el-col :span="10">
              <el-form-item label="网站名称" prop="oldPwd">
                <el-input v-model="form.name" style="width: 400px"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="10">
              <el-form-item label="标题" prop="newPwd1">
                <el-input v-model="form.title" style="width: 400px"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row :gutter="24">
            <el-col :span="10">
              <el-form-item label="关键字" prop="newPwd2">
                <el-input
                  v-model="form.keyword"
                  style="width: 400px"
                ></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="10">
              <el-form-item label="描述" prop="newPwd1">
                <el-input
                  v-model="form.summary"
                  style="width: 400px"
                ></el-input>
              </el-form-item>
            </el-col>
          </el-row>

          <el-row :gutter="24">
            <el-col :span="10">
              <el-form-item label="作者" prop="newPwd2">
                <el-input v-model="form.author" style="width: 400px"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="10">
              <el-form-item label="备案号" prop="newPwd2">
                <el-input
                  v-model="form.recordNum"
                  style="width: 400px"
                ></el-input>
              </el-form-item>
            </el-col>
          </el-row>

          <el-form-item>
            <el-button type="primary" @click="submitForm()">
              保 存
            </el-button>
          </el-form-item>
        </el-form>
      </el-tab-pane>
      <el-tab-pane label="评论和打赏">
        <span slot="label">
          <i class="el-icon-date"></i>
          评论和打赏
        </span>
        暂未开发
      </el-tab-pane>
      <el-tab-pane v-permission="'/webConfig/getWebConfig'">
        <span slot="label">
          <i class="el-icon-date"></i>
          关注我们
        </span>
        <el-form
          style="margin-left: 20px;"
          label-position="left"
          :model="form"
          label-width="80px"
          :rules="rules"
          ref="form"
        >
          <el-checkbox-group v-model="form.showList">
            <el-form-item label="邮箱" prop="email">
              <el-input v-model="form.email" style="width: 400px"></el-input>
              <el-checkbox label="1" style="margin-left: 10px">
                在首页显示
              </el-checkbox>
            </el-form-item>

            <el-form-item label="QQ号" prop="qqNumber">
              <el-input v-model="form.qqNumber" style="width: 400px"></el-input>
              <el-checkbox label="2" style="margin-left: 10px">
                在首页显示
              </el-checkbox>
            </el-form-item>

            <el-form-item label="QQ群" prop="qqGroup">
              <el-input v-model="form.qqGroup" style="width: 400px"></el-input>
              <el-checkbox label="3" style="margin-left: 10px">
                在首页显示
              </el-checkbox>
            </el-form-item>

            <el-form-item label="github" prop="github">
              <el-input v-model="form.github" style="width: 400px"></el-input>
              <el-checkbox label="4" style="margin-left: 10px">
                在首页显示
              </el-checkbox>
            </el-form-item>

            <el-form-item label="Gitee" prop="gitee">
              <el-input v-model="form.gitee" style="width: 400px"></el-input>
              <el-checkbox label="5" style="margin-left: 10px">
                在首页显示
              </el-checkbox>
            </el-form-item>

            <el-form-item label="微信" prop="weChat">
              <el-input v-model="form.weChat" style="width: 400px"></el-input>
              <el-checkbox label="6" style="margin-left: 10px">
                在首页显示
              </el-checkbox>
            </el-form-item>
          </el-checkbox-group>
          <el-form-item>
            <el-button
              type="primary"
              @click="submitForm()"
              v-permission="'/webConfig/editWebConfig'"
            >
              保 存
            </el-button>
          </el-form-item>
        </el-form>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>
<style>
.hide .el-upload--picture-card {
  display: none;
}
</style>
<script>
import webConfigApi from '@/api/core/webConfig'
export default {
  // 定义数据模型
  data() {
    return {
      form: {
        name: '',
        title: '',
        keyword: '',
        summary: '',
        author: '',
        logo: '',
        recordNum: '',
        openComment: '1',
        aliPay: '',
        weixinPay: '',
        aliPayPhoto: '',
        weixinPayPhoto: '',
        showList: [],
        loginTypeList: []
      },
      hideUpload: false,
      limitCount: 1,
      uploadUrl: '/api/oss/file/upload', //图片上传地址
      BASE_API: process.env.VUE_APP_BASE_API, //获取后端接口地址
      fileList: []
    }
  },
  // 页面渲染成功后获取数据
  created() {
    this.fetchData()
  },

  // 定义方法
  methods: {
    handleBeforeUploadImg: function() {
      return this.BASE_API + this.uploadUrl
    },
    handleEditChange(file, fileList) {
      console.log(file)
      this.hideUpload = fileList.length >= this.limitCount
    },
    handleChange(file, fileList) {
      webConfigApi.uploadImage(file).then(response => {
        this.$message.success(response.message)
      })
    },
    onUploadSuccessLOGO(response, file) {
      this.onUploadSuccess(response, file, 'LOGO')
    },
    onUploadSuccess(response, file, type) {
      // debugger
      if (response.code !== 0) {
        this.$message.error(response.message)
        return
      }
      // 填充上传文件
      this.form.logo = response.data.url
    },

    onUploadRemove(file, fileList) {
      //删除oss服务器上的内容
      this.url = file.response.data.url
      webConfigApi.delete(this.url).then(response => {
        this.$message.success(response.message)
        this.hideUpload = fileList.length >= this.limitCount
      })
    },

    fetchData() {
      // 调用api
      webConfigApi.getWebConfig().then(response => {
        this.form = response.data.webConfig
        this.fileList.push({
          name: 'logo',
          url: this.form.logo
        })
      })
    },

    submitForm() {
      let form = this.form
      webConfigApi.editWebConfig(form).then(response => {
        this.$message.success('保存成功')
        this.fileList.splice(0)
        this.fetchData()
      })
    }
  }
}
</script>
