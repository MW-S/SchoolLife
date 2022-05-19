<template>
  <div class="app-container">
    <el-button style="margin-bottom: 10px;" @click="handleCreate">添加</el-button>
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
      <!-- <el-table-column label="ID" prop="id" sortable="custom" align="center" width="80" :class-name="getSortClass('id')">
        <template slot-scope="{row}">
          <span>{{ row.id }}</span>
        </template>
      </el-table-column> -->
      <el-table-column label="闲置物品" width="150px" align="center">
        <template slot-scope="{row}">
          <span>{{ row.name }}</span>
        </template>
      </el-table-column>
      <el-table-column label="物品类型" width="150px" align="center">
        <template slot-scope="{row}">
          <span>{{ row.type }}</span>
        </template>
      </el-table-column>
      <el-table-column  label="物品图片" width="120px" align="center">
        <template slot-scope="{row}" > 
           <el-image v-if="row.picture != undefined "
              style="width: 100px; height: 100px; margin: 0 auto;"
              :src=" row.picture"
              ></el-image>
        </template>
      </el-table-column>
      <el-table-column label="物品信息"  align="center" >
        <template slot-scope="{row}">
          <span>{{ row.info }}</span>
        </template>
      </el-table-column>
      <el-table-column label="价格"  align="center" >
        <template slot-scope="{row}">
          <span>{{ row.price }}</span>
        </template>
      </el-table-column>
      <el-table-column label="手机号" width="110px" align="center" >
        <template slot-scope="{row}">
          <span>{{ row.phone }}</span>
        </template>
      </el-table-column>
      <el-table-column label="微信号" width="110px" align="center" >
        <template slot-scope="{row}">
          <span>{{ row.wechatId }}</span>
        </template>
      </el-table-column>
      <el-table-column label="发布人"  align="center" >
        <template slot-scope="{row}">
          <span>{{ getUserName(row.userId) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="购买人"  align="center" >
        <template slot-scope="{row}">
          <span>{{ getUserName(row.buyerId) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="审核状态"  align="center" >
        <template slot-scope="{row}">
          <span>{{ auditStateText[row.auditState] }}</span>
        </template>
      </el-table-column>
      <el-table-column label="物品状态"  align="center" >
        <template slot-scope="{row}">
          <span>{{ stateText[row.state] }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" width="300" class-name="small-padding fixed-width">
        <template slot-scope="{row,$index}">
          <el-button  type="primary" size="mini" @click="handleUpdate(row)">
            编辑
          </el-button>
          <el-button  size="mini" type="danger" @click="handleDelete(row,$index)">
            删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total>0" :total="total" :page.sync="listQuery.page" :limit.sync="listQuery.limit" @pagination="getList" />

    <el-dialog :title="textMap[dialogStatus]" :visible.sync="dialogFormVisible">
      <el-form ref="dataForm" :rules="rules" :model="temp" label-position="left" label-width="100px" style="display: flex-direction: column;align-items: center;justify-content: center;">
        <el-form-item label="闲置物品" prop="name">
          <el-input v-model="temp.name" type="text" />
        </el-form-item>
        <el-form-item label="物品类型" prop="type">
          <el-input v-model="temp.type" type="text" />
        </el-form-item>
        <el-form-item label="物品信息" prop="info">
          <el-input v-model="temp.info" type="textarea" />
        </el-form-item>
        <el-form-item label="价格" prop="price">
          <el-input v-model="temp.price" type="text" />
        </el-form-item>
        <el-form-item label="手机号" prop="phone">
          <el-input v-model="temp.phone" type="text" />
        </el-form-item>
        <el-form-item label="微信号" prop="wechatId">
          <el-input v-model="temp.wechatId" type="text" />
        </el-form-item>
        <el-form-item v-if="temp.id!=undefined" label="发布人" prop="userId">
         <el-select v-model="temp.userId" placeholder="请选择">
            <el-option
              v-for="item in users"
              :key="item.id"
              :label="item.name"
              :value="item.id">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item v-if="temp.id!=undefined" label="购买人" prop="buyerId">
          <el-select v-model="temp.buyerId" placeholder="请选择">
            <el-option
              v-for="item in users"
              :key="item.id"
              :label="item.name"
              :value="item.id">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item v-if="temp.id!=undefined" label="审核状态" prop="auditState">
          <el-select v-model="temp.auditState"  placeholder="请选择">
             <el-option
              v-for="(item, index) in auditStateText"
              :key="index"
              :label="item"
              :value="index">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item v-if="temp.id!=undefined" label="物品状态" prop="state">
          <el-select v-model="temp.state" placeholder="请选择" >
            <el-option key="false" label="未售出" value="false" />
            <el-option key="true" label="已售出" value="true" />
          </el-select>
        </el-form-item>
         <el-form-item label="图片" >
          <!-- <el-input v-model="temp.pictures" type="textarea" /> -->
          <el-upload
            class="upload-demo"
            action=""
            :http-request="uploadFile"
            :on-preview="handlePreview"
            :on-remove="handleRemove"
            :before-remove="beforeRemove"
            multiple
            :limit="3"
            :on-exceed="handleExceed"
            list-type="picture-card"
            :file-list="fileList">
            <!-- <img v-for="file in fileList" :src="file.url" class="avatar" />  -->
						<i class="el-icon-plus"></i>
            <!-- <el-button size="small" type="primary">点击上传</el-button> -->
            <div slot="tip" class="el-upload__tip">只能上传jpg/png文件，且不超过500kb</div>
          </el-upload>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">
          取消
        </el-button>
        <el-button type="primary" @click="saveData()">
          确定
        </el-button>
      </div>
    </el-dialog>
    <!-- <el-dialog title="文件上传" :visible.sync="dialogAddFile">
      <upload :id="temp.id" @child-event="uploadSuccess" />
    </el-dialog> -->

  </div>
</template>

<script>
import * as user from '@/api/user'
import { getList, getById, save, delByIds, uploadFile } from '@/api/common'
import waves from '@/directive/waves' // waves directive
import { parseTime } from '@/utils'
import Pagination from '@/components/Pagination' // secondary package based on el-pagination
import Upload from '@/components/Upload'

const calendarTypeOptions = [
  { key: 'CN', display_name: 'China' },
  { key: 'US', display_name: 'USA' },
  { key: 'JP', display_name: 'Japan' },
  { key: 'EU', display_name: 'Eurozone' }
]

// arr to obj, such as { CN : "China", US : "USA" }
// const calendarTypeKeyValue = calendarTypeOptions.reduce((acc, cur) => {
//   acc[cur.key] = cur.display_name
//   return acc
// }, {})

export default {
  name: 'Goods',
  components: { Pagination, Upload },
  directives: { waves },
  filters: {
    statusCOntent(status) {
      const statusMap = ['Starting', 'Finished']
      return statusMap[status]
    },
    statusFilter(status) {
      const statusMap = {
        0: 'info',
        1: 'success',
        2: 'danger'
      }
      return statusMap[status]
    }
  },
  data() {
    return {
      serverUrl: this.SERVERURL,
      fileList: [],
      target: "goods",
      auditStateText:{"0": "待审核", "1": "已过审", "2": "未过审"},
      stateText: {"false" : "未出售", "true": "已出售"},
      tableKey: 0,
      list: [],
      users: [],
      total: 0,
      listLoading: true,
      listQuery: {
        page: 1,
        size: 20,
        isAdmin: false,
        importance: undefined,
        title: undefined,
        type: undefined,
        sort: '+id'
      },
      importanceOptions: [1, 2, 3],
      calendarTypeOptions,
      sortOptions: [{ label: 'ID Ascending', key: '+id' }, { label: 'ID Descending', key: '-id' }],
      statusOptions: ['published', 'draft', 'deleted'],
      showReviewer: false,
      temp: {
        id: undefined,
      },
      tempRow: '',
      users: [],
      dialogAddFile: false,
      dialogFormVisible: false,
      dialogStatus: '',
      textMap: {
        update: 'Edit',
        create: 'Create'
      },
      rules: {
        name: [{ required: true, message: 'name is required', trigger: 'change' }],
        type: [{ required: true, message: 'type is required', trigger: 'change' }]
      },
      downloadLoading: false,
      addArr: [],
      addType: '',
      addId: '',
      addFileName: ''
    }
  },
  created() {
    this.listQuery.isAdmin = this.$store.state.user.isAdmin
    this.getUsers()
    this.getList()
  },
  methods: {
    uploadFile(param){
      var formData = new FormData()
      formData.append('file', param.file)
      uploadFile(formData).then(response => {
        console.log('上传图片成功')
        this.fileList.push({
            name: param.file.name,
            url: response.data.path
          })
      }).catch(response => {
        console.log('图片上传失败')
      })
    },
     handleRemove(file, fileList) {
      this.fileList.splice(this.fileList.indexOf(file),1)
    },
    handlePreview(file) {
      this.dialogImageUrl = file.url;
		  this.dialogPreviewVisible = true;
    },
    handleExceed(files, fileList) {
      this.$message.warning(`当前限制选择 3 个文件，本次选择了 ${files.length} 个文件，共选择了 ${files.length + fileList.length} 个文件`);
    },
    beforeRemove(file, fileList) {
      return this.$confirm(`确定移除 ${ file.name }？`);
    },
    uploadSuccess(data) {
      console.log(data)
      this.dialogAddFile = data
      this.$notify({
        title: 'Success',
        message: 'Upload Successfully',
        type: 'success',
        duration: 2000
      })
      const tempData = Object.assign({}, this.temp)
      updateTask(tempData).then(() => {
        const index = this.list.findIndex(v => v.id === tempData.id)
        this.list.splice(index, 1, tempData)
        this.dialogFormVisible = false
        this.$notify({
          title: 'Success',
          message: 'Update Successfully',
          type: 'success',
          duration: 2000
        })
      })
    },

    resetAdd() {
      this.addArr = []
    },
    getUserName(id){
       var res = "未指派"
      this.users.forEach(item=>{
        if(item.id == id){
          res =  item.name;
        }
      })
      return res;
    },
    getUsers(){
      this.listLoading = true
      user.getList({
        page: 1,
        size: 100,
      }).then(response => {
        this.users = response.data.data
        // Just to simulate the time of the request
        setTimeout(() => {
          this.listLoading = false
        }, 1.5 * 1000)
      })
    },
    getList() {
      this.listLoading = true
      getList(this.target, this.listQuery).then(response => {
        this.list = response.data.list
        this.total = response.data.total
        // Just to simulate the time of the request
        this.list.forEach(item=>{
          if(item.pictures != null && item.pictures != undefined && item.pictures != ""){
            var imgs = JSON.parse(item.pictures);
            item.picture = imgs.length != 0? imgs[0]: undefined;
          }
        })
        setTimeout(() => {
          this.listLoading = false
        }, 1.5 * 1000)
      })
    },
    handleFilter() {
      this.listQuery.page = 1
      this.getList()
    },
    handleModifyStatus(row, status) {
      this.temp = Object.assign({}, row)
      this.temp.state = status
      if (status === 1) {
        this.dialogAddFile = true
      } else {
        const tempData = Object.assign({}, this.temp)
        updateTask(tempData).then(() => {
          const index = this.list.findIndex(v => v.id === tempData.id)
          this.list.splice(index, 1, tempData)
          this.dialogFormVisible = false
          this.$notify({
            title: 'Success',
            message: 'Update Successfully',
            type: 'success',
            duration: 2000
          })
        })
      }
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
        state: 0
      }
      this.fileList = []
    },
    handleCreate() {
      this.resetTemp()
      this.dialogFormVisible = true
      this.$nextTick(() => {
        this.$refs['dataForm'].clearValidate()
      })
    },
    saveData() {
      this.$refs['dataForm'].validate((valid) => {
        if (valid) {
          var imgs = []
          this.fileList.forEach(item=>{
            imgs.push(item.url);
          })
          this.temp.pictures = JSON.stringify(imgs);
          save(this.target, this.temp).then(() => {
            this.getList()
            this.dialogFormVisible = false
            this.$notify({
              title: 'Success',
              message: 'Successfully',
              type: 'success',
              duration: 2000
            })
          })
        }
      })
    },
    handleUpdate(obj){
      this.resetTemp()
      this.temp = Object.assign({},obj);
      var imgs = obj.pictures != null?JSON.parse(obj.pictures):[];
      imgs.forEach(item=>{
        this.fileList.push({name: item, url: item})
      })
      this.dialogFormVisible = true;
    },
    handleDelete(row, index) {
      delByIds(this.target, {ids: [row.id]} ).then(() => {
        this.getList()
        this.dialogFormVisible = false
        this.$notify({
          title: 'Success',
          message: 'Successfully',
          type: 'success',
          duration: 2000
        })
        this.list.splice(index, 1)
      })
    },
    handleDownload() {
      this.downloadLoading = true
      import('@/vendor/Export2Excel').then(excel => {
        const tHeader = ['timestamp', 'title', 'type', 'importance', 'status']
        const filterVal = ['timestamp', 'title', 'type', 'importance', 'status']
        const data = this.formatJson(filterVal)
        excel.export_json_to_excel({
          header: tHeader,
          data,
          filename: 'table-list'
        })
        this.downloadLoading = false
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
    }
  }
}
</script>
