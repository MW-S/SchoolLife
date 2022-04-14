const app = getApp()
import api from '../../../utils/util.js'

Page({

  /**
   * 页面的初始数据
   */
  data: {
    schoolArea: ["官渡校区", "西城校区", "光华校区"],
    schoolAreaIndex: 0,
    floor: [["六楼", "七楼"],[],[]],
    floorIndex: 0,
    room: [["612", "613"], ["712"]],
    roomIndex: 0,
    checkboxItems: [
      { name: '08:00-09:00', value: '1' },
      { name: '09:00-10:00', value: '2' },
      { name: '10:00-11:00', value: '3' },
      { name: '11:00-12:00', value: '4' },
      { name: '13:30-14:30', value: '5' },
      { name: '14:30-15:30', value: '6' },
      { name: '15:30-16:30', value: '7' },
      { name: '16:30-17:30', value: '8' },
      { name: '18:30-20:30', value: '10' },
      { name: '20:30-22:30', value: '11,12' }
    ]
  },
  getToday: function (todayClassName) {
    
  },
  buildPicker: function (e) {
    var type = e.currentTarget.dataset.type,
    target = "schoolAreaIndex";
    if(type == "0"){
      target = "schoolAreaIndex"
    }else if(type == "1"){
      target = "floorIndex"
    }else if(type == "2"){
      target = "roomIndex"
    }
    this.setData({
      [target]: e.detail.value
    })
    
  },
  checkboxChange: function (e) {
    let checkboxItems = this.data.checkboxItems, values = e.detail.value
    for (var i = 0, lenI = checkboxItems.length; i < lenI; ++i) {
      checkboxItems[i].checked = false;
      for (var j = 0, lenJ = values.length; j < lenJ; ++j) {
        if (checkboxItems[i].value == values[j]) {
          checkboxItems[i].checked = true;
          break;
        }
      }
    }
    let checkboxValues = values.join(',')
    this.setData({ checkboxItems, checkboxValues })
  },
  searchRoom: function () {
        wx.navigateTo({
          url: './search/search?schoolAreaIndex=' + this.data.schoolAreaIndex +
           '&floorIndex=' + this.data.floorIndex +
           '.&roomIndex=' + this.data.roomIndex 
        })
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    this.getToday('freeroom')
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
      title: '西土城路10号空教室查询',
      desc: '「西土城路10号空教室查询」提供北京邮电大学在校生空闲教室查询服务。',
      path: '/pages/index/index'
    }
  }
})