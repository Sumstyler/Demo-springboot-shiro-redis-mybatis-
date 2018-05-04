<template>
  <div class="panel">
    <panel-title :title="$route.meta.title">
      <el-button @click.stop="on_refresh" size="small">
        <i class="fa fa-refresh"></i>
      </el-button>
      <router-link :to="{name: 'userAdd'}" tag="span">
        <el-button type="primary" icon="el-icon-plus" size="small">添加数据</el-button>
      </router-link>
    </panel-title>
    <div class="panel-body">
      <el-table
        :data="table_data"
        v-loading="load_data"
        element-loading-text="拼命加载中"
        border
        style="width: 100%;">
        <el-table-column
          prop="id"
          label="序号"
          width="80">
        </el-table-column>
        <el-table-column
          prop="username"
          label="用户名"
          width="100">
        </el-table-column>
        <el-table-column
          prop="name"
          label="姓名"
          width="120">
        </el-table-column>
        <el-table-column
          prop="sex"
          label="性别"
          width="80">
          <template slot-scope="props">
            <span v-text="props.row.sex == 'M' ? '男' : '女'"></span>
          </template>
        </el-table-column>
        <el-table-column
          prop="age"
          label="年龄"
          width="80">
        </el-table-column>
        <el-table-column
          prop="birthday"
          label="生日"
          width="100">
        </el-table-column>
        <el-table-column
          prop="tel"
          label="联系方式"
          width="130">
        </el-table-column>
        <el-table-column
          prop="email"
          label="邮箱">
        </el-table-column>
        <el-table-column
          prop="valid"
          label="有效标识">
          <template slot-scope="props">
            <span v-text="props.row.valid == 'Y' ? '是' : '否'"></span>
          </template>
        </el-table-column>
        <el-table-column
          label="操作"
          width="300">
          <template slot-scope="props">
            <router-link :to="{name: 'userUpdate', params: {id: props.row.id}}" tag="span">
              <el-button type="info" size="small" icon="el-icon-edit">修改</el-button>
            </router-link>
            <el-button type="danger" size="small" icon="el-icon-delete" @click="delete_data(props.row)">删除</el-button>
            <el-button type="info" size="small" icon="el-icon-share" @click="relate_role(props.row)">关联角色</el-button>
          </template>
        </el-table-column>
      </el-table>
      <bottom-tool-bar>
        <div slot="page">
          <el-pagination
            @current-change="handleCurrentChange"
            :current-page="currentPage"
            :page-size="5"
            layout="total, prev, pager, next"
            :total="total">
          </el-pagination>
        </div>
      </bottom-tool-bar>
    </div>
    <el-dialog title="关联角色" :visible.sync="roleDialogVisible" width="45%">
      <el-table
        ref="multipleTable"
        tooltip-effect="dark"
        :data="role_table_data"
        @selection-change="handleSelectionChange"
        border
        style="width: 100%">
        <el-table-column
         type="selection"
         width="55">
        </el-table-column>
        <el-table-column
          prop="id"
          label="序号"
          width="120">
        </el-table-column>
        <el-table-column
          prop="name"
          label="名称">
        </el-table-column>
      </el-table>
      <span slot="footer" class="dialog-footer">
        <el-button @click="roleDialogVisible = false">取消</el-button>
         <el-button type="primary" @click="submit_relate_role()">确定</el-button>
      </span>
    </el-dialog>

  </div>
</template>
<script type="text/ecmascript-6">
  import {panelTitle, bottomToolBar} from 'components'
  import  roleDialog from './roleDialog'
  export default{
    data() {
      return {
        table_data: null,
        //当前页码
        currentPage: 1,
        //数据总条目
        total: 0,
        //每页显示多少条数据
        length: 5,
        //请求时的loading效果
        load_data: true,
        //批量选择数组
        multipleSelection: [],
        roleDialogVisible: false,
        role_table_data: null,
        checkUserId: null
      }
    },
    components: {
      panelTitle,
        bottomToolBar,
        roleDialog
    },
    created(){
      this.get_table_data()
    },
    methods: {
      //刷新
      on_refresh() {
        this.get_table_data()
      },
      //获取数据
      get_table_data() {
        this.load_data = true
        this.$fetch.api_user.page({
          page: this.currentPage,
          length: this.length
        })
        .then(({data: {rows, pageNum, total}}) => {
          this.table_data = rows
          this.currentPage = pageNum
          this.total = total
          this.load_data = false
        })
         .catch(() => {
          this.load_data = false
        })
      },
      //单个删除
      delete_data(item) {
        this.$confirm('此操作将删除该数据, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
            this.load_data = true
            this.$fetch.api_user.del(item).then(({msg}) => {
                this.get_table_data()
                this.$message.success(msg)
              }).catch(() => {
                this.load_data = false
              })
          }).catch(() => {
          })
      },
      //关联角色 加载数据并设置默认选中
      relate_role(data) {
        this.checkUserId = data.id
        this.$fetch.api_user.relate(data)
        .then(({data}) => {
            this.roleDialogVisible = true
            this.role_table_data = data
            setTimeout(console.log(this.$refs.multipleTable),3000)
        })
        .catch(() => {
        })
      },
      submit_relate_role() {
          let data ={}
          let rows = this.multipleSelection
          let roleIds =[]
          if(rows){
            rows.forEach(row => {
              roleIds.push(row.id)
            });
          }
          data.roleIds = roleIds
          data.id = this.checkUserId
          this.$fetch.api_user.submit_relate(data)
          .then(({msg}) => {
              this.$message.success(msg)
              this.roleDialogVisible = false
          })
          .catch(() => {
          })
      },
      //页码选择
      handleCurrentChange(val) {
        this.currentPage = val
        this.get_table_data()
      },
      handleSelectionChange(val) {
        this.multipleSelection = val
      }
    }
  }
</script>
