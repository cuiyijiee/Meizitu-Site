import service from "@/api/request";

export function listCategory(current, pageSize, query) {
    return service({
        url: "admin/category/list",
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
        url: "admin/album/list",
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
        url: "admin/album/detail",
        method: "post",
        data: {
            id
        }
    })
}
