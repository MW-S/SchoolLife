const app = getApp()
import api from '../../../../utils/util.js'

Page({

  /**
   * 页面的初始数据
   */
  data: {
    listData: [],
    teachBuilding:""
  },

  getRoom: function (options) {
    that = this

    // var sections = options.section.split(",")
    var sections = [];
    var buildings = {
      "主教": "0",
      "二教A": "1",
      "二教B": "2"
    }

    var freebuildings = {
      "主教": "教室：主教-",
      "二教A": "教室：2A",
      "二教B": "教室：2B"
    }
    

    var st = new Set()
    st.add("01")
    st.add("02")
    var sec, x, y
    var count = 0
    var flag = false
    var timer = setInterval(function () {
      // console.log("循环定时器等待循环请求结束")
      // console.log("count:" + count)
      // console.log("sections.length:" + sections.length)
      if (count == sections.length) {
        if (flag){
          wx.showModal({
            content: '抱歉，无空闲教室',
            showCancel: false,
            complete: function () {
              wx.navigateBack({
                delta: 1
              })
            }
          })
        }
        let listData = Array.from(st)
        let teachBuilding = freebuildings[options.build]
        that.setData({ listData, teachBuilding })
        clearInterval(timer);
      }
    }
      , 500)
  },
  backIndex: function () {
    wx.reLaunch({
      url: '/pages/index/index'
    })
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    this.getRoom(options)
  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {

  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {

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
    return {
      title: '空闲教室查询结果',
      path: '/pages/search/room/index?build=' + this.options.build + '&section=' + this.options.section + '&day=' + this.options.day + '&week=' + this.options.week
    }
  }
})

var that;
function json2Form(json) {
  var str = [];
  for (var p in json) {
    str.push(encodeURIComponent(p) + "=" + encodeURIComponent(json[p]));
  }
  return str.join("&");
}