import request from '@/utils/request'

// 查询电话列表
export function listTelephone(query) {
  return request({
    url: '/tel/message/getTelList',
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

// 查询当日电话数量
export function todayTelephoneNum() {
  return request({
    url: '/tel/message/getTelCount',
    method: 'get'
  })
}
