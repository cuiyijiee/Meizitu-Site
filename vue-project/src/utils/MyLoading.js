import {ElLoading} from "element-plus";

let loading;

export default {
    showLoading(el) {
        loading = ElLoading.service({
            target: el,
            lock: true,
            fullscreen: true
        })
    },
    hideLoading() {
        loading.close();
    }
}

