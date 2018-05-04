<template>
  <div class="panel">
    <panel-title :title="$route.meta.title">
      <el-button @click.stop="on_refresh" size="small">
        <i class="fa fa-refresh"></i>
      </el-button>
    </panel-title>
    <el-table
      :data="table_data"
      v-loading="load_data"
      element-loading-text="拼命加载中"
      border
      style="width: 100%;"
      @selection-change="handleSelectionChange">
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
        label="名称"
        width="120">
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
    </el-table>
  </div>
</template>
<script type="text/ecmascript-6">
  import {panelTitle} from 'components'

  export default{
    data()  {
      return {
        load_data: true,
        table_data: null
      }
    },
    created() {
      this.loadData()
    },
    methods: {
      loadData() {
        this.load_data = true
        this.$fetch.api_role.page({
          page: 1,
          length: 100
        })
          .then(({data: {rows, pageNum, total}}) => {
            this.table_data = rows
            this.load_data = false
          })
          .catch(() => {
            this.load_data = false
          })
      },
      handleSelectionChange(val) {
        this.multipleSelection = val
      }
    },
    components: {
      panelTitle
    }
  }
</script>
