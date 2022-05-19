/** When your routing table is too long, you can split it into small modules**/

import Layout from '@/layout'

const projectRouter = {
  path: '/parking',
  component: Layout,
  redirect: 'noRedirect',
  name: 'Parking',
  meta: {
    title: '停车场管理',
    icon: 'traffic'
  },
  children: [
    {
      path: 'index',
      component: () => import('@/views/core/parking/index'),
      name: 'ParkingList',
      meta: { title: '车位列表', noCache: true }
    },
    {
      path: 'car',
      component: () => import('@/views/core/parking/car'),
      name: 'CarList',
      meta: { title: '车辆列表', noCache: true }
    },
    {
      path: 'order',
      component: () => import('@/views/core/parking/order'),
      name: 'ParkingOrderList',
      meta: { title: '停车订单列表', noCache: true }
    }
  ]
}

export default projectRouter
