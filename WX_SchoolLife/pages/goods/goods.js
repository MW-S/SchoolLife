
const api = require('../../utils/api.js');
const app = getApp();
Page({

  /**
   * 页面的初始数据
   */
  data: {
    fileServerUrl: app.globalData.fileServerUrl,
    imgbox: [],
    stateText: ["未出售", "已出售"],
    vo:{userId: wx.getStorageSync("user").id},
    dataList: [
    //   {
    //   name: "xxx",
    //   type: "鞋子",
    //   price: "100",
    //   info: "超级好的二手货",
    //   pictures: ['../../img/add.png', '../../img/add.png', '../../img/add.png'],
    //   state: 0,
    //   gmtCreate: new Date().toJSON().replace('T', ' ').split('.')[0]
    // },
    // {
    //   name: "xxx",
    //   type: "衣服",
    //   price: "1001",
    //   info: "超级好的二手货",
    //   pictures: ["../../img/add.png","../../img/add.png","../../img/add.png"],
    //   state: 1,
    //   gmtCreate: new Date().toJSON().replace('T', ' ').split('.')[0]
    // }
  ],
    total: 0,
    page:{
      page: 1,
      size: 4
    },
    userId: undefined
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: async function(options) {
    var that = this
    console.log(options)
    var userId = options.userId;
    if(userId != undefined){
      that.setData({
        userId: userId
      })
      await that.getListByVo();
    }else{
      await that.getList();
    }
  },
  delete: function(e) {
    let that = this;
    that.del(e.currentTarget.dataset.id);
    that.getList();
  },
  updateState: function(e){
    let that = this;
    var vo = {
      id: e.currentTarget.dataset.id,
      state: true
    }
    api.post("/goods/save", vo, 1).then(res=>{
      wx.hideLoading();
      if(res.code == 1){
        wx.showToast({
          title: "修改成功"
        })
        that.getListByVo();
      }
    }).catch(res=>{
      wx.hideLoading();
      wx.showToast({
        title: res.msg
      })
      console.log(res);
    })
  },
  send: function() {
    wx.getStorage({
      key: 'login',
      success: function (res) {
        if (res.data) {
          wx.navigateTo({
            url: './send/send?name=xianzhi',
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
  go: function(event) {
    var id = event.currentTarget.dataset.id
    // wx.setStorage({
    //   key: 'goodsId',
    //   data: id,
    // })
    wx.navigateTo({
      url: './temp/temp?id=' + id,
    })
  },
  del(id){
    wx.showLoading({title:"正在删除"})
    let that = this;
    var ids =  [id],
    a ={a:12312}
    api.post("/goods/delByIds", {ids: ids}, 0, 0 ).then(res=>{
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
  getList(type = 0){
    let that = this;
    var list = that.data.dataList;
    var page = that.data.page;
    if(type == 0){
      list = [], page.page = 1;
    }
    api.get("/goods/getList", that.data.page).then(res=>{
      if(res.code == 1){
        page.page = (page.page * page.size < res.data.total)? page.page + 1: page.page
        res.data.list.forEach(item=>{
          var tmp = that.formatDate(item.gmtCreate);
          item.pictures = JSON.parse(item.pictures);
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
    api.post("/goods/getListByVo",
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
          item.pictures = JSON.parse(item.pictures);
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
  onReady: function() {

  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function() {
    // this.onLoad()
    if(this.data.userId == undefined){
      this.getList()
    }else{
      this.getListByVo()
    }
    
  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function() {

  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function() {

  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function() {

  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function() {
    if(this.data.total > this.data.dataList.length ){
      if(this.data.userId == undefined){
        this.getList(1)
      }else{
        this.getListByVo(1)
      }
    }
  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function() {

  }
})