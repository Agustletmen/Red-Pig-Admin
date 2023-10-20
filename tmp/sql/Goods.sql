INSERT INTO `redpig_sys_perm` (`deleteStatus`,`name`, `tag`, `create_by`, `update_by`, `remark`, `menu_id`) VALUES ('1', '商品管理分页查询', 'goods:page', '张三', '李四', '商品管理', '1');
INSERT INTO `redpig_sys_perm` (`deleteStatus`,`name`, `tag`, `create_by`, `update_by`, `remark`, `menu_id`) VALUES ('1', '保存或更新商品管理', 'goods:saveOrUpdate', '张三', '李四', '商品管理', '1');
INSERT INTO `redpig_sys_perm` (`deleteStatus`,`name`, `tag`, `create_by`, `update_by`, `remark`, `menu_id`) VALUES ('1', '根据ID删除商品管理', 'goods:delById', '张三', '李四', '商品管理', '1');
INSERT INTO `redpig_sys_perm` (`deleteStatus`,`name`, `tag`, `create_by`, `update_by`, `remark`, `menu_id`) VALUES ('1', '根据ID查询商品管理', 'goods:getById', '张三', '李四', '商品管理', '1');
INSERT INTO `redpig_sys_perm` (`deleteStatus`,`name`, `tag`, `create_by`, `update_by`, `remark`, `menu_id`) VALUES ('1', '批量删除商品管理', 'goods:removeBatchByIds', '张三', '李四', '商品管理', '1');

INSERT INTO `redpig_sys_menu`
(`deleteStatus`, `createTime`, `updateTime`, `name`, `path`, `component`, `title`, `type`,
`icon`, `showLink`, `rank`, `parent_id`) VALUES
('1', '2023-07-12 15:43:25', '2023-07-12 15:43:25', 'GoodsManage', '/goods', NULL, '商品管理管理', '0', 'fluent-mdl2:knowledge-article', b'1', '1', '0');

INSERT INTO `redpig_sys_menu`
(`deleteStatus`, `createTime`, `updateTime`, `name`, `path`, `component`, `title`, `type`, `icon`, `showLink`, `rank`, `parent_id`) VALUES
('1', '2023-07-12 15:44:22', '2023-07-12 15:44:22', 'GoodsList', '/system/goods/list', NULL, '商品管理列表', '1', 'fluent-mdl2:knowledge-article', b'1', '1', '69');

INSERT INTO `redpig_sys_menu`
(`deleteStatus`, `createTime`, `updateTime`, `name`, `path`, `component`, `title`, `type`, `icon`, `showLink`, `rank`, `parent_id`) VALUES
('1', '2023-07-12 15:45:56', '2023-07-12 15:45:56', 'Goods_page', '/goods/page', NULL, '商品管理分页查询', '2', 'fluent-mdl2:knowledge-article', b'0', '1', '70');

INSERT INTO `redpig_sys_menu`
(`deleteStatus`, `createTime`, `updateTime`, `name`, `path`, `component`, `title`, `type`, `icon`, `showLink`, `rank`, `parent_id`) VALUES
('1', '2023-07-12 15:46:45', '2023-07-12 15:46:45', 'Goods_saveOrUpdate', '/goods/saveOrUpdate', NULL, '新增或者更新商品管理', '2', 'fluent-mdl2:knowledge-article', b'0', '1', '70');
INSERT INTO `redpig_sys_menu`
(`deleteStatus`, `createTime`, `updateTime`, `name`, `path`, `component`, `title`, `type`, `icon`, `showLink`, `rank`, `parent_id`) VALUES
('1', '2023-07-12 15:47:28', '2023-07-12 15:47:28', 'Goods_delById', '/goods/delById', NULL, '根据ID删除商品管理', '2', 'fluent-mdl2:knowledge-article', b'0', '1', '70');
INSERT INTO `redpig_sys_menu`
(`deleteStatus`, `createTime`, `updateTime`, `name`, `path`, `component`, `title`, `type`, `icon`, `showLink`, `rank`, `parent_id`) VALUES
('1', '2023-07-12 15:48:01', '2023-07-12 15:48:01', 'Goods_getById', '/goods/getById', NULL, '根据ID查询商品管理', '2', 'fluent-mdl2:knowledge-article', b'0', '1', '70');
INSERT INTO `redpig_sys_menu`
(`deleteStatus`, `createTime`, `updateTime`, `name`, `path`, `component`, `title`, `type`, `icon`, `showLink`, `rank`, `parent_id`) VALUES
('1', '2023-07-12 15:52:03', '2023-07-12 15:52:03', 'Goods_removeBatchByIds', '/goods/removeBatchByIds', NULL, '批量删除商品管理', '2', 'fluent-mdl2:knowledge-article', b'0', '1', '70');
