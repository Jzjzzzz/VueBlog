import request from '@/utils/request'
export default {
  getWebConfig(params) {
    return request({
      url: '/admin/core/webConfig/getWebConfig',
      method: 'get',
      params
    })
  },
  editWebConfig(params) {
    return request({
      url: '/admin/core/webConfig/editWebConfig',
      method: 'put',
      data: params
    })
  },
  uploadImage(data) {
    return request({
      url: '/api/oss/file/upload',
      method: 'post',
      data: data
    })
  },
  delete(url) {
    return request({
      url: '/api/oss/file/remove?url=' + url,
      method: 'delete'
    })
  }
}
