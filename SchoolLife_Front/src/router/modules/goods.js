/** When your routing table is too long, you can split it into small modules**/

import Layout from '@/layout'

const projectRouter = {
  path: '/goods',
  component: Layout,
  redirect: 'noRedirect',
  name: 'Goods',
  meta: {
    title: '闲置物品管理',
    icon: 'goods'
  },
  children: [
    {
      path: 'index',
      component: () => import('@/views/core/goods/index'),
      name: 'Goods',
      meta: { title: '闲置物品列表', noCache: true }
    }
  ]
}

export default projectRouter
