import request from '@/utils/request'

// 查询问答列表
export function listWord(query) {
  return request({
    url: '/key/word/select',
    method: 'get',
    params: query
  })
}

// 修改问答
export function updateWord(data) {
  return request({
    url: '/key/word/update',
    method: 'post',
    data: data
  })
}

// 新增问答
export function addWord(data) {
  return request({
    url: '/key/word/add',
    method: 'post',
    data: data
  })
}

// 删除问答
export function delWord(query) {
  return request({
    url: '/key/word/delete',
    method: 'get',
    params: query
  })
}

// 刷新问答
export function refreshWord() {
  return request({
    url: '/key/word/refresh',
    method: 'get'
  })
}
