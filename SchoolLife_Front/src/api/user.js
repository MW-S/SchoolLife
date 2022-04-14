import request from '@/utils/request'

export function login(data) {
  return request({
    url: '/auth/login',
    method: 'post',
    data
  })
}

export function getInfo() {
  return request({
    url: '/auth/info',
    method: 'get'
  })
}

export function logout() {
  return request({
    url: '/auth/logout',
    method: 'post'
  })
}

export function getList(query) {
  return request({
    url: '/user/getList',
    method: 'get',
    params: query
  })
}

export function fetchUser(id) {
  return request({
    url: '/user/getById',
    method: 'get',
    params: { id }
  })
}

export function updateUser(data) {
  return request({
    url: '/user/update',
    method: 'post',
    data
  })
}

export function createUser(data) {
  return request({
    url: '/user/add',
    method: 'post',
    data
  })
}

export function delUser(data) {
  return request({
    url: '/user/del',
    method: 'post',
    data
  })
}
