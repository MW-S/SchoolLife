/**

 * 如有技术问题或商业合作，可以添加本人微信:Exixir99

 * @author 全黑科技

 * @Time 2021-06-16 21:49:01

 * @URL http://www.abtwork.com/

 */
const time = require('../../utils/time.js')
const api = require('../../utils/api.js')
//获取应用实例
const app = getApp()
// const db = wx.cloud.database()
Page({

  /**
   * 页面的初始数据
   */
  data: {
    loadModal: true,
    times: [
      "08:30-10:00",
      "10:15-11:45",
      "14:30-16:00",
      "16:15-17:45",
      "19:00-20:30",
    ],
    isLogin: false,
    indicatorDots: false,
    autoplay: true,
    interval: 5000,
    duration: 800,
    circular: true,
    user_data: {},
    // 轮播图
    imgUrls: [],

    "navs": [
      {
        path: "goods/goods",
        key: "lost",
        desc: "闲置物品",
        verify: ""
      }, 
      {
        path: "canteen/canteen",
        key: "gold",
        desc: "食堂菜单",
        verify: ""
      },
      {
        path: "vindicate/vindicate",
        key: "lovewall",
        desc: "表白墙",
        verify: ""
      },
      {
        path: "delivery/delivery",
        key: "shop",
        desc: "跑腿代购",
        verify: ""
      },
      {
        path: "scenes/classRoom/classRoom",
        key: "shop",
        desc: "空闲教室",
        verify: ""
      },
      {
        path: "scenes/seatRoom/seatRoom",
        key: "shop",
        desc: "图书馆座位",
        verify: ""
      },
      {
        path: "",
        key: "more",
        desc: "更多功能",
        verify: ""
      }
    ],

    //轮播图的切换事件
    swiperChange: function (e) {
      this.setData({
        swiperCurrent: e.detail.current
      })
    },
    //轮播图点击事件
    swipclick: function (e) {
      console.log(this.data.swiperCurrent)
    },
  },
  previewImage: function (e) {
    var current = e.target.dataset.src;
    wx.previewImage({
      current: current, // 当前显示图片的http链接
      urls: this.data.share_detail.images // 需要预览的图片http链接列表
    })
  },
  getInfo(){
    api.get("/user/auth/info").then(res=>{
      if(res.code == 1){
        wx.setStorageSync('user', res.data.data)
        wx.setStorageSync("noteCount", res.data.noteCount)
        wx.setStorageSync("vindicateCount", res.data.vindicateCount)
        wx.setStorageSync("dormitoryId", res.data.dormitoryId)
      }
    }).catch(res=>{
      console.log(res);
    })
  },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    //system库主要是存放一些学校的开学时间，是否在微信审核等数据,包括很多一段时间就会有变动的数据，为了不需要每次更改都重上线小程序，放在云函数中和云存储中是最合适不过的了 这里因为方便展示的原因写死数据
    let lunbo_data = ['/images/3961622281214_.pic_hd.jpg']
    this.setData({
      imgUrls: lunbo_data,
    })
    this.getInfo();
  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {
    this.setData({
      loadModal: false
    })
  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {

    //this.getTodayClass()

  },
  //获取用户登录、课表等数据
  nowClass() {
    let time_arr = this.data.times;
    for (var i = 0; i < time_arr.length; i++) {
      var start = new Date(time.formatTime_fanxiexian(new Date()) + ' ' + time_arr[i].split('-')[0]).getTime();
      var end = new Date(time.formatTime_fanxiexian(new Date()) + ' ' + time_arr[i].split('-')[1]).getTime();
      var now = new Date().getTime();
      if (now > start && now < end) {
        console.log(i + "=======>", "====>")
        this.setData({
          now_class: i
        })
        break;
      }
    }
  },
  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function () {

  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function () {

  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function () {
    if (app.user_token) {
      this.data.pull = true
      this.getStuclass()
    } else
      wx.stopPullDownRefresh();
  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {

  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {

  },
  init: function (data = undefined) {
    var that = this;
    var not_flag = wx.getStorageSync("not_flag");
    if (app.low_day && (not_flag == false)) {
      // 开学前半个月不使用缓存
      wx.removeStorageSync("stuclass");
    }

    if (data != undefined) {
      /*
      获取到服务器的版本后，初始化完毕
      然后到index进行判断是否为新版本，如果是新版本，则清空缓存重新获取信息。
     */
      that.kb_version = data.version;
      that.slides = data.slides;
      if (that.kb_version != wx.getStorageSync("kb_version")) {
        wx.removeStorageSync("stuclass"); //清空stuclass，重新进行课表获取。
        wx.setStorageSync("kb_version", that.kb_version);
      }
      wx.setStorageSync("slides", that.slides);
    } else {
      that.kb_version = wx.getStorageSync("kb_version")
      that.slides = wx.getStorageSync("slides")
    }
    if (app.user_token) {
      var stuclass = wx.getStorageSync("stuclass");
      if (stuclass == "" && app.offline == false) {
        this.getStuclass(1)
      } else {
        this.getTodayClass(stuclass)
      }
    }
    this.setData({
      "notices": this.slides
    })
  },
  /*跳转到登陆界面，待完善研究生登陆*/
  auth: function (e) {
    console.log(e)
    var type = "jwc"
    wx.navigateTo({
      url: '/pages/login/login?type=' + type,
    })
  },
  navigatetokb: function () {
    wx.navigateTo({
      url: '/pages/core/timetable/timetable',
    })
  },
  submit: function (e) {
    var key = e.detail.target.dataset.key //要去的地方。
    var path = e.detail.target.dataset.path //要去的地方。
    var verify = e.detail.target.dataset.verify; //需要的权限
    var content = ""
    var url = ""
    console.log(app)
    // let is_bind = wx.getStorageSync('is_bind')
    wx.navigateTo({
      url: '/pages/' + path,
      fail: function () {
        wx.showToast({
          title: '即将开放',
          icon: 'none'
        })
      }
    })

    // if (!is_bind) {
    //   wx.showModal({
    //     title: '绑定提示',
    //     content: content,
    //     confirmText: "去绑定",
    //     success: function (res) {
    //       if (res.confirm) {
    //         wx.navigateTo({
    //           url: '/pages/login/login',
    //         })
    //       }
    //     }
    //   })
    //   return;
    // }
    // if (key === 'gold') {
    //   wx.navigateToMiniProgram({
    //     appId: 'wxd7f640f8d9c0e1c3',
    //     path: 'pages/index/index',
    //     envVersion: 'release',
    //     success(res) {
    //       // 打开成功
    //       console.log(res)
    //       return;
    //     }
    //   })
    // } else {
    //   wx.navigateTo({
    //     url: '/pages/core/' + key + "/" + key,
    //     fail: function () {
    //       wx.showToast({
    //         title: '即将开放',
    //         icon: 'none'
    //       })
    //     }
    //   })
    // }
  },
})