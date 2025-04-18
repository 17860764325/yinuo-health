-- 注意：该页面对应的前台目录为views/doctor文件夹下
-- 如果你想更改到其他目录，请修改sql中component字段对应的值


INSERT INTO sys_permission(id, parent_id, name, url, component, component_name, redirect, menu_type, perms, perms_type, sort_no, always_show, icon, is_route, is_leaf, keep_alive, hidden, hide_tab, description, status, del_flag, rule_flag, create_by, create_time, update_by, update_time, internal_or_external) 
VALUES ('202310190521220330', NULL, '条码信息报告信息绑定', '/doctor/lisApplyBarCodeReportIdList', 'doctor/LisApplyBarCodeReportIdList', NULL, NULL, 0, NULL, '1', 0.00, 0, NULL, 1, 0, 0, 0, 0, NULL, '1', 0, 0, 'admin', '2023-10-19 17:21:33', NULL, NULL, 0);

-- 权限控制sql
-- 新增
INSERT INTO sys_permission(id, parent_id, name, url, component, is_route, component_name, redirect, menu_type, perms, perms_type, sort_no, always_show, icon, is_leaf, keep_alive, hidden, hide_tab, description, create_by, create_time, update_by, update_time, del_flag, rule_flag, status, internal_or_external)
VALUES ('202310190521230331', '202310190521220330', '添加条码信息报告信息绑定', NULL, NULL, 0, NULL, NULL, 2, 'doctor:lis_apply_bar_code_report_id:add', '1', NULL, 0, NULL, 1, 0, 0, 0, NULL, 'admin', '2023-10-19 17:21:33', NULL, NULL, 0, 0, '1', 0);
-- 编辑
INSERT INTO sys_permission(id, parent_id, name, url, component, is_route, component_name, redirect, menu_type, perms, perms_type, sort_no, always_show, icon, is_leaf, keep_alive, hidden, hide_tab, description, create_by, create_time, update_by, update_time, del_flag, rule_flag, status, internal_or_external)
VALUES ('202310190521230332', '202310190521220330', '编辑条码信息报告信息绑定', NULL, NULL, 0, NULL, NULL, 2, 'doctor:lis_apply_bar_code_report_id:edit', '1', NULL, 0, NULL, 1, 0, 0, 0, NULL, 'admin', '2023-10-19 17:21:33', NULL, NULL, 0, 0, '1', 0);
-- 删除
INSERT INTO sys_permission(id, parent_id, name, url, component, is_route, component_name, redirect, menu_type, perms, perms_type, sort_no, always_show, icon, is_leaf, keep_alive, hidden, hide_tab, description, create_by, create_time, update_by, update_time, del_flag, rule_flag, status, internal_or_external)
VALUES ('202310190521230333', '202310190521220330', '删除条码信息报告信息绑定', NULL, NULL, 0, NULL, NULL, 2, 'doctor:lis_apply_bar_code_report_id:delete', '1', NULL, 0, NULL, 1, 0, 0, 0, NULL, 'admin', '2023-10-19 17:21:33', NULL, NULL, 0, 0, '1', 0);
-- 批量删除
INSERT INTO sys_permission(id, parent_id, name, url, component, is_route, component_name, redirect, menu_type, perms, perms_type, sort_no, always_show, icon, is_leaf, keep_alive, hidden, hide_tab, description, create_by, create_time, update_by, update_time, del_flag, rule_flag, status, internal_or_external)
VALUES ('202310190521230334', '202310190521220330', '批量删除条码信息报告信息绑定', NULL, NULL, 0, NULL, NULL, 2, 'doctor:lis_apply_bar_code_report_id:deleteBatch', '1', NULL, 0, NULL, 1, 0, 0, 0, NULL, 'admin', '2023-10-19 17:21:33', NULL, NULL, 0, 0, '1', 0);
-- 导出excel
INSERT INTO sys_permission(id, parent_id, name, url, component, is_route, component_name, redirect, menu_type, perms, perms_type, sort_no, always_show, icon, is_leaf, keep_alive, hidden, hide_tab, description, create_by, create_time, update_by, update_time, del_flag, rule_flag, status, internal_or_external)
VALUES ('202310190521230335', '202310190521220330', '导出excel_条码信息报告信息绑定', NULL, NULL, 0, NULL, NULL, 2, 'doctor:lis_apply_bar_code_report_id:exportXls', '1', NULL, 0, NULL, 1, 0, 0, 0, NULL, 'admin', '2023-10-19 17:21:33', NULL, NULL, 0, 0, '1', 0);
-- 导入excel
INSERT INTO sys_permission(id, parent_id, name, url, component, is_route, component_name, redirect, menu_type, perms, perms_type, sort_no, always_show, icon, is_leaf, keep_alive, hidden, hide_tab, description, create_by, create_time, update_by, update_time, del_flag, rule_flag, status, internal_or_external)
VALUES ('202310190521230336', '202310190521220330', '导入excel_条码信息报告信息绑定', NULL, NULL, 0, NULL, NULL, 2, 'doctor:lis_apply_bar_code_report_id:importExcel', '1', NULL, 0, NULL, 1, 0, 0, 0, NULL, 'admin', '2023-10-19 17:21:33', NULL, NULL, 0, 0, '1', 0);