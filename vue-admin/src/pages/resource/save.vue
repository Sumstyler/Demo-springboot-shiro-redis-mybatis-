<template>
  <div class="panel">
    <panel-title :title="$route.meta.title"></panel-title>
    <div class="panel-body"
         v-loading="load_data"
         element-loading-text="拼命加载中">
      <el-row>
        <el-col :span="8">
          <el-form ref="form" :model="form" :rules="rules" label-width="100px">
            <el-form-item label="名称:" prop="name">
              <el-input v-model="form.name" placeholder="请输入内容" style="width: 250px;"></el-input>
            </el-form-item>
            <el-form-item label="类别:" prop="type" >
              <el-radio-group v-model="form.type">
                <el-radio label="MENU">菜单</el-radio>
                <el-radio label="FUN">功能</el-radio>
              </el-radio-group>
            </el-form-item>
            <el-form-item label="路径:" prop="path">
              <el-input v-model="form.path" placeholder="请输入路径" style="width: 250px;"></el-input>
            </el-form-item>
            <el-form-item label="编码:" prop="percode">
              <el-input v-model="form.percode" placeholder="请输入编码" style="width: 250px;"></el-input>
            </el-form-item>
            <el-form-item label="父节点:" prop="parentid">
              <el-input type="parentid" v-model.number="form.parentid" placeholder="请输入父节点Id" style="width: 250px;"></el-input>
            </el-form-item>
            <el-form-item label="序号:" prop="sid">
              <el-input-number
                placeholder="请输入序号"
                :max="100"
                :min="1"
                :value="20"
                :controls="false"
                v-model.number="form.sid"
                style="width: 250px;">
              </el-input-number>
            </el-form-item>
            <el-form-item label="有效标识:" prop="valid">
              <el-radio-group v-model="form.valid">
                <el-radio label="Y">是</el-radio>
                <el-radio label="N">否</el-radio>
              </el-radio-group>
            </el-form-item>

            <el-form-item>
              <el-button type="primary" @click="on_submit_form" :loading="on_submit_loading">立即提交</el-button>
              <el-button @click="$router.back()">取消</el-button>
            </el-form-item>
          </el-form>
        </el-col>
      </el-row>
    </div>
  </div>
</template>
<script type="text/javascript">
  import {panelTitle} from 'components'

  export default{
    data(){
      return {
        form: {
          name: null,
          parentid: 1,
          type: null,
          path: null,
          percode: null,
          valid: "Y",
          sid: 1
        },
        route_id: this.$route.params.id,
        load_data: false,
        on_submit_loading: false,
        rules: {
          name: [{required: true, message: '名称不能为空', trigger: 'blur'}],
          parentid: [{required: true, message: '父节点不能为空', trigger: 'blur'}],
          type: [{required: true, message: '类型不能为空', trigger: 'blur'}],
          percode: [{required: true, message: '路径不能为空', trigger: 'blur'}],
          valid: [{required: true}],
          sid: [{required: true, message: '序号不能为空', trigger: 'blur'}],
        }
      }
    },
    created(){
      this.route_id && this.get_form_data()
    },
    methods: {
      //获取数据
      get_form_data(){
        this.load_data = true
        this.$fetch.api_resource.get({
          id: this.route_id
        })
          .then(({data}) => {
            this.form = data
            this.load_data = false
          })
          .catch(() => {
            this.load_data = false
          })
      },
      //提交
      on_submit_form(){
        this.$refs.form.validate((valid) => {
          if (!valid) return false
          this.on_submit_loading = true
          this.$fetch.api_resource.save(this.form)
            .then(({msg}) => {
              this.$message.success(msg)
              setTimeout(this.$router.back(), 500)
            })
            .catch(() => {
              this.on_submit_loading = false
            })
        })
      }
    },
    components: {
      panelTitle
    }
  }
</script>
