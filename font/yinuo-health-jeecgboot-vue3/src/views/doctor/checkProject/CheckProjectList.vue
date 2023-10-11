<template>
  <div>
    <!--引用表格-->
    <BasicTable @register="registerTable" :rowSelection="rowSelection" :expandedRowKeys="expandedRowKeys"  @expand="handleExpand" @selection-change="selectRowChang">
      <!-- 内嵌table区域 begin -->
      <template #expandedRowRender="{record}">
        <a-tabs tabPosition="top">
          <a-tab-pane tab="项目详情表" key="checkProjectDetail" forceRender>
<!--            主子表数据纽带-->
            <checkProjectDetailSubTable :id="record.labItemId"/>
          </a-tab-pane>
        </a-tabs>
      </template>
      <!-- 内嵌table区域 end -->
      <!--插槽:table标题-->
      <template #tableTitle>
        <a-button type="primary" @click="handleAdd" v-auth="'doctor:check_project:add'"  preIcon="ant-design:plus-outlined"> 新增</a-button>
        <a-button  type="primary" preIcon="ant-design:export-outlined" @click="onExportXls"> 导出</a-button>
        <j-upload-button  type="primary" preIcon="ant-design:import-outlined" v-auth="'doctor:check_project:import'" @click="onImportXls">导入</j-upload-button>
        <a-button  type="primary"  v-auth="'doctor:check_project:async'" @click="asyncProject" :icon="h(CloudSyncOutlined)">同步数据</a-button>
        <a-dropdown v-if="selectedRowKeys.length > 0">
          <template #overlay>
            <a-menu>
              <a-menu-item key="1" @click="batchHandleDelete">
                <Icon icon="ant-design:delete-outlined"></Icon>
                删除
              </a-menu-item>
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
        <a-button v-else :ghost="true" type="primary" preIcon="ant-design:download-outlined" size="small" @click="downloadFile(text)">下载</a-button>
      </template>
    </BasicTable>
    <!-- 表单区域 -->
    <CheckProjectModal @register="registerModal" @success="handleSuccess"></CheckProjectModal>
  </div>
</template>

<script lang="ts" name="doctor-checkProject" setup>
import {ref, computed, unref} from 'vue';
import {BasicTable, useTable, TableAction} from '/@/components/Table';
import { useListPage } from '/@/hooks/system/useListPage'
import {useModal} from '/@/components/Modal';
import CheckProjectModal from './components/CheckProjectModal.vue'
import CheckProjectDetailSubTable from './subTables/CheckProjectDetailSubTable.vue'
import {columns, searchFormSchema} from './CheckProject.data';
import {list, deleteOne, batchDelete, getImportUrl,getExportUrl,async} from './CheckProject.api';
import {downloadFile} from '/@/utils/common/renderUtils';
import {useMessage} from "@/hooks/web/useMessage";
import { h } from 'vue';
import {CloudSyncOutlined, LoadingOutlined} from "@ant-design/icons-vue";
// 展开key
const expandedRowKeys = ref<any[]>([]);
//注册model
const [registerModal, {openModal}] = useModal();
//注册table数据
const { prefixCls,tableContext,onExportXls,onImportXls } = useListPage({
  tableProps:{
    title: '项目表',
    api: list,
    columns,
    canResize:false,
    formConfig: {
      //labelWidth: 120,
      schemas: searchFormSchema,
      autoSubmitOnEnter:true,
      showAdvancedButton:true,
      fieldMapToNumber: [
      ],
      fieldMapToTime: [
      ],
    },
    actionColumn: {
      width: 120,
      fixed:'right'
    },
  },
  exportConfig: {
    name:"项目表",
    url: getExportUrl,
  },
  importConfig: {
    url: getImportUrl,
    success: handleSuccess
  },
})

const [registerTable, {reload},{ rowSelection, selectedRowKeys }] = tableContext
// 展示提示消息
const { createMessage, createErrorModal } = useMessage();
/**
 * 数据同步
 * */
async function asyncProject(){
  await async().then((res)=>{
    console.log(res)
    createMessage.success(res);
    reload()
  })
}

/**
 * 行选中变化方法
 * */
function selectRowChang({keys,rows}){
  console.log(rows)
  console.log(keys)
}
/**
 * 展开事件
 * */
function handleExpand(expanded, record){
  console.log(expanded)
  console.log(record)
  expandedRowKeys.value=[];
  if (expanded === true) {
    // 主子表主键
    expandedRowKeys.value.push(record.id)
  }
  console.log(expandedRowKeys.value)
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
  await batchDelete({ids: selectedRowKeys.value},handleSuccess);
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
function getTableAction(record){
  return [
    {
      label: '编辑',
      onClick: handleEdit.bind(null, record),
    }
  ]
}


/**
 * 下拉操作栏
 */
function getDropDownAction(record){
  return [
    {
      label: '详情',
      onClick: handleDetail.bind(null, record),
    }, {
      label: '删除',
      popConfirm: {
        title: '是否确认删除',
        confirm: handleDelete.bind(null, record),
      }
    }
  ]
}

</script>

<style scoped>

</style>
