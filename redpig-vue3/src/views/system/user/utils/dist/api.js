"use strict";
exports.__esModule = true;
exports.getDeptList = exports.getRoleList = exports.saveOrUpdate = exports.delById = exports.getUserList = void 0;
var http_1 = require("@/utils/http");
exports.getUserList = function (data) {
    return http_1.http.get("/user/page", data);
};
exports.delById = function (data) {
    return http_1.http.post("/user/delById", { data: data });
};
exports.saveOrUpdate = function (data) {
    return http_1.http.post("/user/saveOrUpdate", { data: data });
};
/** 获取角色管理列表 */
exports.getRoleList = function (data) {
    return http_1.http.post("/role", { data: data });
};
/** 获取部门管理列表 */
exports.getDeptList = function (data) {
    return http_1.http.get("/dept/getDeptList", { data: data });
};
