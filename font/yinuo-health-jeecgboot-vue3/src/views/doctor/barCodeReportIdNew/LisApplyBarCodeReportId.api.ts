import {defHttp} from '/@/utils/http/axios';
import { useMessage } from "/@/hooks/web/useMessage";

const { createConfirm } = useMessage();

enum Api {
  list = '/doctor/lisApplyBarCodeReportId/list',
  save='/doctor/lisApplyBarCodeReportId/add',
  edit='/doctor/lisApplyBarCodeReportId/edit',
  deleteOne = '/doctor/lisApplyBarCodeReportId/delete',
  deleteBatch = '/doctor/lisApplyBarCodeReportId/deleteBatch',
  importExcel = '/doctor/lisApplyBarCodeReportId/importExcel',
  exportXls = '/doctor/lisApplyBarCodeReportId/exportXls',
  reportDetailList = '/doctor/lisApplyBarCodeReportId/listReportDetailByMainId',
  reportDetailSave='/doctor/lisApplyBarCodeReportId/addReportDetail',
  reportDetailEdit='/doctor/lisApplyBarCodeReportId/editReportDetail',
  reportDetailDelete = '/doctor/lisApplyBarCodeReportId/deleteReportDetail',
  reportDetailDeleteBatch = '/doctor/lisApplyBarCodeReportId/deleteBatchReportDetail',
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
export const list = (params) =>
  defHttp.get({url: Api.list, params});

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
/**
 * 列表接口
 * @param params
 */
export const reportDetailList = (params) => {
  if(params['reportId']){
    return defHttp.get({url: Api.reportDetailList, params});
  }
  return Promise.resolve({});
}


/**
 * 删除单个
 */
export const reportDetailDelete = (params,handleSuccess) => {
  return defHttp.delete({url: Api.reportDetailDelete, params}, {joinParamsToUrl: true}).then(() => {
    handleSuccess();
  });
}
/**
 * 批量删除
 * @param params
 */
export const reportDetailDeleteBatch = (params, handleSuccess) => {
  createConfirm({
    iconType: 'warning',
    title: '确认删除',
    content: '是否删除选中数据',
    okText: '确认',
    cancelText: '取消',
    onOk: () => {
      return defHttp.delete({url: Api.reportDetailDeleteBatch, data: params}, {joinParamsToUrl: true}).then(() => {
        handleSuccess();
      });
    }
  });
}
/**
 * 保存或者更新
 * @param params
 */
export const  reportDetailSaveOrUpdate = (params, isUpdate) => {
  let url = isUpdate ? Api.reportDetailEdit : Api.reportDetailSave;
  return defHttp.post({url: url, params});
}
/**
 * 导入
 */
export const reportDetailImportUrl = '/doctor/lisApplyBarCodeReportId/importReportDetail'

/**
 * 导出
 */
export const reportDetailExportXlsUrl = '/doctor/lisApplyBarCodeReportId/exportReportDetail'
