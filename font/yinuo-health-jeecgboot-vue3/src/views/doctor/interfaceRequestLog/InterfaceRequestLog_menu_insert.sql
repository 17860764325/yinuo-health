-- 注意：该页面对应的前台目录为views/doctor文件夹下
-- 如果你想更改到其他目录，请修改sql中component字段对应的值


INSERT INTO sys_permission(id, parent_id, name, url, component, component_name, redirect, menu_type, perms, perms_type, sort_no, always_show, icon, is_route, is_leaf, keep_alive, hidden, hide_tab, description, status, del_flag, rule_flag, create_by, create_time, update_by, update_time, internal_or_external)
VALUES ('2023101107266200460', NULL, '人员提交信息', '/doctor/interfaceRequestLogList', 'doctor/interfaceRequestLog/InterfaceRequestLogList', NULL, NULL, 0, NULL, '1', 0.00, 0, NULL, 1, 0, 0, 0, 0, NULL, '1', 0, 0, 'admin', '2023-10-11 19:26:46', NULL, NULL, 0);

-- 权限控制sql
-- 新增
INSERT INTO sys_permission(id, parent_id, name, url, component, is_route, component_name, redirect, menu_type, perms, perms_type, sort_no, always_show, icon, is_leaf, keep_alive, hidden, hide_tab, description, create_by, create_time, update_by, update_time, del_flag, rule_flag, status, internal_or_external)
VALUES ('2023101107266200461', '2023101107266200460', '添加人员提交信息', NULL, NULL, 0, NULL, NULL, 2, 'doctor:interface_request_log:add', '1', NULL, 0, NULL, 1, 0, 0, 0, NULL, 'admin', '2023-10-11 19:26:46', NULL, NULL, 0, 0, '1', 0);
-- 编辑
INSERT INTO sys_permission(id, parent_id, name, url, component, is_route, component_name, redirect, menu_type, perms, perms_type, sort_no, always_show, icon, is_leaf, keep_alive, hidden, hide_tab, description, create_by, create_time, update_by, update_time, del_flag, rule_flag, status, internal_or_external)
VALUES ('2023101107266200462', '2023101107266200460', '编辑人员提交信息', NULL, NULL, 0, NULL, NULL, 2, 'doctor:interface_request_log:edit', '1', NULL, 0, NULL, 1, 0, 0, 0, NULL, 'admin', '2023-10-11 19:26:46', NULL, NULL, 0, 0, '1', 0);
-- 删除
INSERT INTO sys_permission(id, parent_id, name, url, component, is_route, component_name, redirect, menu_type, perms, perms_type, sort_no, always_show, icon, is_leaf, keep_alive, hidden, hide_tab, description, create_by, create_time, update_by, update_time, del_flag, rule_flag, status, internal_or_external)
VALUES ('2023101107266200463', '2023101107266200460', '删除人员提交信息', NULL, NULL, 0, NULL, NULL, 2, 'doctor:interface_request_log:delete', '1', NULL, 0, NULL, 1, 0, 0, 0, NULL, 'admin', '2023-10-11 19:26:46', NULL, NULL, 0, 0, '1', 0);
-- 批量删除
INSERT INTO sys_permission(id, parent_id, name, url, component, is_route, component_name, redirect, menu_type, perms, perms_type, sort_no, always_show, icon, is_leaf, keep_alive, hidden, hide_tab, description, create_by, create_time, update_by, update_time, del_flag, rule_flag, status, internal_or_external)
VALUES ('2023101107266200464', '2023101107266200460', '批量删除人员提交信息', NULL, NULL, 0, NULL, NULL, 2, 'doctor:interface_request_log:deleteBatch', '1', NULL, 0, NULL, 1, 0, 0, 0, NULL, 'admin', '2023-10-11 19:26:46', NULL, NULL, 0, 0, '1', 0);
-- 导出excel
INSERT INTO sys_permission(id, parent_id, name, url, component, is_route, component_name, redirect, menu_type, perms, perms_type, sort_no, always_show, icon, is_leaf, keep_alive, hidden, hide_tab, description, create_by, create_time, update_by, update_time, del_flag, rule_flag, status, internal_or_external)
VALUES ('2023101107266200465', '2023101107266200460', '导出excel_人员提交信息', NULL, NULL, 0, NULL, NULL, 2, 'doctor:interface_request_log:exportXls', '1', NULL, 0, NULL, 1, 0, 0, 0, NULL, 'admin', '2023-10-11 19:26:46', NULL, NULL, 0, 0, '1', 0);
-- 导入excel
INSERT INTO sys_permission(id, parent_id, name, url, component, is_route, component_name, redirect, menu_type, perms, perms_type, sort_no, always_show, icon, is_leaf, keep_alive, hidden, hide_tab, description, create_by, create_time, update_by, update_time, del_flag, rule_flag, status, internal_or_external)
VALUES ('2023101107266200466', '2023101107266200460', '导入excel_人员提交信息', NULL, NULL, 0, NULL, NULL, 2, 'doctor:interface_request_log:importExcel', '1', NULL, 0, NULL, 1, 0, 0, 0, NULL, 'admin', '2023-10-11 19:26:46', NULL, NULL, 0, 0, '1', 0);
