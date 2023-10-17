import {defHttp} from '/@/utils/http/axios';
import { useMessage } from "/@/hooks/web/useMessage";

const { createConfirm } = useMessage();

enum Api {
  list = '/doctor/peRegisterList/list',
  save='/doctor/peRegisterList/add',
  edit='/doctor/peRegisterList/edit',
  deleteOne = '/doctor/peRegisterList/delete',
  deleteBatch = '/doctor/peRegisterList/deleteBatch',
  importExcel = '/doctor/peRegisterList/importExcel',
  exportXls = '/doctor/peRegisterList/exportXls',
  personSearch = '/doctor/peRegisterList/personSearch/',
  personCreate = '/doctor/peRegisterList/personCreate/',
  newLogTest = '/doctor/peRegisterList/newLogTest/',
  LisApply = '/doctor/peRegisterList/LISApply'
}
/**
 * 导出api
 * @param params
 */
export const getExportUrl = Api.exportXls;
/**
 * 导入api
 */
export const getImportUrl = Api.importExcel;
/**
 * 列表接口
 * @param params
 */
export const list = (params) =>{
  // 添加固定查询条件
  // if (params !== null || params !== undefined || params !== ''){
  //   params.status = 3
  // }else {
  //   params = {status: 3}
  // }
  return defHttp.get({url: Api.list, params});
}

/**
 * 删除单个
 */
export const deleteOne = (params,handleSuccess) => {
  return defHttp.delete({url: Api.deleteOne, params}, {joinParamsToUrl: true}).then(() => {
    handleSuccess();
  });
}
/**
 * 批量删除
 * @param params
 */
export const batchDelete = (params, handleSuccess) => {
  createConfirm({
    iconType: 'warning',
    title: '确认删除',
    content: '是否删除选中数据',
    okText: '确认',
    cancelText: '取消',
    onOk: () => {
      return defHttp.delete({url: Api.deleteBatch, data: params}, {joinParamsToUrl: true}).then(() => {
        handleSuccess();
      });
    }
  });
}
/**
 * 保存或者更新
 * @param params
 */
export const saveOrUpdate = (params, isUpdate) => {
  let url = isUpdate ? Api.edit : Api.save;
  return defHttp.post({url: url, params});
}

export const personSearch = (ids) => {
  return defHttp.get({url: Api.personSearch + ids})
}

export const personCreate = (ids) => {
  return defHttp.get({url: Api.personCreate + ids})
}

export const newLogTest = (ids) => {
  return defHttp.get({url: Api.newLogTest + ids})
}

export const LISApply = (params) => {
  return defHttp.post({url: Api.LisApply,params})
}
