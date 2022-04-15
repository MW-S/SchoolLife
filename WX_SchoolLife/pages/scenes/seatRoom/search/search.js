const app = getApp()
const api = require('../../../../utils/api.js');

Page({

  /**
   * 页面的初始数据
   */
  data: {
    listData: [],
    teachBuilding:"",
    show: false,
    columns:["1个小时","2个小时","3个小时","4个小时","5个小时","6个小时",]
  },
  book: function(e){
    var id = e.currentTarget.dataset.id;
    this.setData({
      show: true,
      saveVo: {seatId:id}
    })
  },
  selectTime(){
    var saveVo = this.data.saveVo;
    saveVo.userId = wx.getStorageSync("user").id
    this.setData({
      saveVo: saveVo
    })
    this.save(saveVo)
  },
  onClose:  function(id){
    this.setData({
      show: false,
      saveVo: {}
    })
  },
  onChange(event) {
    const { picker, value, index } = event.detail;
    var saveVo = this.data.saveVo;
    saveVo.useTime = value;
    this.setData({
      saveVo: saveVo
    })
  },
  backIndex: function () {
    wx.reLaunch({
      url: '/pages/index/index'
    })
  },
  save(vo){
    wx.showLoading({title:"正在预约"})
    let that = this;
    api.post("/seat/saveOrder", vo, 1).then(res=>{
      wx.hideLoading();
      if(res.code == 1){
        wx.showToast({
          title: "预约成功"
        })
        that.setData({
          showformbox: false
        })
        that.getList();
      }
    }).catch(res=>{
      wx.hideLoading();
      wx.showToast({
        title: res.msg
      })
      console.log(res);
    })
  },
  getList(type = 0){
    wx.showLoading({title:"正在加载"})
    let that = this;
    api.post("/seat/getListByVo", 
    {"aimVo": JSON.stringify(that.data.vo)}
    , 0 , 0).then(res=>{
      if(res.code == 1){
        var list = [];
        res.data.list.forEach(item=>{
          var tmp = that.formatDate(item.gmtCreate);
          item.gmtCreate = tmp;
          list.push(item);
        })
        that.setData({
          list: list,
          total: res.data.total
        })
        wx.hideLoading();
      }
    }).catch(res=>{
      wx.hideLoading();
      console.log(res);
    })
  },
  formatDate(time){
    var date = undefined,res ;
    if(time == undefined || time == null){
      date = new Date()
      res =  date.toJSON().replace('T', ' ').split('.')[0];
    }else{
      date = new Date(time);
      date = new Date(date.getTime() + 16 * 60 * 60 * 1000 );
      res =  date.toJSON().replace('T', ' ').split('.')[0];
    }
    return res;
  },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    var vo = {
      school: options.school,
      location: options.location,
      state: 0
    }
    this.setData({
      vo: vo
    })
    this.getList()
    // this.getRoom(options)
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