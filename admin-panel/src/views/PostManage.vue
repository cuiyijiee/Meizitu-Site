<template>
  <div ref="tableRef">
    <div>
      <el-row :gutter="20">
        <el-col :span="6">
          <el-input v-model="query" class="w-50 m-2" placeholder="请输入关键字" clearable/>
        </el-col>
        <el-col :span="12">
          <el-select v-model="orderBy" class="m-2" placeholder="请选择排序条件">
            <el-option v-for="item in orderByOption" :key="item.value" :label="item.label"
                       :value="item.value"/>
          </el-select>
          <el-select v-model="queryCategory" class="m-2" placeholder="请选择分类" clearable>
            <el-option v-for="item in categoryData" :key="item.id" :label="item.name"
                       :value="item.id"/>
          </el-select>
        </el-col>
        <el-col :span="6">
          <el-button type="primary" @click="handleListAlbum">查询</el-button>
          <el-button type="primary" @click="handleAddAlbum">新增</el-button>
        </el-col>
      </el-row>
    </div>
    <el-table :data="albumData" style="width: 100%">
      <el-table-column label="ID" prop="id" width="80"/>
      <el-table-column align="light" width="120" label="原始ID">
        <template #default="scope">
          <a :href="scope.row.albumUrl" target="_blank">{{ scope.row.originId }}</a>
        </template>
      </el-table-column>
      <el-table-column align="light" width="120" label="分类">
        <template #default="scope">
          {{ categoryData.filter(item => item.id === scope.row.category)[0].name }}
        </template>
      </el-table-column>
      <el-table-column label="标题" prop="title" show-overflow-tooltip/>
      <el-table-column label="创建时间" prop="createdAt" width="250"/>
      <el-table-column label="查看次数" prop="viewNum" width="80"/>
      <el-table-column label="图片数量" prop="pictureCount" width="80"/>
      <el-table-column align="right" label="操作" width="100">
        <template #default="scope">
          <el-button size="small" @click="handleOpenAlbumEdit(scope.$index, scope.row)">
            编辑
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
    <AlbumEdit ref="editDrawerRef"></AlbumEdit>
    <AlbumAdd ref="addDrawerRef"></AlbumAdd>
    <el-backtop :right="100" :bottom="100"/>
  </div>
</template>

<script>
import {onMounted, reactive, toRefs, ref, nextTick} from "vue";
import {listAlbum, listCategory} from "../api";

import AlbumEdit from "../components/AlbumEdit.vue";
import AlbumAdd from "../components/AlbumAdd.vue";

import GlobalLoading from "../utils/GlobalLoading";

const orderByOption = reactive([
  {value: "createdAtAsc", label: "创建时间正序"},
  {value: "createdAtDesc", label: "创建时间倒序"},
  {value: "viewNumAsc", label: "浏览次数正序"},
  {value: "viewNumDesc", label: "浏览次数倒序"},
])

export default {
  name: "PostManage",
  components: {
    AlbumEdit, AlbumAdd
  },
  setup() {
    const state = reactive({
      query: "",
      orderBy: "createdAtDesc",
      queryCategory: "",

      current: 0,
      pageSize: 10,
      total: 0,
      categoryData: [],
      albumData: [],
      showCover: false
    })
    const tableRef = ref(null);
    const editDrawerRef = ref(null);
    const addDrawerRef = ref(null);

    onMounted(() => {
      nextTick(() => {
        GlobalLoading.showLoading(tableRef.value);
      })
      listCategory(0, 100, "").then(resp => {
        nextTick(() => {
          GlobalLoading.hideLoading();
        })
        state.categoryData = resp.data.obj.data;
        handleListAlbum();
      }).catch(() => {
        nextTick(() => {
          GlobalLoading.hideLoading();
        })
      })
    })

    function handleListAlbum() {
      nextTick(() => {
        GlobalLoading.showLoading(tableRef.value);
      })
      listAlbum(state.current, state.pageSize, state.query, state.queryCategory, state.orderBy).then(resp => {
        state.pageSize = resp.data.obj.pageSize;
        state.current = resp.data.obj.current;
        state.total = resp.data.obj.total;
        state.albumData = resp.data.obj.data;
      }).finally(() => {
        nextTick(() => {
          GlobalLoading.hideLoading();
        })
      })
    }

    function handleAddAlbum() {
      addDrawerRef.value.open();
    }

    function handlePageSizeChange(size) {
      state.pageSize = size;
      handleListAlbum();
    }

    function handleCurrentChange(num) {
      state.current = num;
      handleListAlbum();
    }

    function handleOpenAlbumEdit(index, row) {
      editDrawerRef.value.open(row.id);
    }

    return {
      ...toRefs(state),
      orderByOption,
      tableRef,
      editDrawerRef,
      addDrawerRef,
      handleListAlbum,
      handleAddAlbum,
      handlePageSizeChange,
      handleCurrentChange,
      handleOpenAlbumEdit,
    }
  }
}
</script>

<style scoped>

</style>
