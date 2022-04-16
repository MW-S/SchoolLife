// app.js
App({
  onLaunch() {
    // 展示本地存储能力
    // const logs = wx.getStorageSync('logs') || []
    // logs.unshift(Date.now())
    // wx.setStorageSync('logs', logs)
    // 登录
    wx.login({
      success: res => {
        // 发送 res.code 到后台换取 openId, sessionKey, unionId
        wx.getUserInfo({
          success: function(res) {
            wx.setStorageSync({
              key: 'user',
              value: res.userInfo
            });
          }
        })
      }
    })
    this.globalData = {
      serverUrl: 'http://192.168.43.147:8090',
      fileServerUrl: 'http://172.26.95.166:9001',
      userInfo: {},
      is_bind_school: false,
      is_login: false,
      school_week: 1,
      week_day: '',
      systemInfo: null,
      windowHeight: null, // rpx换算px后的窗口高度
      screenHeight: null,
      pathMap: {
        "0": "my/note/note",
        "1": "vindicate/vindicate",
        "2": "goods/goods",
        "3": "my/delivery/delivery",
        "4": "scenes/seatRoom/order/order"
      }
    }
    wx.getSystemInfo({
      success: e => {
        this.globalData.StatusBar = e.statusBarHeight;
        let capsule = wx.getMenuButtonBoundingClientRect();
        if (capsule) {
          this.globalData.Custom = capsule;
          this.globalData.CustomBar = capsule.bottom + capsule.top - e.statusBarHeight;
        } else {
          this.globalData.CustomBar = e.statusBarHeight + 50;
        }
        this.globalData.systemInfo = e
        this.globalData.windowHeight = e.windowHeight / (e.windowWidth / 750)
        this.globalData.screenHeight = e.screenHeight / (e.screenWidth / 750)
      }
    })
  },
  globalData: {
    userInfo: null
  }
})
