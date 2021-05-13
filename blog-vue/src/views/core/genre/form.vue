<template>
  <div class="app-container">
    <!-- 输入表单 -->
    <el-form label-width="120px">
      <el-form-item label="博客分类名称">
        <el-input
          v-model="genre.typeName"
          placeholder="请输入博客分类名称"
          style="width:300px"
        />
      </el-form-item>
      <el-form-item>
        <el-button
          :disabled="saveBtnDisabled"
          type="primary"
          @click="saveOrUpdate()"
        >
          保存
        </el-button>
      </el-form-item>
    </el-form>
  </div>
</template>
<script>
import genreApi from '@/api/core/genre'
export default {
  data() {
    return {
      genre: {}, // 初始化数据
      saveBtnDisabled: false // 保存按钮是否禁用，防止表单重复提交
    }
  },
  //页面渲染成功
  created() {
    if (this.$route.params.id) {
      this.fetchDataById(this.$route.params.id)
    }
  },
  methods: {
    saveOrUpdate() {
      // 禁用保存按钮
      this.saveBtnDisabled = true
      if (!this.genre.id) {
        this.saveData()
      } else {
        this.updateData()
      }
    },
    // 新增数据
    saveData() {
      // debugger
      genreApi.save(this.genre).then(response => {
        this.$message({
          type: 'success',
          message: response.message
        })
        this.$router.push('/core/genre/list')
      })
    },
    // 根据id查询记录
    fetchDataById(id) {
      genreApi.getById(id).then(response => {
        this.genre = response.data.model
      })
    },
    // 根据id更新记录
    updateData() {
      // 数据的获取
      genreApi.updateById(this.genre).then(response => {
        this.$message({
          type: 'success',
          message: response.message
        })
        this.$router.push('/core/genre/list')
      })
    }
  }
}
</script>
