<template>
  <div class="panel">
    <panel-title :title="$route.meta.title"></panel-title>
    <div class="panel-body"
         v-loading="load_data"
         element-loading-text="拼命加载中">
      <el-row>
        <el-col :span="8">
          <el-form ref="form" :model="form" :rules="rules" label-width="100px">
            <el-form-item label="模板名:" prop="name">
              <el-input v-model="form.name" placeholder="请输入内容" style="width: 250px;"></el-input>
            </el-form-item>
            <el-form-item label="导出文件名:" prop="exportName">
              <el-input v-model="form.exportName" placeholder="请输入内容" style="width: 250px;"></el-input>
            </el-form-item>
            <el-form-item label="模板文件:" prop="newFileName">
              <el-upload
                class ="upload-demo"
                :action="upload_url"
                :with-credentials=true
                :on-change="handleChange"
                :before-upload="handleBeforeUpload"
                :on-success="handleSuccess"
                :on-remove="handleRemove"
                :limit = 1
                :file-list="fileList">
                <el-button size="small" type="primary">点击上传</el-button>
                <div slot="tip" class="el-upload__tip">只能上传ftl文件,且大小不超过1M</div>
              </el-upload>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="on_submit_form" :loading="on_submit_loading">确定</el-button>
              <el-button @click="$router.back()">取消</el-button>
            </el-form-item>
          </el-form>
        </el-col>
      </el-row>
    </div>
  </div>
</template>
<script type="text/ecmascript-6">
  import {panelTitle} from 'components'
  import {export_template_url} from 'common/config'
  export default{
    data(){
      return {
        form: {
          name: null,
          newFileName: null
        },
        route_id: this.$route.params.id,
        upload_url:export_template_url,
        load_data: false,
        on_submit_loading: false,
        fileList: [],
        rules: {
          name: [{required: true, message: '模板名称不能为空', trigger: 'blur'}],
          exportName: [{required: true,message: '导出名称不能为空',trigger: 'blur'}],
          newFileName: [{required: true, message: '模板文件不能为空',trigger: 'blur'}]
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
        this.$fetch.api_export.get({
          id: this.route_id
        })
        .then(({data}) => {
          this.form = data
          this.fileList.push({name:data.fileName})
          this.load_data = false
        })
        .catch(() => {
          this.load_data = false
        })
      },
      handleBeforeUpload(file){
        console.log("before upload")
        console.log(file);
        let fileName = file.name;
        let sufixName = fileName.substr(fileName.lastIndexOf(".")+1)
        let fileType = ["ftl"];
        let typeFlag;
        for(let i =0;i<fileType.length;i++){
          if(sufixName == fileType[i]){
            typeFlag = true;
            break;
          }
        }
        let size = file.size;

        if(!typeFlag){
          this.$message.warning("文件后缀名必须为ftl");
          return false;
        }else if(size>1024*1024*1){
          this.$message.warning("文件大小超过限制");
          return false;
        }
        return true
      },
      handleChange(file,fileList){
        this.fileList = fileList;
      },
      //文件上传成功后
      handleSuccess(response, file, fileList){
        let status = response.status;
        if(status){
          let data = response.data;
          this.form.fileName = data.fileName;
          this.form.newFileName = data.newFileName;
        }else{
          this.$message.warning(response.msg);
          fileList.pop()
        }
      },
      handleRemove(file,fileList){
          this.form.fileName = ""
          this.form.newFileName = ""
      },
      //提交
      on_submit_form(){
        this.$refs.form.validate((valid) => {
          if (!valid) return false
          this.on_submit_loading = true
          this.$fetch.api_export.save(this.form)
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
