//引入axios的初始化模块
import request from '@/utils/request'
export default {
  listByParentId(parentId) {
    return request({
      url: `/admin/core/dict/listByParentId/${parentId}`,
      method: 'get'
    })
  }
}
