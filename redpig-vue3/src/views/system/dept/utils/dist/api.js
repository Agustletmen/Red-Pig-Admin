"use strict";
exports.__esModule = true;
exports.getDeptList = exports.saveOrUpdate = exports.delById = exports.getPageList = void 0;
var http_1 = require("@/utils/http");
exports.getPageList = function (data) {
    return http_1.http.get("/dept/page", data);
};
exports.delById = function (data) {
    return http_1.http.post("/dept/delById", { data: data });
};
exports.saveOrUpdate = function (data) {
    return http_1.http.post("/dept/saveOrUpdate", { data: data });
};
/** 获取部门管理列表 */
exports.getDeptList = function (data) {
    return http_1.http.get("/dept/getDeptList", { data: data });
};
