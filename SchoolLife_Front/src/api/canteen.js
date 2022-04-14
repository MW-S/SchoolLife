import request from '@/utils/request'
import qs from 'qs'

export function getList(page) {
  return request({
    url: '/canteen/getList',
    method: 'get',
    params: page
  })
}

export function getById(id) {
  return request({
    url: '/canteen/getById',
    method: 'get',
    params:  id 
  })
}

export function save(data) {
  return request({
    url: '/canteen/save',
    method: 'post',
    data
  })
}


export function delByIds(query) {
  return request({
    url: '/canteen/delByIds',
    method: 'post',
    params: query,
    paramsSerializer: params => {
      return qs.stringify(params, {
        indices: false
      })
    }
  })
}
