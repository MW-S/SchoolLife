/** When your routing table is too long, you can split it into small modules**/

import Layout from '@/layout'

const projectRouter = {
  path: '/place',
  component: Layout,
  redirect: 'noRedirect',
  name: 'Place',
  meta: {
    title: '场景管理',
    icon: 'place'
  },
  children: [
    {
      path: 'classRoom',
      component: () => import('@/views/core/classRoom/index'),
      name: 'SeatList',
      meta: { title: '教室列表', noCache: true }
    },
    {
      path: 'seat',
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
