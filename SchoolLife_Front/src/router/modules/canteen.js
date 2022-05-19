/** When your routing table is too long, you can split it into small modules**/

import Layout from '@/layout'

const projectRouter = {
  path: '/canteen',
  component: Layout,
  redirect: 'noRedirect',
  name: 'Canteen',
  meta: {
    title: '饭堂管理',
    icon: 'canteen'
  },
  children: [
    {
      path: 'index',
      component: () => import('@/views/core/canteen/index'),
      name: 'CanteenList',
      meta: { title: '饭堂列表', noCache: true }
    },
    {
      path: 'food',
      component: () => import('@/views/core/canteen/food'),
      name: 'FoodList',
      meta: { title: '菜谱列表', noCache: true }
    }
  ]
}

export default projectRouter
