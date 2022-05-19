/** When your routing table is too long, you can split it into small modules**/

import Layout from '@/layout'

const projectRouter = {
  path: '/deliveryOrder',
  component: Layout,
  redirect: 'noRedirect',
  name: 'DeliveryOrder',
  meta: {
    title: '跑腿管理',
    icon: 'delivery'
  },
  children: [
    {
      path: 'index',
      component: () => import('@/views/core/delivery-order/index'),
      name: 'DeliveryOrderList',
      meta: { title: '跑腿订单列表', noCache: true }
    }
  ]
}

export default projectRouter
