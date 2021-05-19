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
  }
}
