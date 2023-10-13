import {BasicColumn} from '/@/components/Table';
import {FormSchema} from '/@/components/Table';
import { rules} from '/@/utils/helper/validator';
import { render } from '/@/utils/common/renderUtils';
//列表数据
export const columns: BasicColumn[] = [
    {
        title: '时间',
        align:"center",
        dataIndex: 'createTime',
        width:200
    },
    {
        title: '接口名称',
        align:"center",
        dataIndex: 'interfaceName'
    },
    {
        title: '姓名',
        align:"center",
        dataIndex: 'patientName'
    },
    {
        title: '具体人员体检号',
        align:"center",
        dataIndex: 'patientNo'
    },
    {
        title: '患者 id',
        align:"center",
        dataIndex: 'patId'
    },
    {
        title: '请求是否成功',
        align:"center",
        dataIndex: 'success',
        customRender: ({value}) => {
            return render.renderDict(value, 'success_fail');
        }
    },
    {
        title: '请求信息',
        align:"center",
        dataIndex: 'sendMessage',
        resizable:true
    },
    {
        title: '接收信息',
        align:"center",
        dataIndex: 'receiveMessage',
        resizable:true
    },
    {
        title: '日志内容',
        align:"center",
        dataIndex: 'remark',
        resizable:true
    },
];
//查询数据
export const searchFormSchema: FormSchema[] = [
    {
        label: "时间：",
        field: "createTime",
        component: 'RangePicker',
        componentProps: {
            valueType: 'Date',
            showTime:true
        },
        colProps: {span: 6},
    },
    {
        label: "姓名",
        field: 'patientName',
        component: 'Input',
        colProps: {span: 3},
    },
    {
        label: "体检号",
        field: 'patientNo',
        component: 'Input',
        colProps: {span: 4},
    },
    {
        label: "接口名称",
        field: 'interfaceName',
        component: 'Input',
        colProps: {span: 6},
    },
    {
        label: "患者 id",
        field: 'patId',
        component: 'Input',
        colProps: {span: 4},
    },
];
//表单数据
export const formSchema: FormSchema[] = [
    {
        label: '接口编码',
        field: 'interfaceCode',
        component: 'Input',
    },
    {
        label: '接口名称',
        field: 'interfaceName',
        component: 'Input',
    },
    {
        label: '姓名',
        field: 'patientName',
        component: 'Input',
    },{
        label: '具体人员体检号',
        field: 'patientNo',
        component: 'InputNumber',
    },
    {
        label: '患者 id',
        field: 'patId',
        component: 'Input',
    },
    {
        label: '请求是否成功',
        field: 'success',
        component: 'JDictSelectTag',
        componentProps: {
            dictCode: 'success_fail',
        },
    },
    {
        label: '请求信息',
        field: 'sendMessage',
        component: 'InputTextArea',
    },
    {
        label: '接收信息',
        field: 'receiveMessage',
        component: 'InputTextArea',
    },
    {
        label: '日志内容',
        field: 'remark',
        component: 'InputTextArea',
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
