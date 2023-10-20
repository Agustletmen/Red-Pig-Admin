"use strict";
var __assign = (this && this.__assign) || function () {
    __assign = Object.assign || function(t) {
        for (var s, i = 1, n = arguments.length; i < n; i++) {
            s = arguments[i];
            for (var p in s) if (Object.prototype.hasOwnProperty.call(s, p))
                t[p] = s[p];
        }
        return t;
    };
    return __assign.apply(this, arguments);
};
var __awaiter = (this && this.__awaiter) || function (thisArg, _arguments, P, generator) {
    function adopt(value) { return value instanceof P ? value : new P(function (resolve) { resolve(value); }); }
    return new (P || (P = Promise))(function (resolve, reject) {
        function fulfilled(value) { try { step(generator.next(value)); } catch (e) { reject(e); } }
        function rejected(value) { try { step(generator["throw"](value)); } catch (e) { reject(e); } }
        function step(result) { result.done ? resolve(result.value) : adopt(result.value).then(fulfilled, rejected); }
        step((generator = generator.apply(thisArg, _arguments || [])).next());
    });
};
var __generator = (this && this.__generator) || function (thisArg, body) {
    var _ = { label: 0, sent: function() { if (t[0] & 1) throw t[1]; return t[1]; }, trys: [], ops: [] }, f, y, t, g;
    return g = { next: verb(0), "throw": verb(1), "return": verb(2) }, typeof Symbol === "function" && (g[Symbol.iterator] = function() { return this; }), g;
    function verb(n) { return function (v) { return step([n, v]); }; }
    function step(op) {
        if (f) throw new TypeError("Generator is already executing.");
        while (_) try {
            if (f = 1, y && (t = op[0] & 2 ? y["return"] : op[0] ? y["throw"] || ((t = y["return"]) && t.call(y), 0) : y.next) && !(t = t.call(y, op[1])).done) return t;
            if (y = 0, t) op = [op[0] & 2, t.value];
            switch (op[0]) {
                case 0: case 1: t = op; break;
                case 4: _.label++; return { value: op[1], done: false };
                case 5: _.label++; y = op[1]; op = [0]; continue;
                case 7: op = _.ops.pop(); _.trys.pop(); continue;
                default:
                    if (!(t = _.trys, t = t.length > 0 && t[t.length - 1]) && (op[0] === 6 || op[0] === 2)) { _ = 0; continue; }
                    if (op[0] === 3 && (!t || (op[1] > t[0] && op[1] < t[3]))) { _.label = op[1]; break; }
                    if (op[0] === 6 && _.label < t[1]) { _.label = t[1]; t = op; break; }
                    if (t && _.label < t[2]) { _.label = t[2]; _.ops.push(op); break; }
                    if (t[2]) _.ops.pop();
                    _.trys.pop(); continue;
            }
            op = body.call(thisArg, _);
        } catch (e) { op = [6, e]; y = 0; } finally { f = t = 0; }
        if (op[0] & 5) throw op[1]; return { value: op[0] ? op[1] : void 0, done: true };
    }
};
exports.__esModule = true;
exports.http = void 0;
var axios_1 = require("axios");
var qs_1 = require("qs");
var progress_1 = require("../progress");
var auth_1 = require("@/utils/auth");
var user_1 = require("@/store/modules/user");
// 相关配置请参考：www.axios-js.com/zh-cn/docs/#axios-request-config-1
var defaultConfig = {
    // 请求超时时间
    timeout: 10000,
    headers: {
        Accept: "application/json, text/plain, */*",
        "Content-Type": "application/json",
        "X-Requested-With": "XMLHttpRequest"
    },
    // 数组格式参数序列化（https://github.com/axios/axios/issues/5142）
    paramsSerializer: {
        serialize: qs_1.stringify
    }
};
var PureHttp = /** @class */ (function () {
    function PureHttp() {
        this.httpInterceptorsRequest();
        this.httpInterceptorsResponse();
    }
    /** 重连原始请求 */
    PureHttp.retryOriginalRequest = function (config) {
        return new Promise(function (resolve) {
            PureHttp.requests.push(function (token) {
                config.headers["Authorization"] = auth_1.formatToken(token);
                resolve(config);
            });
        });
    };
    /** 请求拦截 */
    PureHttp.prototype.httpInterceptorsRequest = function () {
        var _this = this;
        PureHttp.axiosInstance.interceptors.request.use(function (config) { return __awaiter(_this, void 0, Promise, function () {
            var whiteList;
            return __generator(this, function (_a) {
                // 开启进度条动画
                progress_1["default"].start();
                // 优先判断post/get等方法是否传入回调，否则执行初始化设置等回调
                if (typeof config.beforeRequestCallback === "function") {
                    config.beforeRequestCallback(config);
                    return [2 /*return*/, config];
                }
                if (PureHttp.initConfig.beforeRequestCallback) {
                    PureHttp.initConfig.beforeRequestCallback(config);
                    return [2 /*return*/, config];
                }
                whiteList = ["/refreshToken", "/login"];
                return [2 /*return*/, whiteList.some(function (v) { return config.url.indexOf(v) > -1; })
                        ? config
                        : new Promise(function (resolve) {
                            var data = auth_1.getToken();
                            if (data) {
                                var now = new Date().getTime();
                                var expired = parseInt(data.expires) - now <= 0;
                                if (expired) {
                                    if (!PureHttp.isRefreshing) {
                                        PureHttp.isRefreshing = true;
                                        // token过期刷新
                                        user_1.useUserStoreHook()
                                            .handRefreshToken({ refreshToken: data.refreshToken })
                                            .then(function (res) {
                                            var token = res.data.accessToken;
                                            config.headers["Authorization"] = auth_1.formatToken(token);
                                            PureHttp.requests.forEach(function (cb) { return cb(token); });
                                            PureHttp.requests = [];
                                        })["finally"](function () {
                                            PureHttp.isRefreshing = false;
                                        });
                                    }
                                    resolve(PureHttp.retryOriginalRequest(config));
                                }
                                else {
                                    config.headers["Authorization"] = auth_1.formatToken(data.accessToken);
                                    resolve(config);
                                }
                            }
                            else {
                                resolve(config);
                            }
                        })];
            });
        }); }, function (error) {
            return Promise.reject(error);
        });
    };
    /** 响应拦截 */
    PureHttp.prototype.httpInterceptorsResponse = function () {
        var instance = PureHttp.axiosInstance;
        instance.interceptors.response.use(function (response) {
            var $config = response.config;
            // 关闭进度条动画
            progress_1["default"].done();
            // 优先判断post/get等方法是否传入回调，否则执行初始化设置等回调
            if (typeof $config.beforeResponseCallback === "function") {
                $config.beforeResponseCallback(response);
                return response.data;
            }
            if (PureHttp.initConfig.beforeResponseCallback) {
                PureHttp.initConfig.beforeResponseCallback(response);
                return response.data;
            }
            return response.data;
        }, function (error) {
            var $error = error;
            $error.isCancelRequest = axios_1["default"].isCancel($error);
            // 关闭进度条动画
            progress_1["default"].done();
            // 所有的响应异常 区分来源为取消请求/非取消请求
            return Promise.reject($error);
        });
    };
    /** 通用请求工具函数 */
    PureHttp.prototype.request = function (method, url, param, axiosConfig) {
        var config = __assign(__assign({ method: method,
            url: url }, param), axiosConfig);
        // 单独处理自定义请求/响应回调
        return new Promise(function (resolve, reject) {
            PureHttp.axiosInstance
                .request(config)
                .then(function (response) {
                resolve(response);
            })["catch"](function (error) {
                reject(error);
            });
        });
    };
    /** 单独抽离的post工具函数 */
    PureHttp.prototype.post = function (url, params, config) {
        return this.request("post", url, params, config);
    };
    /** 单独抽离的get工具函数 */
    PureHttp.prototype.get = function (url, params, config) {
        return this.request("get", url, params, config);
    };
    /** token过期后，暂存待执行的请求 */
    PureHttp.requests = [];
    /** 防止重复刷新token */
    PureHttp.isRefreshing = false;
    /** 初始化配置对象 */
    PureHttp.initConfig = {};
    /** 保存当前Axios实例对象 */
    PureHttp.axiosInstance = axios_1["default"].create(defaultConfig);
    return PureHttp;
}());
exports.http = new PureHttp();
