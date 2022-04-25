const api = require('../../../../utils/api.js')
Page({
  data: {
    // stateText:["待扫码", "已预约", "已结束"],
    stateText:["使用中", "已结束"],
    schoolText: ["官渡校区", "西城校区", "光华校区"],
    list: [
      {
        name: "612-011",
        orderState: 0,
        useTime: "1小时",
        gmtCreate: new Date().toJSON().replace('T', ' ').split('.')[0]
      },
      {
        name: "612-013",
        orderState: 1,
        useTime: "2小时",
        gmtCreate: new Date().toJSON().replace('T', ' ').split('.')[0]
      },
      {
        name: "612-014",
        orderState: 0,
        useTime: "3小时",
        gmtCreate: new Date().toJSON().replace('T', ' ').split('.')[0]
      },
      {
        name: "712-011",
        orderState: 1,
        useTime: "2小时",
        gmtCreate: new Date().toJSON().replace('T', ' ').split('.')[0]
      }
    ],
    total: 0,
    page:{
      page: 1,
      size: 4
    }
  },
  getList(type = 0){
    wx.showLoading({title:"正在加载...."})
    let that = this;
    api.post("/place/seat/getOrderListByVo",
     {"page": that.data.page.page,
     "size": that.data.page.size,
       "aimVo": JSON.stringify(that.data.vo)}
    , 0 , 0).then(res=>{
      if(res.code == 1){
        var list = that.data.list;
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
  async cancel(e){
    var item = e.currentTarget.dataset.item;
    wx.showLoading({title:"正在结束预约"})
    let that = this;
    let res =  await api.post("/place/seat/saveOrder", {id:item.id, state: 1}, 1);
    if(res.code == 1){
      await api.post("/place/seat/save", {id:item.seatId, state: 0}, 1);
      this.getList()
      wx.hideLoading()
    }else{
      wx.hideLoading()
      wx.showToast({title: "结束失败！"});
    }
  },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    var vo = {
      userId: wx.getStorageSync("user").id
    }
    this.setData({
      vo: vo
    })
    this.getList()
    // this.getRoom(options)
  },
    /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function() {
    if(this.data.total > this.data.list.length ){
      this.getList(1)
    }
  },  
})