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
exports.useDept = void 0;
var dayjs_1 = require("dayjs");
var add_vue_1 = require("../add.vue");
var tree_1 = require("@/utils/tree");
var message_1 = require("@/utils/message");
var api_1 = require("./api");
var hooks_1 = require("../../hooks");
var ReDialog_1 = require("@/components/ReDialog");
var vue_1 = require("vue");
var utils_1 = require("@pureadmin/utils");
function useDept() {
    var form = vue_1.reactive({
        deptName: "",
        status: null
    });
    var formRef = vue_1.ref();
    var dataList = vue_1.ref([]);
    var loading = vue_1.ref(true);
    var tagStyle = hooks_1.usePublicHooks().tagStyle;
    var columns = [
        {
            label: "部门名称",
            prop: "deptName",
            width: 180,
            align: "left"
        },
        {
            label: "状态",
            prop: "status",
            minWidth: 100,
            cellRenderer: function (_a) {
                var row = _a.row, props = _a.props;
                return (React.createElement("el-tag", { size: props.size, style: tagStyle.value(row.status) }, row.status === true ? "启用" : "停用"));
            }
        },
        {
            label: "创建时间",
            minWidth: 200,
            prop: "createTime",
            formatter: function (_a) {
                var createTime = _a.createTime;
                return dayjs_1["default"](createTime).format("YYYY-MM-DD HH:mm:ss");
            }
        },
        {
            label: "操作",
            fixed: "right",
            width: 160,
            slot: "operation"
        }
    ];
    function handleSelectionChange(val) {
        console.log("handleSelectionChange", val);
    }
    function resetForm(formEl) {
        if (!formEl)
            return;
        formEl.resetFields();
        onSearch();
    }
    function onSearch() {
        return __awaiter(this, void 0, void 0, function () {
            var data, newData;
            return __generator(this, function (_a) {
                switch (_a.label) {
                    case 0:
                        loading.value = true;
                        return [4 /*yield*/, api_1.getDeptList()];
                    case 1:
                        data = (_a.sent()).data;
                        newData = data;
                        dataList.value = tree_1.handleTree(newData); // 处理成树结构
                        setTimeout(function () {
                            loading.value = false;
                        }, 500);
                        return [2 /*return*/];
                }
            });
        });
    }
    function formatHigherDeptOptions(treeList) {
        // 根据返回数据的status字段值判断追加是否禁用disabled字段，返回处理后的树结构，用于上级部门级联选择器的展示（实际开发中也是如此，不可能前端需要的每个字段后端都会返回，这时需要前端自行根据后端返回的某些字段做逻辑处理）
        if (!treeList || !treeList.length)
            return;
        var newTreeList = [];
        for (var i = 0; i < treeList.length; i++) {
            treeList[i].disabled = treeList[i].status === 0 ? true : false;
            formatHigherDeptOptions(treeList[i].children);
            newTreeList.push(treeList[i]);
        }
        return newTreeList;
    }
    function openDialog(title, row) {
        var _a, _b, _c, _d, _e, _f, _g, _h, _j, _k;
        if (title === void 0) { title = "新增"; }
        ReDialog_1.addDialog({
            title: title + "\u90E8\u95E8",
            props: {
                formInline: {
                    higherDeptOptions: formatHigherDeptOptions(utils_1.cloneDeep(dataList.value)),
                    id: (_a = row === null || row === void 0 ? void 0 : row.id) !== null && _a !== void 0 ? _a : "",
                    deptName: (_b = row === null || row === void 0 ? void 0 : row.deptName) !== null && _b !== void 0 ? _b : "",
                    orderNum: (_c = row === null || row === void 0 ? void 0 : row.orderNum) !== null && _c !== void 0 ? _c : "0",
                    manager: (_d = row === null || row === void 0 ? void 0 : row.manager) !== null && _d !== void 0 ? _d : "",
                    phone: (_e = row === null || row === void 0 ? void 0 : row.phone) !== null && _e !== void 0 ? _e : "",
                    email: (_f = row === null || row === void 0 ? void 0 : row.email) !== null && _f !== void 0 ? _f : "",
                    status: (_g = row === null || row === void 0 ? void 0 : row.status) !== null && _g !== void 0 ? _g : null,
                    createBy: (_h = row === null || row === void 0 ? void 0 : row.createBy) !== null && _h !== void 0 ? _h : "",
                    updateBy: (_j = row === null || row === void 0 ? void 0 : row.updateBy) !== null && _j !== void 0 ? _j : "",
                    parentId: (_k = row === null || row === void 0 ? void 0 : row.parentId) !== null && _k !== void 0 ? _k : ""
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
                    message_1.message("\u60A8" + title + "\u4E86\u90E8\u95E8\u540D\u79F0\u4E3A" + curData.deptName + "\u7684\u8FD9\u6761\u6570\u636E", {
                        type: "success"
                    });
                    done(); // 关闭弹框
                    onSearch(); // 刷新表格数据
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
    function handleDelete(row) {
        message_1.message("\u60A8\u5220\u9664\u4E86\u90E8\u95E8\u540D\u79F0\u4E3A" + row.name + "\u7684\u8FD9\u6761\u6570\u636E", { type: "success" });
        onSearch();
    }
    vue_1.onMounted(function () {
        onSearch();
    });
    return {
        form: form,
        loading: loading,
        columns: columns,
        dataList: dataList,
        /** 搜索 */
        onSearch: onSearch,
        /** 重置 */
        resetForm: resetForm,
        /** 新增、编辑部门 */
        openDialog: openDialog,
        /** 删除部门 */
        handleDelete: handleDelete,
        handleSelectionChange: handleSelectionChange
    };
}
exports.useDept = useDept;
