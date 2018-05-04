<template>
  <div class="panel">
    <panel-title :title="$route.meta.title"></panel-title>
    <div class="panel-body">
      <el-upload
        class="upload-demo"
        action="http://127.0.0.1:8080/api/file/batch/upload"
        :with-credentials=true
        :on-remove="handleRemove"
        :on-preview="handlePreview"
        :on-success="handleSuccess"
        :file-list="fileList2"
        list-type="picture">
        <el-button size="small" type="primary">点击上传</el-button>
        <div slot="tip" class="el-upload__tip">只能上传jpg/png文件，且不超过500kb</div>
      </el-upload>
    </div>
  </div>
</template>
<script type="text/ecmascript-6">
  import {panelTitle} from 'components'

  export default{
    data() {
      return {
        form:{
          name:''
        },
        fileList2: [{
          name: 'food.jpeg',
          url: 'https://fuss10.elemecdn.com/3/63/4e7f3a15429bfda99bce42a18cdd1jpeg.jpeg?imageMogr2/thumbnail/360x360/format/webp/quality/100',
          status: 'finished'
        }, {
          name: 'food2.jpeg',
          url: 'https://fuss10.elemecdn.com/3/63/4e7f3a15429bfda99bce42a18cdd1jpeg.jpeg?imageMogr2/thumbnail/360x360/format/webp/quality/100',
          status: 'finished'
        }]
      };
    },
    methods: {
      handleRemove(file, fileList) {
        console.log(file, fileList);
      },
      handlePreview(file) {
        console.log("预览")
        console.log( file.response);
      },
      handleSuccess(response, file, fileList){
        let item = {}
        if(response.status){
          item.name = "1.jpg";
          item.url = "http://127.0.0.1:8080/api/file/download/7543D36080A66B267B121B3D1657C088"
          item.status = "finished"
          this.fileList2.push(item)
        }
      }
    },
    components: {
      panelTitle
    }
  }
</script>
