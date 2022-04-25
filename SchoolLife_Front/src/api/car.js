import request from '@/utils/request'
import qs from 'qs'

export function getList(query) {
  return request({
    url: '/traffic/car/getList',
    method: 'get',
    params: query
  })
}

export function getById(id) {
  return request({
    url: '/traffic/car/getById',
    method: 'get',
    params:  id 
  })
}

export function save(data) {
  return request({
    url: '/traffic/car/save',
    method: 'post',
    data
  })
}


export function delByIds(ids) {
  return request({
    url: '/traffic/car/delByIds',
    method: 'post',
    params: ids,
    paramsSerializer: params => {
      return qs.stringify(params, {
        indices: false
      })
    }
  })
}
