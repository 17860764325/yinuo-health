import {BasicColumn} from '/@/components/Table';
import {FormSchema} from '/@/components/Table';
import { rules} from '/@/utils/helper/validator';
import { render } from '/@/utils/common/renderUtils';
//列表数据
export const columns: BasicColumn[] = [
  {
    title: 'patientNo',
    align: "center",
    dataIndex: 'patientNo'
  },
  {
    title: 'peDate',
    align: "center",
    dataIndex: 'peDate'
  },
  {
    title: 'patientName',
    align: "center",
    dataIndex: 'patientName'
  },
  {
    title: 'sex',
    align: "center",
    dataIndex: 'sex'
  },
  {
    title: 'birthday',
    align: "center",
    dataIndex: 'birthday'
  },
  {
    title: 'age',
    align: "center",
    dataIndex: 'age'
  },
  {
    title: 'personNo',
    align: "center",
    dataIndex: 'personNo'
  },
  {
    title: 'address',
    align: "center",
    dataIndex: 'address'
  },
  {
    title: 'telphone',
    align: "center",
    dataIndex: 'telphone'
  },
  {
    title: 'email',
    align: "center",
    dataIndex: 'email'
  },
  {
    title: 'branchHospitalId',
    align: "center",
    dataIndex: 'branchHospitalId'
  },
  {
    title: 'transFlag',
    align: "center",
    dataIndex: 'transFlag'
  },
  {
    title: 'isSuccess',
    align: "center",
    dataIndex: 'isSuccess'
  },
];

//查询数据
export const searchFormSchema: FormSchema[] = [
  {
    label: "patientNo",
    field: 'patientNo',
    component: 'Input',
    colProps: {span: 6},
  },
  {
    label: "peDate",
    field: "peDate",
    component: 'RangePicker',
    componentProps: {
      showTime: true,
    },
    colProps: {span: 6},
  },
];

//表单数据
export const formSchema: FormSchema[] = [
  {
    label: 'patientNo',
    field: 'patientNo',
    component: 'Input',
    dynamicRules: ({model,schema}) => {
      return [
              { required: true, message: '请输入patientNo!'},
             ];
    },
  },
  {
    label: 'peDate',
    field: 'peDate',
    component: 'DatePicker',
    componentProps: {
      showTime: true,
      valueFormat: 'YYYY-MM-DD HH:mm:ss'
    },
  },
  {
    label: 'patientName',
    field: 'patientName',
    component: 'Input',
  },
  {
    label: 'sex',
    field: 'sex',
    component: 'Input',
  },
  {
    label: 'birthday',
    field: 'birthday',
    component: 'DatePicker',
    componentProps: {
      showTime: true,
      valueFormat: 'YYYY-MM-DD HH:mm:ss'
    },
  },
  {
    label: 'age',
    field: 'age',
    component: 'InputNumber',
  },
  {
    label: 'personNo',
    field: 'personNo',
    component: 'Input',
  },
  {
    label: 'address',
    field: 'address',
    component: 'Input',
  },
  {
    label: 'telphone',
    field: 'telphone',
    component: 'Input',
  },
  {
    label: 'email',
    field: 'email',
    component: 'Input',
  },
  {
    label: 'branchHospitalId',
    field: 'branchHospitalId',
    component: 'InputNumber',
  },
  {
    label: 'transFlag',
    field: 'transFlag',
    component: 'Input',
  },
  {
    label: 'isSuccess',
    field: 'isSuccess',
    component: 'InputNumber',
    dynamicRules: ({model,schema}) => {
      return [
              { required: true, message: '请输入isSuccess!'},
             ];
    },
  },
	// TODO 主键隐藏字段，目前写死为ID
  {
    label: '',
    field: 'id',
    component: 'Input',
    show: false,
  },
];
