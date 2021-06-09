import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

/* Layout */
import Layout from '@/layout'

/**
 * Note: sub-menu only appear when route children.length >= 1
 * Detail see: https://panjiachen.github.io/vue-element-admin-site/guide/essentials/router-and-nav.html
 *
 * hidden: true                   if set true, item will not show in the sidebar(default is false)
 * alwaysShow: true               if set true, will always show the root menu
 *                                if not set alwaysShow, when item has more than one children route,
 *                                it will becomes nested mode, otherwise not show the root menu
 * redirect: noRedirect           if set noRedirect will no redirect in the breadcrumb
 * name:'router-name'             the name is used by <keep-alive> (must set!!!)
 * meta : {
    roles: ['admin','editor']    control the page roles (you can set multiple roles)
    title: 'title'               the name show in sidebar and breadcrumb (recommend set)
    icon: 'svg-name'/'el-icon-x' the icon show in the sidebar
    breadcrumb: false            if set false, the item will hidden in breadcrumb(default is true)
    activeMenu: '/example/list'  if set path, the sidebar will highlight the path you set
  }
 */

/**
 * constantRoutes
 * a base page that does not have permission requirements
 * all roles can be accessed
 */
export const constantRoutes = [
  {
    path: '/login',
    component: () => import('@/views/login/index'),
    hidden: true
  },

  {
    path: '/404',
    component: () => import('@/views/404'),
    hidden: true
  },

  {
    path: '/',
    component: Layout,
    redirect: '/dashboard',
    children: [
      {
        path: 'dashboard',
        name: '首页',
        component: () => import('@/views/dashboard/index'),
        meta: { title: '首页', icon: 'dashboard' }
      }
    ]
  },

  {
    path: '/core/blog',
    component: Layout,
    redirect: '/core/blog/blog',
    name: 'coreGenre',
    meta: { title: '博客管理', icon: 'el-icon-postcard' },
    alwaysShow: true,
    children: [
      {
        path: 'blog',
        name: 'coreblogList',
        component: () => import('@/views/core/blog/blog'),
        meta: { title: '博客管理' }
      },
      {
        path: 'blogGenre',
        name: 'coreGenreList',
        component: () => import('@/views/core/blog/blogGenre'),
        meta: { title: '博客分类管理' }
      },
      {
        path: 'blogTag',
        name: 'coreTagList',
        component: () => import('@/views/core/blog/blogTag'),
        meta: { title: '博客标签管理' }
      }
    ]
  },

  {
    path: '/core',
    component: Layout,
    redirect: '/core/system/webConfig',
    name: 'coreSystem',
    meta: { title: '系统设置', icon: 'el-icon-setting' },
    alwaysShow: true,
    children: [
      {
        path: 'system/webConfig',
        name: '站点配置',
        component: () => import('@/views/core/system/webConfig'),
        meta: { title: '站点配置' }
      },
      {
        path: 'system/dict',
        name: '数据字典',
        component: () => import('@/views/core/system/dict'),
        meta: { title: '数据字典' }
      },
      {
        path: 'system/dictData',
        name: '字典数据',
        hidden: true, // 隐藏
        component: () => import('@/views/core/system/dictData'),
        meta: { title: '字典数据' }
      }
    ]
  }
]

/**
 * asyncRoutes
 * the routes that need to be dynamically loaded based on user roles
 */
export const asyncRoutes = [
  {
    path: 'external-link',
    component: Layout,
    children: [
      {
        path: 'https://gitee.com/jzjzz/VueBlog',
        meta: { title: '访问Gitee', icon: 'link' }
      }
    ]
  },

  // 404 page must be placed at the end !!!
  { path: '*', redirect: '/404', hidden: true }
]

const createRouter = () =>
  new Router({
    // mode: 'history', // require service support
    scrollBehavior: () => ({ y: 0 }),
    routes: constantRoutes
  })

const router = createRouter()

// Detail see: https://github.com/vuejs/vue-router/issues/1234#issuecomment-357941465
export function resetRouter() {
  const newRouter = createRouter()
  router.matcher = newRouter.matcher // reset router
}

export default router
