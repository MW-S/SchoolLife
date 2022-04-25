import request from '@/utils/request'
import qs from 'qs'

const baseUrl = "/diet"
export function getList(page) {
  return request({
    url: baseUrl + '/canteen/getList',
    method: 'get',
    params: page
  })
}

export function getById(id) {
  return request({
    url: baseUrl + '/canteen/getById',
    method: 'get',
    params:  id 
  })
}

export function save(data) {
  return request({
    url: baseUrl + '/canteen/save',
    method: 'post',
    data
  })
}


export function delByIds(query) {
  return request({
    url: baseUrl + '/canteen/delByIds',
    method: 'post',
    params: query,
    paramsSerializer: params => {
      return qs.stringify(params, {
        indices: false
      })
    }
  })
}
