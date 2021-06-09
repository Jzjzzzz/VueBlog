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
  }
}
