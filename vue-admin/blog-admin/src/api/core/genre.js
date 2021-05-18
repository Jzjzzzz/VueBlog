import request from '@/utils/request'
export default {
  list(page, limit, searchObj) {
    return request({
      url: `/admin/core/genre/list/${page}/${limit}`,
      method: 'get',
      params: searchObj
    })
  },
  removeById(id) {
    return request({
      url: `/admin/core/genre/remove/${id}`,
      method: 'delete'
    })
  },
  approval(tag) {
    return request({
      url: '/admin/core/genre/save',
      method: 'post',
      data: tag
    })
  },

  getById(id) {
    return request({
      url: `/admin/core/genre/getById/${id}`,
      method: 'get'
    })
  },
  updateById(tag) {
    return request({
      url: '/admin/core/genre/update',
      method: 'put',
      data: tag
    })
  },
  stickyBlogById(id) {
    return request({
      url: `/admin/core/genre/stickyBlogById/${id}`,
      method: 'get'
    })
  },
  deleteBatchTag(params) {
    return request({
      url: '/admin/core/genre/deleteBatch',
      method: 'post',
      data: params
    })
  }
}
