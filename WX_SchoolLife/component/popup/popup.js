// components/popup/popup.js

Component({
    externalClasses: ['my-class'], //外部样式类
    options: {
        multipleSlots: true, // 在组件定义时的选项中启用多slot支持
        styleIsolation: 'isolated', //指定特殊的样式隔离选项
    },

    properties: {
        // 内容
        content: {
            type: String, // 类型（必填），目前接受的类型包括：String, Number, Boolean, Object, Array, null（表示任意类型）
            value: '内容描述'
        },

        // 描述
        desc: {
            type: String,
            value: ''
        },

        // 取消
        cancelBtn: {
            type: String,
            value: '再想想'
        },

        // 确定
        confirmBtn: {
            type: String,
            value: '确定'
        },

        // 显示操作按钮 【false：显示一个按钮】
        isShowBtn: {
            type: Boolean, //注意: Boolean类型需要加{{}}， 否则解析成字符串
            value: false
        }
    },

    /**
     * 私有数据，可用于模板渲染
     */
    data: {
        isShowPopup: true
    },

    lifetimes: {
        //  页面创建时执行
        attached: function () {
            console.log('页面创建时执行', this.data.isShowBtn);
        }
    },
    methods: {
        /**
         * 隐藏弹框
         */
        hidePopup() {
            this.setData({
                isShowPopup: !this.data.isShowPopup
            })
        },

        /**
         * 展示弹框
         */
        showPopup() {
            this.setData({
                isShowPopup: !this.data.isShowPopup
            })
        },

        /** 
         * 内部私有方法建议以下划线开头 
         * triggerEvent 用于触发事件 
         */
        _errorCallback() { //触发取消回调 
            this.triggerEvent("error")
        },

        /**
         * 触发成功回调
         */
        _successCallback() {
            this.triggerEvent("success");
        }
    }
})