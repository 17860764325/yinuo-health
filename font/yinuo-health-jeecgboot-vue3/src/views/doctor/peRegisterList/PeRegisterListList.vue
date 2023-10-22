<template>
  <div>
    <!--引用表格-->
    <BasicTable @register="registerTable" :rowSelection="rowSelection" :loading="loading"
      @selection-change="sleectChange">
      <!--插槽:table标题-->
      <template #tableTitle>
        <!--          <a-button type="primary" @click="handleAdd" preIcon="ant-design:plus-outlined"> 新增</a-button>-->
        <!--          <a-button  type="primary" preIcon="ant-design:export-outlined" @click="onExportXls"> 导出</a-button>-->
        <!--          <j-upload-button  type="primary" preIcon="ant-design:import-outlined" @click="onImportXls">导入</j-upload-button>-->

<!--        <a-button type="primary" @click="newLogTestClick" > 新日志测试</a-button>-->
        <a-button type="primary" @click="buttonAllClick" v-if="!isDisabledAuth('doctor:pe_register_list:buttonAll')">  条码生成</a-button>
        <a-button type="primary" @click="personSearchClick" :icon="h(SearchOutlined)" v-if="!isDisabledAuth('doctor:pe_register_list:personSearch')"> 人员档案查询</a-button>
        <a-button type="primary" @click="personCreateClick" preIcon="ant-design:plus-outlined" v-if="!isDisabledAuth('doctor:pe_register_list:personCreate')"> 创建人员档案</a-button>
        <a-button type="primary" @click="LISApply" :icon="h(ArrowUpOutlined)" :disabled="LISApplyDisabled" v-if="!isDisabledAuth('doctor:pe_register_list:LISApply')"> LIS检验申请
        </a-button>
        <a-button type="primary" @click="barCodebuildClick" :icon="h(BarcodeOutlined)" :disabled="barCodeBuildDisabled" v-if="!isDisabledAuth('doctor:pe_register_list:barCodeBuild')">
          条码生成old</a-button>
        <a-button type="primary" @click="reportSearchClick" :icon="h(FileSearchOutlined)"
          :disabled="reportSearchDisabled" v-if="!isDisabledAuth('doctor:pe_register_list:reportSearch')"> 报告查询</a-button>

        <a-dropdown v-if="selectedRowKeys.length > 0">
          <template #overlay>
            <a-menu>
              <!--                  批量操作的按钮在这里定义-->
              <a-menu-item key="1" @click="personSearchClick">
                <SearchOutlined/>
                人员信息查询
              </a-menu-item>
              <a-menu-item key="1" @click="personCreateClick">
                <Icon icon="ant-design:plus-outlined"></Icon>
                创建人员档案
              </a-menu-item>
              <a-menu-item key="2" @click="LISApply">
                <ArrowUpOutlined />
                LIS检验申请
              </a-menu-item>
              <a-menu-item key="3" @click="barCodebuildClick">
                <BarcodeOutlined />
                条码打印
              </a-menu-item>
              <a-menu-item key="4" @click="reportSearchClick">
                <FileSearchOutlined />
                报告生成
              </a-menu-item>
            </a-menu>
          </template>
          <a-button>批量操作
            <Icon icon="mdi:chevron-down"></Icon>
          </a-button>
        </a-dropdown>
      </template>

      <template #isLisApply="{ record }">
        <Tag :color="soltColor(record.isLisApply)">
          {{ soltFontSize(record.isLisApply) }}
        </Tag>
      </template>

      <template #isBarCodeBuild="{ record }">
        <Tag :color="soltColor(record.isBarCodeBuild)">
          {{ soltFontSize(record.isBarCodeBuild) }}
        </Tag>
      </template>

      <template #isReport="{ record }">
        <Tag :color="soltColor(record.isReport)">
          {{ soltFontSize(record.isReport) }}
        </Tag>
      </template>

      <!--操作栏-->
      <template #action="{ record }">
        <TableAction :actions="getTableAction(record)" :dropDownActions="getDropDownAction(record)" />
      </template>
      <!--字段回显插槽-->
      <template #htmlSlot="{ text }">
        <div v-html="text"></div>
      </template>
      <!--省市区字段回显插槽-->
      <template #pcaSlot="{ text }">
        {{ getAreaTextByCode(text) }}
      </template>
      <template #fileSlot="{ text }">
        <span v-if="!text" style="font-size: 12px;font-style: italic;">无文件</span>
        <a-button v-else :ghost="true" type="primary" preIcon="ant-design:download-outlined" size="small"
          @click="downloadFile(text)">下载
        </a-button>
      </template>
    </BasicTable>
    <!-- 表单区域 -->
    <PeRegisterListModal @register="registerModal" @success="handleSuccess"></PeRegisterListModal>
    <Modal @register="registerLisApplyModal" :ids="ids" @lodding="loading = true" @success="handleSuccess"></Modal>
  </div>
</template>

<script lang="ts" name="doctor-peRegisterList" setup>
import { ref, computed, unref } from 'vue';
import { BasicTable, useTable, TableAction } from '/@/components/Table';
import { useModal } from '/@/components/Modal';
import { useListPage } from '/@/hooks/system/useListPage'
import PeRegisterListModal from './components/PeRegisterListModal.vue'
import { columns, searchFormSchema } from './PeRegisterList.data';
import {
  list,
  deleteOne,
  batchDelete,
  getImportUrl,
  getExportUrl,
  personSearch,
  personCreate,
  barCodeBuild,
  reportSearch,
  buttonAll,
  newLogTest
} from './PeRegisterList.api';
import { downloadFile } from '/@/utils/common/renderUtils';
import { getAreaTextByCode } from "../../../components/Form/src/utils/Area";
import { h } from 'vue';
import { SearchOutlined, ArrowUpOutlined, BarcodeOutlined, FileSearchOutlined } from '@ant-design/icons-vue';
import { useMessage } from "@/hooks/web/useMessage";
import Modal from './components/LISApplyModal.vue';
import { Tag, Avatar } from 'ant-design-vue';
import {usePermission} from "@/hooks/web/usePermission";

const { createMessage, createErrorModal, createConfirm } = useMessage();

const checkedKeys = ref<Array<string | number>>([]);
//注册model
const [registerModal, { openModal }] = useModal();
// 注册LIS申请的弹窗
const [registerLisApplyModal, { openModal: openLisApplyModal, closeModal: closeLisApplyModal, setModalProps: setLisApplyModalProps }] = useModal();
const { isDisabledAuth } = usePermission();
//注册table数据
const { prefixCls, tableContext, onExportXls, onImportXls } = useListPage({
  tableProps: {
    title: '人员信息查询',
    api: list,
    columns,
    canResize: false,
    formConfig: {
      //labelWidth: 120,
      schemas: searchFormSchema,
      autoSubmitOnEnter: true,
      showAdvancedButton: true,
      fieldMapToNumber: [],
      fieldMapToTime: [
        ['peDate', ['peDate_begin', 'peDate_end'], 'YYYY-MM-DD HH:mm:ss'],
      ],
    },
    actionColumn: {
      width: 120,
      fixed: 'right'
    },
    // 设置列表查询的排序字段，默认是 createTime 。
    defSort: { column: 'patientNo', order: 'desc' }
  },
  exportConfig: {
    name: "人员信息查询",
    url: getExportUrl,
  },
  importConfig: {
    url: getImportUrl,
    success: handleSuccess
  },
})

const allButtonShow = ref()

const [registerTable, { reload, setLoading, clearSelectedRowKeys }, { rowSelection, selectedRowKeys }] = tableContext

// LIS检验申请是否需要禁用
const LISApplyDisabled = ref(false)
// 条码生成是否禁用
const barCodeBuildDisabled = ref(false)

// 报告查询是否禁用
const reportSearchDisabled = ref(false)

// 创建档案按钮是否禁用
const personCreateDisabled = ref(false)
const loading = ref(false)
// 获取选中人员
const ids = ref<Array<String>>([])

/**
 * 选中修改事件
 */
function sleectChange({ keys, rows }) {
  console.log(123)
  // rows：你选中的那些行的数据
  LISApplyDisabled.value = false
  barCodeBuildDisabled.value = false
  // reportSearchDisabled.value = false
  rows.map(item => {
    if (item.isLisApply === '1') {
      LISApplyDisabled.value = true
    }
    if (item.isBarCodeBuild === '1' || item.isLisApply !== '1') {
      barCodeBuildDisabled.value = true
    }
    // if (item.isReport === '1' || item.isLisApply !== '1' || item.isBarCodeBuild !== '1') {
    //   reportSearchDisabled.value = true
    // }
  })
}

/**
 * 人员档案查询
 */
async function personSearchClick() {
  // 如果没有选中的数据提示请选择消息
  if (rowSelection.selectedRows.length === 0) {
    createMessage.warning("请选择数据！")
  } else {
    // 将选中行的 ids 传到后端，调用人员查询接口
    const ids = ref<Array<String>>([])
    rowSelection.selectedRows.forEach(item => {
      ids.value.push(item.id)
    })
    console.log(ids.value)
    loading.value = true
    await personSearch(ids.value).then(res => {
      createConfirm({
        iconType: 'info',
        title: '返回结果',
        content: res,
        okText: '确认',
        onOk: function () {
          reload()
          clearSelectedRowKeys()
          loading.value = false
        },
        onCancel: function () {
          reload()
          clearSelectedRowKeys()
          loading.value = false
        },
      });
    })
  }
}

/**
 * 创建档案
 */
async function personCreateClick() {
  // 如果没有选中的数据提示请选择消息
  if (rowSelection.selectedRows.length === 0) {
    createMessage.warning("请选择数据！")
  } else {
    // 发送请求创建人员档案，携带人员的ID
    const ids = ref<Array<String>>([])
    rowSelection.selectedRows.forEach(item => {
      // 筛选出，没有 患者id 的数据 的 id 传输到后端，有patId的不需要创建档案
      if (item.patId === undefined || item.patId === null || item.patId === '') {
        ids.value.push(item.id)
      }
    })

    if (ids.value.length > 0) {
      loading.value = true
      await personCreate(ids.value).then(res => {
        createConfirm({
          iconType: 'info',
          title: '返回结果',
          content: res,
          okText: '确认',
          onOk: function () {
            reload()
            clearSelectedRowKeys()
            loading.value = false
          },
          onCancel: function () {
            reload()
            clearSelectedRowKeys()
            loading.value = false
          },
        });
      })
    } else {
      createMessage.warning("请选择没有维护“患者id”的数据！")
    }
  }
}

/**
 * LIS检验申请提交
 */
async function LISApply() {
  // 如果没有选中的数据提示请选择消息
  if (rowSelection.selectedRows.length === 0) {
    createMessage.warning("请选择数据！")
  } else {
    rowSelection.selectedRows.forEach(item => {
      // 筛选出，有 患者id 的数据 的 id 传输到后端，有patId的不需要创建档案
      if (item.patId !== undefined && item.patId !== null && item.patId !== '') {
        ids.value.push(item.id)
      }
    })
    // 如果选择的人都是没有患者id的那么就提示
    if (ids.value.length > 0) {
      // 弹出表单弹窗
      setLisApplyModalProps({ useWrapper: true });
      openLisApplyModal(true, {
        ids: ids.value,
        type: 'lis'
      });
    } else {
      createMessage.warning("请选择维护了“患者id”的数据！");
    }

  }
}

/**
 * 条码生成
 */
async function barCodebuildClick() {
  if (rowSelection.selectedRows.length === 0) {
    createMessage.warning("请选择数据！")
  } else {
    const ids = ref<Array<String>>([])
    rowSelection.selectedRows.forEach(item => {
      ids.value.push(item.id)
    })
    loading.value = true
    await barCodeBuild(ids.value).then(res => {
      createConfirm({
        iconType: 'info',
        title: '返回结果',
        content: res,
        okText: '确认',
        onOk: function () {
          reload()
        },
        onCancel: function () {
          reload()
        },
      });
      loading.value = false
      clearSelectedRowKeys()
    })
  }
}

/**
 * 报告查询维护
 */
async function reportSearchClick() {
  if (rowSelection.selectedRows.length === 0) {
    createMessage.warning("请选择数据！")
  } else {
    const ids = ref<Array<String>>([])
    rowSelection.selectedRows.forEach(item => {
      ids.value.push(item.id)
    })
    loading.value = true
    await reportSearch(ids.value).then(res => {
      createConfirm({
        iconType: 'info',
        title: '返回结果',
        content: res,
        okText: '确认',
        onOk: function () {
          reload()
        },
        onCancel: function () {
          reload()
        },
      });
      loading.value = false
      clearSelectedRowKeys()
    })
  }
}

/**
 * 完成插槽，字以及颜色
 */
function soltFontSize(value: string) {
  if (value === '1') {
    return '已完成'
  } else {
    return '未完成'
  }
}

function soltColor(value: string) {
  if (value === '1') {
    return 'green'
  } else {
    return 'red'
  }
}

/**
 * 多重操作
 */
async function buttonAllClick() {
  // 如果没有选中的数据提示请选择消息
  if (rowSelection.selectedRows.length === 0) {
    createMessage.warning("请选择数据！")
  } else {
    rowSelection.selectedRows.forEach(item => {
        ids.value.push(item.id)
    })
    // 如果选择的人都是没有患者id的那么就提示
    if (ids.value.length > 0) {
      // 弹出表单弹窗
      setLisApplyModalProps({ useWrapper: true });
      openLisApplyModal(true, {
        ids: ids.value,
        type: 'all'
      });
    } else {
      createMessage.warning("请选择维护了“患者id”的数据！");
    }

  }
}


async function newLogTestClick() {
  // 如果没有选中的数据提示请选择消息
  if (rowSelection.selectedRows.length === 0) {
    createMessage.warning("请选择数据！")
  } else {
    // 将选中行的 ids 传到后端，调用人员查询接口
    const ids = ref<Array<String>>([])
    rowSelection.selectedRows.forEach(item => {
      ids.value.push(item.id)
    })
    console.log(ids.value)
    await newLogTest(ids.value).then(res => {
      createConfirm({
        iconType: 'info',
        title: '返回结果',
        content: res,
        okText: '确认',
        onOk: function () {
          reload()
        },
        onCancel: function () {
          reload()
        },
      });
    })
  }
}

/**
 * 新增事件
 */
function handleAdd() {
  openModal(true, {
    isUpdate: false,
    showFooter: true,
  });
}

/**
 * 编辑事件
 */
function handleEdit(record: Recordable) {
  openModal(true, {
    record,
    isUpdate: true,
    showFooter: true,
  });
}

/**
 * 详情
 */
function handleDetail(record: Recordable) {
  openModal(true, {
    record,
    isUpdate: true,
    showFooter: false,
  });
}

/**
 * 删除事件
 */
async function handleDelete(record) {
  await deleteOne({ id: record.id }, handleSuccess);
}

/**
 * 批量删除事件
 */
async function batchHandleDelete() {
  await batchDelete({ ids: selectedRowKeys.value }, handleSuccess);
}

/**
 * 成功回调
 */
function handleSuccess() {
  loading.value = false
  clearSelectedRowKeys()
  ids.value = [];
  (selectedRowKeys.value = []) && reload();
}

/**
 * 操作栏
 */
function getTableAction(record) {
  return [
    // {
    //   label: '编辑',
    //   onClick: handleEdit.bind(null, record),
    // }
    {
      label: '详情',
      onClick: handleDetail.bind(null, record),
    }
  ]
}

/**
 * 下拉操作栏
 */
function getDropDownAction(record) {
  return [
    // {
    //   label: '详情',
    //   onClick: handleDetail.bind(null, record),
    // }
  ]
}


</script>

<style scoped></style>
