<template>
    <el-drawer v-model="visible" direction="rtl" :close-on-press-escape="false" :close-on-click-modal="false">
        <template #header>
            <h4>编辑</h4>
        </template>
        <template #default>
            <div>
                <el-image style="width: 400px; height: 200px" :src="album.coverUrl"
                          :preview-src-list="[album.coverUrl]"
                          fit="contain"/>
                <el-descriptions :title="album.title" border direction="vertical">
                    <el-descriptions-item label="分类">{{ category.name }}</el-descriptions-item>
                    <el-descriptions-item label="创建时间">{{ album.createdAt }}</el-descriptions-item>
                    <el-descriptions-item label="浏览次数">{{ album.viewNum }}</el-descriptions-item>
                </el-descriptions>
                <el-divider content-position="left"><b>图片(共 {{ pictureList.length }} 张)</b></el-divider>

                <el-empty v-show="pictureList.length === 0" description="暂无图片"/>
                <el-image v-for="(picture,index) in pictureList"
                          style="width: 200px; height: 200px"
                          :preview-src-list="pictureList.map(pic => pic.url)"
                          :initial-index="index"
                          :src="picture.url" fit="cover"/>
            </div>
        </template>
        <!--            <template #footer>-->
        <!--                <div style="flex: auto">-->
        <!--                    <el-button @click="cancelClick">cancel</el-button>-->
        <!--                    <el-button type="primary" @click="confirmClick">confirm</el-button>-->
        <!--                </div>-->
        <!--            </template>-->
    </el-drawer>
</template>

<script>
import {reactive, ref, toRefs} from "vue";
import {albumDetail} from "@/api";

export default {
    name: "AlbumEdit",
    setup() {
        const state = reactive({
            visible: false,
            id: '',
            album: {},
            category: {},
            pictureList: [],
        })


        const open = (id) => {
            state.visible = true;
            state.id = id;
            albumDetail(id).then(resp => {
                console.log(resp)
                state.album = resp.obj.album;
                state.category = resp.obj.category;
                state.pictureList = resp.obj.pictureList;
            })
        }

        const close = () => {
            state.visible = false;
        }

        return {
            ...toRefs(state),
            open,
            close
        }
    }
}
</script>

<style scoped>

</style>
