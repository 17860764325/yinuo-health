import { BasicColumn } from '/@/components/Table';
import { FormSchema } from '/@/components/Table';
//列表数据
export const columns: BasicColumn[] = [
  {
    title: '体检号',
    align: 'center',
    dataIndex: 'patientNo',
  },
  {
    title: '姓名',
    align: 'center',
    dataIndex: 'patientName',
  },
  {
    title: '患者id',
    align: 'center',
    dataIndex: 'patId',
  },
  {
    title: '项目id',
    align: 'center',
    dataIndex: 'labItemId',
  },
  {
    title: '项目名称',
    align: 'center',
    dataIndex: 'labItemName',
  },
  {
    title: '本地项目号',
    align: 'center',
    dataIndex: 'itemNo',
  },
  {
    title: '条码',
    align: 'center',
    dataIndex: 'barCode',
  },
  {
    title: '报告id',
    align: 'center',
    dataIndex: 'reportId',
    width: 200,
  },
  {
    title: "试管颜色",
    align: 'center',
    dataIndex: 'remark',
  }
];
//查询数据
export const searchFormSchema: FormSchema[] = [
  {
    label: '体检号',
    field: 'patientNo',
    component: 'Input',
    colProps: { span: 6 },
  },
  {
    label: '姓名',
    field: 'patientName',
    component: 'Input',
    colProps: { span: 6 },
  },
  {
    label: '患者id',
    field: 'patId',
    component: 'Input',
    colProps: { span: 6 },
  },
  {
    label: '项目名称',
    field: 'labItemName',
    component: 'Input',
    colProps: { span: 6 },
  },
  {
    label: '本地项目号',
    field: 'itemNo',
    component: 'Input',
    colProps: { span: 6 },
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
  // TODO 主键隐藏字段，目前写死为ID
  {
    label: '',
    field: 'id',
    component: 'Input',
    show: false,
  },
];

//子表列表数据
export const reportDetailColumns: BasicColumn[] = [
  {
    title: '报告id',
    align: 'center',
    dataIndex: 'reportId',
  },
  {
    title: '报告单分组号',
    align: 'center',
    dataIndex: 'keynoGroup',
  },
  {
    title: '明细项目Id',
    align: 'center',
    dataIndex: 'itemId',
  },
  {
    title: '明细项目名称',
    align: 'center',
    dataIndex: 'itemName',
  },
  {
    title: '标准代码',
    align: 'center',
    dataIndex: 'itemNo',
  },
  {
    title: '明细项目英文名称',
    align: 'center',
    dataIndex: 'itemEname',
  },
  {
    title: '参考范围类型',
    align: 'center',
    dataIndex: 'itemRangeType',
  },
  {
    title: '参考范围',
    align: 'center',
    dataIndex: 'itemRange',
  },
  {
    title: '参考范围高值',
    align: 'center',
    dataIndex: 'itemRangeHigh',
  },
  {
    title: '参考范围低值',
    align: 'center',
    dataIndex: 'itemRangeLow',
  },
  {
    title: '实验项目编码',
    align: 'center',
    dataIndex: 'labItemNo',
  },
  {
    title: '结果',
    align: 'center',
    dataIndex: 'itemResult',
  },
  {
    title: '报警标识',
    align: 'center',
    dataIndex: 'mark',
  },
  {
    title: '排序号',
    align: 'center',
    dataIndex: 'sno',
  },
  {
    title: '细菌编码',
    align: 'center',
    dataIndex: 'germNo',
  },
  {
    title: '细菌名称',
    align: 'center',
    dataIndex: 'germName',
  },
  {
    title: '阴阳性',
    align: 'center',
    dataIndex: 'yinYangNature',
  },
  {
    title: '专家意见',
    align: 'center',
    dataIndex: 'expertOpinion',
  },
  {
    title: '检验结果（阴性）',
    align: 'center',
    dataIndex: 'filingMemo',
  },
  {
    title: '报告备注',
    align: 'center',
    dataIndex: 'reportNotes',
  },
  {
    title: '涂片结果（阴性）',
    align: 'center',
    dataIndex: 'smearMemo',
  },
  {
    title: '是否危急值报告,0:否,1:是;',
    align: 'center',
    dataIndex: 'flagCritical',
  },
  {
    title: '结果时间',
    align: 'center',
    dataIndex: 'resultTime',
  },
  {
    title: '样式 1:普通 2:手工 3:微生物阴性 4:微生物阳性',
    align: 'center',
    dataIndex: 'pageType',
  },
  {
    title: '样本类型id',
    align: 'center',
    dataIndex: 'sampleClassId',
  },
  {
    title: '样本类型名称',
    align: 'center',
    dataIndex: 'sampleClassName',
  },
];
//子表表单数据
export const reportDetailFormSchema: FormSchema[] = [
  // TODO 子表隐藏字段，目前写死为ID
  {
    label: '',
    field: 'id',
    component: 'Input',
    show: false,
  },
  {
    label: '报告id',
    field: 'reportId',
    component: 'Input',
  },
  {
    label: '报告单分组号',
    field: 'keynoGroup',
    component: 'Input',
  },
  {
    label: '明细项目Id',
    field: 'itemId',
    component: 'Input',
  },
  {
    label: '明细项目名称',
    field: 'itemName',
    component: 'Input',
  },
  {
    label: '标准代码',
    field: 'itemNo',
    component: 'Input',
  },
  {
    label: '明细项目英文名称',
    field: 'itemEname',
    component: 'Input',
  },
  {
    label: '参考范围类型',
    field: 'itemRangeType',
    component: 'Input',
  },
  {
    label: '参考范围',
    field: 'itemRange',
    component: 'Input',
  },
  {
    label: '参考范围高值',
    field: 'itemRangeHigh',
    component: 'Input',
  },
  {
    label: '参考范围低值',
    field: 'itemRangeLow',
    component: 'Input',
  },
  {
    label: '实验项目编码',
    field: 'labItemNo',
    component: 'Input',
  },
  {
    label: '结果',
    field: 'itemResult',
    component: 'Input',
  },
  {
    label: '报警标识',
    field: 'mark',
    component: 'Input',
  },
  {
    label: '排序号',
    field: 'sno',
    component: 'Input',
  },
  {
    label: '细菌编码',
    field: 'germNo',
    component: 'Input',
  },
  {
    label: '细菌名称',
    field: 'germName',
    component: 'Input',
  },
  {
    label: '阴阳性',
    field: 'yinYangNature',
    component: 'Input',
  },
  {
    label: '专家意见',
    field: 'expertOpinion',
    component: 'Input',
  },
  {
    label: '检验结果（阴性）',
    field: 'filingMemo',
    component: 'Input',
  },
  {
    label: '报告备注',
    field: 'reportNotes',
    component: 'Input',
  },
  {
    label: '涂片结果（阴性）',
    field: 'smearMemo',
    component: 'Input',
  },
  {
    label: '是否危急值报告,0:否,1:是;',
    field: 'flagCritical',
    component: 'InputNumber',
  },
  {
    label: '结果时间',
    field: 'resultTime',
    component: 'Input',
  },
  {
    label: '样式 1:普通 2:手工 3:微生物阴性 4:微生物阳性',
    field: 'pageType',
    component: 'Input',
  },
  {
    label: '样本类型id',
    field: 'sampleClassId',
    component: 'Input',
  },
  {
    label: '样本类型名称',
    field: 'sampleClassName',
    component: 'Input',
  },
];
