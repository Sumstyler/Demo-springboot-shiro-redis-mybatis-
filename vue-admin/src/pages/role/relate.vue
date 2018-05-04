<template>
  <div class="panel">
    <panel-title :title="$route.meta.title">
      <el-button @click.stop="on_refresh" size="small">
        <i class="fa fa-refresh"></i>
      </el-button>
    </panel-title>
    <div class="panel-body">
      <el-tree
        :data="data"
        show-checkbox
        ref="tree"
        node-key="id"
        default-expand-all
        check-strictly
        highlight-current
        :props="defaultProps">
      </el-tree>
      <bottom-tool-bar>
        <el-button  size="small" slot="handler" @click="checkedAll"><span>全选</span></el-button>
        <el-button  size="small" slot="handler" @click="resetChecked"><span>清空</span></el-button>
        <el-button  size="small" slot="handler" @click="loadData"><span>重置</span></el-button>
        <el-button  size="small" slot="handler" @click="cancelChecked"><span>取消</span></el-button>
        <el-button  size="small" slot="handler" @click="submit_checked"><span>确认</span></el-button>
      </bottom-tool-bar>
    </div>
  </div>
</template>
<script type="text/ecmascript-6">
  import {panelTitle, bottomToolBar} from 'components'

  export default{
    data()  {
      return {
        route_id: this.$route.params.id,
        data: [],
        defaultProps: {
          children: 'children',
          label: 'label'
        }
      }
    },
    created() {
      this.loadData()
    },
    methods: {
      loadData() {
        this.$fetch.api_role.relate({
          id: this.route_id
        })
        .then(({data:{tree,checks}}) => {
          this.data = [tree]
          setTimeout(this.$refs.tree.setCheckedKeys(checks), 500)
        })
        .catch(() => {
        })
      },
      checkedAll() {
        this.$refs.tree.setCheckedKeys([1])
      },
      cancelChecked() {
        setTimeout(this.$router.back(), 500)
      },
      resetChecked() {
        this.$refs.tree.setCheckedKeys([])
      },
      submit_checked() {
        let permissionIds = this.$refs.tree.getCheckedKeys()
        this.$fetch.api_role.submit_relate({
          id: this.route_id,
          permissionIds: permissionIds
        })
        .then(({msg}) => {
          this.$message.success(msg)
          setTimeout(this.$router.back(), 500)
        })
        .catch(() => {
        })
      }
    },
    components: {
      panelTitle,
      bottomToolBar
    }
  }
</script>
