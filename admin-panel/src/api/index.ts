import request from '../utils/request';

const BASE_URL = "/admin-api";

export const fetchData = () => {
    return request({
        url: './table.json',
        method: 'get'
    });
};


export const listCategory = (current: Number, pageSize: Number, query: String) => {
    return request({
        url: BASE_URL + "/category/list",
        method: "post",
        data: {
            current,
            pageSize,
            query
        }
    })
}

export const listAlbum = (current: Number, pageSize: Number, query: String, category: String, orderBy: String) => {
    return request({
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

export const albumDetail = (id: Number) => {
    return request({
        url: BASE_URL + "/album/detail",
        method: "post",
        data: {
            id
        }
    })
}

export const uploadFile = (md5: String, file: File) => {
    const formData = new FormData();
    formData.append("file", file);
    formData.append("md5", String(md5));
    return request.post(BASE_URL + "/album/uploadFile", formData, {
        headers: {
            'Content-Type': 'multipart/form-data'
        }
    })
}