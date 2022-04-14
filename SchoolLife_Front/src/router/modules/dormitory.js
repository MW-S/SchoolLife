/** When your routing table is too long, you can split it into small modules**/

import Layout from '@/layout'

const projectRouter = {
  path: '/dormitory',
  component: Layout,
  redirect: 'noRedirect',
  name: 'Dormitory',
  meta: {
    title: '宿舍管理',
    icon: 'chart'
  },
  children: [
    {
      path: 'index',
      component: () => import('@/views/core/dormitory/index'),
      name: 'DormitoryList',
      meta: { title: '宿舍列表', noCache: true }
    }
  ]
}

export default projectRouter
