<template>
    <div>
        <h1>系统登录</h1>
        <el-form label-position="top" :model="ruleForm" ref="loginForm">
            <el-form-item label="账号" prop="username">
                <el-input type="text" v-model.trim="ruleForm.username" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="密码" prop="password">
                <el-input type="password" v-model.trim="ruleForm.password" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item>
                <div style="color: #333">登录表示您已同意<a>《服务条款》</a></div>
                <el-button style="width: 100%" type="primary" @click="submitLoginForm">立即登录</el-button>
                <el-checkbox v-model="checked" @change="!checked">下次自动登录</el-checkbox>
            </el-form-item>
        </el-form>

    </div>
</template>
<script>
import {useRouter} from 'vue-router';
import {ref, reactive, toRefs} from "vue";

export default {
    name: "Login",
    setup() {
        const router = useRouter();

        function submitLoginForm() {
            router.push("/")
        }

        const loginForm = ref(null)
        const state = reactive({
            ruleForm: {
                username: "",
                password: ""
            },
            checked: false
        })

        return {
            ...toRefs(state),
            loginForm,
            submitLoginForm
        }
    }
}
</script>

<style scoped>

</style>
