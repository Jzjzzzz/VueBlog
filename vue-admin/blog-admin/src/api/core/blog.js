//引入axios的初始化模块
import request from '@/utils/request'
export default {
  getCategoryLabels() {
    return request({
      url: '/admin/core/blog/getCategoryLabels',
      method: 'get'
    })
  },
  dict() {
    return request({
      url: '/admin/core/blog/dictList',
      method: 'get'
    })
  },
  save(blog) {
    return request({
      url: '/admin/core/blog/save',
      method: 'post',
      data: blog
    })
  },
  list(page, limit, searchObj) {
    return request({
      url: `/admin/core/blog/blogList/${page}/${limit}`,
      method: 'get',
      params: searchObj
    })
  },
  removeById(id) {
    return request({
      url: `/admin/core/blog/remove/${id}`,
      method: 'delete'
    })
  },
  getById(id) {
    return request({
      url: `/admin/core/blog/getById/${id}`,
      method: 'get'
    })
  },
  updateById(blog) {
    return request({
      url: '/admin/core/blog/update',
      method: 'put',
      data: blog
    })
  }
}
