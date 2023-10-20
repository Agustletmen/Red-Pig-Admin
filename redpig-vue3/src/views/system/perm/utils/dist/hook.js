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
exports.usePerm = void 0;
var add_vue_1 = require("../add.vue");
var message_1 = require("@/utils/message");
var hooks_1 = require("../../hooks");
var ReDialog_1 = require("@/components/ReDialog");
var vue_1 = require("vue");
var api_1 = require("./api");
function usePerm() {
    var form = vue_1.reactive({
        time: ""
    });
    var formRef = vue_1.ref();
    var dataList = vue_1.ref([]);
    var loading = vue_1.ref(true);
    var switchLoadMap = vue_1.ref({});
    var switchStyle = hooks_1.usePublicHooks().switchStyle;
    var pagination = vue_1.reactive({
        total: 0,
        pageSize: 10,
        currentPage: 1,
        background: true
    });
    var columns = [
        {
            label: "主键ID",
            prop: "id"
        },
        {
            label: "创建时间",
            prop: "createTime",
            width: 200
        },
        {
            label: "更新时间",
            prop: "updateTime",
            width: 200
        },
        {
            label: "权限名称",
            prop: "name"
        },
        {
            label: "权限标签",
            prop: "tag"
        },
        {
            label: "创建者",
            prop: "createBy"
        },
        {
            label: "更新者",
            prop: "updateBy"
        },
        {
            label: "备注",
            prop: "remark"
        },
        {
            label: "所属菜单",
            prop: "menuId"
        },
        {
            label: "操作",
            fixed: "right",
            width: 180,
            slot: "operation"
        }
    ];
    function handleDelete(row) {
        api_1.delById({ id: row.id }).then(function () {
            message_1.message("\u60A8\u5220\u9664\u4E86ID\u4E3A" + row.id + "\u7684\u8FD9\u6761\u6570\u636E", { type: "success" });
            onSearch();
        });
    }
    function handleSizeChange(val) {
        pagination.currentPage = 1;
        pagination.pageSize = val;
        onSearch();
    }
    function handleCurrentChange(val) {
        pagination.currentPage = val;
        onSearch();
    }
    function handleSelectionChange(val) {
        console.log("handleSelectionChange", val);
    }
    function onSearch() {
        return __awaiter(this, void 0, void 0, function () {
            var data;
            return __generator(this, function (_a) {
                switch (_a.label) {
                    case 0:
                        loading.value = true;
                        return [4 /*yield*/, api_1.getPageList({
                                currentPage: pagination.currentPage,
                                pageSize: pagination.pageSize,
                                startTime: form.time[0],
                                endTime: form.time[1]
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
    function openDialog(title, row) {
        var _a, _b, _c, _d, _e, _f, _g;
        if (title === void 0) { title = "新增用户权限"; }
        ReDialog_1.addDialog({
            title: title + "\u7528\u6237\u6743\u9650",
            props: {
                formInline: {
                    id: (_a = row === null || row === void 0 ? void 0 : row.id) !== null && _a !== void 0 ? _a : "",
                    name: (_b = row === null || row === void 0 ? void 0 : row.name) !== null && _b !== void 0 ? _b : "",
                    tag: (_c = row === null || row === void 0 ? void 0 : row.tag) !== null && _c !== void 0 ? _c : "",
                    createBy: (_d = row === null || row === void 0 ? void 0 : row.createBy) !== null && _d !== void 0 ? _d : "",
                    updateBy: (_e = row === null || row === void 0 ? void 0 : row.updateBy) !== null && _e !== void 0 ? _e : "",
                    remark: (_f = row === null || row === void 0 ? void 0 : row.remark) !== null && _f !== void 0 ? _f : "",
                    menuId: (_g = row === null || row === void 0 ? void 0 : row.menuId) !== null && _g !== void 0 ? _g : ""
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
                        message_1.message("\u60A8" + title + "\u4E86ID\u4E3A" + curData.id + "\u7684\u8FD9\u6761\u6570\u636E", { type: "success" });
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
    return {
        form: form,
        loading: loading,
        columns: columns,
        dataList: dataList,
        pagination: pagination,
        onSearch: onSearch,
        resetForm: resetForm,
        openDialog: openDialog,
        handleDelete: handleDelete,
        handleSizeChange: handleSizeChange,
        handleCurrentChange: handleCurrentChange,
        handleSelectionChange: handleSelectionChange
    };
}
exports.usePerm = usePerm;
