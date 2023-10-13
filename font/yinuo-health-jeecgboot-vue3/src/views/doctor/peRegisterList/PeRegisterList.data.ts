import {BasicColumn} from '/@/components/Table';
import {FormSchema} from '/@/components/Table';
//列表数据
export const columns: BasicColumn[] = [
    {
        title: '体检号',
        align: "center",
        dataIndex: 'patientNo',
        sorter: true
    },
    {
        title: '患者id',
        align: "center",
        dataIndex: 'patId'
    },
    {
        title: '体检时间',
        align: "center",
        dataIndex: 'peDate'
    },
    {
        title: '姓名',
        align: "center",
        dataIndex: 'patientName'
    },
    {
        title: '性别',
        align: "center",
        dataIndex: 'sex'
    },
    {
        title: '生日',
        align: "center",
        dataIndex: 'birthday'
    },
    {
        title: '年龄',
        align: "center",
        dataIndex: 'age'
    },
    {
        title: '身份证号',
        align: "center",
        dataIndex: 'personNo'
    },
    {
        title: '地址',
        align: "center",
        dataIndex: 'address'
    },
    {
        title: '手机号',
        align: "center",
        dataIndex: 'telphone'
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
        label: "患者ID",
        field: 'patId',
        component: 'Input',
        colProps: {span: 6},
    },
    {
        label: "体检日期",
        field: "peDate",
        component: 'RangePicker',
        componentProps: {
            valueType: 'Date',
            showTime: true
        },
        colProps: {span: 6},
    },
    {
        label: "姓名",
        field: 'patientName',
        component: 'Input',
        colProps: {span: 6},
    },
    {
        label: "身份证号",
        field: 'personNo',
        component: 'Input',
        colProps: {span: 6},
    },
    {
        label: "地址",
        field: 'address',
        component: 'Input',
        colProps: {span: 6},
    },
    {
        label: "手机号",
        field: 'telphone',
        component: 'Input',
        colProps: {span: 6},
    },
];
//表单数据
export const formSchema: FormSchema[] = [
    {
        label: 'patientNo',
        field: 'patientNo',
        component: 'Input',
        dynamicRules: ({model, schema}) => {
            return [
                {required: true, message: '请输入patientNo!'},
            ];
        },
    },
    {
        label: '患者 id',
        field: 'patId',
        component: 'Input',
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
        dynamicRules: ({model, schema}) => {
            return [
                {required: true, message: '请输入isSuccess!'},
            ];
        },
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
export function getBpmFormSchema(_formData): FormSchema[] {
    // 默认和原始表单保持一致 如果流程中配置了权限数据，这里需要单独处理formSchema
    return formSchema;
}
