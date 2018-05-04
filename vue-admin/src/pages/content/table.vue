<template>
  <div class="panel">
    <panel-title :title="$route.meta.title">
      <el-button @click.stop="on_refresh" size="small">
        <i class="fa fa-refresh"></i>
      </el-button>
    </panel-title>
    <div class="panel-body">
      <el-table
        :data="table_data"
        v-loading="load_data"
        element-loading-text="拼命加载中"
        border
        style="width: 100%;">
        <el-table-column
          prop="name"
          label="文件名"
          width="300">
        </el-table-column>
        <el-table-column
          prop="type"
          label="文件类型" width="100">
        </el-table-column>
        <el-table-column
          prop="size"
          label="文件大小" width="120">
        </el-table-column>
        <el-table-column
          prop="updateTime"
          label="上传时间" >
        </el-table-column>
        <el-table-column
          label="操作"
          width="120">
          <template slot-scope="props">
            <el-button type="success" size="small" icon="el-icon-download" @click="download(props.row)">下载</el-button>
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
  import {download_url} from 'common/config'
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
        this.$fetch.api_content.page({
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
      //下载
      download(item){
          window.location.href = download_url+item.name
      },
      //页码选择
      handleCurrentChange(val) {
        this.currentPage = val
        this.get_table_data()
      }
    }
  }
</script>
