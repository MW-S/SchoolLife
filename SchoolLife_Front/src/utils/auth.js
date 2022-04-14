import Cookies from 'js-cookie'

const TokenKey = 'Admin-Token'

export function getToken() {
  return Cookies.get(TokenKey)
}

export function setToken(token) {
  return Cookies.set(TokenKey, token)
}

export function removeToken() {
  return Cookies.remove(TokenKey)
}

export function getJWTToken() {
  return Cookies.get('Authorization')
}

export function setJWTToken(token) {
  return Cookies.set('Authorization', 'Bearer ' + token)
}

export function removeJWTToken() {
  return Cookies.remove('Authorization')
}
