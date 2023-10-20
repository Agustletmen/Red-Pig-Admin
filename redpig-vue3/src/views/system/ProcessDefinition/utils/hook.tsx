import editForm from "../add.vue";
import { message } from "@/utils/message";
import { addDialog } from "@/components/ReDialog";
import { type FormItemProps } from "../utils/types";
import { type PaginationProps } from "@pureadmin/table";
import { reactive, ref, onMounted, h } from "vue";
import { ElMessageBox } from "element-plus";
import { getPageList, delById, removeBatchByIds } from "@/api/system";

import { utils, writeFile } from "xlsx";

export function useProcessDefinition() {
  const form = reactive({
    time: ""
  });
  const formRef = ref();
  const dataList = ref([]);
  const loading = ref(true);
  const multipleSelection = ref([]);
  const single = ref(true);
  const multiple = ref(true);
  const pagination = reactive<PaginationProps>({
    total: 0,
    pageSize: 10,
    currentPage: 1,
    background: true
  });

  const columns: TableColumnList = [
    {
      type: "selection",
      align: "left"
    },
    {
      label: "主键id",
      prop: "id"
    },
    {
      label: "名称",
      prop: "name"
    },
    {
      label: "部署ID",
      prop: "deploymentId"
    },
    {
      label: "操作",
      fixed: "right",
      width: 180,
      slot: "operation"
    }
  ];

  function handleDelete(row) {
    delById("/processDefinition/delById", {
      deploymentId: row.deploymentId
    }).then(() => {
      message(`确认这条数据`, { type: "success" });
      onSearch();
    });
  }

  function delByIds() {
    const ids: any[] = multipleSelection.value.map(item => item.id);
    ElMessageBox.confirm("确定要执行批量删除吗？", "系统提示", {
      confirmButtonText: "确定",
      cancelButtonText: "取消",
      type: "warning",
      dangerouslyUseHTMLString: true,
      draggable: true
    }).then(() => {
      removeBatchByIds("/processDefinition/removeBatchByIds", ids).then(res => {
        if (res.code != 200) {
          message(res.data + "", { type: "error" });
        } else {
          message(`批量删除成功`, { type: "success" });
          onSearch();
        }
      });
    });
  }

  function handleSizeChange(val: number) {
    pagination.currentPage = 1;
    pagination.pageSize = val;
    onSearch();
  }

  function handleCurrentChange(val: number) {
    pagination.currentPage = val;
    onSearch();
  }

  function handleSelectionChange(selection) {
    multipleSelection.value = selection;
    single.value = selection.length != 1;
    multiple.value = !selection.length;
  }

  const updateRow = () => {
    openDialog("编辑", multipleSelection.value[0]);
  };

  const exportExcel = () => {
    const res = dataList.value.map(item => {
      const arr = [];
      columns.forEach(column => {
        arr.push(item[column.prop as string]);
      });
      return arr;
    });
    const titleList = [];
    columns.forEach(column => {
      titleList.push(column.label);
    });
    res.unshift(titleList);
    const workSheet = utils.aoa_to_sheet(res);
    const workBook = utils.book_new();
    utils.book_append_sheet(workBook, workSheet, "数据报表");
    writeFile(workBook, "导出.xlsx");
    message("导出成功", {
      type: "success"
    });
  };

  async function onSearch() {
    loading.value = true;
    const { data } = await getPageList("/processDefinition/page", {
      currentPage: pagination.currentPage,
      pageSize: pagination.pageSize,
      startTime: form.time[0],
      endTime: form.time[1]
    });

    dataList.value = data.records;
    pagination.total = data.total;
    pagination.pageSize = data.pageSize;
    pagination.currentPage = data.currentPage;

    setTimeout(() => {
      loading.value = false;
    }, 500);
  }

  const resetForm = formEl => {
    if (!formEl) return;
    formEl.resetFields();
    onSearch();
  };

  function openDialog(title = "新增流程定义", row?: FormItemProps) {
    addDialog({
      title: `${title}`,
      props: {
        formInline: {
          deploymentId: row?.deploymentId ?? ""
        }
      },
      width: "40%",
      draggable: true,
      fullscreenIcon: true,
      closeOnClickModal: false,
      contentRenderer: () => h(editForm, { ref: formRef })
    });
  }

  onMounted(() => {
    onSearch();
  });

  return {
    form,
    loading,
    columns,
    dataList,
    pagination,
    onSearch,
    updateRow,
    exportExcel,
    single,
    multiple,
    delByIds,
    resetForm,
    openDialog,
    handleDelete,
    handleSizeChange,
    handleCurrentChange,
    handleSelectionChange
  };
}
