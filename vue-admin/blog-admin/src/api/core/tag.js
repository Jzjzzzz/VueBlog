import request from '@/utils/request'
export default {
  dict() {
    return request({
      url: '/admin/core/tag/dictList',
      method: 'get'
    })
  },
  list(page, limit, searchObj) {
    return request({
      url: `/admin/core/tag/list/${page}/${limit}`,
      method: 'get',
      params: searchObj
    })
  },
  removeById(id) {
    return request({
      url: `/admin/core/tag/remove/${id}`,
      method: 'delete'
    })
  },
  approval(tag) {
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
  },
  stickyBlogById(id) {
    return request({
      url: `/admin/core/tag/stickyBlogById/${id}`,
      method: 'get'
    })
  },
  deleteBatchTag(params) {
    return request({
      url: '/admin/core/tag/deleteBatch',
      method: 'post',
      data: params
    })
  }
}
