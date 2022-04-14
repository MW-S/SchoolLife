const app = getApp()
var util = require('../../util/util.js');
const api = require('../../../utils/api.js');

Page({
  /**
   * 页面的初始数据
   */
  data: {
    imgbox: [], //选择图片
    pictures: [], //上传云存储后的返回值
  },
  onLoad: function(options) {
    this.setData({
      type: options.name
    })
    console.log(this.data.type)
    if (this.data.type == 'lostlost' || this.data.type == 'lostfound') {
      wx.setNavigationBarTitle({
        title: '失物招领'
      })
    } else {
      wx.setNavigationBarTitle({
        title: '闲置发布'
      })
    }
    var that = this
    wx.getStorage({
      key: 'userInfo',
      success: function(res) {
        that.setData({
          user: res.data
        })
      },
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
  // 删除照片 &&
  imgDelete1: function(e) {
    let that = this;
    let index = e.currentTarget.dataset.deindex;
    let imgbox = this.data.imgbox;
    imgbox.splice(index, 1)
    that.setData({
      imgbox: imgbox
    });
  },
  // 选择图片 &&&
  addPic1: function(e) {
    var imgbox = this.data.imgbox;
    var that = this;
    var n = 5;
    if (5 > imgbox.length > 0) {
      n = 5 - imgbox.length;
    } else if (imgbox.length == 5) {
      n = 1;
    }
    wx.chooseImage({
      count: n, // 默认9，设置图片张数
      sizeType: ['original', 'compressed'], // 可以指定是原图还是压缩图，默认二者都有
      sourceType: ['album', 'camera'], // 可以指定来源是相册还是相机，默认二者都有
      success: function(res) {
        // console.log(res.tempFilePaths)
        // 返回选定照片的本地文件路径列表，tempFilePath可以作为img标签的src属性显示图片
        var tempFilePaths = res.tempFilePaths
        if (imgbox.length == 0) {
          imgbox = tempFilePaths
        } else if (5 > imgbox.length) {
          imgbox = imgbox.concat(tempFilePaths);
        }
        that.setData({
          imgbox: imgbox
        });
      }
    })
  },
  save(){
    wx.showLoading({title: "正在发布"})
    let that = this;
    var vo =  that.data.vo;
    vo.userId = wx.getStorageSync("user").id
    vo.pictures = JSON.stringify(that.data.imgbox)
    api.post("/goods/save", vo, 1).then(res=>{
      wx.hideLoading();
      if(res.code == 1){
        wx.showToast({
          title: "发布成功"
        })
        wx.navigateBack({
          delta: 1 //返回的页面数，如果 delta 大于现有页面数，则返回到首页,
        });
      }
    }).catch(res=>{
      wx.hideLoading();
      wx.showToast({
        title: res.msg
      })
      console.log(res);
    })
  },
  //发布按钮
  fb: function(e) {
    if (this.data.type == 'lostlost') {
      var room = 'lost'
    } else if (this.data.type == 'lostfound') {
      var room = 'found'
    } else {
      var room = 'xianzhi'
    }
    console.log(room)
    if (!this.data.imgbox.length) {
      wx.showToast({
        icon: 'none',
        title: '图片类容为空'
      });
    } else {
      //上传图片到云存储
      wx.showLoading({
        title: '上传中',
      })
      let promiseArr = [];
      for (let i = 0; i < this.data.imgbox.length; i++) {
        promiseArr.push(new Promise((reslove, reject) => {
          let item = this.data.imgbox[i];
          let suffix = /\.\w+$/.exec(item)[0]; //正则表达式返回文件的扩展名
       
          var that = this
      
        }));
      }
      Promise.all(promiseArr).then(res => { //等数组都做完后做then方法
       
      })

    }
  },

})