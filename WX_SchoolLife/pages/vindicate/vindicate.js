// const db = wx.cloud.database()
var util = require('../util/util.js');
const api = require('../../utils/api.js');
Page({

  /**
   * 页面的初始数据
   */
  data: {
    isSend: false,
    page: {
      page: 1,
      size: 10
    },
    queryVo: {
      userId: wx.getStorageSync("user").id
    },
    total: 0
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: async function (options) {
    var that = this
    var userId = options.userId;
    if(userId != undefined){
      await that.getListByVo();
    }else{
      await that.getList();
    }
    wx.getStorage({
      key: 'user',
      success: function (res) {
        that.setData({
          userId: res.data.id
        })
      },
    })
  },
  //获取输入内容
  getInput1(event) {
    console.log("输入的对象", event.detail.value)
    this.setData({
      receiver: event.detail.value
    })
  },
  getInput2(event) {
    console.log("输入的称呼", event.detail.value)
    this.setData({
      writer: event.detail.value
    })
  },
  getInput3(event) {
    console.log("输入的内容", event.detail.value)
    this.setData({
      content: event.detail.value
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
    let content = that.data.content
    if (!content || content.length < 6) {
      wx.showToast({
        icon: "none",
        title: '内容要多于六个字'
      })
      return
    }
    if (!that.data.writer ) {
      wx.showToast({
        icon: "none",
        title: '发送者不得为空'
      })
      return
    }
    if (!that.data.receiver) {
      wx.showToast({
        icon: "none",
        title: '目标不得为空'
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
    api.post("/entertainment/vindicate/delByIds", {ids: ids}, 0, 0 ).then(res=>{
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
    var vo = {
      writer: that.data.writer,
      receiver: that.data.receiver,
      content: that.data.content,
      userId: wx.getStorageSync("user").id
    }
    api.post("/entertainment/vindicate/save", vo, 1).then(res=>{
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
    let that = this;
    var list = that.data.dataList;
    var page = that.data.page;
    if(type == 0){
      list = [], page.page = 1;
    }
    api.get("/entertainment/vindicate/getList", that.data.page).then(res=>{
      if(res.code == 1){
        page.page = (page.page * page.size < that.data.total)? page.page + 1: page.page
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
    }).catch(res=>{
      console.log(res);
    })
  },
  getListByVo(type = 0){
    let that = this;
    api.post("/entertainment/vindicate/getListByVo",
     {"page": that.data.page.page,
     "size": that.data.page.size,
       "aimVo": JSON.stringify(that.data.queryVo)}
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
    }).catch(res=>{
      console.log(res);
    })
  },
  formatDate(time){
    var date = undefined,res ;
    if(time == undefined || time == null){
      date = new Date()
      res =  date.toJSON().replace('T', ' ').split('.')[0];
    }else{
      date = new Date(time.replace(/-/g,'/'));
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