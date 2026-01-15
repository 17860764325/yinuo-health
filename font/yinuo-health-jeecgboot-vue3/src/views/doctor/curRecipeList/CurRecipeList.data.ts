import {BasicColumn} from '/@/components/Table';
import {FormSchema} from '/@/components/Table';
import { rules} from '/@/utils/helper/validator';
import { render } from '/@/utils/common/renderUtils';

// 左侧列表列配置 - 处方号、患者号和患者姓名
export const leftColumns: BasicColumn[] = [
  {
    title: '处方号',
    align: "center",
    dataIndex: 'recipeNo',
    width: 140,
  },
  {
    title: '患者姓名',
    align: "center",
    dataIndex: 'patientName',
    width: 120,
  },
];

// 右侧列表列配置 - 处方明细（简化版）
export const rightColumns: BasicColumn[] = [
  {
    title: '药品名称',
    align: "center",
    dataIndex: 'itemName',
    width: 150,
  },
  {
    title: '规格',
    align: "center",
    dataIndex: 'drgSpec',
    width: 120,
  },
  {
    title: '单位',
    align: "center",
    dataIndex: 'unit',
    width: 80,
  },
  {
    title: '数量',
    align: "center",
    dataIndex: 'amount',
    width: 100,
  },
  {
    title: '单价',
    align: "center",
    dataIndex: 'price',
    width: 100,
  },
  {
    title: '用法',
    align: "center",
    dataIndex: 'usage',
    width: 120,
  },
  {
    title: '频次',
    align: "center",
    dataIndex: 'frequencySortId',
    width: 100,
  },
  {
    title: '用量',
    align: "center",
    dataIndex: 'singleUseAmount',
    width: 100,
  },
  {
    title: '总量',
    align: "center",
    dataIndex: 'totalFee',
    width: 100,
  },
  {
    title: '生产厂家',
    align: "center",
    dataIndex: 'drgProductorId',
    width: 120,
  },
  {
    title: '生产批号',
    align: "center",
    dataIndex: 'transNo',
    width: 120,
  },
  {
    title: '失效期',
    align: "center",
    dataIndex: 'abateDate',
    width: 130,
  },
  {
    title: '库存数量',
    align: "center",
    dataIndex: 'skinDrugMinus',
    width: 100,
  },
];

//列表数据
export const columns: BasicColumn[] = [
   {
    title: 'recipeListId',
    align:"center",
    dataIndex: 'recipeListId'
   },
   {
    title: 'patientSource',
    align:"center",
    dataIndex: 'patientSource'
   },
   {
    title: 'recipeNo',
    align:"center",
    dataIndex: 'recipeNo'
   },
   {
    title: 'groupNo',
    align:"center",
    dataIndex: 'groupNo'
   },
   {
    title: 'itemIndex',
    align:"center",
    dataIndex: 'itemIndex'
   },
   {
    title: 'drgRecipeSortId',
    align:"center",
    dataIndex: 'drgRecipeSortId'
   },
   {
    title: 'recipeSortId',
    align:"center",
    dataIndex: 'recipeSortId'
   },
   {
    title: 'hospPkno',
    align:"center",
    dataIndex: 'hospPkno'
   },
   {
    title: 'patientNo',
    align:"center",
    dataIndex: 'patientNo'
   },
   {
    title: 'caseNo',
    align:"center",
    dataIndex: 'caseNo'
   },
   {
    title: 'patientName',
    align:"center",
    dataIndex: 'patientName'
   },
   {
    title: 'sex',
    align:"center",
    dataIndex: 'sex'
   },
   {
    title: 'age',
    align:"center",
    dataIndex: 'age'
   },
   {
    title: 'fareIdentity',
    align:"center",
    dataIndex: 'fareIdentity'
   },
   {
    title: 'weight',
    align:"center",
    dataIndex: 'weight'
   },
   {
    title: 'companyId',
    align:"center",
    dataIndex: 'companyId'
   },
   {
    title: 'diagnoseName',
    align:"center",
    dataIndex: 'diagnoseName'
   },
   {
    title: 'personNo',
    align:"center",
    dataIndex: 'personNo'
   },
   {
    title: 'bedName',
    align:"center",
    dataIndex: 'bedName'
   },
   {
    title: 'inputCode',
    align:"center",
    dataIndex: 'inputCode'
   },
   {
    title: 'address',
    align:"center",
    dataIndex: 'address'
   },
   {
    title: 'ihDepartmentId',
    align:"center",
    dataIndex: 'ihDepartmentId'
   },
   {
    title: 'currWardId',
    align:"center",
    dataIndex: 'currWardId'
   },
   {
    title: 'departmentId',
    align:"center",
    dataIndex: 'departmentId'
   },
   {
    title: 'doctorId',
    align:"center",
    dataIndex: 'doctorId'
   },
   {
    title: 'operatorId',
    align:"center",
    dataIndex: 'operatorId'
   },
   {
    title: 'operateDate',
    align:"center",
    dataIndex: 'operateDate'
   },
   {
    title: 'chargeDate',
    align:"center",
    dataIndex: 'chargeDate'
   },
   {
    title: 'infusionDepartmentId',
    align:"center",
    dataIndex: 'infusionDepartmentId'
   },
   {
    title: 'infusionStatus',
    align:"center",
    dataIndex: 'infusionStatus'
   },
   {
    title: 'infusionProcOperator',
    align:"center",
    dataIndex: 'infusionProcOperator'
   },
   {
    title: 'infusionProcDate',
    align:"center",
    dataIndex: 'infusionProcDate'
   },
   {
    title: 'infusionAbateOperator',
    align:"center",
    dataIndex: 'infusionAbateOperator'
   },
   {
    title: 'infusionAbateDate',
    align:"center",
    dataIndex: 'infusionAbateDate'
   },
   {
    title: 'procDepartmentId',
    align:"center",
    dataIndex: 'procDepartmentId'
   },
   {
    title: 'procOperatorId',
    align:"center",
    dataIndex: 'procOperatorId'
   },
   {
    title: 'checkOperatorId',
    align:"center",
    dataIndex: 'checkOperatorId'
   },
   {
    title: 'procDate',
    align:"center",
    dataIndex: 'procDate'
   },
   {
    title: 'inoutNo',
    align:"center",
    dataIndex: 'inoutNo'
   },
   {
    title: 'itemNo',
    align:"center",
    dataIndex: 'itemNo'
   },
   {
    title: 'itemName',
    align:"center",
    dataIndex: 'itemName'
   },
   {
    title: 'drgSpec',
    align:"center",
    dataIndex: 'drgSpec'
   },
   {
    title: 'drgFormSort',
    align:"center",
    dataIndex: 'drgFormSort'
   },
   {
    title: 'mediSortId',
    align:"center",
    dataIndex: 'mediSortId'
   },
   {
    title: '中药付数',
    align:"center",
    dataIndex: 'herbsTimes'
   },
   {
    title: 'drgProductorId',
    align:"center",
    dataIndex: 'drgProductorId'
   },
   {
    title: 'amount',
    align:"center",
    dataIndex: 'amount'
   },
   {
    title: 'unit',
    align:"center",
    dataIndex: 'unit'
   },
   {
    title: 'price',
    align:"center",
    dataIndex: 'price'
   },
   {
    title: 'retailPrice',
    align:"center",
    dataIndex: 'retailPrice'
   },
   {
    title: 'totalFee',
    align:"center",
    dataIndex: 'totalFee'
   },
   {
    title: 'dosage',
    align:"center",
    dataIndex: 'dosage'
   },
   {
    title: 'singleUseAmount',
    align:"center",
    dataIndex: 'singleUseAmount'
   },
   {
    title: 'singleUseUnits',
    align:"center",
    dataIndex: 'singleUseUnits'
   },
   {
    title: 'usageSortId',
    align:"center",
    dataIndex: 'usageSortId'
   },
   {
    title: 'frequencySortId',
    align:"center",
    dataIndex: 'frequencySortId'
   },
   {
    title: 'perPackRetailAmount',
    align:"center",
    dataIndex: 'perPackRetailAmount'
   },
   {
    title: 'perRetailMinAmount',
    align:"center",
    dataIndex: 'perRetailMinAmount'
   },
   {
    title: 'doctorAdviceId',
    align:"center",
    dataIndex: 'doctorAdviceId'
   },
   {
    title: 'chargeMode',
    align:"center",
    dataIndex: 'chargeMode'
   },
   {
    title: 'charged',
    align:"center",
    dataIndex: 'charged'
   },
   {
    title: 'ihDrawNo',
    align:"center",
    dataIndex: 'ihDrawNo'
   },
   {
    title: 'drawPersonName',
    align:"center",
    dataIndex: 'drawPersonName'
   },
   {
    title: 'drawPersonNo',
    align:"center",
    dataIndex: 'drawPersonNo'
   },
   {
    title: 'status',
    align:"center",
    dataIndex: 'status'
   },
   {
    title: 'chargeSortId',
    align:"center",
    dataIndex: 'chargeSortId'
   },
   {
    title: 'backRatifier',
    align:"center",
    dataIndex: 'backRatifier'
   },
   {
    title: 'backReason',
    align:"center",
    dataIndex: 'backReason'
   },
   {
    title: 'remark',
    align:"center",
    dataIndex: 'remark'
   },
   {
    title: 'abateOperatorId',
    align:"center",
    dataIndex: 'abateOperatorId'
   },
   {
    title: 'abateDate',
    align:"center",
    dataIndex: 'abateDate'
   },
   {
    title: 'isCardCharge',
    align:"center",
    dataIndex: 'isCardCharge'
   },
   {
    title: 'isTreatRecipe',
    align:"center",
    dataIndex: 'isTreatRecipe'
   },
   {
    title: 'treatSortId',
    align:"center",
    dataIndex: 'treatSortId'
   },
   {
    title: 'treatNo',
    align:"center",
    dataIndex: 'treatNo'
   },
   {
    title: 'isLock',
    align:"center",
    dataIndex: 'isLock'
   },
   {
    title: 'isClinicSpecialDiagnose',
    align:"center",
    dataIndex: 'isClinicSpecialDiagnose'
   },
   {
    title: 'lastUpdateOperator',
    align:"center",
    dataIndex: 'lastUpdateOperator'
   },
   {
    title: 'lastUpdateStation',
    align:"center",
    dataIndex: 'lastUpdateStation'
   },
   {
    title: 'lastUpdateVersion',
    align:"center",
    dataIndex: 'lastUpdateVersion'
   },
   {
    title: 'lastUpdateTime',
    align:"center",
    dataIndex: 'lastUpdateTime'
   },
   {
    title: 'transFlag',
    align:"center",
    dataIndex: 'transFlag'
   },
   {
    title: 'transNo',
    align:"center",
    dataIndex: 'transNo'
   },
   {
    title: 'infusionNo',
    align:"center",
    dataIndex: 'infusionNo'
   },
   {
    title: 'skinDrugMinus',
    align:"center",
    dataIndex: 'skinDrugMinus'
   },
   {
    title: 'drgAcceptNo',
    align:"center",
    dataIndex: 'drgAcceptNo'
   },
   {
    title: 'lockDepartmentId',
    align:"center",
    dataIndex: 'lockDepartmentId'
   },
   {
    title: 'lockModuleId',
    align:"center",
    dataIndex: 'lockModuleId'
   },
   {
    title: 'lockOperatorId',
    align:"center",
    dataIndex: 'lockOperatorId'
   },
   {
    title: 'lockRemark',
    align:"center",
    dataIndex: 'lockRemark'
   },
   {
    title: 'lockTime',
    align:"center",
    dataIndex: 'lockTime'
   },
   {
    title: 'orderProcDate',
    align:"center",
    dataIndex: 'orderProcDate'
   },
   {
    title: 'skinTestResult',
    align:"center",
    dataIndex: 'skinTestResult'
   },
   {
    title: 'secretContent',
    align:"center",
    dataIndex: 'secretContent'
   },
   {
    title: 'commissionPersonName',
    align:"center",
    dataIndex: 'commissionPersonName'
   },
   {
    title: 'commissionPersonNo',
    align:"center",
    dataIndex: 'commissionPersonNo'
   },
   {
    title: 'commissionPhone',
    align:"center",
    dataIndex: 'commissionPhone'
   },
   {
    title: 'selfPayPercent',
    align:"center",
    dataIndex: 'selfPayPercent'
   },
   {
    title: 'isOutsideHospital',
    align:"center",
    dataIndex: 'isOutsideHospital'
   },
   {
    title: 'rxTraceCode',
    align:"center",
    dataIndex: 'rxTraceCode'
   },
   {
    title: 'hiRxNo',
    align:"center",
    dataIndex: 'hiRxNo'
   },
   {
    title: 'recipeCheckDate',
    align:"center",
    dataIndex: 'recipeCheckDate'
   },
   {
    title: 'recipeCheckOperatorId',
    align:"center",
    dataIndex: 'recipeCheckOperatorId'
   },
   {
    title: 'recipeCheckStatus',
    align:"center",
    dataIndex: 'recipeCheckStatus'
   },
   {
    title: 'recipeUncheckDate',
    align:"center",
    dataIndex: 'recipeUncheckDate'
   },
   {
    title: 'recipeUncheckOperatorId',
    align:"center",
    dataIndex: 'recipeUncheckOperatorId'
   },
];
//查询数据
export const searchFormSchema: FormSchema[] = [
	{
      label: "处方号：",
      field: 'recipeNo',
      component: 'Input',
      colProps: {span: 24},
 	},
	{
      label: "患者号：",
      field: 'patientNo',
      component: 'Input',
      colProps: {span: 24},
 	},
	{
      label: "患者名称：",
      field: 'patientName',
      component: 'Input',
      colProps: {span: 24},
 	},
];
//表单数据
export const formSchema: FormSchema[] = [
  {
    label: 'recipeListId',
    field: 'recipeListId',
    component: 'InputNumber',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入recipeListId!'},
          ];
     },
  },
  {
    label: 'patientSource',
    field: 'patientSource',
    component: 'InputNumber',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入patientSource!'},
          ];
     },
  },
  {
    label: 'recipeNo',
    field: 'recipeNo',
    component: 'Input',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入recipeNo!'},
          ];
     },
  },
  {
    label: 'groupNo',
    field: 'groupNo',
    component: 'Input',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入groupNo!'},
          ];
     },
  },
  {
    label: 'itemIndex',
    field: 'itemIndex',
    component: 'InputNumber',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入itemIndex!'},
          ];
     },
  },
  {
    label: 'drgRecipeSortId',
    field: 'drgRecipeSortId',
    component: 'InputNumber',
  },
  {
    label: 'recipeSortId',
    field: 'recipeSortId',
    component: 'InputNumber',
  },
  {
    label: 'hospPkno',
    field: 'hospPkno',
    component: 'Input',
  },
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
    label: 'caseNo',
    field: 'caseNo',
    component: 'Input',
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
    label: 'age',
    field: 'age',
    component: 'InputNumber',
  },
  {
    label: 'fareIdentity',
    field: 'fareIdentity',
    component: 'InputNumber',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入fareIdentity!'},
          ];
     },
  },
  {
    label: 'weight',
    field: 'weight',
    component: 'InputNumber',
  },
  {
    label: 'companyId',
    field: 'companyId',
    component: 'InputNumber',
  },
  {
    label: 'diagnoseName',
    field: 'diagnoseName',
    component: 'Input',
  },
  {
    label: 'personNo',
    field: 'personNo',
    component: 'Input',
  },
  {
    label: 'bedName',
    field: 'bedName',
    component: 'Input',
  },
  {
    label: 'inputCode',
    field: 'inputCode',
    component: 'Input',
  },
  {
    label: 'address',
    field: 'address',
    component: 'Input',
  },
  {
    label: 'ihDepartmentId',
    field: 'ihDepartmentId',
    component: 'InputNumber',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入ihDepartmentId!'},
          ];
     },
  },
  {
    label: 'currWardId',
    field: 'currWardId',
    component: 'InputNumber',
  },
  {
    label: 'departmentId',
    field: 'departmentId',
    component: 'InputNumber',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入departmentId!'},
          ];
     },
  },
  {
    label: 'doctorId',
    field: 'doctorId',
    component: 'InputNumber',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入doctorId!'},
          ];
     },
  },
  {
    label: 'operatorId',
    field: 'operatorId',
    component: 'InputNumber',
  },
  {
    label: 'operateDate',
    field: 'operateDate',
    component: 'DatePicker',
    componentProps: {
       showTime: true,
       valueFormat: 'YYYY-MM-DD HH:mm:ss'
     },
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入operateDate!'},
          ];
     },
  },
  {
    label: 'chargeDate',
    field: 'chargeDate',
    component: 'DatePicker',
    componentProps: {
       showTime: true,
       valueFormat: 'YYYY-MM-DD HH:mm:ss'
     },
  },
  {
    label: 'infusionDepartmentId',
    field: 'infusionDepartmentId',
    component: 'InputNumber',
  },
  {
    label: 'infusionStatus',
    field: 'infusionStatus',
    component: 'InputNumber',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入infusionStatus!'},
          ];
     },
  },
  {
    label: 'infusionProcOperator',
    field: 'infusionProcOperator',
    component: 'InputNumber',
  },
  {
    label: 'infusionProcDate',
    field: 'infusionProcDate',
    component: 'DatePicker',
    componentProps: {
       showTime: true,
       valueFormat: 'YYYY-MM-DD HH:mm:ss'
     },
  },
  {
    label: 'infusionAbateOperator',
    field: 'infusionAbateOperator',
    component: 'InputNumber',
  },
  {
    label: 'infusionAbateDate',
    field: 'infusionAbateDate',
    component: 'DatePicker',
    componentProps: {
       showTime: true,
       valueFormat: 'YYYY-MM-DD HH:mm:ss'
     },
  },
  {
    label: 'procDepartmentId',
    field: 'procDepartmentId',
    component: 'InputNumber',
  },
  {
    label: 'procOperatorId',
    field: 'procOperatorId',
    component: 'InputNumber',
  },
  {
    label: 'checkOperatorId',
    field: 'checkOperatorId',
    component: 'InputNumber',
  },
  {
    label: 'procDate',
    field: 'procDate',
    component: 'DatePicker',
    componentProps: {
       showTime: true,
       valueFormat: 'YYYY-MM-DD HH:mm:ss'
     },
  },
  {
    label: 'inoutNo',
    field: 'inoutNo',
    component: 'Input',
  },
  {
    label: 'itemNo',
    field: 'itemNo',
    component: 'Input',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入itemNo!'},
          ];
     },
  },
  {
    label: 'itemName',
    field: 'itemName',
    component: 'Input',
  },
  {
    label: 'drgSpec',
    field: 'drgSpec',
    component: 'Input',
  },
  {
    label: 'drgFormSort',
    field: 'drgFormSort',
    component: 'Input',
  },
  {
    label: 'mediSortId',
    field: 'mediSortId',
    component: 'InputNumber',
  },
  {
    label: '中药付数',
    field: 'herbsTimes',
    component: 'InputNumber',
  },
  {
    label: 'drgProductorId',
    field: 'drgProductorId',
    component: 'InputNumber',
  },
  {
    label: 'amount',
    field: 'amount',
    component: 'InputNumber',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入amount!'},
          ];
     },
  },
  {
    label: 'unit',
    field: 'unit',
    component: 'Input',
  },
  {
    label: 'price',
    field: 'price',
    component: 'Input',
  },
  {
    label: 'retailPrice',
    field: 'retailPrice',
    component: 'Input',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入retailPrice!'},
          ];
     },
  },
  {
    label: 'totalFee',
    field: 'totalFee',
    component: 'Input',
  },
  {
    label: 'dosage',
    field: 'dosage',
    component: 'Input',
  },
  {
    label: 'singleUseAmount',
    field: 'singleUseAmount',
    component: 'InputNumber',
  },
  {
    label: 'singleUseUnits',
    field: 'singleUseUnits',
    component: 'Input',
  },
  {
    label: 'usageSortId',
    field: 'usageSortId',
    component: 'InputNumber',
  },
  {
    label: 'frequencySortId',
    field: 'frequencySortId',
    component: 'InputNumber',
  },
  {
    label: 'perPackRetailAmount',
    field: 'perPackRetailAmount',
    component: 'InputNumber',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入perPackRetailAmount!'},
          ];
     },
  },
  {
    label: 'perRetailMinAmount',
    field: 'perRetailMinAmount',
    component: 'InputNumber',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入perRetailMinAmount!'},
          ];
     },
  },
  {
    label: 'doctorAdviceId',
    field: 'doctorAdviceId',
    component: 'InputNumber',
  },
  {
    label: 'chargeMode',
    field: 'chargeMode',
    component: 'InputNumber',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入chargeMode!'},
          ];
     },
  },
  {
    label: 'charged',
    field: 'charged',
    component: 'Input',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入charged!'},
          ];
     },
  },
  {
    label: 'ihDrawNo',
    field: 'ihDrawNo',
    component: 'Input',
  },
  {
    label: 'drawPersonName',
    field: 'drawPersonName',
    component: 'Input',
  },
  {
    label: 'drawPersonNo',
    field: 'drawPersonNo',
    component: 'Input',
  },
  {
    label: 'status',
    field: 'status',
    component: 'InputNumber',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入status!'},
          ];
     },
  },
  {
    label: 'chargeSortId',
    field: 'chargeSortId',
    component: 'InputNumber',
  },
  {
    label: 'backRatifier',
    field: 'backRatifier',
    component: 'Input',
  },
  {
    label: 'backReason',
    field: 'backReason',
    component: 'Input',
  },
  {
    label: 'remark',
    field: 'remark',
    component: 'Input',
  },
  {
    label: 'abateOperatorId',
    field: 'abateOperatorId',
    component: 'InputNumber',
  },
  {
    label: 'abateDate',
    field: 'abateDate',
    component: 'DatePicker',
    componentProps: {
       showTime: true,
       valueFormat: 'YYYY-MM-DD HH:mm:ss'
     },
  },
  {
    label: 'isCardCharge',
    field: 'isCardCharge',
    component: 'Input',
  },
  {
    label: 'isTreatRecipe',
    field: 'isTreatRecipe',
    component: 'Input',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入isTreatRecipe!'},
          ];
     },
  },
  {
    label: 'treatSortId',
    field: 'treatSortId',
    component: 'InputNumber',
  },
  {
    label: 'treatNo',
    field: 'treatNo',
    component: 'Input',
  },
  {
    label: 'isLock',
    field: 'isLock',
    component: 'Input',
  },
  {
    label: 'isClinicSpecialDiagnose',
    field: 'isClinicSpecialDiagnose',
    component: 'Input',
  },
  {
    label: 'lastUpdateOperator',
    field: 'lastUpdateOperator',
    component: 'InputNumber',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入lastUpdateOperator!'},
          ];
     },
  },
  {
    label: 'lastUpdateStation',
    field: 'lastUpdateStation',
    component: 'InputNumber',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入lastUpdateStation!'},
          ];
     },
  },
  {
    label: 'lastUpdateVersion',
    field: 'lastUpdateVersion',
    component: 'InputNumber',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入lastUpdateVersion!'},
          ];
     },
  },
  {
    label: 'lastUpdateTime',
    field: 'lastUpdateTime',
    component: 'DatePicker',
    componentProps: {
       showTime: true,
       valueFormat: 'YYYY-MM-DD HH:mm:ss'
     },
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入lastUpdateTime!'},
          ];
     },
  },
  {
    label: 'transFlag',
    field: 'transFlag',
    component: 'Input',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入transFlag!'},
          ];
     },
  },
  {
    label: 'transNo',
    field: 'transNo',
    component: 'Input',
  },
  {
    label: 'infusionNo',
    field: 'infusionNo',
    component: 'Input',
  },
  {
    label: 'skinDrugMinus',
    field: 'skinDrugMinus',
    component: 'InputNumber',
  },
  {
    label: 'drgAcceptNo',
    field: 'drgAcceptNo',
    component: 'Input',
  },
  {
    label: 'lockDepartmentId',
    field: 'lockDepartmentId',
    component: 'InputNumber',
  },
  {
    label: 'lockModuleId',
    field: 'lockModuleId',
    component: 'InputNumber',
  },
  {
    label: 'lockOperatorId',
    field: 'lockOperatorId',
    component: 'InputNumber',
  },
  {
    label: 'lockRemark',
    field: 'lockRemark',
    component: 'Input',
  },
  {
    label: 'lockTime',
    field: 'lockTime',
    component: 'DatePicker',
    componentProps: {
       showTime: true,
       valueFormat: 'YYYY-MM-DD HH:mm:ss'
     },
  },
  {
    label: 'orderProcDate',
    field: 'orderProcDate',
    component: 'DatePicker',
    componentProps: {
       showTime: true,
       valueFormat: 'YYYY-MM-DD HH:mm:ss'
     },
  },
  {
    label: 'skinTestResult',
    field: 'skinTestResult',
    component: 'InputNumber',
  },
  {
    label: 'secretContent',
    field: 'secretContent',
    component: 'Input',
  },
  {
    label: 'commissionPersonName',
    field: 'commissionPersonName',
    component: 'Input',
  },
  {
    label: 'commissionPersonNo',
    field: 'commissionPersonNo',
    component: 'Input',
  },
  {
    label: 'commissionPhone',
    field: 'commissionPhone',
    component: 'Input',
  },
  {
    label: 'selfPayPercent',
    field: 'selfPayPercent',
    component: 'InputNumber',
  },
  {
    label: 'isOutsideHospital',
    field: 'isOutsideHospital',
    component: 'Input',
  },
  {
    label: 'rxTraceCode',
    field: 'rxTraceCode',
    component: 'Input',
  },
  {
    label: 'hiRxNo',
    field: 'hiRxNo',
    component: 'Input',
  },
  {
    label: 'recipeCheckDate',
    field: 'recipeCheckDate',
    component: 'DatePicker',
    componentProps: {
       showTime: true,
       valueFormat: 'YYYY-MM-DD HH:mm:ss'
     },
  },
  {
    label: 'recipeCheckOperatorId',
    field: 'recipeCheckOperatorId',
    component: 'InputNumber',
  },
  {
    label: 'recipeCheckStatus',
    field: 'recipeCheckStatus',
    component: 'InputNumber',
  },
  {
    label: 'recipeUncheckDate',
    field: 'recipeUncheckDate',
    component: 'DatePicker',
    componentProps: {
       showTime: true,
       valueFormat: 'YYYY-MM-DD HH:mm:ss'
     },
  },
  {
    label: 'recipeUncheckOperatorId',
    field: 'recipeUncheckOperatorId',
    component: 'InputNumber',
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
