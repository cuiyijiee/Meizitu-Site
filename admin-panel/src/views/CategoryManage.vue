<template>
  <div ref="tableRef">
    <el-table :data="categoryData" style="width: 100%">
      <el-table-column label="ID" prop="id"/>
      <el-table-column label="目录名称" prop="name"/>
      <el-table-column label="展示顺序" prop="showOrder"/>
      <el-table-column label="文章数量" prop="albumCount"/>
      <el-table-column label="创建时间" prop="createdAt"/>
      <el-table-column align="right">
        <template #default="scope">
          <el-button size="small" @click="handleEdit(scope.$index, scope.row)">编辑
          </el-button>
          <el-button size="small" type="danger" @click="handleDelete(scope.$index, scope.row)">
            删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination
        style="position: absolute;right:0;padding: 20px"
        v-model:currentPage="current"
        v-model:page-size="pageSize"
        :page-sizes="[10, 20, 50, 100]"
        :background="true"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total"
        @size-change="handlePageSizeChange"
        @current-change="handleCurrentChange"
    />
  </div>
</template>

<script lang="ts" setup name="CategoryManage">

import {onMounted, ref, nextTick} from "vue";
import {listCategory} from "../api"
import GlobalLoading from "../utils/GlobalLoading";

interface CategoryItem {
  id: number;
  name: string;
  showOrder: number;
  createdAt: string;
  albumCount: number;
}

const categoryData = ref<CategoryItem[]>([]);

let query: String = "";
let total = 0; // 总条数
let current = 1; // 当前页
let pageSize = 10; // 分页大小

onMounted(() => {
  handleGetCategory();
})

let tableRef = ref<any>()

function handleGetCategory() {
  nextTick(() => {
    GlobalLoading.showLoading(tableRef.value);
  })
  listCategory(current, pageSize, query).then(resp => {
    pageSize = resp.data.obj.pageSize;
    current = resp.data.obj.current;
    total = resp.data.obj.total;
    categoryData.value = resp.data.obj.data;
  }).catch(e => {
    console.log("request exist error:" + e);
  }).finally(() => {
    nextTick(() => {
      GlobalLoading.hideLoading();
    })
  })
}

function handleEdit(index: Number, row: number) {

}

function handleDelete(index: Number, row: number) {

}

function handlePageSizeChange(size: number) {
  pageSize = size;
  handleGetCategory();
}

function handleCurrentChange(num: number) {
  current = num;
  handleGetCategory();
}
</script>

<style scoped>

</style>
