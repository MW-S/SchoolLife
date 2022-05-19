<template>
  <div class="dashboard-container" >
    <img style="position: absolute;width: 100%;height: 100%;opacity:0.7;" src="@/assets/background.jpg" >
    <h class="welcome">欢迎来到School Life管理系统</h>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'

export default {
  name: 'Dashboard',
  data() {
    return {
      currentRole: 'adminDashboard'
    }
  },
  computed: {
    ...mapGetters([
      'roles'
    ])
  },
  created() {
    // if (!this.roles.includes('admin')) {
    //   this.currentRole = 'editorDashboard'
    // }
  }
}
</script>
<style scoped>
.welcome{
  font-size: 50px;
    width: 100%;
    justify-content: center;
    display: flex;
    padding-top: 50px;
}
</style>
