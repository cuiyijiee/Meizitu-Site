import service from "@/api/request";

const BASE_URL = "/admin-api";

export function listCategory(current, pageSize, query) {
    return service({
        url: BASE_URL + "/category/list",
        method: "post",
        data: {
            current,
            pageSize,
            query
        }
    })
}

export function listAlbum(current, pageSize, query, category, orderBy) {
    return service({
        url: BASE_URL + "/album/list",
        method: "post",
        data: {
            current,
            pageSize,
            query,
            category,
            orderBy
        }
    })
}

export function albumDetail(id) {
    return service({
        url: BASE_URL + "/album/detail",
        method: "post",
        data: {
            id
        }
    })
}

export function uploadFile(md5, file) {
    const formData = new FormData();
    formData.append("file", file);
    formData.append("md5", md5);
    return service.post(BASE_URL + "/album/uploadFile", formData, {
        headers: {
            'Content-Type': 'multipart/form-data'
        }
    })
}
