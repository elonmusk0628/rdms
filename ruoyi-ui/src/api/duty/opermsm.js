import request from '@/utils/request'

// 查询短信列表
export function list(query) {
  return request({
    url: '/msm/message/list',
    method: 'post',
    data: query
  })
}

// 查询短信详细
export function getMsmInfoById(sendInfoId) {
  return request({
    url: '/msm/message/getInfoById' + sendInfoId,
    method: 'get'
  })
}
