-- 注意：该页面对应的前台目录为views/doctor文件夹下
-- 如果你想更改到其他目录，请修改sql中component字段对应的值


INSERT INTO sys_permission(id, parent_id, name, url, component, component_name, redirect, menu_type, perms, perms_type, sort_no, always_show, icon, is_route, is_leaf, keep_alive, hidden, hide_tab, description, status, del_flag, rule_flag, create_by, create_time, update_by, update_time, internal_or_external) 
VALUES ('202310180143640030', NULL, '条码信息报告信息绑定', '/doctor/lisApplyBarCodeReportIdList', 'doctor/LisApplyBarCodeReportIdList', NULL, NULL, 0, NULL, '1', 0.00, 0, NULL, 1, 0, 0, 0, 0, NULL, '1', 0, 0, 'admin', '2023-10-18 13:43:03', NULL, NULL, 0);

-- 权限控制sql
-- 新增
INSERT INTO sys_permission(id, parent_id, name, url, component, is_route, component_name, redirect, menu_type, perms, perms_type, sort_no, always_show, icon, is_leaf, keep_alive, hidden, hide_tab, description, create_by, create_time, update_by, update_time, del_flag, rule_flag, status, internal_or_external)
VALUES ('202310180143640031', '202310180143640030', '添加条码信息报告信息绑定', NULL, NULL, 0, NULL, NULL, 2, 'doctor:lis_apply_bar_code_report_id:add', '1', NULL, 0, NULL, 1, 0, 0, 0, NULL, 'admin', '2023-10-18 13:43:03', NULL, NULL, 0, 0, '1', 0);
-- 编辑
INSERT INTO sys_permission(id, parent_id, name, url, component, is_route, component_name, redirect, menu_type, perms, perms_type, sort_no, always_show, icon, is_leaf, keep_alive, hidden, hide_tab, description, create_by, create_time, update_by, update_time, del_flag, rule_flag, status, internal_or_external)
VALUES ('202310180143640032', '202310180143640030', '编辑条码信息报告信息绑定', NULL, NULL, 0, NULL, NULL, 2, 'doctor:lis_apply_bar_code_report_id:edit', '1', NULL, 0, NULL, 1, 0, 0, 0, NULL, 'admin', '2023-10-18 13:43:03', NULL, NULL, 0, 0, '1', 0);
-- 删除
INSERT INTO sys_permission(id, parent_id, name, url, component, is_route, component_name, redirect, menu_type, perms, perms_type, sort_no, always_show, icon, is_leaf, keep_alive, hidden, hide_tab, description, create_by, create_time, update_by, update_time, del_flag, rule_flag, status, internal_or_external)
VALUES ('202310180143640033', '202310180143640030', '删除条码信息报告信息绑定', NULL, NULL, 0, NULL, NULL, 2, 'doctor:lis_apply_bar_code_report_id:delete', '1', NULL, 0, NULL, 1, 0, 0, 0, NULL, 'admin', '2023-10-18 13:43:03', NULL, NULL, 0, 0, '1', 0);
-- 批量删除
INSERT INTO sys_permission(id, parent_id, name, url, component, is_route, component_name, redirect, menu_type, perms, perms_type, sort_no, always_show, icon, is_leaf, keep_alive, hidden, hide_tab, description, create_by, create_time, update_by, update_time, del_flag, rule_flag, status, internal_or_external)
VALUES ('202310180143640034', '202310180143640030', '批量删除条码信息报告信息绑定', NULL, NULL, 0, NULL, NULL, 2, 'doctor:lis_apply_bar_code_report_id:deleteBatch', '1', NULL, 0, NULL, 1, 0, 0, 0, NULL, 'admin', '2023-10-18 13:43:03', NULL, NULL, 0, 0, '1', 0);
-- 导出excel
INSERT INTO sys_permission(id, parent_id, name, url, component, is_route, component_name, redirect, menu_type, perms, perms_type, sort_no, always_show, icon, is_leaf, keep_alive, hidden, hide_tab, description, create_by, create_time, update_by, update_time, del_flag, rule_flag, status, internal_or_external)
VALUES ('202310180143640035', '202310180143640030', '导出excel_条码信息报告信息绑定', NULL, NULL, 0, NULL, NULL, 2, 'doctor:lis_apply_bar_code_report_id:exportXls', '1', NULL, 0, NULL, 1, 0, 0, 0, NULL, 'admin', '2023-10-18 13:43:03', NULL, NULL, 0, 0, '1', 0);
-- 导入excel
INSERT INTO sys_permission(id, parent_id, name, url, component, is_route, component_name, redirect, menu_type, perms, perms_type, sort_no, always_show, icon, is_leaf, keep_alive, hidden, hide_tab, description, create_by, create_time, update_by, update_time, del_flag, rule_flag, status, internal_or_external)
VALUES ('202310180143640036', '202310180143640030', '导入excel_条码信息报告信息绑定', NULL, NULL, 0, NULL, NULL, 2, 'doctor:lis_apply_bar_code_report_id:importExcel', '1', NULL, 0, NULL, 1, 0, 0, 0, NULL, 'admin', '2023-10-18 13:43:03', NULL, NULL, 0, 0, '1', 0);