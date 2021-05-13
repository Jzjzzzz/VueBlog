import request from '@/utils/request'
export default {
  list(page, limit) {
    return request({
      url: `/admin/core/tag/list/${page}/${limit}`,
      method: 'get'
    })
  },
  removeById(id) {
    return request({
      url: `/admin/core/tag/remove/${id}`,
      method: 'delete'
    })
  },
  save(tag) {
    return request({
      url: '/admin/core/tag/save',
      method: 'post',
      data: tag
    })
  },

  getById(id) {
    return request({
      url: `/admin/core/tag/getById/${id}`,
      method: 'get'
    })
  },
  updateById(tag) {
    return request({
      url: '/admin/core/tag/update',
      method: 'put',
      data: tag
    })
  }
}
