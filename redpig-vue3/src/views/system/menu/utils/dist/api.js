"use strict";
exports.__esModule = true;
exports.saveAuth = exports.getSelectedPerms = exports.getSelectedRoles = exports.getPermList = exports.getRoleList = exports.getMenuList = exports.saveOrUpdate = exports.remoteByIds = exports.delById = exports.getPageList = void 0;
var http_1 = require("@/utils/http");
exports.getPageList = function (data) {
    return http_1.http.get("/menu/page", data);
};
exports.delById = function (data) {
    return http_1.http.post("/menu/delById", { data: data });
};
exports.remoteByIds = function (data) {
    return http_1.http.post("/menu/remoteByIds", { data: data });
};
exports.saveOrUpdate = function (data) {
    return http_1.http.post("/menu/saveOrUpdate", { data: data });
};
/** 获取菜单列表 */
exports.getMenuList = function (data) {
    return http_1.http.get("/menu/getMenuList", data);
};
/** 获取角色列表 */
exports.getRoleList = function (data) {
    return http_1.http.get("/role/getRoleList", data);
};
/** 获取权限列表 */
exports.getPermList = function (data) {
    return http_1.http.get("/perm/getPermList", data);
};
exports.getSelectedRoles = function (data) {
    return http_1.http.get("/role/getSelectedRolesByMenuId", data);
};
exports.getSelectedPerms = function (data) {
    return http_1.http.get("/perm/getSelectedPermsByMenuId", data);
};
exports.saveAuth = function (data) {
    console.log(data);
    debugger;
    return http_1.http.post("/menu/saveAuth", { data: data });
};
