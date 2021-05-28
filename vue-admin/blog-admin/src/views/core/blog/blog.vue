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
    <el-button
      class="filter-item"
      type="danger"
      size="medium"
      @click="handleDeleteBatch"
      icon="el-icon-delete"
    >
      删除选中
    </el-button>
    <!-- 添加或修改对话框 -->
    <el-dialog title="新增博客" :visible.sync="dialogFormVisible" fullscreen>
      <el-form :model="form" :rules="rules" ref="form">
        <el-row>
          <el-col :span="16">
            <el-form-item
              label="标题"
              :label-width="formLabelWidth"
              prop="title"
            >
              <el-input
                v-model="form.title"
                auto-complete="off"
                @input="contentChange"
              ></el-input>
            </el-form-item>
            <el-form-item label="简介" :label-width="formLabelWidth">
              <el-input
                v-model="form.summary"
                auto-complete="off"
                @input="contentChange"
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="标题图" :label-width="formLabelWidth">
              1111
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="6">
            <el-form-item
              label="分类"
              :label-width="formLabelWidth"
              prop="blogSortUid"
            >
              <el-select
                @input="contentChange"
                v-model="form.blogSortUid"
                size="small"
                placeholder="请选择"
                style="width:150px"
              >
                <el-option
                  v-for="item in blogSortData"
                  :key="item.uid"
                  :label="item.sortName"
                  :value="item.uid"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>

          <el-col :span="6">
            <el-form-item label="标签" label-width="80px" prop="tagUid">
              <el-select
                @input="contentChange"
                v-model="tagValue"
                multiple
                size="small"
                placeholder="请选择"
                style="width:210px"
                filterable
              >
                <el-option
                  v-for="item in tagData"
                  :key="item.uid"
                  :label="item.content"
                  :value="item.uid"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>

          <el-col :span="6">
            <el-form-item
              label="推荐等级"
              :label-width="maxLineLabelWidth"
              prop="level"
            >
              <el-select
                v-model="form.level"
                size="small"
                placeholder="请选择"
                style="width:210px"
              >
                <el-option
                  v-for="item in blogLevelDictList"
                  :key="item.uid"
                  :label="item.dictLabel"
                  :value="parseInt(item.dictValue)"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="6.5">
            <el-form-item
              label="是否原创"
              :label-width="formLabelWidth"
              prop="isOriginal"
            >
              <el-radio-group v-model="form.isOriginal" size="small">
                <el-radio
                  v-for="item in blogOriginalDictList"
                  :key="item.uid"
                  :label="item.dictValue"
                  border
                >
                  {{ item.dictLabel }}
                </el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
    </el-dialog>
  </div>
</template>
<script>
import tagApi from '@/api/core/tag'
export default {
  // 定义数据模型
  data() {
    return {
      dialogFormVisible: false, //弹出框状态
      form: {
        uid: null,
        title: null,
        summary: null,
        content: null,
        tagUid: null,
        fileUid: null,
        isOriginal: null, //是否原创
        isPublish: null,
        author: null, //作者
        clickCount: 0,
        articlesPart: null //文章出处
      },
      blogOriginalDictList: [] //存储区域字典
    }
  },
  // 页面渲染成功后获取数据
  created() {},
  // 定义方法
  methods: {}
}
</script>
