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
      <el-table-column label="名称" width="150px" align="center">
        <template slot-scope="{row}">
          <span>{{ row.name }}</span>
        </template>
      </el-table-column>
      <el-table-column  label="饭堂照片" width="120px" align="center">
        <template slot-scope="{row}" > 
          <!-- <span class="link-type" @click="handleUpdate(row)">{{ row.avatarUrl }}</span> -->
           <el-image v-if="row.picture != undefined "
              style="width: 100px; height: 100px; margin: 0 auto;"
              :src=" row.picture"
              ></el-image>
        </template>
      </el-table-column>
      <el-table-column label="供应饭堂" width="150px" align="center">
        <template slot-scope="{row}">
          <span>{{ getCanteenName(row.canteenId) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="价格" width="150px" align="center">
        <template slot-scope="{row}">
          <span>{{ row.price }}元</span>
        </template>
      </el-table-column>
      <el-table-column label="食物类型" width="150px" align="center">
        <template slot-scope="{row}">
          <span>{{ typeText[row.type] }}</span>
        </template>
      </el-table-column>
      <el-table-column label="提供日期" width="150px" align="center">
        <template slot-scope="{row}">
          <span>{{ row.offerDate }}</span>
        </template>
      </el-table-column>
      <!-- <el-table-column label="图片"  align="center" >
        <template slot-scope="{row}">
          <span>{{ row.pictures }}</span>
        </template>
      </el-table-column> -->
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
        <el-form-item label="名称" prop="name">
          <el-input v-model="temp.name" type="text" />
        </el-form-item>
        <el-form-item label="供应饭堂" prop="canteenId">
          <el-select v-model="temp.canteenId" placeholder="请选择">
            <el-option
              v-for="item in canteens"
              :key="item.id"
              :label="item.name"
              :value="item.id">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="价格" prop="price">
          <el-input v-model="temp.price" type="text" />
        </el-form-item>
        <el-form-item label="食物类型" prop="type">
          <el-select v-model="temp.type" placeholder="请选择">
            <el-option
              v-for="item in types"
              :key="item.id"
              :label="item.name"
              :value="item.id">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="提供日期" prop="offerDate">
          <el-date-picker
              v-model="temp.offerDate"
              value-format="yyyy-MM-dd"
              type="date"
              placeholder="选择日期">
          </el-date-picker>
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
    <el-dialog :visible.sync="dialogPreviewVisible" :modal-append-to-body="false"> 
      <img width="100%" :src="dialogImageUrl" alt>
    </el-dialog>
  </div>
</template>

<script>
import { getList, getById, save, delByIds, uploadFile} from '@/api/common'
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
  name: 'Food',
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
      canteens:[],
      types: [{
        id: 0,
        name: "早餐"
      },
      {
        id: 1,
        name: "午餐"
      },
      {
        id: 2,
        name: "晚餐"
      },
      {
        id: 3,
        name: "宵夜"
      }],
      typeText: ["早餐", "午餐", "晚餐", "宵夜"],
      target: "diet/food",
      dialogImageUrl: "",
      dialogPreviewVisible: false,
      tableKey: 0,
      list: [],
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
        name: '',
        location: '',
        pictures: undefined
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
    this.listQuery.isAdmin =  this.$store.state.user.isAdmin
    // this.getCanteens()
    this.getList();
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
    getCanteenName(id){
       var item ;
      for(var index in this.canteens){
        item = this.canteens[index]
        if(item.id == id){
          return item.name
        }
      }
      return "该食堂不存在"
    },
    resetAdd() {
      this.addArr = []
    },
   async getList() {
      const that = this
      if(this.canteens.length == 0){
        var res = await this.getCanteens();
      }
      that.listLoading = true
      that.list = []
      getList(that.target, that.listQuery).then(response => {
        response.data.list.forEach(item=>{
          // item.canteen = that.getCanteenName(item.id)
          if(item.pictures != null && item.pictures != undefined && item.pictures != ""){
            var imgs = JSON.parse(item.pictures);
            item.picture = imgs.length != 0? imgs[0]: undefined;
          }
          that.list.push(item)
        })
        that.total = response.data.total
        // Just to simulate the time of the request
        setTimeout(() => {
          this.listLoading = false
        }, 1.5 * 1000)
      })
    },
    getCanteens() {
      this.listLoading = true
      getList("diet/canteen", {
        page: 1,
        size: 30,
      }).then(response => {
        this.canteens = response.data.list
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
        name: ''
      }
      this.fileList = [];
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
              message: 'Created Successfully',
              type: 'success',
              duration: 2000
            })
          })
        }
      })
    },
    handleUpdate(obj){
      this.resetTemp();
      this.dialogFormVisible = true;
      this.temp = Object.assign({},obj);
      var imgs = obj.pictures != null?JSON.parse(obj.pictures):[];
      imgs.forEach(item=>{
        this.fileList.push({name: item, url: item})
      })
    },
    handleDelete(row, index) {
      delByIds(this.target, {ids: [row.id]} ).then(() => {
        this.getList()
        this.dialogFormVisible = false
        this.$notify({
          title: 'Success',
          message: 'Update Successfully',
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
