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
exports.useMenu = void 0;
var add_vue_1 = require("../add.vue");
var auth_vue_1 = require("../auth.vue");
var message_1 = require("@/utils/message");
var ReDialog_1 = require("@/components/ReDialog");
var vue_1 = require("vue");
var api_1 = require("./api");
var xlsx_1 = require("xlsx");
var iconifyIconOnline_1 = require("@/components/ReIcon/src/iconifyIconOnline");
function useMenu() {
    var form = vue_1.reactive({
        time: "",
        parendId: 0
    });
    var formRef = vue_1.ref();
    var dataList = vue_1.ref([]);
    var loading = vue_1.ref(true);
    var multipleSelection = vue_1.ref([]);
    var single = vue_1.ref(true);
    var multiple = vue_1.ref(true);
    var pagination = vue_1.reactive({
        total: 0,
        pageSize: 10,
        currentPage: 1,
        background: true
    });
    var columns = [
        {
            type: "selection",
            align: "left"
        },
        // {
        //   label: "主键ID",
        //   prop: "id"
        // },
        // {
        //   label: "创建时间",
        //   prop: "createTime",
        //   width: 200
        // },
        {
            label: "更新时间",
            prop: "updateTime",
            width: 160
        },
        {
            label: "标题",
            prop: "title",
            width: 120
        },
        {
            label: "菜单名称",
            prop: "name",
            width: 100
        },
        {
            label: "菜单地址",
            prop: "path",
            width: 200
        },
        {
            label: "组件名",
            prop: "component",
            width: 80
        },
        {
            label: "类型",
            prop: "type",
            width: 100,
            cellRenderer: function (scope) { return (React.createElement(iconifyIconOnline_1["default"], { icon: scope.row.type == 0
                    ? "ph:folder"
                    : scope.row.type == 1
                        ? "ep:menu"
                        : "uil:rss-interface", width: "70px", height: "20px" })); }
        },
        {
            label: "图标",
            prop: "icon",
            width: 100,
            cellRenderer: function (scope) { return (React.createElement(iconifyIconOnline_1["default"], { icon: scope.row.icon, width: "70px", height: "20px" })); }
        },
        {
            label: "链接",
            prop: "showLink",
            width: 100,
            cellRenderer: function (scope) { return (React.createElement("el-switch", { size: scope.props.size === "small" ? "small" : "default", "v-model": scope.row.showLink, "active-value": true, "inactive-value": false, onChange: function () { return api_1.saveOrUpdate(scope.row); }, "active-text": "\u5DF2\u5F00\u542F", "inactive-text": "\u5DF2\u5173\u95ED", "inline-prompt": true })); }
        },
        {
            label: "排序",
            prop: "rank",
            width: 70
        },
        {
            label: "父菜单",
            prop: "parent.title",
            width: 150,
            cellRenderer: function (scope) {
                return scope.row.parentId == 0 ? "顶级菜单" : scope.row.parent.title;
            }
        },
        {
            label: "操作",
            fixed: "right",
            width: 250,
            slot: "operation"
        }
    ];
    function handleDelete(row) {
        api_1.delById({ id: row.id }).then(function () {
            message_1.message("\u60A8\u5220\u9664\u4E86ID\u4E3A" + row.id + "\u7684\u8FD9\u6761\u6570\u636E", { type: "success" });
            onSearch();
        });
    }
    function delByIds() {
        var ids = multipleSelection.value.map(function (item) { return item.id; });
        api_1.remoteByIds(ids).then(function () {
            message_1.message("\u6279\u91CF\u5220\u9664\u6210\u529F", { type: "success" });
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
    function handleSelectionChange(selection) {
        multipleSelection.value = selection;
        single.value = selection.length != 1;
        multiple.value = !selection.length;
    }
    var exportExcel = function () {
        var res = dataList.value.map(function (item) {
            var arr = [];
            columns.forEach(function (column) {
                arr.push(item[column.prop]);
            });
            return arr;
        });
        var titleList = [];
        columns.forEach(function (column) {
            titleList.push(column.label);
        });
        res.unshift(titleList);
        var workSheet = xlsx_1.utils.aoa_to_sheet(res);
        var workBook = xlsx_1.utils.book_new();
        xlsx_1.utils.book_append_sheet(workBook, workSheet, "数据报表");
        xlsx_1.writeFile(workBook, "Menu.xlsx");
        message_1.message("导出成功", {
            type: "success"
        });
    };
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
                                endTime: form.time[1],
                                parendId: form.parendId
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
    var updateRow = function () {
        openDialog("编辑", multipleSelection.value[0]);
    };
    function openDialog(title, row) {
        var _a, _b, _c, _d, _e, _f, _g, _h, _j, _k;
        if (title === void 0) { title = "新增菜单"; }
        ReDialog_1.addDialog({
            title: "" + title,
            props: {
                formInline: {
                    id: (_a = row === null || row === void 0 ? void 0 : row.id) !== null && _a !== void 0 ? _a : "",
                    name: (_b = row === null || row === void 0 ? void 0 : row.name) !== null && _b !== void 0 ? _b : "",
                    path: (_c = row === null || row === void 0 ? void 0 : row.path) !== null && _c !== void 0 ? _c : "",
                    component: (_d = row === null || row === void 0 ? void 0 : row.component) !== null && _d !== void 0 ? _d : "",
                    title: (_e = row === null || row === void 0 ? void 0 : row.title) !== null && _e !== void 0 ? _e : "",
                    type: (_f = row === null || row === void 0 ? void 0 : row.type) !== null && _f !== void 0 ? _f : "",
                    icon: (_g = row === null || row === void 0 ? void 0 : row.icon) !== null && _g !== void 0 ? _g : "",
                    showLink: (_h = row === null || row === void 0 ? void 0 : row.showLink) !== null && _h !== void 0 ? _h : "",
                    rank: (_j = row === null || row === void 0 ? void 0 : row.rank) !== null && _j !== void 0 ? _j : "0",
                    parentId: (_k = row === null || row === void 0 ? void 0 : row.parentId) !== null && _k !== void 0 ? _k : "0"
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
                        message_1.message("\u60A8" + title + "\u4E86ID\u4E3A" + curData.id + "\u7684\u8FD9\u6761\u6570\u636E", {
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
    function openAuthFormDialog(title, row) {
        var _a, _b, _c, _d, _e;
        if (title === void 0) { title = "授权"; }
        ReDialog_1.addDialog({
            title: "" + title,
            props: {
                formInline: {
                    id: (_a = row === null || row === void 0 ? void 0 : row.id) !== null && _a !== void 0 ? _a : "",
                    roles: (_b = row === null || row === void 0 ? void 0 : row.roles) !== null && _b !== void 0 ? _b : [],
                    perms: (_c = row === null || row === void 0 ? void 0 : row.perms) !== null && _c !== void 0 ? _c : [],
                    name: (_d = row === null || row === void 0 ? void 0 : row.name) !== null && _d !== void 0 ? _d : "",
                    title: (_e = row === null || row === void 0 ? void 0 : row.title) !== null && _e !== void 0 ? _e : ""
                }
            },
            width: "45%",
            draggable: true,
            fullscreenIcon: true,
            closeOnClickModal: false,
            contentRenderer: function () { return vue_1.h(auth_vue_1["default"], { ref: formRef }); },
            beforeSure: function (done, _a) {
                var options = _a.options;
                var FormRef = formRef.value.getRef();
                var curData = options.props.formInline;
                api_1.saveAuth(curData).then(function () {
                    message_1.message("\u60A8" + title + "\u4E86ID\u4E3A" + curData.id + "\u7684\u8FD9\u6761\u6570\u636E", {
                        type: "success"
                    });
                    done(); // 关闭弹框
                    onSearch(); // 刷新表格数据
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
        single: single,
        multiple: multiple,
        delByIds: delByIds,
        updateRow: updateRow,
        onSearch: onSearch,
        resetForm: resetForm,
        openDialog: openDialog,
        openAuthFormDialog: openAuthFormDialog,
        handleDelete: handleDelete,
        handleSizeChange: handleSizeChange,
        handleCurrentChange: handleCurrentChange,
        handleSelectionChange: handleSelectionChange,
        exportExcel: exportExcel
    };
}
exports.useMenu = useMenu;
