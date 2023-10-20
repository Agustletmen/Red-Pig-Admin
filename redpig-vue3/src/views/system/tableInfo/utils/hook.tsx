import editForm from "../add.vue";
import addProperty from "../addProperty.vue";
import { message } from "@/utils/message";
import { addDialog } from "@/components/ReDialog";
import { type FormItemProps, type FormItemPropertyProps } from "../utils/types";
import { type PaginationProps } from "@pureadmin/table";
import { reactive, ref, onMounted, h, computed } from "vue";
import { ElMessageBox } from "element-plus";
import {
  getPageList,
  delById,
  saveOrUpdate,
  removeBatchByIds,
  getById,
  createTable,
  generatorCode,
  asyncDatabase
} from "@/api/system";

import { utils, writeFile } from "xlsx";

export function useTableInfo() {
  const form = reactive({
    time: "",
    tableName: ""
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
      label: "主键ID",
      prop: "id",
      width: 120
    },
    {
      label: "创建时间",
      prop: "createTime",
      width: 200
    },
    {
      label: "更新时间",
      prop: "updateTime",
      width: 200
    },
    {
      label: "表名",
      prop: "tableName",
      width: 150
    },
    {
      label: "表备注",
      prop: "tableComment",
      width: 120
    },
    {
      label: "类名",
      prop: "className",
      width: 120
    },
    {
      label: "创建者",
      prop: "createBy",
      width: 120
    },
    {
      label: "更新者",
      prop: "updateBy",
      width: 120
    },
    {
      label: "备注",
      prop: "remark",
      width: 120
    },
    {
      label: "操作",
      fixed: "right",
      width: 250,
      slot: "operation"
    }
  ];

  const buttonClass = computed(() => {
    return [
      "!h-[20px]",
      "reset-margin",
      "!text-gray-500",
      "dark:!text-white",
      "dark:hover:!text-primary"
    ];
  });

  function handleDelete(row) {
    delById("/tableInfo/delById", { id: row.id }).then(() => {
      message(`您删除了ID为${row.id}的这条数据`, { type: "success" });
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
      removeBatchByIds("/tableInfo/removeBatchByIds", ids).then(res => {
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

  const syncIcon = ref("clarity:sync-line");
  const doSyncDatabase = () => {
    syncIcon.value = "svg-spinners:180-ring";
    asyncDatabase("/tableInfo/asyncDatabase").then(() => {
      syncIcon.value = "clarity:sync-line";
      message("同步成功", {
        type: "success"
      });

      setTimeout(function () {
        onSearch();
      }, 3000);
    });
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
    const { data } = await getPageList("/tableInfo/page", {
      currentPage: pagination.currentPage,
      pageSize: pagination.pageSize,
      startTime: form.time[0],
      endTime: form.time[1],
      tableName:form.tableName
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

  const doCreateTable = row => {
    createTable("/tableInfo/createTable", { id: row.id }).then(() => {
      onSearch();
    });
  };

  const doGeneratorCode = row => {
    generatorCode("/tableInfo/generatorByTableInfoId", { id: row.id }).then(
      res => {
        const content = res;
        const blob = new Blob([content]);
        const fileName = "code.zip";
        if ("download" in document.createElement("a")) {
          // 非IE下载
          const elink = document.createElement("a");
          elink.download = fileName;
          elink.style.display = "none";
          elink.href = URL.createObjectURL(blob);
          document.body.appendChild(elink);
          elink.click();
          URL.revokeObjectURL(elink.href); // 释放URL 对象
          document.body.removeChild(elink);
        } else {
          // IE10+下载
          navigator.msSaveBlob(blob, fileName);
        }

        onSearch();
      }
    );
  };

  const doGenerateMenu = row => {
    createTable("/tableInfo/generateMenu", { id: row.id }).then(() => {
      onSearch();
    });
  };

  function openDialog(title = "新增表信息", row?: FormItemProps) {
    addDialog({
      title: `${title}`,
      props: {
        formInline: {
          id: row?.id ?? "",
          tableName: row?.tableName ?? "",
          tableComment: row?.tableComment ?? "",
          className: row?.className ?? "",
          createBy: row?.createBy ?? "",
          updateBy: row?.updateBy ?? "",
          remark: row?.remark ?? ""
        }
      },
      width: "40%",
      draggable: true,
      fullscreenIcon: true,
      closeOnClickModal: false,
      contentRenderer: () => h(editForm, { ref: formRef }),
      beforeSure: (done, { options }) => {
        const FormRef = formRef.value.getRef();
        const curData = options.props.formInline as FormItemProps;
        function chores() {
          saveOrUpdate("/tableInfo/saveOrUpdate", curData).then(() => {
            message(`您${title}了ID为${curData.id}的这条数据`, {
              type: "success"
            });
            done(); // 关闭弹框
            onSearch(); // 刷新表格数据
          });
        }
        FormRef.validate(valid => {
          if (valid) {
            // 表单规则校验通过
            if (title === "新增") {
              // 实际开发先调用新增接口，再进行下面操作
              chores();
            } else {
              // 实际开发先调用编辑接口，再进行下面操作
              chores();
            }
          }
        });
      }
    });
  }

  async function openDialogAddProperty(
    title = "新增表字段",
    row?: FormItemPropertyProps
  ) {
    debugger;
    console.log("表ID:", row?.id ?? "");
    const tableId = row?.id ?? "";

    const ret = await getById("/tableProperty/getByTableInfoId", {
      id: tableId
    });
    addDialog({
      title: `${title}`,
      props: {
        formInline: {
          id: row?.id ?? "",
          tableProperties: ret?.data ?? []
        }
      },
      width: "60%",
      fullscreen: true,
      draggable: true,
      fullscreenIcon: true,
      closeOnClickModal: false,
      contentRenderer: () => h(addProperty, { ref: formRef }),
      beforeSure: (done, { options }) => {
        const FormRef = formRef.value.getRef();
        const curData = options.props.formInline as FormItemPropertyProps;
        function chores() {
          debugger;
          saveOrUpdate("/tableProperty/saveTableProperties", curData).then(
            () => {
              message(`您${title}了ID为${curData.id}的这条数据`, {
                type: "success"
              });
              done(); // 关闭弹框
              onSearch(); // 刷新表格数据
            }
          );
        }
        FormRef.validate(valid => {
          if (valid) {
            // 表单规则校验通过
            if (title === "新增") {
              // 实际开发先调用新增接口，再进行下面操作
              chores();
            } else {
              // 实际开发先调用编辑接口，再进行下面操作
              chores();
            }
          }
        });
      }
    });
  }

  onMounted(() => {
    onSearch();
  });

  const addPropertyColumn = [
    {
      label: "列名",
      prop: "columnName",
      fixed: true,
      width: 120
    },
    {
      label: "列类型",
      prop: "columnType",
      width: 150
    },
    {
      label: "列长度",
      prop: "columnLength",
      width: 200
    },
    {
      label: "列说明",
      prop: "columnComment",
      width: 150
    },
    {
      label: "不为空",
      prop: "notNull",
      width: 100
    },
    {
      label: "主键",
      prop: "primarykey",
      width: 100
    },
    {
      label: "属性名",
      prop: "fieldName",
      width: 150
    },
    {
      label: "属性类型",
      prop: "fieldType",
      width: 120
    },
    {
      label: "查询select",
      prop: "selectIs",
      width: 100
    },
    {
      label: "查询方式",
      prop: "selectType",
      width: 100
    },
    {
      label: "表单验证",
      prop: "formIs",
      width: 100
    },
    {
      label: "必填",
      prop: "inputMust",
      width: 100
    },
    {
      label: "表单类型",
      prop: "formType",
      width: 150
    },
    {
      label: "排序",
      prop: "seq",
      width: 100
    },
    {
      label: "备注",
      prop: "remark",
      width: 260
    },
    {
      label: "操作",
      width: 100,
      fixed: "right",
      slot: "operation"
    }
  ];

  return {
    buttonClass,
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
    openDialogAddProperty,
    doCreateTable,
    doGeneratorCode,
    doGenerateMenu,
    handleDelete,
    handleSizeChange,
    handleCurrentChange,
    handleSelectionChange,
    addPropertyColumn,
    doSyncDatabase,
    syncIcon
  };
}
