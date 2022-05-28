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
    fileServerUrl: app.globalData.fileServerUrl,
    fileList: [],
    color: "",
    xh: "",
    isLogin: false,
    starCount: 0,
    forksCount: 0,
    visitTotal: 0,
    noteCount: wx.getStorageSync("noteCount"),
    vindicateCount : wx.getStorageSync("vindicateCount"),
    dormitoryId: wx.getStorageSync("dormitoryId"),
    carId: wx.getStorageSync("user").carId,
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
    wx.hideLoading()
    // this.getCars();
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
  closeDialog(){
    this.setData({
      showformbox: false
    })
  },
  toLogin: function () {
    var type = "jwc"
    wx.navigateTo({
      url: '/pages/login/login?type=' + type,
    })
  },
  getInfo(){
    let that = this;
    wx.showLoading({
      title: '正在加载...'
    });
    api.get("/user/auth/info").then(res=>{
      if(res.code == 1){
        wx.setStorageSync('user', res.data.data)
        wx.setStorageSync("noteCount", res.data.noteCount)
        wx.setStorageSync("vindicateCount", res.data.vindicateCount)
        wx.setStorageSync("dormitoryId", res.data.dormitoryId)
        this.setData({
          user: res.data.data,
          carId: res.data.data.carId,
          carPicture: res.data.data.carPicture,
          fileList: [{name: res.data.data.carPicture, url: that.data.fileServerUrl + res.data.data.carPicture }],
          dormitoryId: res.data.dormitoryId
        })
        this.setIndexById(res.data.dormitoryId);
        this.setIndexById(res.data.data.carId,1);
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
    api.get("/traffic/car/getList").then(res=>{
      if(res.code == 1){
        var list = [];
        res.data.data.forEach((item, index)=>{
          var tmp = that.formatDate(item.gmtCreate);
          item.gmtCreate = tmp;
          // map.set(item.id, item)
          if(item.id ==  wx.getStorageSync('user').carId){
            that.setData({
              carIndex: index
            })
          }
          list.push(item);
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
    api.get("/user/dormitory/getList").then(res=>{
      if(res.code == 1){
        // var map = new Map();
        var list = []
        res.data.list.forEach((item, index)=>{
          if(item.id == that.data.dormitoryId){
            that.setData({
              dormitoryIndex: index
            })
          }
          var tmp = that.formatDate(item.gmtCreate);
          item.gmtCreate = tmp;
          item.locationName = item.location + item.name
          // map.set(item.id, item)
          list.push(item);
        })
        that.setData({
          dormitories: list,
        })
        wx.hideLoading();
      }
    }).catch(res=>{
      wx.hideLoading();
      console.log(res);
    })
  },
  deleteImg(event){
    let index= event.detail.index
    var fileList = this.data.fileList;
    fileList.splice(index, 1);
    this.setData({
      fileList
    });
    console.log(index)//输出的就是图片所在fileList的下标，自己根据需要进行操作就行
  },
  afterRead(event) {
    let that = this;
    const { file } = event.detail;
    // 当设置 mutiple 为 true 时, file 为数组格式，否则为对象格式
    wx.uploadFile({
      url: app.globalData.serverUrl + "/traffic/car/recognize", // 仅为示例，非真实的接口地址
      // url: app.globalData.serverUrl + "/user/uploadOss",
      header: {"Authorization": "Bearer " +wx.getStorageSync('token')},
      filePath: file.url,
      name: 'image',
      // name: "file",
      formData: { user: 'test' },
      success(res) {
        // 上传完成需要更新 fileList
        var data = JSON.parse(res.data)
        const  fileList = [] ;
        fileList.push({ ...file, url: that.data.fileServerUrl + data.data.path });
        that.setData({ 
          fileList: fileList,
          carId: data.data.code,
          carPicture: data.data.path
         });
      },
    });
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
  bindInfo(e){
    wx.showLoading({
      title:"正在绑定...."
    })
    var vo = {}
    if(this.data.type==0){
      vo.userId = wx.getStorageSync("user").id
      vo.dormitoryId = this.data.dormitories[this.data.dormitoryIndex].id;
      api.post("/user/dormitoryUser/save", vo, 1).then(res=>{
        if(res.code == 1){
          this.setData({
            dormitoryId: vo.dormitoryId
          })
          this.closeDialog()
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
        carId: this.data.carId,
        carPicture: this.data.carPicture
      }
      api.post("/user/auth/updateCarId", vo, 1).then(res=>{
        if(res.code == 1){
          this.closeDialog()
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
    //绑定信息
    bindVal(e){
      var val = e.detail.value;
      var field = e.currentTarget.dataset.field
      this.setData({
        [field]: val
      })
    },
    setIndexById(id, type=0){
      let that = this;
      //0表示宿舍  1表示车辆
      var typeText = ["dormitories", "cars"]
      var idText = ["dormitoryId", "carId"]
      var indexText = ["dormitoryIndex", "carIndex"]
      var list = that.data[typeText[type]];
      if(list == undefined){
        return;
      }
      list.forEach((item, index)=>{
        if(item.id == that.data[idText[type]]){
          that.setData({
            [indexText[type]]: index
          })
        }else if(type == 1 && item.id == wx.getStorageSync('user').carId){
          that.setData({
            [indexText[type]]: index
          })
        }
      })
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