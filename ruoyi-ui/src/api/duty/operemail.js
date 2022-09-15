import request from '@/utils/request'

// 查询邮件列表
export function list(query) {
  return request({
    url: '/mail/message/list',
    method: 'post',
    data: query
  })
}

// 查询邮件详细
export function getEmailInfoById(mailAttachmentId) {
  return request({
    url: '/baseFile/mailMsg/' + mailAttachmentId,
    method: 'get'
  })
}