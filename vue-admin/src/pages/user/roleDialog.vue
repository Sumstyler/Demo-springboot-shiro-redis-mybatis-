<template>
  <el-dialog title="角色关联" :visible.sync="roleDialogVisible"  width="30%">
    <span>test</span>
    <span slot="footer" class="dialog-footer">
      <el-button @click="up">取消</el-button>
       <el-button type="primary" @click="up">确定</el-button>
    </span>
  </el-dialog>
</template>
<script type="text/ecmascript-6">
  export default{
    name: 'roleDialog',
    props: {
      roleDialogVisible: {
        type: Boolean
      }
    },
    data : function(){
      return {
        dialogVisible: false
      }
    },
    methods: {
      up() {
        this.$emit('upup',this.dialogVisible)
      }
    }
  }
</script>
