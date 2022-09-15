import request from '@/utils/request'

// 查询电话列表
export function list(query) {
  return request({
    url: '/fxb/telrecord/list',
    method: 'post',
    data: query
  })
}

// 查询电话详细
export function getTelInfoById(telRecordId) {
  return request({
    url: '/fxb/telrecord/' + telRecordId,
    method: 'get'
  })
}

// 删除电话
export function delTel(telRecordId) {
  return request({
    url: ' /fxb/sendinfo/' + telRecordId,
    method: 'delete'
  })
}

// 修改电话
export function updateTel(data) {
  return request({
    url: '/fxb/link/',
    method: 'put',
    data: data
  })
}

// 新增电话
export function addTel(data) {
  return request({
    url: '/fxb/link/',
    method: 'post',
    data: data
  })
}