import request from '@/utils/request'

export function getList(query) {
  return request({
    url: '/car/getList',
    method: 'get',
    params: query
  })
}

export function getById(id) {
  return request({
    url: '/car/getById',
    method: 'get',
    params:  id 
  })
}

export function save(data) {
  return request({
    url: '/car/save',
    method: 'post',
    data
  })
}


export function delByIds(ids) {
  return request({
    url: '/car/delByIds',
    method: 'post',
    ids
  })
}
