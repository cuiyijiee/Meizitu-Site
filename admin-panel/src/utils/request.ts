import axios, {AxiosInstance, AxiosError, AxiosResponse, AxiosRequestConfig} from 'axios';
import {getCurrentInstance} from "vue";
import {ElMessage} from "element-plus";
import {ComponentInternalInstance} from "@vue/runtime-core";

const service: AxiosInstance = axios.create({
    timeout: 5000
});

service.interceptors.request.use(
    (config: AxiosRequestConfig) => {
        return config;
    },
    (error: AxiosError) => {
        console.log(error);
        return Promise.reject();
    }
);

service.interceptors.response.use(
    (response: AxiosResponse) => {
        if (response.status === 200) {
            const myResponse = response.data
            if (myResponse.code === "0") {
                return response;
            } else {
                const currentInstance: ComponentInternalInstance | null = getCurrentInstance();
                ElMessage({
                    message: myResponse.message || 'Error',
                    type: 'error',
                    duration: 5 * 1000
                }, currentInstance?.appContext);
                Promise.reject();
            }
        } else {
            Promise.reject();
        }
    },
    (error: AxiosError) => {
        console.log(error);
        return Promise.reject();
    }
);

export default service;
