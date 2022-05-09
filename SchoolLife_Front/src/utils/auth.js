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
  return localStorage.getItem('Authorization')
}

export function setJWTToken(token) {
  return localStorage.setItem("Authorization",'Bearer ' + token);
}

export function removeJWTToken() {
  return localStorage.removeItem('Authorization')
}
