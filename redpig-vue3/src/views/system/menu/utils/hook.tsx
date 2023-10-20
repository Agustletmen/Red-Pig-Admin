import editForm from "../add.vue";
import authForm from "../auth.vue";
import { message } from "@/utils/message";
import { addDialog } from "@/components/ReDialog";
import { type FormItemProps, type MenuRolePerm } from "../utils/types";
import { type PaginationProps } from "@pureadmin/table";
import { reactive, ref, onMounted, h } from "vue";
import { ElMessageBox } from "element-plus";

import {
  getPageList,
  delById,
  removeBatchByIds,
  saveOrUpdate,
  saveAuth
} from "@/api/system";

import { utils, writeFile } from "xlsx";
import IconifyIconOnline from "@/components/ReIcon/src/iconifyIconOnline";

export function useMenu() {
  const form = reactive({
    time: "",
    parendId: 0
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
    // {
    //   label: "主键ID",
    //   prop: "id"
    // },
    // {
    //   label: "创建时间",
    //   prop: "createTime",
    //   width: 200
    // },
    {
      label: "更新时间",
      prop: "updateTime",
      width: 160
    },
    {
      label: "标题",
      prop: "title",
      width: 120
    },
    {
      label: "菜单名称",
      prop: "name",
      width: 100
    },
    {
      label: "菜单地址",
      prop: "path",
      width: 200
    },
    {
      label: "组件名",
      prop: "component",
      width: 80
    },

    {
      label: "类型",
      prop: "type",
      width: 100,
      cellRenderer: scope => (
        <IconifyIconOnline
          icon={
            scope.row.type == 0
              ? "ph:folder"
              : scope.row.type == 1
              ? "ep:menu"
              : "uil:rss-interface"
          }
          width="70px"
          height="20px"
        />
      )
    },
    {
      label: "图标",
      prop: "icon",
      width: 100,
      cellRenderer: scope => (
        <IconifyIconOnline icon={scope.row.icon} width="70px" height="20px" />
      )
    },
    {
      label: "链接",
      prop: "showLink",
      width: 100,
      cellRenderer: scope => (
        <el-switch
          size={scope.props.size === "small" ? "small" : "default"}
          v-model={scope.row.showLink}
          active-value={true}
          inactive-value={false}
          onChange={() => saveOrUpdate("/menu/saveOrUpdate", scope.row)}
          active-text="已开启"
          inactive-text="已关闭"
          inline-prompt
        />
      )
    },
    {
      label: "排序",
      prop: "rank",
      width: 70
    },
    {
      label: "父菜单",
      prop: "title",
      width: 150,
      cellRenderer: scope =>
        scope.row.parentId == 0 ? "顶级菜单" : scope.row.parent.title
    },
    {
      label: "角色",
      prop: "roles",
      width: 250,
      cellRenderer: scope =>
        scope.row.roles != null && scope.row.roles.length > 0
          ? scope.row.roles.map(e => e.name + ":" + e.tag).join(",")
          : "没有角色"
    },
    {
      label: "权限",
      prop: "perms",
      width: 250,
      cellRenderer: scope =>
        scope.row.perms != null && scope.row.perms.length > 0
          ? scope.row.perms.map(e => e.name + ":" + e.tag).join(",")
          : "没有权限"
    },
    {
      label: "操作",
      fixed: "right",
      width: 250,
      slot: "operation"
    }
  ];

  function handleDelete(row) {
    delById("/menu/delById", { id: row.id }).then(() => {
      message(`您删除了ID为${row.id}的这条数据`, { type: "success" });
      onSearch();
    });
  }

  function delByIds() {
    const ids: any[] = multipleSelection.value.map(item => item.id);
    if (ids.includes(1)) {
      ElMessageBox.alert("系统管理员不能被删除", "警告", {
        confirmButtonText: "OK"
      });
    } else {
      ElMessageBox.confirm("确定要执行批量删除吗？", "系统提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
        dangerouslyUseHTMLString: true,
        draggable: true
      }).then(() => {
        removeBatchByIds("/menu/removeBatchByIds", ids).then(res => {
          if (res.code != 200) {
            message(res.data + "", { type: "error" });
          } else {
            message(`批量删除成功`, { type: "success" });
            onSearch();
          }
        });
      });
    }
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
    writeFile(workBook, "Menu.xlsx");
    message("导出成功", {
      type: "success"
    });
  };

  async function onSearch() {
    loading.value = true;
    const { data } = await getPageList("/menu/page", {
      currentPage: pagination.currentPage,
      pageSize: pagination.pageSize,
      startTime: form.time[0],
      endTime: form.time[1],
      parendId: form.parendId
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

  const updateRow = () => {
    openDialog("编辑", multipleSelection.value[0]);
  };

  function openDialog(title = "新增菜单", row?: FormItemProps) {
    addDialog({
      title: `${title}`,
      props: {
        formInline: {
          id: row?.id ?? "",
          name: row?.name ?? "",
          path: row?.path ?? "",
          component: row?.component ?? "",
          title: row?.title ?? "",
          type: row?.type ?? "",
          icon: row?.icon ?? "",
          showLink: row?.showLink ?? false,
          rank: row?.rank ?? 1,
          parentId: row?.parentId ?? "0"
        }
      },
      width: "45%",
      draggable: true,
      fullscreenIcon: true,
      closeOnClickModal: false,
      contentRenderer: () => h(editForm, { ref: formRef }),
      beforeSure: (done, { options }) => {
        const FormRef = formRef.value.getRef();
        const curData = options.props.formInline as FormItemProps;
        function chores() {
          saveOrUpdate("/menu/saveOrUpdate", curData).then(() => {
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

  function openAuthFormDialog(title = "授权", row?: MenuRolePerm) {
    addDialog({
      title: `${title}`,
      props: {
        formInline: {
          id: row?.id ?? "",
          roles: row?.roles ?? [],
          perms: row?.perms ?? [],
          name: row?.name ?? "",
          title: row?.title ?? ""
        }
      },
      width: "45%",
      draggable: true,
      fullscreenIcon: true,
      closeOnClickModal: false,
      contentRenderer: () => h(authForm, { ref: formRef }),
      beforeSure: (done, { options }) => {
        const curData = options.props.formInline as FormItemProps;
        saveAuth("/menu/saveAuth", curData).then(() => {
          message(`授权成功`, {
            type: "success"
          });
          done(); // 关闭弹框
          onSearch(); // 刷新表格数据
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
    single,
    multiple,
    delByIds,
    updateRow,
    onSearch,
    resetForm,
    openDialog,
    openAuthFormDialog,
    handleDelete,
    handleSizeChange,
    handleCurrentChange,
    handleSelectionChange,
    exportExcel
  };
}
