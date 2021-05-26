//引入axios的初始化模块
import request from '@/utils/request'
export default {
  listByParentId(parentId) {
    return request({
      url: `/admin/core/dict/listByParentId/${parentId}`,
      method: 'get'
    })
  },
  listPage(page, limit, searchObj) {
    return request({
      url: `/admin/core/dict/list/${page}/${limit}`,
      method: 'get',
      params: searchObj
    })
  },
  removeByIdTop(id) {
    return request({
      url: `/admin/core/dict/removeByIdTop/${id}`,
      method: 'delete'
    })
  },
  saveTop(dict) {
    return request({
      url: '/admin/core/dict/saveTop',
      method: 'post',
      data: dict
    })
  },
  getById(id) {
    return request({
      url: `/admin/core/dict/getById/${id}`,
      method: 'get'
    })
  },
  updateById(dict) {
    return request({
      url: '/admin/core/dict/update',
      method: 'put',
      data: dict
    })
  }
}
