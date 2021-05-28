//引入axios的初始化模块
import request from '@/utils/request'
export default {
  listByParentId(page, limit, searchObj, parentId) {
    return request({
      url: `/admin/core/dict/listByParentId/${page}/${limit}/${parentId}`,
      method: 'get',
      params: searchObj
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
  },
  saveSun(dict, parentId) {
    return request({
      url: `/admin/core/dict/saveSun/${parentId}`,
      method: 'post',
      data: dict
    })
  },
  dict() {
    return request({
      url: '/admin/core/dict/dictList',
      method: 'get'
    })
  },
  removeDictRedis() {
    return request({
      url: '/admin/core/dict/removeDictRedis',
      method: 'delete'
    })
  }
}
