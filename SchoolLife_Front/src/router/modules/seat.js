/** When your routing table is too long, you can split it into small modules**/

import Layout from '@/layout'

const projectRouter = {
  path: '/seat',
  component: Layout,
  redirect: 'noRedirect',
  name: 'Seat',
  meta: {
    title: '图书馆座位管理',
    icon: 'chart'
  },
  children: [
    {
      path: 'index',
      component: () => import('@/views/core/seat/index'),
      name: 'SeatList',
      meta: { title: '座位列表', noCache: true }
    },
    {
      path: 'order',
      component: () => import('@/views/core/seat/order'),
      name: 'SeatOrderList',
      meta: { title: '预约订单列表', noCache: true }
    }
  ]
}

export default projectRouter
