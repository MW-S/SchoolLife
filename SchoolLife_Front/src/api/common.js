import request from '@/utils/request'
import qs from 'qs'

export function getList(target, page) {
  return request({
    url: '/' + target + '/getList',
    method: 'get',
    params: page
  })
}

export function getById(target, id) {
  return request({
    url: '/' + target + '/getById',
    method: 'get',
    params:  id 
  })
}

export function save(target, data) {
  return request({
    url: '/' + target + '/save',
    method: 'post',
    data
  })
}


export function delByIds(target, query) {
  return request({
    url: '/' + target + '/delByIds',
    method: 'post',
    params: query,
    paramsSerializer: params => {
      return qs.stringify(params, {
        indices: false
      })
    }
  })
}

export function uploadFile(file) {
  return request({
    url: '/uploadOss',
    method: 'post',
    headers: {
      "Content-Type":"multipart/form-data"
    },
    data: file
  })
}
