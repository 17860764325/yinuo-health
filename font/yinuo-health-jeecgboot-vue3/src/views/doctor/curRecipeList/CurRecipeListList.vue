<template>
  <div class="cur-recipe-list-container">
    <a-row :gutter="16">
      <!-- 左侧列表 - 处方号去重 -->
      <a-col :span="6">
        <BasicTable
          @register="registerLeftTable"
          :rowSelection="leftRowSelection"
          :clickable-row="false"
        >
          <template #tableTitle>
            <div class="table-title">
              <a-tag v-if="selectedRecipeNo" color="blue">已选: {{ selectedPatientName }}</a-tag>
            </div>
            <a-space style="margin-left: 16px;">
              <a-button type="primary" @click="boilMedicine" :disabled="!selectedRecipeNo">
                中药煎药
              </a-button>
            </a-space>
          </template>
        </BasicTable>
      </a-col>

      <!-- 右侧列表 - 处方明细 -->
      <a-col :span="18">
        <BasicTable @register="registerRightTable">
          <template #tableTitle>
            <div class="table-title">
              <span>处方明细</span>
              <a-tag v-if="selectedRecipeNo" color="green">处方号: {{ selectedRecipeNo }}</a-tag>
            </div>
          </template>
          <template #action="{ record }">
            <TableAction :actions="getTableAction(record)" :dropDownActions="getDropDownAction(record)"/>
          </template>
        </BasicTable>
      </a-col>
    </a-row>
    <!-- 表单区域 -->
    <CurRecipeListModal @register="registerModal" @success="handleSuccess"></CurRecipeListModal>
  </div>
</template>

<script lang="ts" setup>
import {ref} from 'vue';
import {BasicTable, useTable, TableAction} from '/@/components/Table';
import {useModal} from '/@/components/Modal';
import CurRecipeListModal from './components/CurRecipeListModal.vue';
import {searchFormSchema, leftColumns, rightColumns} from './CurRecipeList.data';
import {list, deleteOne, boilMedicineBack} from './CurRecipeList.api';
import {useMessage} from "@/hooks/web/useMessage";

const {createMessage} = useMessage();

// 选中的处方号和人名
const selectedRecipeNo = ref<string>('');
const selectedPatientName = ref<string>('');

// 注册model
const [registerModal, {openModal}] = useModal();

// 左侧行选择配置
const leftRowSelection = {
  type: 'radio',
  columnWidth: 50,
  onChange: (selectedRowKeys, selectedRows) => {
    if (selectedRows.length > 0) {
      const record = selectedRows[0];
      selectedRecipeNo.value = record.recipeNo;
      selectedPatientName.value = record.patientName;
      // 刷新右侧表格
      reloadRight();
    }
  },
};

// 左侧表格 - 处方列表
const [registerLeftTable, leftTableContext] = useTable({
  api: list,
  columns: leftColumns,
  rowKey: 'recipeNo',
  canResize: false,
  showIndexColumn: false,
  minHeight: 500,
  useSearchForm: true,
  formConfig: {
    schemas: searchFormSchema,
    autoSubmitOnEnter: true,
    showAdvancedButton: true,
    fieldMapToNumber: [],
    fieldMapToTime: [],
  },
  afterFetch: (data) => {
    // 按处方号去重，只取第一条的 patientName 和 patientNo
    const uniqueMap = new Map();
    data.forEach((item) => {
      if (item.recipeNo && !uniqueMap.has(item.recipeNo)) {
        uniqueMap.set(item.recipeNo, {
          recipeNo: item.recipeNo,
          patientName: item.patientName,
          patientNo: item.patientNo,
        });
      }
    });
    // 返回去重后的数据
    return Array.from(uniqueMap.values());
  },
  handleSearchInfoFn: (res) => {
    // 手动设置搜索信息，包括去重后的总数
    return {
      total: res.data ? res.data.length : 0,
      current: res.current,
      pageSize: res.pageSize,
    };
  },
  immediate: true,
});
const {reload: reloadLeft} = leftTableContext;

// 右侧表格 - 处方明细
const [registerRightTable, rightTableContext] = useTable({
  api: list,
  columns: rightColumns,
  rowKey: "recipeListId",
  canResize: false,
  useSearchForm: false,
  formConfig: false,
  beforeFetch: (params) => {
    if (selectedRecipeNo.value) {
      return {...params, recipeNo: selectedRecipeNo.value};
    }
    return params;
  },
  actionColumn: {
    width: 120,
    fixed: 'right'
  },
  immediate: false,
});
const {reload: reloadRight, getDataSource} = rightTableContext;

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
 * 成功回调
 */
function handleSuccess() {
  reloadLeft();
  if (selectedRecipeNo.value) {
    reloadRight();
  }
}

/**
 * 操作栏
 */
function getTableAction(record) {
  return [
    {
      label: '编辑',
      onClick: handleEdit.bind(null, record),
    }
  ];
}

/**
 * 下拉操作栏
 */
function getDropDownAction(record) {
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
  ];
}

/**
 * 中药煎药
 */
async function boilMedicine() {
  if (!selectedRecipeNo.value) {
    createMessage.warning("请先选择处方！");
    return;
  }
  try {
    await boilMedicineBack(selectedRecipeNo.value);
    createMessage.success("煎药操作成功");
  } catch (error) {
    createMessage.error("煎药操作失败");
  }
}
</script>

<style scoped>
.cur-recipe-list-container {
  padding: 16px;
  background: #f0f2f5;
}

.table-title {
  display: flex;
  align-items: center;
  font-size: 16px;
  font-weight: 500;
}

.table-title span {
  margin-right: 8px;
}
</style>
