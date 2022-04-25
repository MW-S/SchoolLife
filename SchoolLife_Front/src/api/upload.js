import request from '@/utils/request'

const preUrl = "/user";
export function upload(data) {
  return request({
    url: preUrl + '/uploadOss',
    method: 'post',
    headers: {"Content-Type": "multipart/form-data"},
    data
  })
}
