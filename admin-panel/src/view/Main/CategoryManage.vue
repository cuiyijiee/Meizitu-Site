<template>
    <div>
        <el-table :data="categoryData" style="width: 100%">
            <el-table-column label="ID" prop="id"/>
            <el-table-column label="目录名称" prop="name"/>
            <el-table-column label="展示顺序" prop="showOrder"/>
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
    </div>
</template>

<script>

import {onMounted, reactive, toRefs, ref, watch} from "vue";
import {listCategory} from "@/api"

export default {
    name: "CategoryManage",
    setup() {
        const state = reactive({
            loading: false,
            query: "",
            categoryData: [],
            total: 0, // 总条数
            current: 1, // 当前页
            pageSize: 10, // 分页大小
        });

        onMounted(() => {
            handleGetCategory()
        })

        function handleGetCategory() {
            listCategory(state.current, state.pageSize, state.query).then(resp => {
                state.pageSize = resp.obj.size;
                state.current = resp.obj.current;
                state.total = resp.obj.total;
                state.categoryData = resp.obj.records;
            })
        }

        function handleEdit(index, row) {

        }

        function handleDelete(index, row) {

        }

        return {
            ...toRefs(state),
            handleEdit,
            handleDelete
        }
    }
}
</script>

<style scoped>

</style>
