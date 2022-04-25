import request from '@/utils/request'

const preUrl = "/user";
export function login(data) {
  return request({
    url: preUrl + '/auth/login',
    method: 'post',
    data
  })
}

export function getInfo() {
  return request({
    url: preUrl + '/auth/info',
    method: 'get'
  })
}

export function logout() {
  return request({
    url: preUrl + '/auth/logout',
    method: 'post'
  })
}

export function getList(query) {
  return request({
    url: preUrl + '/user/getList',
    method: 'get',
    params: query
  })
}

export function fetchUser(id) {
  return request({
    url: preUrl + '/user/getById',
    method: 'get',
    params: { id }
  })
}

export function updateUser(data) {
  return request({
    url: preUrl + '/user/update',
    method: 'post',
    data
  })
}

export function createUser(data) {
  return request({
    url: preUrl + '/user/add',
    method: 'post',
    data
  })
}

export function delUser(data) {
  return request({
    url: preUrl + '/user/del',
    method: 'post',
    data
  })
}
