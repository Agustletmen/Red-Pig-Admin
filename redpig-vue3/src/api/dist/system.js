"use strict";
exports.__esModule = true;
exports.getRoleList = exports.getUserList = void 0;
var http_1 = require("@/utils/http");
/** 获取用户管理列表 */
exports.getUserList = function (data) {
    return http_1.http.post("/user/lists", { data: data });
};
/** 获取角色管理列表 */
exports.getRoleList = function (data) {
    return http_1.http.post("/role", { data: data });
};
