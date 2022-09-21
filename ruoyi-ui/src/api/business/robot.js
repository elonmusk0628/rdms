import request from '@/utils/request'

// 查询水情信息列表
export function listWord(query) {
  return request({
    url: '/key/word/select',
    method: 'get',
    params: query
  })
}

// 修改水情信息
export function updateWord(data) {
  return request({
    url: '/key/word/update',
    method: 'post',
    data: data
  })
}

// 新增水情信息
export function addWord(data) {
  return request({
    url: '/key/word/add',
    method: 'post',
    data: data
  })
}

// 删除水情信息
export function delWord(query) {
  return request({
    url: '/key/word/delete',
    method: 'get',
    params: query
  })
}
