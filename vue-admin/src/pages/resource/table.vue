<template>
  <div class="panel">
    <panel-title :title="$route.meta.title">
      <el-button @click.stop="on_refresh" size="small">
        <i class="fa fa-refresh"></i>
      </el-button>
      <router-link :to="{name: 'resourceAdd'}" tag="span">
        <el-button type="primary" icon="plus" size="small">添加数据</el-button>
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
          label="ID"
          width="120">
        </el-table-column>
        <el-table-column
          prop="name"
          label="名称"
          width="120">
        </el-table-column>
        <el-table-column
          prop="type"
          label="类型" width="120">
          <template slot-scope="props">
            <span v-text="props.row.type == 'MENU' ? '菜单' : '功能'"></span>
          </template>
        </el-table-column>
        <el-table-column
          prop="path"
          label="路径">
        </el-table-column>
        <el-table-column
          prop="percode"
          label="编码">
        </el-table-column>
        <el-table-column
          prop="parentid"
          label="父节点"
          width="120">
        </el-table-column>
        <el-table-column
          prop="valid"
          label="有效标识" width="120">
          <template slot-scope="props">
            <span v-text="props.row.valid == 'Y' ? '是' : '否'"></span>
          </template>
        </el-table-column>
        <el-table-column
          prop="sid"
          label="序号"
          width="120">
        </el-table-column>
        <el-table-column
          label="操作"
          width="180">
          <template slot-scope="props">
            <router-link :to="{name: 'resourceUpdate', params: {id: props.row.id}}" tag="span">
              <el-button type="info"  size="small" icon="edit">修改</el-button>
            </router-link>
            <el-button type="danger" size="small" icon="delete" @click="delete_data(props.row)">删除</el-button>
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
        length: 5,
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
        this.$fetch.api_resource.page({
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
      delete_data(item){
        this.$confirm('此操作将删除该数据, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        })
          .then(() => {
            this.load_data = true
            this.$fetch.api_resource.del(item)
              .then(({msg}) => {
                this.get_table_data()
                this.$message.success(msg)
              })
              .catch(() => {
                this.load_data = false
              })
          })
          .catch(() => {
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
