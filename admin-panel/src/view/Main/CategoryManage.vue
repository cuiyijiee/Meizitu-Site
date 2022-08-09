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

<script>

import {onMounted, reactive, toRefs, ref, nextTick} from "vue";
import {listCategory} from "@/api"
import MyLoading from "@/utils/MyLoading";

export default {
    name: "CategoryManage",
    setup() {
        const state = reactive({
            query: "",
            categoryData: [],
            total: 0, // 总条数
            current: 1, // 当前页
            pageSize: 10, // 分页大小
        });

        onMounted(() => {
            handleGetCategory();
        })

        let tableRef = ref(null)

        function handleGetCategory() {
            nextTick(() => {
                MyLoading.showLoading(tableRef.value);
            })
            listCategory(state.current, state.pageSize, state.query).then(resp => {
                state.pageSize = resp.obj.pageSize;
                state.current = resp.obj.current;
                state.total = resp.obj.total;
                state.categoryData = resp.obj.data;
            }).finally(() => {
                nextTick(() => {
                    MyLoading.hideLoading();
                })
            })
        }

        function handleEdit(index, row) {

        }

        function handleDelete(index, row) {

        }

        function handlePageSizeChange(size) {
            state.pageSize = size;
            handleGetCategory();
        }

        function handleCurrentChange(num) {
            state.current = num;
            handleGetCategory();
        }

        return {
            ...toRefs(state),
            tableRef,
            handleEdit,
            handleDelete,
            handlePageSizeChange,
            handleCurrentChange
        }
    }
}
</script>

<style scoped>

</style>
