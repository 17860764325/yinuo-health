<template>
  <BasicModal v-bind="$attrs" @register="registerModal" title="LIS检验申请" @ok="handleSubmit" @cancel="cancel" :helpMessage="['请选择你提交的所有人员的人员类型']">
    <BasicForm @register="registerForm"  style="margin-top: 50px; margin-left: 50px" />
  </BasicModal>
</template>
<script lang="ts" setup>
import {defineComponent, unref} from 'vue';
const {createMessage, createErrorModal, createConfirm} = useMessage();
import {BasicModal, useModalInner} from '/@/components/Modal';
//引入依赖
import { useForm, BasicForm, FormSchema } from '/@/components/Form';
// 引入后端传输请求方法
import {LISApply, saveOrUpdate} from '../PeRegisterList.api'
import {useMessage} from "@/hooks/web/useMessage";
// Emits声明
const emit = defineEmits(['success','register']);
// vue3的方式接受参数
const props = defineProps({
  ids:{
    type:Array,
  }
});
//自定义表单字段
const formSchemas: FormSchema[] = [
  {
    label: '患者类型：',
    field: 'patType',
    component: 'JDictSelectTag',
    componentProps: {
      dictCode: 'pat_type',
    },
    labelWidth: 50,
    //标题长度，超过位数隐藏
    labelLength: 2,
    //一列占比总共24，比如一行显示2列
    colProps: { span: 15 },
    dynamicRules: ({model, schema}) => {
      return [
        {required: true, message: '请选择患者类型!'},
      ];
    },
  },
];

/**
 * BasicForm绑定注册;
 * useForm 是整个框架的核心用于表单渲染，里边封装了很多公共方法;
 * 支持（schemas: 渲染表单列，autoSubmitOnEnter：回车提交,submitButtonOptions：自定义按钮文本和图标等方法）；
 * 平台通过此封装，简化了代码，支持自定义扩展;
 */
const [registerForm,{resetFields,getFieldsValue,validate}] = useForm({
  //注册表单列
  schemas: formSchemas,
  //回车提交
  autoSubmitOnEnter: true,
  //不显示重置按钮
  showResetButton: false,
  showSubmitButton:false,
  //自定义提交按钮文本和图标
  // submitButtonOptions: { text: '提交', preIcon: '' },
  //查询列占比 24代表一行 取值范围 0-24
  actionColOptions: { span: 17 },
});

//表单赋值
const [registerModal, {setModalProps, closeModal,redoModalHeight}] = useModalInner(async (data) => {
  // 可以直接通过openModal传递参数
  console.log(data)
  // 刷新表单参数
  await resetFields();
});
/**
 * 点击提交按钮的value值
 * @param values
 */
async function handleSubmit(values: any) {
  // 判断表单是否已经填写
  try {
    let values = await validate();
    values.patIds = props.ids
    setModalProps({confirmLoading: true});
    //提交表单
    await LISApply(values).then(res => {
      createConfirm({
        iconType: 'info',
        title: '返回结果',
        content: res,
        okText: '确认',
        onOk: function () {
          //关闭弹窗
          closeModal();
          //刷新列表
          emit('success');
        },
        onCancel: function () {
          //关闭弹窗
          closeModal();
          //刷新列表
          emit('success');
        },
      });
    });
  } finally {
    setModalProps({confirmLoading: false});
  }
  console.log('提交按钮数据::::', getFieldsValue());
  // 也可以通过vue3的 props藜麦拿货渠道父组件传输到子组件的参数
  console.log(props.ids)
  emit("success")
}

/**
 * 取消按钮
 */
function cancel(){
  emit("success")
}
</script>
