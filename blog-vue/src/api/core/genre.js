import request from '@/utils/request'
export default {
  list(page, limit) {
    return request({
      url: `/admin/core/genre/list/${page}/${limit}`,
      method: 'get'
    })
  },
  removeById(id) {
    return request({
      url: `/admin/core/genre/remove/${id}`,
      method: 'delete'
    })
  },
  save(genre) {
    return request({
      url: '/admin/core/genre/save',
      method: 'post',
      data: genre
    })
  },

  getById(id) {
    return request({
      url: `/admin/core/genre/getById/${id}`,
      method: 'get'
    })
  },
  updateById(genre) {
    return request({
      url: '/admin/core/genre/update',
      method: 'put',
      data: genre
    })
  }
}
