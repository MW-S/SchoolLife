// const db = wx.cloud.database()
var util = require('../../util/util.js');
const api = require('../../../utils/api.js');
Page({

  /**
   * 页面的初始数据
   */
  data: {
    userId: wx.getStorageSync("user").id,
    isSend: false,
    dataList: [],
    vo:{
      userId: wx.getStorageSync("user").id
    },
    note:{
      content:''
    },
    total: 0,
    page:{
      page: 1,
      size: 4
    }
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {

    this.getList();
  },
  //获取输入内容
  getInput3(event) {
    console.log("输入的内容", event.detail.value)
    this.setData({
      ['note.content']: event.detail.value
    })
  },
  //打开弹窗
  send: function () {
    var that = this
    wx.getStorage({
      key: 'login',
      success: function (res) {
        if (res.data) {
          that.setData({
            isSend: true
          })
        } else {
          wx.showToast({
            icon: "none",
            title: '你还未登录'
          })
        }
      },
      fail: function (res) {
        wx.showToast({
          icon: "none",
          title: '你还未登录'
        })
      }
    })
  },
  // 关闭弹窗
  close: function () {
    this.setData({
      isSend: false
    })
  },
  //上传数据
  publish: function () {
    var that = this;
    let content = that.data.note.content
    if (!content || content.length < 6) {
      wx.showToast({
        icon: "none",
        title: '内容要多于六个字'
      })
      return
    }
    wx.showLoading({
      title: '发布中...',
    })
    that.save();
    that.close()
    wx.hideLoading();
  },
  delete: function (e) {
    let that = this;
    that.del(e.currentTarget.dataset.id);
    that.getList();
  },
  del(id){
    wx.showLoading({title:"正在删除"})
    let that = this;
    var ids =  [id],
    a ={a:12312}
    api.post("/entertainment/note/delByIds", {ids: ids}, 0, 0 ).then(res=>{
      if(res.code == 1){
        wx.showToast({
          title: "发送成功"
        })
        that.getList();
      }
      wx.hideLoading()
    }).catch(res=>{
      wx.showToast({
        title: res.msg
      })
      console.log(res);
      wx.hideLoading()
    })
  },
  save(){
    let that = this;
    var note = {
      content: that.data.note.content,
      userId: wx.getStorageSync("user").id
    }
    api.post("/entertainment/note/save", note, 1).then(res=>{
      if(res.code == 1){
        wx.showToast({
          title: "发送成功"
        })
        that.getList();
      }
    }).catch(res=>{
      wx.showToast({
        title: res.msg
      })
      console.log(res);
    })
  },
  getList(type = 0){
    wx.showLoading({title:"正在加载...."})
    let that = this;
    api.post("/entertainment/note/getListByVo",
     {"page": that.data.page.page,
     "size": that.data.page.size,
       "aimVo": JSON.stringify(that.data.vo)}
    , 0 , 0).then(res=>{
      if(res.code == 1){
        var list = that.data.dataList;
        var page = that.data.page;
        if(type == 0){
          list = [];
          page.page = 1
        }
        page.page = (page.page * page.size < res.data.total)? page.page + 1: page.page
        res.data.list.forEach(item=>{
          var tmp = that.formatDate(item.gmtCreate);
          item.gmtCreate = tmp;
          list.push(item);
        })
        that.setData({
          dataList: list,
          total: res.data.total,
          page: page
        })
      }
      wx.hideLoading();
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
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {

  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {
    // this.getList()
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
    if(this.data.total > this.data.dataList.length ){
      this.getList(1)
    }
    
  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {

  }
})