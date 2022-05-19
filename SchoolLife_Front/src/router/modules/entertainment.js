/** When your routing table is too long, you can split it into small modules**/

import Layout from '@/layout'

const projectRouter = {
  path: '/entertainment',
  component: Layout,
  redirect: 'noRedirect',
  name: 'Entertainment',
  meta: {
    title: '娱乐管理',
    icon: 'entertainment'
  },
  children: [
    {
      path: 'note',
      component: () => import('@/views/core/entertainment/note'),
      name: 'NoteList',
      meta: { title: '动态列表', noCache: true }
    },
    {
      path: 'vindicate',
      component: () => import('@/views/core/entertainment/vindicate'),
      name: 'VindicateList',
      meta: { title: '告白列表', noCache: true }
    }
  ]
}

export default projectRouter
