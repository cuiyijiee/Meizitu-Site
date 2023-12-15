<template>
  <el-drawer v-model="visible" direction="rtl" :close-on-press-escape="true" :close-on-click-modal="false">
    <template #header>
      <h1>新增文章</h1>
    </template>
    <template #default>

      <el-form :model="addForm" label-width="120px">
        <el-form-item label="文章名称">
          <el-input v-model="addForm.name"/>
        </el-form-item>
        <el-form-item label="文章分类">
          <el-select v-model="addForm.categoryRegin" placeholder="请选择分类">
            <el-option label="Zone one" value="shanghai"/>
          </el-select>
        </el-form-item>
        <el-form-item label="文章封面">
          <el-upload
              v-model:file-list="addForm.cover"
              class="upload-demo"
              list-type="picture"
              :limit="1"
              :on-exceed="handleExceedCover"
              :auto-upload="false">
            <template #trigger>
              <el-button type="primary">请选择封面</el-button>
            </template>
            <el-button class="ml-3" type="success" @click="handleUploadCover">
              开始上传
            </el-button>
            <template #tip>
              <div class="el-upload__tip">
                <!--
                上传提示
                -->
              </div>
            </template>
          </el-upload>
        </el-form-item>
        <el-form-item label="文章图片">
          <el-upload
              v-model:file-list="addForm.uploadFileList"
              class="upload-demo"
              multiple
              list-type="picture"
              :auto-upload="false">
            <template #trigger>
              <el-button type="primary">请选择文件</el-button>
            </template>
            <el-button class="ml-3" type="success" @click="handleUploadFile">
              开始上传
            </el-button>
            <template #tip>
              <div class="el-upload__tip">
                <!--
                上传提示
                -->
              </div>
            </template>
          </el-upload>
        </el-form-item>
      </el-form>
      <div>
      </div>
    </template>
  </el-drawer>
</template>

<script>
import {reactive, toRefs} from "vue";
import {genFileId} from 'element-plus'
import {uploadFile} from "@/api";

import BMF from 'browser-md5-file';

export default {
  name: "AlbumEdit",
  setup() {
    const state = reactive({
      visible: false,
      categoryRegin: [],
      addForm: {
        title: '',
        category: '',
        cover: [],
        uploadFileList: []
      },
    })

    const bmf = new BMF();

    const open = () => {
      state.visible = true;
    }

    const close = () => {
      state.visible = false;
    }

    function handleUploadFile() {
      state.addForm.uploadFileList.forEach(fileItem => {
        bmf.md5(
            fileItem.raw,
            (err, md5) => {
              uploadFile(md5, fileItem.raw).then(result => {
                console.log("上传成功：" + result);
                fileItem.status = "success";
              })
            },
            progress => {
              //用来展示上传进度
            },
        );
      })
    }

    function handleUploadCover() {
      console.log(state.addForm.cover);
    }

    function handleExceedCover(files) {
      state.addForm.cover = [];
      const file = files[0];
      file.uid = genFileId();
      state.addForm.cover[0] = file;
    }

    return {
      ...toRefs(state),
      open,
      close,
      handleUploadCover,
      handleExceedCover,
      handleUploadFile
    }
  }
}
</script>

<style scoped>
.avatar-uploader .avatar {
  width: 178px;
  height: 178px;
  display: block;
}
</style>

<style>
.avatar-uploader .el-upload {
  border: 1px dashed var(--el-border-color);
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
  transition: var(--el-transition-duration-fast);
}

.avatar-uploader .el-upload:hover {
  border-color: var(--el-color-primary);
}

.el-icon.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 178px;
  height: 178px;
  text-align: center;
}
</style>