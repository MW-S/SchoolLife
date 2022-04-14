import request from '@/utils/request'
import qs from 'qs'

export function getList(page) {
  return request({
    url: '/vindicate/getList',
    method: 'get',
    params: page
  })
}

export function getById(id) {
  return request({
    url: '/vindicate/getById',
    method: 'get',
    params:  id 
  })
}

export function save(data) {
  return request({
    url: '/vindicate/save',
    method: 'post',
    data
  })
}


export function delByIds(query) {
  return request({
    url: '/vindicate/delByIds',
    method: 'post',
    params: query,
    paramsSerializer: params => {
      return qs.stringify(params, {
        indices: false
      })
    }
  })
}
