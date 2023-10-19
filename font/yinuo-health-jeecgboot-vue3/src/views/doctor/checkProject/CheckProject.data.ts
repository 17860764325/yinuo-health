import {BasicColumn} from '/@/components/Table';
import {FormSchema} from '/@/components/Table';
import { rules} from '/@/utils/helper/validator';
import { render } from '/@/utils/common/renderUtils';
import {JVxeTypes,JVxeColumn} from '/@/components/jeecg/JVxeTable/types'
//列表数据
export const columns: BasicColumn[] = [
    {
        title: '检验项目id',
        align:"center",
        dataIndex: 'labItemId'
    },
    {
        title: '接口传输的项目号',
        align:"center",
        dataIndex: 'mineProjectNo'
    },
    {
        title: '项目号',
        align:"center",
        dataIndex: 'mineItemRemork'
    },
    {
        title: '检验项目名称',
        align:"center",
        dataIndex: 'labName',
        resizable: true //配置列可伸缩
    },
    {
        title: '报告项目Id',
        align:"center",
        dataIndex: 'reportItemId'
    },
    {
        title: '样本类型Id',
        align:"center",
        dataIndex: 'sampleClassId'
    },
    {
        title: '样本类型名称',
        align:"center",
        dataIndex: 'sampleClassName'
    },
    {
        title: '医嘱字典Id',
        align:"center",
        dataIndex: 'orderId'
    },
    {
        title: '医嘱字典名称',
        align:"center",
        dataIndex: 'orderName'
    },

];
//查询数据
export const searchFormSchema: FormSchema[] = [
    {
        label: "检验项目id",
        field: 'labItemId',
        component: 'Input',
        colProps: {span: 6},
    },
    {
        label: "检验项目名称",
        field: 'labName',
        component: 'Input',
        colProps: {span: 6},
    },
    {
        label: "自己系统的项目号",
        field: 'mineProjectNo',
        component: 'Input',
        colProps: {span: 6},
    },
];
//表单数据
export const formSchema: FormSchema[] = [
    {
        label: '检验项目id',
        field: 'labItemId',
        component: 'Input',
        // 禁用
        dynamicDisabled:true
    },
    {
        label: '检验项目名称',
        field: 'labName',
        component: 'Input',
        // 禁用
        dynamicDisabled:true
    },
    {
        label: '报告项目Id',
        field: 'reportItemId',
        component: 'Input',
        // 禁用
        dynamicDisabled:true
    },
    {
        label: '样本类型Id',
        field: 'sampleClassId',
        component: 'Input',
        // 禁用
        dynamicDisabled:true
    },
    {
        label: '样本类型名称',
        field: 'sampleClassName',
        component: 'Input',
        // 禁用
        dynamicDisabled:true
    },
    {
        label: '医嘱字典Id',
        field: 'orderId',
        component: 'Input',
        // 禁用
        dynamicDisabled:true
    },
    {
        label: '医嘱字典名称',
        field: 'orderName',
        component: 'Input',
        // 禁用
        dynamicDisabled:true
    },
    {
        label: '自己系统的项目号',
        field: 'mineProjectNo',
        component: 'Input',
    },
    {
        label: '预留字段',
        field: 'mineItemRemork',
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
//子表单数据
//子表列表数据
export const checkProjectDetailColumns: BasicColumn[] = [
    {
        title: '明细项目Id',
        align:"center",
        dataIndex: 'itemId'
    },
    {
        title: '自己系统项目的标识',
        align:"center",
        dataIndex: 'mineProjectNo'
    },
    {
        title: '自己系统项目的标识备注',
        align:"center",
        dataIndex: 'mineProjectRemock'
    },
    {
        title: '明细项目编码',
        align:"center",
        dataIndex: 'itemNo'
    },
    {
        title: '明细项目名称',
        align:"center",
        dataIndex: 'itemName',
        resizable: true //配置列可伸缩
    },

    {
        title: '主项目id',
        align:"center",
        dataIndex: 'checkProjectId'
    },
];
//子表表格配置
export const checkProjectDetailJVxeColumns: JVxeColumn[] = [
    {
        title: '明细项目Id',
        key: 'itemId',
        type: JVxeTypes.input,
        width:"200px",
        placeholder: '请输入${title}',
        defaultValue:'',
        disabled:true
    },
    {
        title: '明细项目编码',
        key: 'itemNo',
        type: JVxeTypes.input,
        width:"200px",
        placeholder: '请输入${title}',
        defaultValue:'',
        disabled:true
    },
    {
        title: '明细项目名称',
        key: 'itemName',
        type: JVxeTypes.input,
        width:"200px",
        placeholder: '请输入${title}',
        defaultValue:'',
        disabled:true
    },
    {
        title: '自己系统项目的标识',
        key: 'mineProjectNo',
        type: JVxeTypes.input,
        width:"200px",
        placeholder: '请输入${title}',
        defaultValue:'',
    },
    {
        title: '自己系统项目的标识备注',
        key: 'mineProjectRemock',
        type: JVxeTypes.inputNumber,
        width:"200px",
        placeholder: '请输入${title}',
        defaultValue:''
    },
]

/**
 * 流程表单调用这个方法获取formSchema
 * @param param
 */
export function getBpmFormSchema(_formData): FormSchema[]{
    // 默认和原始表单保持一致 如果流程中配置了权限数据，这里需要单独处理formSchema
    return formSchema;
}
