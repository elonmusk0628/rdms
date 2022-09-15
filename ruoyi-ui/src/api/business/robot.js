import request from '@/utils/request'

// 查询水情信息列表
export function listWord(query) {
  return request({
    url: '/key/word/select',
    method: 'get',
    params: query
  })
}

// 查询水情信息详细
export function getWord(dictId) {
  return request({
    url: '/key/word/select/' + dictId,
    method: 'get'
  })
}

// 修改水情信息
export function updateWord(data) {
  return request({
    url: '/key/word/select',
    method: 'put',
    data: data
  })
}

// 新增水情信息
export function addWord(data) {
  return request({
    url: '/key/word/select',
    method: 'post',
    data: data
  })
}

// 删除水情信息
export function delWord(waterId) {
  return request({
    url: '/key/word/select/' + waterId,
    method: 'delete'
  })
}