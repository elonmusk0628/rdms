import request from '@/utils/request'

// 查询传真列表
export function list(query) {
  return request({
    url: '/fax/message/list',
    method: 'post',
    data: query
  })
  
}

// 查询传真详细
export function getFaxInfoById(sendInfoId) {
  return request({
    url: '/fax/message/getInfoById/' + sendInfoId,
    method: 'get'
  })
}
