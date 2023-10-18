import {BasicColumn} from '/@/components/Table';
import {FormSchema} from '/@/components/Table';
import { rules} from '/@/utils/helper/validator';
import { render } from '/@/utils/common/renderUtils';
//列表数据
export const columns: BasicColumn[] = [
   {
    title: '体检号',
    align:"center",
    dataIndex: 'patientNo'
   },
   {
    title: '姓名',
    align:"center",
    dataIndex: 'patientName'
   },
   {
    title: '患者id',
    align:"center",
    dataIndex: 'patId'
   },
   {
    title: '项目id',
    align:"center",
    dataIndex: 'labItemId'
   },
   {
    title: '项目名称',
    align:"center",
    dataIndex: 'labItemName'
   },
   {
    title: '本地项目号',
    align:"center",
    dataIndex: 'itemNo'
   },
   {
    title: '条码',
    align:"center",
    dataIndex: 'barCode'
   },
   {
    title: '报告id',
    align:"center",
    dataIndex: 'reportId'
   },
   {
    title: '预留字段',
    align:"center",
    dataIndex: 'remark'
   },
   {
    title: '预留字段1',
    align:"center",
    dataIndex: 'remark1'
   },
];
//查询数据
export const searchFormSchema: FormSchema[] = [
	{
      label: "体检号",
      field: 'patientNo',
      component: 'Input',
      colProps: {span: 6},
 	},
	{
      label: "姓名",
      field: 'patientName',
      component: 'Input',
      colProps: {span: 6},
 	},
	{
      label: "患者id",
      field: 'patId',
      component: 'Input',
      colProps: {span: 6},
 	},
	{
      label: "项目名称",
      field: 'labItemName',
      component: 'Input',
      colProps: {span: 6},
 	},
	{
      label: "本地项目号",
      field: 'itemNo',
      component: 'Input',
      colProps: {span: 6},
 	},
];
//表单数据
export const formSchema: FormSchema[] = [
  {
    label: '体检号',
    field: 'patientNo',
    component: 'Input',
  },
  {
    label: '姓名',
    field: 'patientName',
    component: 'Input',
  },
  {
    label: '患者id',
    field: 'patId',
    component: 'Input',
  },
  {
    label: '项目id',
    field: 'labItemId',
    component: 'Input',
  },
  {
    label: '项目名称',
    field: 'labItemName',
    component: 'Input',
  },
  {
    label: '本地项目号',
    field: 'itemNo',
    component: 'Input',
  },
  {
    label: '条码',
    field: 'barCode',
    component: 'Input',
  },
  {
    label: '报告id',
    field: 'reportId',
    component: 'Input',
  },
  {
    label: '预留字段',
    field: 'remark',
    component: 'Input',
  },
  {
    label: '预留字段1',
    field: 'remark1',
    component: 'Input',
  },
	// TODO 主键隐藏字段，目前写死为ID
	{
	  label: '',
	  field: 'id',
	  component: 'Input',
	  show: false
	},
];



/**
* 流程表单调用这个方法获取formSchema
* @param param
*/
export function getBpmFormSchema(_formData): FormSchema[]{
  // 默认和原始表单保持一致 如果流程中配置了权限数据，这里需要单独处理formSchema
  return formSchema;
}