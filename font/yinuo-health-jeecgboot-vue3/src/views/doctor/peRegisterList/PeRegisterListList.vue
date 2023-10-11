<template>
  <div>
    <!--引用表格-->
    <BasicTable @register="registerTable" :rowSelection="rowSelection" @selection-change="sleectChange">
      <!--插槽:table标题-->
      <template #tableTitle>
        <!--          <a-button type="primary" @click="handleAdd" preIcon="ant-design:plus-outlined"> 新增</a-button>-->
        <!--          <a-button  type="primary" preIcon="ant-design:export-outlined" @click="onExportXls"> 导出</a-button>-->
        <!--          <j-upload-button  type="primary" preIcon="ant-design:import-outlined" @click="onImportXls">导入</j-upload-button>-->
        <a-button type="primary" @click="" :icon="h(SearchOutlined)"> 人员档案查询</a-button>
        <a-button type="primary" @click="" preIcon="ant-design:plus-outlined"> 创建人员档案</a-button>
        <a-button type="primary" @click="" :icon="h(ArrowUpOutlined)" :disabled="LISApplyDisabled"> LIS检验申请
        </a-button>
        <a-button type="primary" @click="" :icon="h(BarcodeOutlined)"> 条码打印</a-button>
        <a-button type="primary" @click="" :icon="h(FileSearchOutlined)"> 报告查询</a-button>

        <a-dropdown v-if="selectedRowKeys.length > 0">
          <template #overlay>
            <a-menu>
              <!--                  批量操作的按钮在这里定义-->
              <!--                  <a-menu-item key="1" @click="batchHandleDelete">-->
              <!--                    <Icon icon="ant-design:delete-outlined"></Icon>-->
              <!--                    删除-->
              <!--                  </a-menu-item>-->
            </a-menu>
          </template>
          <a-button>批量操作
            <Icon icon="mdi:chevron-down"></Icon>
          </a-button>
        </a-dropdown>
      </template>
      <!--操作栏-->
      <template #action="{ record }">
        <TableAction :actions="getTableAction(record)" :dropDownActions="getDropDownAction(record)"/>
      </template>
      <!--字段回显插槽-->
      <template #htmlSlot="{text}">
        <div v-html="text"></div>
      </template>
      <!--省市区字段回显插槽-->
      <template #pcaSlot="{text}">
        {{ getAreaTextByCode(text) }}
      </template>
      <template #fileSlot="{text}">
        <span v-if="!text" style="font-size: 12px;font-style: italic;">无文件</span>
        <a-button v-else :ghost="true" type="primary" preIcon="ant-design:download-outlined" size="small"
                  @click="downloadFile(text)">下载
        </a-button>
      </template>
    </BasicTable>
    <!-- 表单区域 -->
    <PeRegisterListModal @register="registerModal" @success="handleSuccess"></PeRegisterListModal>
  </div>
</template>

<script lang="ts" name="doctor-peRegisterList" setup>
import {ref, computed, unref} from 'vue';
import {BasicTable, useTable, TableAction} from '/@/components/Table';
import {useModal} from '/@/components/Modal';
import {useListPage} from '/@/hooks/system/useListPage'
import PeRegisterListModal from './components/PeRegisterListModal.vue'
import {columns, searchFormSchema} from './PeRegisterList.data';
import {list, deleteOne, batchDelete, getImportUrl, getExportUrl} from './PeRegisterList.api';
import {downloadFile} from '/@/utils/common/renderUtils';
import {getAreaTextByCode} from "../../../components/Form/src/utils/Area";
import {h} from 'vue';
import {SearchOutlined, ArrowUpOutlined, BarcodeOutlined, FileSearchOutlined} from '@ant-design/icons-vue';

const checkedKeys = ref<Array<string | number>>([]);
//注册model
const [registerModal, {openModal}] = useModal();
//注册table数据
const {prefixCls, tableContext, onExportXls, onImportXls} = useListPage({
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
    defSort: {column: 'patientNo', order: 'desc'}
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

const [registerTable, {reload}, {rowSelection, selectedRowKeys}] = tableContext

// LIS检验申请是否需要禁用
const LISApplyDisabled = ref(false)

/**
 * 选中修改事件
 */
function sleectChange({keys, rows}) {
  if (rows.length > 1) {
    LISApplyDisabled.value = true;
  } else {
    LISApplyDisabled.value = false;
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
  await deleteOne({id: record.id}, handleSuccess);
}

/**
 * 批量删除事件
 */
async function batchHandleDelete() {
  await batchDelete({ids: selectedRowKeys.value}, handleSuccess);
}

/**
 * 成功回调
 */
function handleSuccess() {
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

<style scoped>

</style>
