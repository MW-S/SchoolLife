const app = getApp()

const request = (url, options, type, isJSON) => {
    let typeVal = ['application/x-www-form-urlencoded', 'application/json; charset=UTF-8'];
    let header = {};
    header['Content-Type'] = typeVal[type];
    if(wx.getStorageSync("token")){
        header['Authorization'] = "Bearer " +wx.getStorageSync('token')
    }
   return new Promise((resolve, reject) => {
       wx.request({
           url: `${app.globalData.serverUrl}${url}`,
           method: options.method,
           data: options.method === 'GET' || !isJSON ? options.data : JSON.stringify(options.data),
           header: header,
           success(request) {
               if (request.data.code === 1) {
                   resolve(request.data)
               } else {
                   reject(request.data)
               }
           },
           fail(error) {
               reject(error.data)
           }
       })
   })
}

const get = (url, options = {}, type = 0, isJSON = 1) => {
   return request(url, { method: 'GET', data: options }, type, isJSON)
}

const post = (url, options, type = 0, isJSON = 1) => {
   return request(url, { method: 'POST', data: options }, type, isJSON)
}

const put = (url, options, type = 0, isJSON = 1) => {
   return request(url, { method: 'PUT', data: options }, type, isJSON)
}

// 不能声明DELETE（关键字）
const remove = (url, options, type = 0, isJSON = 1) => {
   return request(url, { method: 'DELETE', data: options }, type, isJSON)
}

module.exports = {
   get,
   post,
   put,
   remove
}
