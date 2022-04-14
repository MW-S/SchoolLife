/** When your routing table is too long, you can split it into small modules**/

import Layout from '@/layout'

const userRouter = {
  path: '/user',
  component: Layout,
  redirect: 'noRedirect',
  name: 'User',
  meta: {
    title: '用户管理',
    icon: 'chart'
  },
  children: [
    {
      path: 'index',
      component: () => import('@/views/core/user/index'),
      name: 'UserList',
      meta: { title: '用户列表', noCache: true }
    }
  ]
}

export default userRouter
