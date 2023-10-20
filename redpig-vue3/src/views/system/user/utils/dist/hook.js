"use strict";
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
exports.useUser = void 0;
var dayjs_1 = require("dayjs");
var add_vue_1 = require("../add.vue");
var message_1 = require("@/utils/message");
var api_1 = require("./api");
var element_plus_1 = require("element-plus");
var vue_1 = require("vue");
var ReDialog_1 = require("@/components/ReDialog");
function useUser() {
    var form = vue_1.reactive({
        username: "",
        mobile: "",
        enabled: null,
        time: "",
        deptId: ""
    });
    var formRef = vue_1.ref();
    var dataList = vue_1.ref([]);
    var loading = vue_1.ref(true);
    var switchLoadMap = vue_1.ref({});
    var pagination = vue_1.reactive({
        total: 0,
        pageSize: 10,
        currentPage: 1,
        background: true
    });
    var columns = [
        {
            label: "序号",
            type: "index",
            width: 70,
            fixed: "left"
        },
        {
            label: "用户编号",
            prop: "id",
            minWidth: 130
        },
        {
            label: "用户名称",
            prop: "username",
            minWidth: 130
        },
        {
            label: "用户昵称",
            prop: "nickname",
            minWidth: 130
        },
        {
            label: "性别",
            prop: "sex",
            minWidth: 90,
            cellRenderer: function (_a) {
                var row = _a.row, props = _a.props;
                return (React.createElement("el-tag", { size: props.size, type: row.sex === 0 ? "danger" : "", effect: "plain" }, row.sex === 0 ? "女" : "男"));
            }
        },
        {
            label: "部门",
            prop: "dept",
            minWidth: 90,
            formatter: function (_a) {
                var dept = _a.dept;
                return dept.deptName;
            }
        },
        {
            label: "手机号码",
            prop: "mobile",
            minWidth: 90
        },
        {
            label: "状态",
            prop: "enabled",
            minWidth: 90,
            cellRenderer: function (scope) {
                var _a;
                return (React.createElement("el-switch", { size: scope.props.size === "small" ? "small" : "default", loading: (_a = switchLoadMap.value[scope.index]) === null || _a === void 0 ? void 0 : _a.loading, "v-model": scope.row.enabled, "active-value": true, "inactive-value": false, "active-text": "\u5DF2\u5F00\u542F", "inactive-text": "\u5DF2\u5173\u95ED", "inline-prompt": true, onChange: function () { return onChange(scope); } }));
            }
        },
        {
            label: "创建时间",
            minWidth: 90,
            prop: "createTime",
            formatter: function (_a) {
                var createTime = _a.createTime;
                return dayjs_1["default"](createTime).format("YYYY-MM-DD HH:mm:ss");
            }
        },
        {
            label: "操作",
            fixed: "right",
            width: 180,
            slot: "operation"
        }
    ];
    var buttonClass = vue_1.computed(function () {
        return [
            "!h-[20px]",
            "reset-margin",
            "!text-gray-500",
            "dark:!text-white",
            "dark:hover:!text-primary"
        ];
    });
    function onChange(_a) {
        var row = _a.row, index = _a.index;
        element_plus_1.ElMessageBox.confirm("\u786E\u8BA4\u8981<strong>" + (row.enabled === false ? "停用" : "启用") + "</strong><strong style='color:var(--el-color-primary)'>" + row.username + "</strong>\u7528\u6237\u5417?", "系统提示", {
            confirmButtonText: "确定",
            cancelButtonText: "取消",
            type: "warning",
            dangerouslyUseHTMLString: true,
            draggable: true
        })
            .then(function () {
            api_1.saveOrUpdate(row).then(function () {
                switchLoadMap.value[index] = Object.assign({}, switchLoadMap.value[index], {
                    loading: true
                });
                setTimeout(function () {
                    switchLoadMap.value[index] = Object.assign({}, switchLoadMap.value[index], {
                        loading: false
                    });
                    message_1.message("已成功修改用户状态", {
                        type: "success"
                    });
                }, 300);
            });
        })["catch"](function () {
            row.enabled === false ? (row.enabled = true) : (row.enabled = false);
        });
    }
    function handleUpdate(row) {
        ({ id: row.id });
    }
    function handleDelete(row) {
        api_1.delById({ id: row.id }).then(function () {
            onSearch();
        });
    }
    function handleSizeChange(val) {
        pagination.currentPage = 1;
        pagination.pageSize = val;
        onSearch();
    }
    function handleCurrentChange(val) {
        console.log(val);
        pagination.currentPage = val;
        onSearch();
    }
    function handleSelectionChange(val) {
        console.log("handleSelectionChange", val);
    }
    function openDialog(title, row) {
        var _a, _b, _c, _d, _e, _f, _g, _h, _j, _k;
        if (title === void 0) { title = "新增"; }
        ReDialog_1.addDialog({
            title: title + "\u7528\u6237",
            props: {
                formInline: {
                    id: (_a = row === null || row === void 0 ? void 0 : row.id) !== null && _a !== void 0 ? _a : 0,
                    username: (_b = row === null || row === void 0 ? void 0 : row.username) !== null && _b !== void 0 ? _b : "",
                    nickname: (_c = row === null || row === void 0 ? void 0 : row.nickname) !== null && _c !== void 0 ? _c : "",
                    password: (_d = row === null || row === void 0 ? void 0 : row.password) !== null && _d !== void 0 ? _d : "",
                    enabled: (_e = row === null || row === void 0 ? void 0 : row.enabled) !== null && _e !== void 0 ? _e : true,
                    sex: (_f = row === null || row === void 0 ? void 0 : row.sex) !== null && _f !== void 0 ? _f : 0,
                    mobile: (_g = row === null || row === void 0 ? void 0 : row.mobile) !== null && _g !== void 0 ? _g : "",
                    email: (_h = row === null || row === void 0 ? void 0 : row.email) !== null && _h !== void 0 ? _h : "",
                    deptId: (_k = (_j = row === null || row === void 0 ? void 0 : row.dept) === null || _j === void 0 ? void 0 : _j.id) !== null && _k !== void 0 ? _k : 100 //默认选择
                }
            },
            width: "40%",
            draggable: true,
            fullscreenIcon: true,
            closeOnClickModal: false,
            contentRenderer: function () { return vue_1.h(add_vue_1["default"], { ref: formRef }); },
            beforeSure: function (done, _a) {
                var options = _a.options;
                var FormRef = formRef.value.getRef();
                var curData = options.props.formInline;
                function chores() {
                    api_1.saveOrUpdate(curData).then(function () {
                        message_1.message("\u60A8" + title + "\u4E86\u540D\u79F0\u4E3A" + curData.nickname + "\u7684\u8FD9\u6761\u6570\u636E", {
                            type: "success"
                        });
                        done(); // 关闭弹框
                        onSearch(); // 刷新表格数据
                    });
                }
                FormRef.validate(function (valid) {
                    if (valid) {
                        console.log("curData", curData);
                        // 表单规则校验通过
                        if (title === "新增") {
                            // 实际开发先调用新增接口，再进行下面操作
                            chores();
                        }
                        else {
                            // 实际开发先调用编辑接口，再进行下面操作
                            chores();
                        }
                    }
                });
            }
        });
    }
    vue_1.onMounted(function () {
        onSearch();
    });
    function onSearch() {
        return __awaiter(this, void 0, void 0, function () {
            var data;
            return __generator(this, function (_a) {
                switch (_a.label) {
                    case 0:
                        loading.value = true;
                        return [4 /*yield*/, api_1.getUserList({
                                currentPage: pagination.currentPage,
                                pageSize: pagination.pageSize,
                                username: form.username,
                                enabled: form.enabled,
                                mobile: form.mobile,
                                startTime: form.time[0],
                                endTime: form.time[1],
                                deptId: form.deptId
                            })];
                    case 1:
                        data = (_a.sent()).data;
                        dataList.value = data.records;
                        pagination.total = data.total;
                        pagination.pageSize = data.pageSize;
                        pagination.currentPage = data.currentPage;
                        setTimeout(function () {
                            loading.value = false;
                        }, 500);
                        return [2 /*return*/];
                }
            });
        });
    }
    var resetForm = function (formEl) {
        if (!formEl)
            return;
        formEl.resetFields();
        onSearch();
    };
    return {
        form: form,
        loading: loading,
        columns: columns,
        dataList: dataList,
        pagination: pagination,
        buttonClass: buttonClass,
        openDialog: openDialog,
        onSearch: onSearch,
        resetForm: resetForm,
        handleUpdate: handleUpdate,
        handleDelete: handleDelete,
        handleSizeChange: handleSizeChange,
        handleCurrentChange: handleCurrentChange,
        handleSelectionChange: handleSelectionChange
    };
}
exports.useUser = useUser;
