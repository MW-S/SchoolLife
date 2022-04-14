/** When your routing table is too long, you can split it into small modules**/

// import Layout from '@/layout'

const registerRouter = {
  path: '/register',
  // component: Layout,
  hidden: true,
  component: () => import('@/views/register/index'),
  name: 'RegisterPage',
  meta: { title: '注册', noCache: true }
}

export default registerRouter
