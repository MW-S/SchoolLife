/**

 * 如有技术问题或商业合作，可以添加本人微信:Exixir99

 * @author 全黑科技

 * @Time 2021-06-16 21:49:01

 * @URL http://www.abtwork.com/

 */
var app = getApp()
const api = require('../../utils/api.js')
Page({

  /**
   * 页面的初始数据
   */
  data: {
    color: "",
    xh: "",
    isLogin: false,
    starCount: 0,
    forksCount: 0,
    visitTotal: 0,
    noteCount: wx.getStorageSync("noteCount"),
    vindicateCount : wx.getStorageSync("vindicateCount"),
    is_bind: false,
    pathMap: app.globalData.pathMap
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {

    console.log("success")
    let that = this;
    wx.showLoading({
      title: '数据加载中',
      mask: true,
    })
   
    that.setData({
      starCount: that.coutNum(that.data.starCount),
      forksCount: that.coutNum(that.data.forksCount),
      visitTotal: that.coutNum(that.data.visitTotal)
    })
    wx.hideLoading()
    this.getCars();
    this.getDormitories();
  },
  toggleFormBox(e) {
    console.log(e);
    let val = e.currentTarget.dataset.val;
    this.setData({
      showformbox: val == 1 ? true : false
    })
  },
  showDialog(e){
    var type = e.currentTarget.dataset.type;
    this.setData({
      showformbox: true,
      type: type
    })
  },
  toLogin: function () {
    var type = "jwc"
    wx.navigateTo({
      url: '/pages/login/login?type=' + type,
    })
  },
  getInfo(){
    wx.showLoading({
      title: '正在加载...'
    });
    api.get("/auth/info").then(res=>{
      if(res.code == 1){
        wx.setStorageSync('user', res.data.data)
        wx.setStorageSync("noteCount", res.data.noteCount)
        wx.setStorageSync("vindicateCount", res.data.vindicateCount)
      }
      wx.hideLoading();
    }).catch(res=>{
      wx.hideLoading();
      console.log(res);
    })
  },
  unLogin() {
    wx.showLoading({
      title: '解绑中...',
    })
    this.setData({
      is_bind: false
    })
    wx.setStorageSync('is_bind', false)
    wx.hideLoading()
    //云函数中将用户记录的状态表示设置为解绑状态，同时本地更新绑定缓存

    // wx.cloud.callFunction({
    //   name: 'user',
    //   data: {
    //     action: 'un_bind'
    //   }
    // }).then(res => {
    //   console.log(res);
    //   wx.hideLoading()
    //   //app.globalData.is_bind = false;

    // }).catch(err => {
    //   wx.hideLoading()
    // })
  },
  get_my_num() {
    console.log("+===>")
    if (!this.data.is_bind) {
      this.setData({
        starCount: 0,
        visitTotal: 0,
        forksCount: 0
      })
    } else {
      this.setData({
        starCount: app.globalData.user_data.star_num,
        visitTotal: app.globalData.user_data.view_num,
        forksCount: app.globalData.user_data.comment_num
      })
    }
  },
  getCars(){
    wx.showLoading({title:"正在加载"})
    let that = this;
    api.get("/car/getList").then(res=>{
      if(res.code == 1){
        var map = new Map();
        res.data.data.forEach(item=>{
          var tmp = that.formatDate(item.gmtCreate);
          item.gmtCreate = tmp;
          map.add(item.id, item)
          // list.push(item);
        })
        that.setData({
          cars: list,
        })
        wx.hideLoading();
      }
    }).catch(res=>{
      wx.hideLoading();
      console.log(res);
    })
  },
  getDormitories(){
    wx.showLoading({title:"正在加载"})
    let that = this;
    api.get("/dormitory/getList").then(res=>{
      if(res.code == 1){
        var map = new Map();
        res.data.list.forEach(item=>{
          var tmp = that.formatDate(item.gmtCreate);
          item.gmtCreate = tmp;
          map.add(item.id, item)
          // list.push(item);
        })
        that.setData({
          dormitories: map,
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
  bindInfo(e){
    wx.showLoading({
      title:"正在绑定...."
    })
    var vo = {}
    if(this.data.type==0){
      vo.userId = wx.getStorageSync("user").id
      vo.dormitoryId = this.data.dormitoryId;
      api.post("/dormitoryUser/save", vo, 1).then(res=>{
        if(res.code == 1){
          wx.showToast({
            title: "绑定成功"
          })
        }
      }).catch(res=>{
        wx.showToast({
          title: res.msg
        })
        console.log(res);
      })
    }else{
      vo = {
        id : wx.getStorageSync("user").id,
        carId: this.data.carId
      }
      api.post("/user/updateCarId", vo, 1).then(res=>{
        if(res.code == 1){
          wx.showToast({
            title: "绑定成功"
          })
          this.getInfo();
        }
      }).catch(res=>{
        wx.showToast({
          title: res.msg
        })
        console.log(res);
      })
    }
    wx.hideLoading()
   
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
   this.getInfo()

  },
  go_nav(e){
    var type = e.currentTarget.dataset.type;
    var path = this.data.pathMap[type];
    wx.navigateTo({
      url: '/pages/' + path + "?userId=" +wx.getStorageSync("user").id,
    })
  },
  logout() {
   wx.clearStorageSync();
   wx.redirectTo({
    url: '/pages/login/login'
  })
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
  coutNum(e) {
    if (e > 1000 && e < 10000) {
      e = (e / 1000).toFixed(1) + 'k'
    }
    if (e > 10000) {
      e = (e / 10000).toFixed(1) + 'W'
    }
    return e
  },
  CopyLink(e) {
    wx.setClipboardData({
      data: e.currentTarget.dataset.link,
      success: res => {
        wx.showToast({
          title: '已复制',
          duration: 1000,
        })
      }
    })
  },
  showModal(e) {
    this.setData({
      modalName: e.currentTarget.dataset.target
    })
  },
  hideModal(e) {
    this.setData({
      modalName: null
    })
  },
  showQrcode() {
    wx.previewImage({
      urls: ['https://636c-cloud1-2gz3zqqx6833b79c-1306077792.tcb.qcloud.la/system/3131622194620_.pic_hd.jpg?sign=4617deea5ae878581e4202237be3f615&t=1623829033'],
      current: 'https://636c-cloud1-2gz3zqqx6833b79c-1306077792.tcb.qcloud.la/system/3131622194620_.pic_hd.jpg?sign=4617deea5ae878581e4202237be3f615&t=1623829033' // 当前显示图片的http链接      
    })
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

  },

})