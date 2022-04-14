Page({
  data: {
    stateText:["待扫码", "已预约", "已结束"],
    list: [
      {
        name: "612-011",
        state: 0,
        useTime: "1小时",
        gmtCreate: new Date().toJSON().replace('T', ' ').split('.')[0]
      },
      {
        name: "612-013",
        state: 1,
        useTime: "2小时",
        gmtCreate: new Date().toJSON().replace('T', ' ').split('.')[0]
      },
      {
        name: "612-014",
        state: 2,
        useTime: "3小时",
        gmtCreate: new Date().toJSON().replace('T', ' ').split('.')[0]
      },
      {
        name: "712-011",
        state: 1,
        useTime: "2小时",
        gmtCreate: new Date().toJSON().replace('T', ' ').split('.')[0]
      }
    ],
  },
  onLoad() {
    var that = this
    
  }
})