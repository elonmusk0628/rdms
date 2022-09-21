import request from '@/utils/request'

// 查询电话列表
export function list(query) {
  return request({
    url: '/tel/message/list',
    method: 'get',
    params: query
  })
}

// 查询电话详细
export function getTelInfoById(id) {
  return request({
    url: '/tel/message/getTelInfoById/' + id,
    method: 'get'
  })
}
