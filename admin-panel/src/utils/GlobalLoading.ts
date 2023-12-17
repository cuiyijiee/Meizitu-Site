import {ElLoading} from "element-plus";

let loading: any

const GlobalLoading = {
    showLoading(el: HTMLElement) {
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

export default GlobalLoading;