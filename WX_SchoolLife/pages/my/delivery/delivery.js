// pages/shophelper/shophelper.js
const app = getApp();
const webUtil = require("../../../utils/web.js");
const api = require('../../../utils/api.js');
Page({

  /**
   * 页面的初始数据
   */
  data: {
    total: 0,
    page:{
      page: 1,
      size: 4
    },
    array: ["官渡校区", "西城校区", "光华校区"],
    vo: {
      userId: wx.getStorageSync("user").id
    },
    navSelectIndex:0,
    shophelper0: [],
    shophelper1: [],
    list:[{
      avatar: "/img/goods.png",
      user: "xxx",
      name: "可比克薯片",
      address: "阿拉斯加",
      price: 100,
      gmtCreate: new Date().toJSON().replace('T', ' ').split('.')[0],
      tag: "",
      state: 0
    },
    {
      avatar: "/img/goods.png",
      user: "xxx",
      name: "可比克薯片",
      address: "阿拉斯加",
      price: 100,
      gmtCreate: new Date().toJSON().replace('T', ' ').split('.')[0],
      tag: "",
      state: 1
    }]
  },
  formatTime(e){
    if(e == undefined)
      return [];
    e.forEach((val,index)=>{
      //友好时间
      val.friendly_time = webUtil.friendly_time(e[index].time); 
    })
    return e;
  },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    this.getList();
  },
  selectNav: async function (e) {
    var that = this;
    let index = e.currentTarget.dataset.index;
    var vo = that.data.vo;
    if(index == 0 || index == '0'){
      vo.serverId = undefined,
      vo.userId = wx.getStorageSync("user").id;
    }else{
      vo.userId = undefined,
      vo.serverId = wx.getStorageSync("user").id;
    }
   
    await this.setData({
      vo: vo,
      navSelectIndex:index
    })
    await that.getList();
  },
  toggleFormBox(e) {
    console.log(e);
    let val = e.currentTarget.dataset.val;
    this.setData({
      showformbox: val == 1 ? true : false
    })
  },
  //绑定信息
  bindVal(e){
    var val = e.detail.value;
    var field = e.currentTarget.dataset.field
    this.setData({
      [field]: val
    })
  },
  select(e){
    let id = e.currentTarget.dataset.id;
    this.save({id: id, serverId: wx.getStorageSync("user").id, state: 1})
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
    api.post("/deliveryOrder/delByIds", {ids: ids}, 0, 0 ).then(res=>{
      if(res.code == 1){
        wx.showToast({
          title: "删除成功"
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
  push(e){
    this.save(undefined);
  },
  save(vo){
    wx.showLoading({title:"正在发布"})
    let that = this;
    if(vo == undefined){
      vo = that.data.vo
      vo.userId = wx.getStorageSync("user").id
    }
    api.post("/deliveryOrder/save", vo, 1).then(res=>{
      wx.hideLoading();
      if(res.code == 1){
        wx.showToast({
          title: "发布成功"
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
    api.post("/deliveryOrder/getListByVo", {"page": that.data.page.page,
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
          list: list,
          total: res.data.total,
          page: page
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
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {

  },
  callphone:function(e){
    wx.makePhoneCall({
      phoneNumber: e.currentTarget.dataset.num,
    })
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
    if(this.data.total > this.data.list.length ){
      this.getList(1)
    }
  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {

  }
})