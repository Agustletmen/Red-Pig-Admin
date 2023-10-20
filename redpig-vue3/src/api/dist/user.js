"use strict";
exports.__esModule = true;
exports.refreshTokenApi = exports.getLogin = void 0;
var http_1 = require("@/utils/http");
/** 登录 */
exports.getLogin = function (data) {
    return http_1.http.request("post", "/api/login", { data: data });
};
/** 刷新token */
exports.refreshTokenApi = function (data) {
    return http_1.http.request("post", "/refreshToken", { data: data });
};
