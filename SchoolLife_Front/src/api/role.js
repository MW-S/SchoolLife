import request from '@/utils/request'
import qs from 'qs'

export function getRoutes() {
  return request({
    url: '/vue-element-admin/routes',
    method: 'get'
  })
}

export function getRoles(query) {
  return request({
    url: '/role/getList',
    method: 'get',
    params: query
  })
}

export function addRole(data) {
  return request({
    url: '/role/save',
    method: 'post',
    data
  })
}

export function deleteRole(ids) {
  return request({
    url: `/role/delByIds`,
    method: 'post',
    params: ids,
    paramsSerializer: params => {
      return qs.stringify(params, {
        indices: false
      })
    }
  })
}
