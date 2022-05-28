const api = require('../../../utils/api.js');
const app = getApp()
Page({
  /**
   * 页面的初始数据
   */
  data: {
    fileServerUrl: app.globalData.fileServerUrl,
    item:{

    }
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function(options) {
    this.getById(options.id)
  },
  getById(id){
    let that = this;
    api.get("/goods/getGoodsById",{"id":id}).then(res=>{
      if(res.code == 1){
        res.data.data.pictures = JSON.parse(res.data.data.pictures);
        that.setData({
          item: res.data.data
        })
      }
    }).catch(res=>{
      console.log(res);
    })
  },
  // 预览图片
  previewImg: function (e) {
    let imgData = e.currentTarget.dataset.img;
    let images = [];
    this.data.item.pictures.forEach(item=>{
      images.push(this.data.fileServerUrl + item);
    })
    wx.previewImage({
      //当前显示图片
      current: this.data.fileServerUrl + imgData,
      //所有图片
      urls: images
    })
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
    wx.removeStorage({
      key: 'info',
      success: function(res) {},
    })
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

  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function() {

  }
})