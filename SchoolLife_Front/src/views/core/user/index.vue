<template>
  <div class="app-container">
    <el-button style="margin-bottom: 10px;" @click="handleCreate">添加管理员</el-button>
    <el-select style="margin-left:30px;" v-model="listQuery.type" placeholder="请选择用户类型" @change="getList">
      <el-option
        v-for="item in types"
        :key="item.value"
        :label="item.label"
        :value="item.value">
      </el-option>
    </el-select>
    <el-table
      :key="tableKey"
      v-loading="listLoading"
      :data="list"
      border
      fit
      highlight-current-row
      style="width: 100%;"
      @sort-change="sortChange"
    >
      <el-table-column label="ID" prop="id" sortable="custom" align="center" width="80" :class-name="getSortClass('id')">
        <template slot-scope="{row}">
          <span>{{ row.id }}</span>
        </template>
      </el-table-column>
      <el-table-column label="Avatar" min-width="150px">
        <template slot-scope="{row}">
          <!-- <span class="link-type" @click="handleUpdate(row)">{{ row.avatarUrl }}</span> -->
           <el-image v-if="row.avatarUrl != undefined && row.avatarUrl != null && row.avatarUrl != ''"
              style="width: 100px; height: 100px"
              :src=" fileServerUrl + row.avatarUrl"
              :fit="fit"></el-image>
        </template>
      </el-table-column>
      <el-table-column label="Name" min-width="150px">
        <template slot-scope="{row}">
          <span>{{ row.name }}</span>
        </template>
      </el-table-column>
      <el-table-column label="Gender" class-name="status-col" width="100">
        <template slot-scope="{row}">
          <span>{{ row.gender | genderFileter }}</span>
        </template>
      </el-table-column>
      <el-table-column label="Type" width="110px" align="center">
        <template slot-scope="{row}">
          <span>{{ row.type | typeFileter }}</span>
        </template>
      </el-table-column>
      <el-table-column label="Account" align="center" width="95">
        <template slot-scope="{row}">
          <span>{{ row.userName }}</span>
        </template>
      </el-table-column>
      <el-table-column label="Phone" width="100">
        <template slot-scope="{row}">
          <span :type="row.phone ">
            {{ row.phone }}
          </span>
        </template>
      </el-table-column>
      <el-table-column label="Account" align="center" width="95">
        <template slot-scope="{row}">
          <span>{{ row.userName }}</span>
        </template>
      </el-table-column>
      <el-table-column label="Number" align="center" width="95"  v-if="listQuery.type == 0">
        <template slot-scope="{row}">
          <span>{{ row.number }}</span>
        </template>
      </el-table-column>
      <el-table-column label="CarID" align="center" width="95"  v-if="listQuery.type == 0">
        <template slot-scope="{row}">
          <span>{{ row.carId }}</span>
        </template>
      </el-table-column>
      <el-table-column label="Picture" align="center" width="150"  v-if="listQuery.type == 0">
        <template slot-scope="{row}">
           <el-image  v-if="row.carPicture != undefined && row.carPicture != null && row.carPicture != ''"
              style="width: 100px; height: 100px"
              :src=" fileServerUrl + row.carPicture"
              :fit="fit"></el-image>
        </template>
      </el-table-column>
      <el-table-column label="CreateTime" width="100">
        <template slot-scope="{row}">
          <span>
            {{ row.gmtCreate }}
          </span>
        </template>
      </el-table-column>
      <el-table-column label="Actions" align="center" width="230" class-name="small-padding fixed-width">
        <template slot-scope="{row,$index}">
          <el-button type="primary" size="mini" @click="handleUpdate(row)">
            Edit
          </el-button>
          <el-button size="mini" type="danger" @click="handleDelete(row,$index)">
            Delete
          </el-button>
        </template>
      </el-table-column>
    </el-table>
    <pagination v-show="total>0" :total="total" :page.sync="listQuery.page" :limit.sync="listQuery.limit" @pagination="getList" />

    <el-dialog :title="textMap[dialogStatus]" :visible.sync="dialogFormVisible">
      <el-form ref="dataForm" :rules="rules" :model="temp" label-position="left" label-width="70px" style="width: 400px; margin-left:50px;">
        <el-form-item label="Avatar" prop="avatar">
            <el-upload 
              class="upload-demo"
              action="#"
              list-type="picture"
              :multiple="false"
              :show-file-list="true"
              :http-request="uploadHttpRequest"
              :file-list="fileList"
              :limit="1"
          >
            <el-button size="small" type="primary">点击上传</el-button>
        </el-upload>
      </el-form-item>
        <el-form-item label="Name" prop="name">
          <el-input v-model="temp.name" type="text" />
        </el-form-item>
        <el-form-item label="Gender" prop="gender">
          <el-radio-group v-model="temp.gender">
            <el-radio label="1" name="male">男</el-radio>
            <el-radio label="0" name="female">女</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="Account" prop="account" v-if="temp.id != undefined">
          <el-input v-model="temp.userName" disabled />
        </el-form-item>
        <el-form-item label="Phone" prop="phone">
          <el-input v-model="temp.phone" />
        </el-form-item>
        <el-form-item v-if="listQuery.type == 0" label="CarID" prop="carId" label-width="100px">
          <el-input v-model="temp.carId" />
        </el-form-item>
        <!-- <el-form-item v-if="listQuery.type == 0" label="Picture" prop="picture">
          <el-upload 
              action="#"
              list-type="picture"
              :multiple="false"
              :show-file-list="true"
              :http-request="uploadCarPicture"
              :file-list="[{name:'picture', url: fileServerUrl + temp.carPicture}]"
              :limit="1"
          />
            <el-button size="small" type="primary">点击上传</el-button>
        </el-form-item> -->
        <el-form-item v-if="listQuery.type == 0" label="CarPicture" prop="carPicture" label-width="100px">
           <el-image v-if="temp.carPicture != null"
              style="width: 100px; height: 100px"
              :src=" fileServerUrl + temp.carPicture"
              :fit="fit"></el-image>
        </el-form-item>
        <el-form-item v-if="listQuery.type == 0" label="Number" prop="number" label-width="100px">
          <el-input v-model="temp.number" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">
          Cancel
        </el-button>
        <el-button type="primary" @click="saveUser()">
          Confirm
        </el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { createUser, getList, updateUser, delUser } from '@/api/user.js'
import { upload } from '@/api/upload.js'
import waves from '@/directive/waves' // waves directive
import Pagination from '@/components/Pagination' // secondary package based on el-pagination
import { parseTime } from '@/utils'

export default {
  name: 'ProjectMember',
  components: { Pagination },
  directives: { waves },
  filters: {
    genderFileter(gender) {
      const statusMap = {
        0: '女',
        1: '男',
        2: '未知'
      }
      console.log()
      return statusMap[gender]
    },
    typeFileter(type) {
      const statusMap = {
        0: '小程序用户',
        1: '管理员'
      }
      return statusMap[type]
    }
  },
  data() {
    return {
      serverUrl: this.SERVERURL,
      fileList: [],
      tableKey: 0,
      list: null,
      total: 0,
      listLoading: true,
      listQuery: {
        page: 1,
        size: 10,
        type: 1,
        sort: '+id'
      },
      temp: {
        id: undefined,
        avatarUrl: '',
        userName: '',
        number: "",
        name: '',
        phone: '',
        type: 1,
        gender: 0
      },
      otherQuery: '',
      dialogFormVisible: false,
      dialogStatus: '',
      rules: {
        name: [{ required: true, message: 'name is required', trigger: 'change' }],
        type: [{ required: true, message: 'type is required', trigger: 'change' }]
      },
      types: [
      {
        name: "user",
        label: "小程序用户",
        value: 0,
      },
      {
        name: "admin",
        label: "管理员",
        value: 1,
      }],
      textMap: {
        update: 'Edit',
        create: 'Create'
      }
    }
  },
  watch: {
    $route: {
      handler: function(route) {
        const query = route.query
        console.log(query)
        if (query) {
          this.otherQuery = this.getOtherQuery(query)
          console.log(this.otherQuery)
        }
      },
      immediate: true
    }
  },
  created() {
    this.getList()
  },
  mounted() {

  },
  destroyed() {
    // window.removeEventListener('storage', this.afterQRScan)
  },
  methods: {
    // 覆盖element的默认上传文件
        uploadHttpRequest(data) {
          let that = this
           that.listLoading = true
            let formData = new FormData()
            formData.append("file", data.file)
            upload(formData).then(res=>{
                if(res.code == 1){
                  that.temp.avatarUrl = res.data.path;
                   this.fileList = [{name: data.file.name, url: this.serverUrl + res.data.path}]
                }
               // Just to simulate the time of the request
              setTimeout(() => {
                  that.listLoading = false
                }, 1.5 * 1000)
            })
        },
        // 限制文件上传的个数只有一个，获取上传列表的最后一个
        handleUploadChange(file, fileList) {
            if (fileList.length > 0) {
                this.fileList = [fileList[fileList.length - 1]] // 这一步，是 展示最后一次选择的文件
            }
        },
    getList() {
      this.listLoading = true
      getList(this.listQuery).then(response => {
        this.list = response.data.data
        this.total = parseInt(response.data.size)

        // Just to simulate the time of the request
        setTimeout(() => {
          this.listLoading = false
        }, 1.5 * 1000)
      })
    },
    handleFilter() {
      this.listQuery.page = 1
      this.getList()
    },
    sortChange(data) {
      const { prop, order } = data
      if (prop === 'id') {
        this.sortByID(order)
      }
    },
    sortByID(order) {
      if (order === 'ascending') {
        this.listQuery.sort = '+id'
      } else {
        this.listQuery.sort = '-id'
      }
      this.handleFilter()
    },
    resetTemp() {
      this.temp = {
        id: undefined,
        avatarUrl: '',
        userName: '',
        name: '',
        phone: '',
        type: 1,
        gender: 0
      }
      this.fileList = []
    },
    handleCreate() {
      this.resetTemp()
      this.temp.type = 1;
      this.dialogStatus = 'create'
      this.dialogFormVisible = true
      this.$nextTick(() => {
        this.$refs['dataForm'].clearValidate()
      })
    },
    createData() {
      this.$refs['dataForm'].validate((valid) => {
        if (valid) {
          // this.temp.id = parseInt(Math.random() * 100) + 1024 // mock a id
          // this.temp.author = 'vue-element-admin'
          createUser(this.temp).then(res => {
            this.list.unshift(this.temp)
            this.dialogFormVisible = false
            this.$notify({
              title: 'Success',
              message: '新建管理员用户名为：' + res.data.username,
              type: 'success',
              duration: 2000
            })
          })
        }
      })
    },
    handleUpdate(row) {
      this.temp = Object.assign({}, row) // copy obj
      this.temp.timestamp = new Date(this.temp.timestamp)
      this.dialogStatus = 'update'
      this.dialogFormVisible = true
      this.$nextTick(() => {
        this.$refs['dataForm'].clearValidate()
      })
    },
    saveUser(){
          if(this.temp.id != undefined && this.temp.id != null && this.temp.id != ""){
            this.updateData();
          }else{
            this.createData();
          }
    },
    updateData() {
      this.$refs['dataForm'].validate((valid) => {
        if (valid) {
          const tempData = Object.assign({}, this.temp)
          // tempData.timestamp = +new Date(tempData.timestamp) // change Thu Nov 30 2017 16:41:05 GMT+0800 (CST) to 1512031311464
            updateUser(tempData).then(() => {
              const index = this.list.findIndex(v => v.id === this.temp.id)
              this.list.splice(index, 1, this.temp)
              this.dialogFormVisible = false
              this.$notify({
                title: 'Success',
                message: 'Update Successfully',
                type: 'success',
                duration: 2000
              })
            })   
        }
      })
    },
    handleDelete(row, index) {
      const tempData = Object.assign({}, row)
      delUser(tempData).then(() => {
        this.$notify({
          title: 'Success',
          message: 'Delete Successfully',
          type: 'success',
          duration: 2000
        })
        this.list.splice(index, 1)
      })
    },
    formatJson(filterVal) {
      return this.list.map(v => filterVal.map(j => {
        if (j === 'timestamp') {
          return parseTime(v[j])
        } else {
          return v[j]
        }
      }))
    },
    getSortClass: function(key) {
      const sort = this.listQuery.sort
      return sort === `+${key}` ? 'ascending' : 'descending'
    },
    getOtherQuery(query) {
      return Object.keys(query).reduce((acc, cur) => {
        if (cur !== 'redirect') {
          acc[cur] = query[cur]
        }
        return acc
      }, {})
    }
  }
}
</script>

<style>
</style>
