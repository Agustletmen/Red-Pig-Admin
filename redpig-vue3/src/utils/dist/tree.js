"use strict";
var __spreadArrays = (this && this.__spreadArrays) || function () {
    for (var s = 0, i = 0, il = arguments.length; i < il; i++) s += arguments[i].length;
    for (var r = Array(s), k = 0, i = 0; i < il; i++)
        for (var a = arguments[i], j = 0, jl = a.length; j < jl; j++, k++)
            r[k] = a[j];
    return r;
};
exports.__esModule = true;
exports.handleTree = exports.appendFieldByUniqueId = exports.getNodeByUniqueId = exports.buildHierarchyTree = exports.deleteChildren = exports.extractPathList = void 0;
/**
 * @description 提取菜单树中的每一项uniqueId
 * @param tree 树
 * @returns 每一项uniqueId组成的数组
 */
exports.extractPathList = function (tree) {
    if (!Array.isArray(tree)) {
        console.warn("tree must be an array");
        return [];
    }
    if (!tree || tree.length === 0)
        return [];
    var expandedPaths = [];
    for (var _i = 0, tree_1 = tree; _i < tree_1.length; _i++) {
        var node = tree_1[_i];
        var hasChildren = node.children && node.children.length > 0;
        if (hasChildren) {
            exports.extractPathList(node.children);
        }
        expandedPaths.push(node.uniqueId);
    }
    return expandedPaths;
};
/**
 * @description 如果父级下children的length为1，删除children并自动组建唯一uniqueId
 * @param tree 树
 * @param pathList 每一项的id组成的数组
 * @returns 组件唯一uniqueId后的树
 */
exports.deleteChildren = function (tree, pathList) {
    if (pathList === void 0) { pathList = []; }
    if (!Array.isArray(tree)) {
        console.warn("menuTree must be an array");
        return [];
    }
    if (!tree || tree.length === 0)
        return [];
    for (var _i = 0, _a = tree.entries(); _i < _a.length; _i++) {
        var _b = _a[_i], key = _b[0], node = _b[1];
        if (node.children && node.children.length === 1)
            delete node.children;
        node.id = key;
        node.parentId = pathList.length ? pathList[pathList.length - 1] : null;
        node.pathList = __spreadArrays(pathList, [node.id]);
        node.uniqueId =
            node.pathList.length > 1 ? node.pathList.join("-") : node.pathList[0];
        var hasChildren = node.children && node.children.length > 0;
        if (hasChildren) {
            exports.deleteChildren(node.children, node.pathList);
        }
    }
    return tree;
};
/**
 * @description 创建层级关系
 * @param tree 树
 * @param pathList 每一项的id组成的数组
 * @returns 创建层级关系后的树
 */
exports.buildHierarchyTree = function (tree, pathList) {
    if (pathList === void 0) { pathList = []; }
    if (!Array.isArray(tree)) {
        console.warn("tree must be an array");
        return [];
    }
    if (!tree || tree.length === 0)
        return [];
    for (var _i = 0, _a = tree.entries(); _i < _a.length; _i++) {
        var _b = _a[_i], key = _b[0], node = _b[1];
        node.id = key;
        node.parentId = pathList.length ? pathList[pathList.length - 1] : null;
        node.pathList = __spreadArrays(pathList, [node.id]);
        var hasChildren = node.children && node.children.length > 0;
        if (hasChildren) {
            exports.buildHierarchyTree(node.children, node.pathList);
        }
    }
    return tree;
};
/**
 * @description 广度优先遍历，根据唯一uniqueId找当前节点信息
 * @param tree 树
 * @param uniqueId 唯一uniqueId
 * @returns 当前节点信息
 */
exports.getNodeByUniqueId = function (tree, uniqueId) {
    if (!Array.isArray(tree)) {
        console.warn("menuTree must be an array");
        return [];
    }
    if (!tree || tree.length === 0)
        return [];
    var item = tree.find(function (node) { return node.uniqueId === uniqueId; });
    if (item)
        return item;
    var childrenList = tree
        .filter(function (node) { return node.children; })
        .map(function (i) { return i.children; })
        .flat(1);
    return exports.getNodeByUniqueId(childrenList, uniqueId);
};
/**
 * @description 向当前唯一uniqueId节点中追加字段
 * @param tree 树
 * @param uniqueId 唯一uniqueId
 * @param fields 需要追加的字段
 * @returns 追加字段后的树
 */
exports.appendFieldByUniqueId = function (tree, uniqueId, fields) {
    if (!Array.isArray(tree)) {
        console.warn("menuTree must be an array");
        return [];
    }
    if (!tree || tree.length === 0)
        return [];
    for (var _i = 0, tree_2 = tree; _i < tree_2.length; _i++) {
        var node = tree_2[_i];
        var hasChildren = node.children && node.children.length > 0;
        if (node.uniqueId === uniqueId &&
            Object.prototype.toString.call(fields) === "[object Object]")
            Object.assign(node, fields);
        if (hasChildren) {
            exports.appendFieldByUniqueId(node.children, uniqueId, fields);
        }
    }
    return tree;
};
/**
 * @description 构造树型结构数据
 * @param data 数据源
 * @param id id字段 默认id
 * @param parentId 父节点字段，默认parentId
 * @param children 子节点字段，默认children
 * @returns 追加字段后的树
 */
exports.handleTree = function (data, id, parentId, children) {
    if (!Array.isArray(data)) {
        console.warn("data must be an array");
        return [];
    }
    var config = {
        id: id || "id",
        parentId: parentId || "parentId",
        childrenList: children || "children"
    };
    var childrenListMap = {};
    var nodeIds = {};
    var tree = [];
    for (var _i = 0, data_1 = data; _i < data_1.length; _i++) {
        var d = data_1[_i];
        var parentId_1 = d[config.parentId];
        if (childrenListMap[parentId_1] == null) {
            childrenListMap[parentId_1] = [];
        }
        nodeIds[d[config.id]] = d;
        childrenListMap[parentId_1].push(d);
    }
    for (var _a = 0, data_2 = data; _a < data_2.length; _a++) {
        var d = data_2[_a];
        var parentId_2 = d[config.parentId];
        if (nodeIds[parentId_2] == null) {
            tree.push(d);
        }
    }
    for (var _b = 0, tree_3 = tree; _b < tree_3.length; _b++) {
        var t = tree_3[_b];
        adaptToChildrenList(t);
    }
    function adaptToChildrenList(o) {
        if (childrenListMap[o[config.id]] !== null) {
            o[config.childrenList] = childrenListMap[o[config.id]];
        }
        if (o[config.childrenList]) {
            for (var _i = 0, _a = o[config.childrenList]; _i < _a.length; _i++) {
                var c = _a[_i];
                adaptToChildrenList(c);
            }
        }
    }
    return tree;
};
