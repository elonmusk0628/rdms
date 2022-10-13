import request from '@/utils/request'

// 查询问答列表
export function listQAndA(query) {
  return request({
    url: '/QAndA/select',
    method: 'get',
    params: query
  })
}

// 修改问答
export function updateQAndA(data) {
  return request({
    url: '/QAndA/update',
    method: 'post',
    data: data
  })
}

// 新增问答
export function addQAndA(data) {
  return request({
    url: '/QAndA/add',
    method: 'post',
    data: data
  })
}

// 删除问答
export function delQAndA(query) {
  return request({
    url: '/QAndA/delete',
    method: 'get',
    params: query
  })
}
