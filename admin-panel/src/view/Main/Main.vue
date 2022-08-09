<template>
    <el-container>
        <el-aside width="200px">
            <el-scrollbar>
                <el-menu router class="left_menu" @open="handleOpen" @close="handleClose">
                    <el-menu-item index="home">
                        <el-icon>
                            <Menu/>
                        </el-icon>
                        <span>菜单管理</span>
                    </el-menu-item>
                    <el-menu-item index="about">
                        <el-icon>
                            <document/>
                        </el-icon>
                        <span>文章管理</span>
                    </el-menu-item>
                </el-menu>
            </el-scrollbar>
        </el-aside>
        <el-container>
            <!--            <el-header style="text-align: right; font-size: 12px">-->
            <!--                <div class="toolbar">-->
            <!--                    <el-dropdown>-->
            <!--                        <el-icon style="margin-right: 8px; margin-top: 1px">-->
            <!--                            <Avatar/>-->
            <!--                        </el-icon>-->
            <!--                        <template #dropdown>-->
            <!--                            <el-dropdown-menu>-->
            <!--                                <el-dropdown-item>View</el-dropdown-item>-->
            <!--                                <el-dropdown-item>Add</el-dropdown-item>-->
            <!--                                <el-dropdown-item>Delete</el-dropdown-item>-->
            <!--                            </el-dropdown-menu>-->
            <!--                        </template>-->
            <!--                    </el-dropdown>-->
            <!--                    <span>Tom</span>-->
            <!--                </div>-->
            <!--            </el-header>-->
            <el-main>
                <router-view></router-view>
            </el-main>
        </el-container>
    </el-container>
</template>

<script>
export default {
    name: "Main",
    setup() {

        function handleOpen() {
        }

        function handleClose() {

        }

        return {
            handleOpen,
            handleClose
        }

    }
}
</script>

<style scoped>

.header-bar {
    display: flex;
    width: 100%;
    height: 40px;
    background: linear-gradient(180deg, #009982, #008671)
}

.left_menu {
    height: 100vh;
}

.el-main::-webkit-scrollbar {
    display: none; /* Chrome Safari */
}
</style>
