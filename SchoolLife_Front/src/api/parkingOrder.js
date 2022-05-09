import request from '@/utils/request'
import qs from 'qs'

export function getList(query) {
  return request({
    url: '/traffic/parking/getOrderList',
    method: 'get',
    params: query
  })
}

export function getById(id) {
  return request({
    url: '/traffic/parking/getOrderById',
    method: 'get',
    params:  id 
  })
}

export function save(data) {
  return request({
    url: '/traffic/parking/saveOrder',
    method: 'post',
    data
  })
}


export function delOrderByIds(ids) {
  return request({
    url: '/traffic/parking/delOrderByIds',
    method: 'post',
    params: ids,
    paramsSerializer: params => {
      return qs.stringify(params, {
        indices: false
      })
    }
  })
}
