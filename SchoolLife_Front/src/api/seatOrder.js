import request from '@/utils/request'
import qs from 'qs'

export function getList(query) {
  return request({
    url: '/place/seat/getOrderList',
    method: 'get',
    params: query
  })
}

export function getById(id) {
  return request({
    url: '/place/seat/getOrderById',
    method: 'get',
    params:  id 
  })
}

export function save(data) {
  return request({
    url: '/place/seat/saveOrder',
    method: 'post',
    data
  })
}


export function delByIds(ids) {
  return request({
    url: '/place/seat/delOrderByIds',
    method: 'post',
    params: ids,
    paramsSerializer: params => {
      return qs.stringify(params, {
        indices: false
      })
    }
  })
}
