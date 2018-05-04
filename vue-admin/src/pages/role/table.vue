<template>
  <div class="panel">
    <panel-title :title="$route.meta.title">
      <el-button @click.stop="on_refresh" size="small">
        <i class="fa fa-refresh"></i>
      </el-button>
      <router-link :to="{name: 'roleAdd'}" tag="span">
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
          label="序号"
          width="120">
        </el-table-column>
        <el-table-column
          prop="name"
          label="名称"
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
          prop="creater"
          label="创建人" width="120">
        </el-table-column>
        <el-table-column
          prop="createTime"
          label="创建时间">
        </el-table-column>
        <el-table-column
          prop="updater"
          label="更新人" width="120">
        </el-table-column>
        <el-table-column
          prop="updateTime"
          label="更新时间">
        </el-table-column>
        <el-table-column
          label="操作"
          width="300">
          <template slot-scope="props">
            <router-link :to="{name: 'roleUpdate', params: {id: props.row.id}}" tag="span">
              <el-button type="info" size="small" icon="el-icon-edit">修改</el-button>
            </router-link>
            <el-button type="danger" size="small" icon="el-icon-delete" @click="delete_data(props.row)">删除</el-button>
           <!-- <el-button type="info" size="small" icon="el-icon-share" @click="relate_data(props.row)">关联权限</el-button>-->
            <router-link :to="{name: 'roleRelate', params: {id: props.row.id}}" tag="span">
              <el-button type="info" size="small" icon="el-icon-edit">关联权限</el-button>
            </router-link>
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
        searchForm: {
          name: null
        },
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
        this.$fetch.api_role.page({
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
            this.$fetch.api_role.del(item)
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
