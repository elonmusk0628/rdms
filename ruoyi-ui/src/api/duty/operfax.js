import request from '@/utils/request'

// 查询传真列表
export function listFax(query) {
  return request({
    url: '/fax/message/getFaxList',
    method: 'get',
    params: query
  })
  
}

// 查询传真详细
export function getFaxInfoById(id) {
  return request({
    url: '/fax/message/getFaxInfoById/' + id,
    method: 'get'
  })
}

// 查询当日传真数量
export function todayFaxNum() {
  return request({
    url: '/fax/message/getFaxCount',
    method: 'get'
  })
}
