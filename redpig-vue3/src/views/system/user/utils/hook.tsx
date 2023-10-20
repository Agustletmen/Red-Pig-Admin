import dayjs from "dayjs";
import editForm from "../add.vue";
import authForm from "../auth.vue";
import { message } from "@/utils/message";
import {
  getPageList,
  delById,
  saveOrUpdate,
  removeBatchByIds,
  ReSetPassword,
  saveAuth
} from "@/api/system";

import { ElMessageBox } from "element-plus";
import { type PaginationProps } from "@pureadmin/table";
import { reactive, ref, computed, onMounted, h } from "vue";
import { type FormItemProps, type UserRolePerm } from "../utils/types";
import { addDialog } from "@/components/ReDialog";

import { utils, writeFile } from "xlsx";

export function useUser() {
  const form = reactive({
    username: "",
    mobile: "",
    enabled: null,
    time: "",
    deptId: ""
  });
  const formRef = ref();
  const dataList = ref([]);
  const multipleSelection = ref([]);
  const single = ref(true);
  const multiple = ref(true);
  const loading = ref(true);
  const switchLoadMap = ref({});
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
    //   label: "序号",
    //   type: "index",
    //   width: 70,
    //   fixed: "left"
    // },
    {
      label: "用户编号",
      prop: "id",
      minWidth: 130
    },
    {
      label: "用户名称",
      prop: "username",
      minWidth: 130
    },
    {
      label: "用户昵称",
      prop: "nickname",
      minWidth: 130
    },
    {
      label: "性别",
      prop: "sex",
      minWidth: 90,
      cellRenderer: ({ row, props }) => (
        <el-tag
          size={props.size}
          type={row.sex === 0 ? "danger" : ""}
          effect="plain"
        >
          {row.sex === 0 ? "女" : "男"}
        </el-tag>
      )
    },
    {
      label: "部门",
      prop: "dept",
      minWidth: 90,
      formatter: ({ dept }) => dept?.deptName ?? "未选择部门"
    },
    // {
    //   label: "角色",
    //   prop: "roles",
    //   width: 250,
    //   cellRenderer: scope =>
    //     scope.row.roles != null && scope.row.roles.length > 0
    //       ? scope.row.roles.map(e => e.name + ":" + e.tag).join(",")
    //       : "没有角色"
    // },
    // {
    //   label: "权限",
    //   prop: "perms",
    //   width: 250,
    //   cellRenderer: scope =>
    //     scope.row.perms != null && scope.row.perms.length > 0
    //       ? scope.row.perms.map(e => e.name + ":" + e.tag).join(",")
    //       : "没有权限"
    // },
    {
      label: "手机号码",
      prop: "mobile",
      minWidth: 90
    },
    {
      label: "状态",
      prop: "enabled",
      minWidth: 90,
      cellRenderer: scope => (
        <el-switch
          size={scope.props.size === "small" ? "small" : "default"}
          loading={switchLoadMap.value[scope.index]?.loading}
          v-model={scope.row.enabled}
          active-value={true}
          inactive-value={false}
          active-text="已开启"
          inactive-text="已关闭"
          inline-prompt
          onChange={() => onChange(scope as any)}
        />
      )
    },
    {
      label: "创建时间",
      minWidth: 90,
      prop: "createTime",
      formatter: ({ createTime }) =>
        dayjs(createTime).format("YYYY-MM-DD HH:mm:ss")
    },
    {
      label: "操作",
      fixed: "right",
      width: 180,
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

  function onChange({ row, index }) {
    ElMessageBox.confirm(
      `确认要<strong>${
        row.enabled === false ? "停用" : "启用"
      }</strong><strong style='color:var(--el-color-primary)'>${
        row.username
      }</strong>用户吗?`,
      "系统提示",
      {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
        dangerouslyUseHTMLString: true,
        draggable: true
      }
    )
      .then(() => {
        saveOrUpdate("/user/saveOrUpdate", row).then(() => {
          switchLoadMap.value[index] = Object.assign(
            {},
            switchLoadMap.value[index],
            {
              loading: true
            }
          );

          setTimeout(() => {
            switchLoadMap.value[index] = Object.assign(
              {},
              switchLoadMap.value[index],
              {
                loading: false
              }
            );
            message("已成功修改用户状态", {
              type: "success"
            });
          }, 300);
        });
      })
      .catch(() => {
        row.enabled === false ? (row.enabled = true) : (row.enabled = false);
      });
  }

  function handleUpdate(row) {
    console.log(row);
  }

  function resetpassword(row) {
    ReSetPassword({ id: row.id }).then(() => {
      onSearch();
    });
  }

  function handleDelete(row) {
    delById("/user/delById", { id: row.id }).then(() => {
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
        removeBatchByIds("/user/removeBatchByIds", ids).then(() => {
          message(`批量删除成功`, { type: "success" });
          onSearch();
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

  function openDialog(title = "新增", row?: FormItemProps) {
    addDialog({
      title: `${title}用户`,
      props: {
        formInline: {
          id: row?.id ?? 0,
          username: row?.username ?? "",
          nickname: row?.nickname ?? "",
          password: row?.password ?? "",
          enabled: row?.enabled ?? true,
          sex: row?.sex ?? 0,
          mobile: row?.mobile ?? "",
          email: row?.email ?? "",
          deptId: row?.dept?.id ?? 1 //默认选择
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
          saveOrUpdate("/user/saveOrUpdate", curData).then(() => {
            message(`您${title}了名称为${curData.nickname}的这条数据`, {
              type: "success"
            });
            done(); // 关闭弹框
            onSearch(); // 刷新表格数据
          });
        }
        FormRef.validate(valid => {
          if (valid) {
            console.log("curData", curData);
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

  function openAuthFormDialog(title = "授权", row?: UserRolePerm) {
    addDialog({
      title: `${title}`,
      props: {
        formInline: {
          id: row?.id ?? "",
          roles: row?.roles ?? [],
          perms: row?.perms ?? [],
          username: row?.username ?? ""
        }
      },
      width: "45%",
      draggable: true,
      fullscreenIcon: true,
      closeOnClickModal: false,
      contentRenderer: () => h(authForm, { ref: formRef }),
      beforeSure: (done, { options }) => {
        const curData = options.props.formInline as FormItemProps;
        saveAuth("/user/saveAuth", curData).then(() => {
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

  async function onSearch() {
    loading.value = true;
    const { data } = await getPageList("/user/page", {
      currentPage: pagination.currentPage,
      pageSize: pagination.pageSize,
      username: form.username,
      enabled: form.enabled,
      mobile: form.mobile,
      startTime: form.time[0],
      endTime: form.time[1],
      deptId: form.deptId
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

  return {
    form,
    loading,
    columns,
    dataList,
    pagination,
    buttonClass,
    single,
    multiple,
    openAuthFormDialog,
    exportExcel,
    updateRow,
    delByIds,
    openDialog,
    onSearch,
    resetForm,
    handleUpdate,
    resetpassword,
    handleDelete,
    handleSizeChange,
    handleCurrentChange,
    handleSelectionChange
  };
}
