import editForm from "../add.vue";
import { message } from "@/utils/message";
import { addDialog } from "@/components/ReDialog";
import { type FormItemProps } from "../utils/types";
import { type PaginationProps } from "@pureadmin/table";
import { reactive, ref, onMounted, h } from "vue";
import { ElMessageBox } from "element-plus";
import { getPageList, delById, saveOrUpdate, removeBatchByIds } from "@/api/system";

import { utils, writeFile } from "xlsx";

export function useOperationLog() {
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
      label: "主键ID",
      prop: "id",
    },
    {
      label: "更新时间",
      prop: "updateTime",
        width: 200,
    },
    {
      label: "创建时间",
      prop: "createTime",
        width: 200,
    },
    {
      label: "操作模块",
      prop: "operModul",
    },
    {
      label: "操作类型",
      prop: "operType",
    },
    {
      label: "操作描述",
      prop: "operDesc",
    },
    {
      label: "请求方法",
      prop: "operMethod",
    },
    {
      label: "请求参数",
      prop: "operRequParam",
    },
    {
      label: "返回参数",
      prop: "operRespParam",
    },
    {
      label: "用户id",
      prop: "operUserId",
    },
    {
      label: "用户名称",
      prop: "operUserName",
    },
    {
      label: "操作IP",
      prop: "operIp",
    },
    {
      label: "操作路径",
      prop: "operUri",
    },
    {
      label: "创建者",
      prop: "createBy",
    },
    {
      label: "更新者",
      prop: "updateBy",
    },
    {
      label: "操作",
      fixed: "right",
      width: 180,
      slot: "operation"
    }
  ];

  function handleDelete(row) {
    delById("/operationLog/delById",{ id: row.id }).then(() => {
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
      removeBatchByIds("/operationLog/removeBatchByIds",ids).then(res => {
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
    const { data } = await getPageList("/operationLog/page",{
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

  function openDialog(title = "新增日志操作记录", row?: FormItemProps) {
    addDialog({
      title: `${title}`,
      props: {
        formInline: {
            id: row?.id ?? "0" ,
            operModul: row?.operModul ?? "" ,
            operType: row?.operType ?? "" ,
            operDesc: row?.operDesc ?? "" ,
            operMethod: row?.operMethod ?? "" ,
            operRequParam: row?.operRequParam ?? "" ,
            operRespParam: row?.operRespParam ?? "" ,
            operUserId: row?.operUserId ?? "" ,
            operUserName: row?.operUserName ?? "" ,
            operIp: row?.operIp ?? "" ,
            operUri: row?.operUri ?? "" ,
            createBy: row?.createBy ?? "" ,
            updateBy: row?.updateBy ?? "" ,
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
          saveOrUpdate("/operationLog/saveOrUpdate",curData).then(() => {
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
