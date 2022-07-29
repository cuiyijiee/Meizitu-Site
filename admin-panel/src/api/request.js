import axios from "axios";
import {getCurrentInstance} from 'vue'
import {ElMessage} from "element-plus"

const service = axios.create({
    baseURL: process.env.VUE_APP_BASE_API,
    timeout: 15000
})


service.interceptors.response.use(
    response => {
        const myResponse = response.data
        if (myResponse.code === "0") {
            return myResponse;
        } else {
            const {appContext} = getCurrentInstance();
            ElMessage({
                message: myResponse.message || 'Error',
                type: 'error',
                duration: 5 * 1000
            }, appContext);
        }
    }
)

export default service;
