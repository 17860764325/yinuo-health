<template>
  <div>
    <!--引用表格-->
    <BasicTable @register="registerTable" :rowSelection="rowSelection" :loading="loading" @selection-change="sleectChange">
      <!--插槽:table标题-->
      <template #tableTitle>
        <!--          <a-button type="primary" @click="handleAdd" preIcon="ant-design:plus-outlined"> 新增</a-button>-->
        <!--          <a-button  type="primary" preIcon="ant-design:export-outlined" @click="onExportXls"> 导出</a-button>-->
        <!--          <j-upload-button  type="primary" preIcon="ant-design:import-outlined" @click="onImportXls">导入</j-upload-button>-->

        <!--        <a-button type="primary" @click="newLogTestClick" > 新日志测试</a-button>-->
        <a-button type="primary" @click="buttonAllClick" v-if="!isDisabledAuth('doctor:pe_register_list:buttonAll')"> 条码生成 </a-button>
        <a-button type="primary" @click="personSearchClick" :icon="h(SearchOutlined)" v-if="!isDisabledAuth('doctor:pe_register_list:personSearch')">
          人员档案查询
        </a-button>
        <a-button
          type="primary"
          @click="personCreateClick"
          preIcon="ant-design:plus-outlined"
          v-if="!isDisabledAuth('doctor:pe_register_list:personCreate')"
        >
          创建人员档案
        </a-button>
        <a-button
          type="primary"
          @click="LISApply"
          :icon="h(ArrowUpOutlined)"
          :disabled="LISApplyDisabled"
          v-if="!isDisabledAuth('doctor:pe_register_list:LISApply')"
        >
          LIS检验申请
        </a-button>
        <a-button
          type="primary"
          @click="barCodebuildClick"
          :icon="h(BarcodeOutlined)"
          :disabled="barCodeBuildDisabled"
          v-if="!isDisabledAuth('doctor:pe_register_list:barCodeBuild')"
        >
          条码生成old
        </a-button>
        <a-button
          type="primary"
          @click="barCodePrintClick"
          :icon="h(FileSearchOutlined)"
          :disabled="reportSearchDisabled"
          v-if="!isDisabledAuth('doctor:pe_register_list:barCodePrintGetData')"
        >
          条码打印
        </a-button>
        <!--        <a-select v-model:value="printMode" style="width: 120px; margin-left: 8px" placeholder="打印模式">-->
        <!--          <a-select-option value="preview">预览打印</a-select-option>-->
        <!--          <a-select-option value="direct">静默打印</a-select-option>-->
        <!--        </a-select>-->
        <a-select
          v-model:value="selectedPrinter"
          style="width: 200px; margin-left: 8px"
          placeholder="选择打印机"
          :loading="loadingPrinters"
          @focus="loadPrinters"
        >
          <a-select-option value="">默认打印机</a-select-option>
          <a-select-option v-for="printer in printerList" :key="printer" :value="printer">
            {{ printer }}
          </a-select-option>
        </a-select>
        <a-button
          type="primary"
          @click="reportSearchClick"
          :icon="h(FileSearchOutlined)"
          :disabled="reportSearchDisabled"
          v-if="!isDisabledAuth('doctor:pe_register_list:reportSearch')"
        >
          报告查询
        </a-button>
        <a-button type="primary" @click="drSearch" :icon="h(FileSearchOutlined)" :disabled="reportSearchDisabled"> 查询DR报告 </a-button>

        <a-dropdown v-if="selectedRowKeys.length > 0">
          <template #overlay>
            <a-menu>
              <!--                  批量操作的按钮在这里定义-->
              <a-menu-item key="1" @click="personSearchClick">
                <SearchOutlined />
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
          <a-button
            >批量操作
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
        <span v-if="!text" style="font-size: 12px; font-style: italic">无文件</span>
        <a-button v-else :ghost="true" type="primary" preIcon="ant-design:download-outlined" size="small" @click="downloadFile(text)">下载 </a-button>
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
  import { useListPage } from '/@/hooks/system/useListPage';
  import PeRegisterListModal from './components/PeRegisterListModal.vue';
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
    barCodePrintGetData,
    newLogTest,
    drSearchApi,
  } from './PeRegisterList.api';
  import { downloadFile } from '/@/utils/common/renderUtils';
  import { getAreaTextByCode } from '../../../components/Form/src/utils/Area';
  import { h } from 'vue';
  import { SearchOutlined, ArrowUpOutlined, BarcodeOutlined, FileSearchOutlined } from '@ant-design/icons-vue';
  import { useMessage } from '@/hooks/web/useMessage';
  import Modal from './components/LISApplyModal.vue';
  import { Tag, Avatar } from 'ant-design-vue';
  import { usePermission } from '@/hooks/web/usePermission';
  // import {getLodop} from '../../../assets/js/Lodop.js'
  import { getLodop, getLodopAsync } from '../../../assets/js/LodopFuncs';
  import { ajaxGetDictItems } from '@/utils/dict';

  const { createMessage, createErrorModal, createConfirm } = useMessage();

  const checkedKeys = ref<Array<string | number>>([]);

  // 打印配置
  const printMode = ref('direct'); // 打印模式：preview-预览打印, direct-静默打印
  const selectedPrinter = ref(''); // 选中的打印机
  const printerList = ref<string[]>([]); // 打印机列表
  const loadingPrinters = ref(false); // 加载打印机列表状态

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
        fieldMapToTime: [['peDate', ['peDate_begin', 'peDate_end'], 'YYYY-MM-DD HH:mm:ss']],
      },
      actionColumn: {
        width: 120,
        fixed: 'right',
      },
      // 设置列表查询的排序字段，默认是 createTime 。
      defSort: { column: 'patientNo', order: 'desc' },
    },
    exportConfig: {
      name: '人员信息查询',
      url: getExportUrl,
    },
    importConfig: {
      url: getImportUrl,
      success: handleSuccess,
    },
  });

  const allButtonShow = ref();

  const [registerTable, { reload, setLoading, clearSelectedRowKeys }, { rowSelection, selectedRowKeys }] = tableContext;

  // LIS检验申请是否需要禁用
  const LISApplyDisabled = ref(false);
  // 条码生成是否禁用
  const barCodeBuildDisabled = ref(false);

  // 报告查询是否禁用
  const reportSearchDisabled = ref(false);

  // 创建档案按钮是否禁用
  const personCreateDisabled = ref(false);
  const loading = ref(false);
  // 获取选中人员
  const ids = ref<Array<String>>([]);
  // 打印ip
  const ip = ref('127.0.0.1');

  // Lodop对象缓存
  let lodopInstance = null;

  /**
   * 加载打印机列表
   */
  async function loadPrinters() {
    if (printerList.value.length > 0) return; // 已加载过，不重复加载

    loadingPrinters.value = true;
    try {
      const LODOP = await getLodopAsync(ip.value);
      if (LODOP) {
        const count = LODOP.GET_PRINTER_COUNT();
        const printers = [];
        for (let i = 0; i < count; i++) {
          printers.push(LODOP.GET_PRINTER_NAME(i));
        }
        printerList.value = printers;
      }
    } catch (err) {
      console.error('加载打印机列表失败:', err);
      createMessage.error('加载打印机列表失败，请确认Lodop服务已启动');
    } finally {
      loadingPrinters.value = false;
    }
  }

  /**
   * 选中修改事件
   */
  function sleectChange({ keys, rows }) {
    console.log(123);
    // rows：你选中的那些行的数据
    LISApplyDisabled.value = false;
    barCodeBuildDisabled.value = false;
    // reportSearchDisabled.value = false
    rows.map((item) => {
      if (item.isLisApply === '1') {
        LISApplyDisabled.value = true;
      }
      if (item.isBarCodeBuild === '1' || item.isLisApply !== '1') {
        barCodeBuildDisabled.value = true;
      }
      // if (item.isReport === '1' || item.isLisApply !== '1' || item.isBarCodeBuild !== '1') {
      //   reportSearchDisabled.value = true
      // }
    });
  }

  /**
   * 人员档案查询
   */
  async function personSearchClick() {
    // 如果没有选中的数据提示请选择消息
    if (rowSelection.selectedRows.length === 0) {
      createMessage.warning('请选择数据！');
    } else {
      // 将选中行的 ids 传到后端，调用人员查询接口
      const ids = ref<Array<String>>([]);
      rowSelection.selectedRows.forEach((item) => {
        ids.value.push(item.id);
      });
      console.log(ids.value);
      loading.value = true;
      await personSearch(ids.value).then((res) => {
        createConfirm({
          iconType: 'info',
          title: '返回结果',
          content: res,
          okText: '确认',
          onOk: function () {
            reload();
            clearSelectedRowKeys();
            loading.value = false;
          },
          onCancel: function () {
            reload();
            clearSelectedRowKeys();
            loading.value = false;
          },
        });
      });
    }
  }

  /**
   * 创建档案
   */
  async function personCreateClick() {
    // 如果没有选中的数据提示请选择消息
    if (rowSelection.selectedRows.length === 0) {
      createMessage.warning('请选择数据！');
    } else {
      // 发送请求创建人员档案，携带人员的ID
      const ids = ref<Array<String>>([]);
      rowSelection.selectedRows.forEach((item) => {
        // 筛选出，没有 患者id 的数据 的 id 传输到后端，有patId的不需要创建档案
        if (item.patId === undefined || item.patId === null || item.patId === '') {
          ids.value.push(item.id);
        }
      });

      if (ids.value.length > 0) {
        loading.value = true;
        await personCreate(ids.value).then((res) => {
          createConfirm({
            iconType: 'info',
            title: '返回结果',
            content: res,
            okText: '确认',
            onOk: function () {
              reload();
              clearSelectedRowKeys();
              loading.value = false;
            },
            onCancel: function () {
              reload();
              clearSelectedRowKeys();
              loading.value = false;
            },
          });
        });
      } else {
        createMessage.warning('请选择没有维护“患者id”的数据！');
      }
    }
  }

  /**
   * LIS检验申请提交
   */
  async function LISApply() {
    // 如果没有选中的数据提示请选择消息
    if (rowSelection.selectedRows.length === 0) {
      createMessage.warning('请选择数据！');
    } else {
      rowSelection.selectedRows.forEach((item) => {
        // 筛选出，有 患者id 的数据 的 id 传输到后端，有patId的不需要创建档案
        if (item.patId !== undefined && item.patId !== null && item.patId !== '') {
          ids.value.push(item.id);
        }
      });
      // 如果选择的人都是没有患者id的那么就提示
      if (ids.value.length > 0) {
        // 弹出表单弹窗
        setLisApplyModalProps({ useWrapper: true });
        openLisApplyModal(true, {
          ids: ids.value,
          type: 'lis',
        });
      } else {
        createMessage.warning('请选择维护了“患者id”的数据！');
      }
    }
  }

  /**
   * 条码生成
   */
  async function barCodebuildClick() {
    if (rowSelection.selectedRows.length === 0) {
      createMessage.warning('请选择数据！');
    } else {
      const ids = ref<Array<String>>([]);
      rowSelection.selectedRows.forEach((item) => {
        ids.value.push(item.id);
      });
      loading.value = true;
      await barCodeBuild(ids.value).then((res) => {
        createConfirm({
          iconType: 'info',
          title: '返回结果',
          content: res,
          okText: '确认',
          onOk: function () {
            reload();
          },
          onCancel: function () {
            reload();
          },
        });
        loading.value = false;
        clearSelectedRowKeys();
      });
    }
  }

  /**
   * 报告查询维护
   */
  async function reportSearchClick() {
    if (rowSelection.selectedRows.length === 0) {
      createMessage.warning('请选择数据！');
    } else {
      const ids = ref<Array<String>>([]);
      rowSelection.selectedRows.forEach((item) => {
        ids.value.push(item.id);
      });
      loading.value = true;
      await reportSearch(ids.value).then((res) => {
        createConfirm({
          iconType: 'info',
          title: '返回结果',
          content: res,
          okText: '确认',
          onOk: function () {
            reload();
          },
          onCancel: function () {
            reload();
          },
        });
        loading.value = false;
        clearSelectedRowKeys();
      });
    }
  }

  // 获取字典值
  ajaxGetDictItems('print_ip', null).then((res) => {
    console.log(res, '获取到的ip字典值');
    let ip1List = res.filter((item) => item.text === 'IP1');
    if (ip1List) {
      ip.value = ip1List[0].value;
      // 获取到数据为：192a168a68a2需要将a替换为英文.
      if (ip.value) {
        ip.value = ip.value.replaceAll('a', '.');
      } else {
        ip.value = '127.0.0.1';
      }
    }
    console.log(ip.value, '最终处理的ip值');
  });

  /**
   * 条码打印
   */
  async function barCodePrintClick() {
    // 获取后台数据
    if (rowSelection.selectedRows.length === 0) {
      createMessage.warning('请选择数据！');
      return;
    }

    const ids = ref<Array<String>>([]);
    rowSelection.selectedRows.forEach((item) => {
      if (item.isBarCodeBuild === '1') {
        ids.value.push(item.id);
      }
    });

    if (ids.value.length === 0) {
      createMessage.warning('请选择已经生成条码的数据！');
      return;
    }

    try {
      // 显示加载提示
      const loadingMsg = createMessage.loading('正在初始化打印服务...', 0);

      // 先初始化Lodop
      const LODOP = await getLodopAsync(ip.value);

      if (!LODOP) {
        loadingMsg();
        createMessage.error('打印服务初始化失败，请确认Lodop服务已启动');
        return;
      }

      loadingMsg();

      // 获取后端数据
      const res = await barCodePrintGetData(ids.value);
      console.log('打印数据:', res);

      // 设置打印机
      if (selectedPrinter.value) {
        LODOP.SET_PRINTER_INDEX(selectedPrinter.value);
      }

      // 收集所有需要打印的数据
      const allPrintData = [];
      res.forEach((item) => {
        item.forEach((personItem) => {
          allPrintData.push(personItem);
        });
      });

      if (allPrintData.length === 0) {
        createMessage.warning('没有可打印的数据');
        return;
      }

      // 只初始化一次打印任务
      LODOP.PRINT_INIT('条形码批量打印');

      // 设置纸张大小为实际条码纸张尺寸（50mm x 30mm）
      LODOP.SET_PRINT_PAGESIZE(1, '50mm', '30mm', '');

      // 设置打印方向和缩放
      // LODOP.SET_PRINT_MODE('PRINT_PAGE_PERCENT', 'Auto-Width'); // 自动适应宽度

      // 循环添加每一页
      allPrintData.forEach((personItem, index) => {
        if (index > 0) {
          // 从第二页开始，添加新页
          LODOP.NewPage();
        }
        // 添加当前页的打印内容
        console.log(`添加第${index + 1}页，条码数据:`, personItem.barCode);
        CreatePrintPageContent(personItem, LODOP);
      });

      // 输出调试信息
      console.log('打印任务已创建，共', allPrintData.length, '页');

      // 根据打印模式执行打印
      if (printMode.value === 'direct') {
        // 静默打印
        LODOP.PRINT();
        createMessage.success(`已发送${allPrintData.length}个条码打印任务到打印机`);
      } else {
        // 预览打印
        LODOP.PREVIEW();
      }
    } catch (err) {
      console.error('打印失败:', err);
      createMessage.error('打印失败: ' + (err.message || '未知错误'));
    }
  }
  // 打印操作
  function CreatePrintPage(data,LODOP) {
    LODOP.PRINT_INIT("条形码");
    // 纸张定义
    LODOP.SET_PRINT_PAGESIZE(1, '50mm', '30mm', "条码码")
    // 字段问题
    LODOP.SET_PRINT_MODE("FULL_HEIGHT_FOR_OVERFLOW", true);
    LODOP.SET_PRINT_STYLE("FontName",'微软雅黑');
    // 字号

    LODOP.SET_PRINT_STYLEA(0, "HOrient", 1);
    // 条码打印
    LODOP.ADD_PRINT_BARCODE('4mm', '4mm', '34mm', '15mm', "128Auto", data.barCode);
    LODOP.SET_PRINT_STYLEA(0, "AlignJustify", 2);

    // 设置文本
    // 试管颜色
    LODOP.ADD_PRINT_TEXT('4mm', '40mm', '30mm', 15, data.tubeColor===null?"浅红色":data.tubeColor)
    // 试管类型
    LODOP.ADD_PRINT_TEXT('8mm', '40mm', '30mm', 15, data.sampleClassName)
    // 体检号
    LODOP.SET_PRINT_STYLE("FontSize", 10);
    LODOP.ADD_PRINT_TEXT(72, '2mm', '60mm', 25, data.patientNo)
    // 姓名
    LODOP.SET_PRINT_STYLE("FontSize", 10);
    LODOP.ADD_PRINT_TEXT(72, '23mm', '40mm', 25, data.patientName)
    // 性别
    LODOP.SET_PRINT_STYLE("FontSize", 10);
    LODOP.ADD_PRINT_TEXT(72, '35mm', '20mm', 25, data.sex)
    // 年龄
    LODOP.SET_PRINT_STYLE("FontSize", 10);
    LODOP.ADD_PRINT_TEXT(72, '40mm', '20mm', 25, data.age + "岁")
    // 部门
    LODOP.SET_PRINT_STYLE("FontSize", 10);
    LODOP.ADD_PRINT_TEXT(88, '2mm', '40mm', 25, data.department)
    // 患者类型
    LODOP.SET_PRINT_STYLE("FontSize", 10);
    LODOP.ADD_PRINT_TEXT(88, '18mm', '40mm', 25, data.patType)
    // 横杠
    LODOP.ADD_PRINT_TEXT(92, '2mm', '80mm', 25, "___________________________________")
    LODOP.SET_PRINT_STYLE("FontSize", 10);
    // 项目名称
    LODOP.ADD_PRINT_TEXT(106, '2mm', '50mm', 30, data.labItemName)
    LODOP.SET_PRINT_STYLEA(0,"TextNeatRow",true);

  }
  /**
   * 创建打印页面内容（不包含PRINT_INIT，用于批量打印）
   * @param data 打印数据
   * @param LODOP Lodop对象
   */
  function CreatePrintPageContent(data, LODOP) {
    // 注意：纸张大小已在PRINT_INIT后统一设置，这里不再重复设置

    // 检查条码数据是否存在
    if (!data.barCode) {
      console.error('条码数据为空！', data);
      return;
    }

    // 条码打印 - 一维条形码 (Code128编码)
    // 位置: 左边距4mm, 上边距4mm, 宽度34mm, 高度15mm
    LODOP.ADD_PRINT_BARCODE('4mm', '4mm', '35mm', '15mm', '128Auto', data.barCode);
    LODOP.SET_PRINT_STYLEA(0, 'ShowBarText', 1); // 1-显示条码文字在条码下方，方便识别
    LODOP.SET_PRINT_STYLEA(0, 'FontSize', 8); // 条码下方文字大小
    LODOP.SET_PRINT_STYLEA(0, 'AlignJustify', 2); // 2-居中对齐

    // 设置文本默认字体
    LODOP.SET_PRINT_STYLE('FontName', '微软雅黑');
    LODOP.SET_PRINT_STYLE('FontSize', 12);

// 试管颜色
    LODOP.ADD_PRINT_TEXT('4mm', '42mm', '30mm', 15, data.tubeColor===null?"浅红色":data.tubeColor)
    // 试管类型
    LODOP.ADD_PRINT_TEXT('8mm', '42mm', '30mm', 15, data.sampleClassName)
    // 体检号
    LODOP.SET_PRINT_STYLE("FontSize", 10);
    LODOP.ADD_PRINT_TEXT(72, '2mm', '60mm', 25, data.patientNo)
    // 姓名
    LODOP.SET_PRINT_STYLE("FontSize", 10);
    LODOP.ADD_PRINT_TEXT(72, '23mm', '40mm', 25, data.patientName)
    // 性别
    LODOP.SET_PRINT_STYLE("FontSize", 10);
    LODOP.ADD_PRINT_TEXT(72, '35mm', '20mm', 25, data.sex)
    // 年龄
    LODOP.SET_PRINT_STYLE("FontSize", 10);
    LODOP.ADD_PRINT_TEXT(72, '40mm', '20mm', 25, data.age + "岁")
    // 部门
    LODOP.SET_PRINT_STYLE("FontSize", 10);
    LODOP.ADD_PRINT_TEXT(88, '2mm', '40mm', 25, data.department)
    // 患者类型
    LODOP.SET_PRINT_STYLE("FontSize", 10);
    LODOP.ADD_PRINT_TEXT(88, '18mm', '40mm', 25, data.patType)
    // 横杠
    LODOP.ADD_PRINT_TEXT(92, '2mm', '80mm', 25, "___________________________________")
    LODOP.SET_PRINT_STYLE("FontSize", 10);
    // 项目名称
    LODOP.ADD_PRINT_TEXT(106, '2mm', '50mm', 30, data.labItemName)
    LODOP.SET_PRINT_STYLEA(0,"TextNeatRow",true);
  }

  async function drSearch() {
    // 获取选择的人员
    if (rowSelection.selectedRows.length === 0) {
      createMessage.warning('请选择数据！');
    } else {
      const ids = ref<Array<String>>([]);
      rowSelection.selectedRows.forEach((item) => {
        ids.value.push(item.patientNo);
      });
      loading.value = true;
      await drSearchApi({ patIds: ids.value }).then((res) => {
        createConfirm({
          iconType: 'info',
          title: '返回结果',
          content: res,
          okText: '确认',
          onOk: function () {
            reload();
          },
          onCancel: function () {
            reload();
          },
        });
        loading.value = false;
        clearSelectedRowKeys();
      });
    }
  }

  /**
   * 完成插槽，字以及颜色
   */
  function soltFontSize(value: string) {
    if (value === '1') {
      return '已完成';
    } else {
      return '未完成';
    }
  }

  function soltColor(value: string) {
    if (value === '1') {
      return 'green';
    } else {
      return 'red';
    }
  }

  /**
   * 多重操作
   */
  async function buttonAllClick() {
    // 如果没有选中的数据提示请选择消息
    if (rowSelection.selectedRows.length === 0) {
      createMessage.warning('请选择数据！');
    } else {
      const ids = ref<Array<String>>([]);
      rowSelection.selectedRows.forEach((item) => {
        ids.value.push(item.id);
      });
      // // 如果选择的人都是没有患者id的那么就提示
      // if (ids.value.length > 0) {
      //   // 弹出表单弹窗
      //   setLisApplyModalProps({useWrapper: true});
      //   openLisApplyModal(true, {
      //     ids: ids.value,
      //     type: 'all'
      //   });
      // } else {
      //   createMessage.warning("请选择维护了“患者id”的数据！");
      // }
      // 创建对象
      const param = ref({ patType: '5', patIds: ids.value });
      //提交表单
      loading.value = true;
      await buttonAll(param.value).then((res) => {
        createConfirm({
          iconType: 'info',
          title: '返回结果',
          content: res,
          okText: '确认',
          onOk: function () {},
          onCancel: function () {},
        });
        loading.value = false;
        reload();
        clearSelectedRowKeys();
      });
    }
  }

  async function newLogTestClick() {
    // 如果没有选中的数据提示请选择消息
    if (rowSelection.selectedRows.length === 0) {
      createMessage.warning('请选择数据！');
    } else {
      // 将选中行的 ids 传到后端，调用人员查询接口
      const ids = ref<Array<String>>([]);
      rowSelection.selectedRows.forEach((item) => {
        ids.value.push(item.id);
      });
      console.log(ids.value);
      await newLogTest(ids.value).then((res) => {
        createConfirm({
          iconType: 'info',
          title: '返回结果',
          content: res,
          okText: '确认',
          onOk: function () {
            reload();
          },
          onCancel: function () {
            reload();
          },
        });
      });
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
    loading.value = false;
    clearSelectedRowKeys();
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
      },
    ];
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
    ];
  }
</script>

<style scoped></style>
