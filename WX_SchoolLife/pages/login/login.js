/**

 * 如有技术问题或商业合作，可以添加本人微信:Exixir99

 * @author 全黑科技

 * @Time 2021-06-16 21:49:01

 * @URL http://www.abtwork.com/

 */
var app = getApp();
var RSA = require('../../utils/wxapp_rsa.js')

const api = require('../../utils/api.js')
Page({

  /**
   * 页面的初始数据
   */
  data: {
    fileServerUrl: app.globalData.fileServerUrl,
    type: "jwc",
    openId: '',
    userid_focus: false,
    passwd_focus: false,
    class_focus: false,
    angle: 0,
    help_status: false,
    tem_file_id: '',
    username: '',
    password: '',
    check_word: '',
    the_cookie: ''
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: async function (options) {
    await this.isLogin();
  },
  tapHelp: function (e) {
    if (e.target.id == 'help') {
      this.hideHelp();
    }
  },

  showHelp: function (e) {
    this.setData({
      'help_status': true
    });
  },
  hideHelp: function (e) {
    this.setData({
      'help_status': false
    });
  },
  UidInput: function (e) {
    if (e.detail.value.length >= 13) {
      wx.hideKeyboard();
    }
  },
  ClassInput: function (e) {

  },

  inputFocus: function (e) {
    if (e.target.id == 'userid') {
      this.setData({
        'userid_focus': true
      });
    } else if (e.target.id == 'passwd') {
      this.setData({
        'passwd_focus': true
      });
    } else if (e.target.id == 'user_class') {
      this.setData({
        'class_focus': true
      });
    } else if (e.target.id == 'resetUid') {
      this.setData({
        'resetUid_focus': true
      });
    } else if (e.target.id == 'idCardNO') {
      this.setData({
        'idCardNO_focus': true
      });
    }
  },
  inputBlur: function (e) {
    if (e.target.id == 'userid') {
      this.setData({
        'userid_focus': false
      });
    } else if (e.target.id == 'passwd') {
      this.setData({
        'passwd_focus': false
      });
    } else if (e.target.id == 'user_class') {
      this.setData({
        'class_focus': false
      });
    } else if (e.target.id == 'resetUid') {
      this.setData({
        'resetUid_focus': false
      });
    } else if (e.target.id == 'idCardNO') {
      this.setData({
        'idCardNO_focus': false
      });
    }
  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {
    var that = this;
    setTimeout(function () {
      that.setData({
        isLoading: false
      });
    }, 1000);
    wx.onAccelerometerChange(function (res) {
      var angle = -(res.x * 30).toFixed(1);
      if (angle > 14) {
        angle = 14;
      } else if (angle < -14) {
        angle = -14;
      }
      if (that.data.angle !== angle) {
        that.setData({
          angle: angle
        });
      }
    });
  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {},

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function () {

  },

  isLogin(){
    let that = this;
    wx.showLoading({
      title: 'Loading...', //提示的内容,
    });
    api.get("/user/auth/isLogin").then(res=>{
      wx.hideLoading();
      if(res.code == 1){
        wx.setStorageSync('login', true);
        wx.switchTab({url:"/pages/index/index"})
      }else{
        wx.setStorageSync('login', false);
      }
    }).catch(res=>{
      wx.setStorageSync('login', false);
      wx.hideLoading();
      console.log(res)
    })
  },
  //获取JsCode
  getJsCode(){
    let that = this;
    wx.login({
      success (res) {
        if (res.code) {
          that.setData({
           code: res.code
         })
        } else {
          wx.showToast({
            title: '登录失败',
          })
          console.log('登录失败！' + res.errMsg)
        }
      }
    })
  },
  // 登录
   login(e) {
      wx.getUserProfile({
        desc: '用于完善会员资料', // 声明获取用户个人信息后的用途，后续会展示在弹窗中，请谨慎填写
        success: (res) => {
          var aseVo = Object.assign({code: this.data.code}, res);
          delete aseVo.errMsg;
          delete aseVo.userInfo;
          api.post("/user/auth/wxLogin", aseVo, 1).then(res=>{
            wx.setStorageSync('token', res.data.token);
            wx.setStorageSync('user',  res.data.user);
            wx.setStorageSync('login', true);
            wx.switchTab({ url: '/pages/index/index' });
          })
          // .fail(res=>{
          //   wx.showToast({
          //     title: '请授权登录',
          //   })
          // })
          .catch(res=>{
            wx.showToast({
              title: '授权失败',
            })
            console.log(res)
          })
        }
      })
  }
})