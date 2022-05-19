const api = require('../../utils/api.js');
const app = getApp();
Page({
  data: {
    fileServerUrl:  app.globalData.fileServerUrl,
    title: '喜爱菜品',
    typeText: ["早餐", "午餐", "晚餐", "宵夜"],
    favorlist: [
      {
        name: "番茄炒鸡蛋",
        canteen: "一饭",
        type: 0,
        price: "3.6",
        pictures: "/img/add.png",
      },
      {
        name: "番茄炒鸡蛋",
        canteen: "一饭",
        type: 1,
        price: "3.6",
        pictures: "/img/add.png",
      },
      {
        name: "番茄炒鸡蛋",
        canteen: "一饭",
        type: 2,
        price: "3.6",
        pictures: "/img/add.png",
      },
      {
        name: "番茄炒鸡蛋",
        canteen: "一饭",
        type: 3,
        price: "3.6",
        pictures: "/img/add.png",
      }
    ],
    total: 0,
    page:{
      page: 1,
      size: 4
    }
  },
  getList(type = 0){
    let that = this;
    api.get("/diet/food/getFoodList", that.data.page).then(res=>{
      if(res.code == 1){
        var list = that.data.favorlist;
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
          favorlist: list,
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
  onLoad(ops) {
    var that = this
    that.getList();
  },
  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function() {
    if(this.data.total > this.data.favorlist.length ){
      this.getList(1)
    }
  },  
})