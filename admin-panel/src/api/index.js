import service from "@/api/request";

export function listCategory(current, pageSize, query) {
    return service({
        url: "admin/listCategory",
        method: "post",
        data: {
            current,
            pageSize,
            query
        }
    })
}
