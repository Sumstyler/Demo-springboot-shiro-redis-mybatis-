<template>
  <div class="panel">
    <panel-title :title="$route.meta.title">
      <el-button @click.stop="on_refresh" size="small">
        <i class="fa fa-refresh"></i>
      </el-button>
      <el-button type="success" size="small" icon="el-icon-delete" @click="clear()">清理权限缓存</el-button>
    </panel-title>
    <div class="panel-body">
      <el-table
        :data="table_data"
        v-loading="load_data"
        element-loading-text="拼命加载中"
        border
        style="width: 100%;">
        <el-table-column
          prop="sessionId"
          label="会话ID"
          width="300">
        </el-table-column>
        <el-table-column
          prop="username"
          label="用户名"
          width="120">
        </el-table-column>
        <el-table-column
          prop="ip"
          label="访问地址"
          width="120">
        </el-table-column>
        <el-table-column
          prop="loginTime"
          label="登录时间"
          width="200">
        </el-table-column>
        <el-table-column
          prop="lastVisitTime"
          label="最新访问时间">
        </el-table-column>
        <el-table-column
          label="操作"
          width="120">
          <template slot-scope="props">
            <el-button type="danger" size="small" icon="el-icon-remove" @click="kickedout(props.row)">踢出</el-button>
          </template>
        </el-table-column>
      </el-table>
      <bottom-tool-bar>
        <div slot="page">
          <el-pagination
            @current-change="handleCurrentChange"
            :current-page="currentPage"
            :page-size="10"
            layout="total, prev, pager, next"
            :total="total">
          </el-pagination>
        </div>
      </bottom-tool-bar>
    </div>
  </div>
</template>
<script type="text/javascript">
  import {panelTitle, bottomToolBar} from 'components'

  export default{
    data(){
      return {
        table_data: null,
        //当前页码
        currentPage: 1,
        //数据总条目
        total: 0,
        //每页显示多少条数据
        length: 10,
        //请求时的loading效果
        load_data: true,
        //批量选择数组
        batch_select: []
      }
    },
    components: {
      panelTitle,
      bottomToolBar
    },
    created(){
      this.get_table_data()
    },
    methods: {
      //刷新
      on_refresh(){
        this.get_table_data()
      },
      //获取数据
      get_table_data(){
        this.load_data = true
        this.$fetch.api_sessions.online()
        .then(({data: {rows, total}}) => {
          this.table_data = rows
          this.load_data = false
        })
        .catch(() => {
          this.load_data = false
        })
      },
      //单个删除
      kickedout(item){
        this.$confirm('此操作将踢出当前用户, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        })
        .then(() => {
          this.load_data = true
          this.$fetch.api_sessions.kickedout(item)
          .then(({msg}) => {
            this.get_table_data()
            this.$message.success(msg)
          })
          .catch(() => {
            this.load_data = false
          })
        })
        .catch(() => {
          this.load_data = false
        })
      },
      clear(){
          this.load_data = true
          this.$fetch.api_cache.clearAll()
            .then(({msg}) => {
            this.load_data = false
            this.$message.success(msg)
          })
        .catch(() => {
            this.load_data = false
          })
      },
      //页码选择
      handleCurrentChange(val) {
        this.currentPage = val
        this.get_table_data()
      }
    }
  }
</script>
