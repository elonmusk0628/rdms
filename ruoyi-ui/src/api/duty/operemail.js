import request from '@/utils/request'

// 查询邮件列表
export function listEmail(query) {
  return request({
    url: '/mail/message/getMailList',
    method: 'get',
    params: query
  })
}

// 查询邮件详细
export function getEmailInfoById(id) {
  return request({
    url: '/mail/message/getMailInfoById/' + id,
    method: 'get'
  })
}
