<script setup lang="ts">
import { ref } from "vue";
import { useOperationLog } from "./utils/hook";
import { PureTableBar } from "@/components/RePureTableBar";
import { useRenderIcon } from "@/components/ReIcon/src/hooks";

import Delete from "@iconify-icons/ep/delete";
import EditPen from "@iconify-icons/ep/edit-pen";
import Search from "@iconify-icons/ep/search";
import Refresh from "@iconify-icons/ep/refresh";
import AddFill from "@iconify-icons/ri/add-circle-line";
import Edit from "@iconify-icons/ep/edit";
import Export from "@iconify-icons/ph/export";

defineOptions({
  name: "OperationLog"
});

const formRef = ref();
const {
  form,
  loading,
  columns,
  dataList,
  pagination,
  updateRow,
  exportExcel,
  single,
  multiple,
  delByIds,
  onSearch,
  resetForm,
  openDialog,
  handleDelete,
  handleSizeChange,
  handleCurrentChange,
  handleSelectionChange
} = useOperationLog();
</script>

<template>
  <div class="main w-[90%]">
    <el-form
      ref="formRef"
      :inline="true"
      :model="form"
      class="search-form bg-bg_color w-[60/100] pl-8 pt-[12px]"
    >
      <el-form-item label="更新时间：" prop="time">
        <el-date-picker
          v-model="form.time"
          type="daterange"
          range-separator="到"
          format="YYYY-MM-DD"
          value-format="YYYY-MM-DD"
          start-placeholder="更新开始时间"
          end-placeholder="更新结束时间"
        />
      </el-form-item>

      <el-form-item>
        <el-button
          type="primary"
          :icon="useRenderIcon(Search)"
          :loading="loading"
          @click="onSearch"
        >
          搜索
        </el-button>
        <el-button :icon="useRenderIcon(Refresh)" @click="resetForm(formRef)">
          重置
        </el-button>
      </el-form-item>
    </el-form>

    <PureTableBar title="日志操作记录列表" :columns="columns" @refresh="onSearch">
      <template #buttons>
        <el-button
          type="primary"
          :icon="useRenderIcon(AddFill)"
          @click="openDialog('新增')"
        >
          新增
        </el-button>

        <el-button
          type="warning"
          :icon="useRenderIcon(Edit)"
          @click="updateRow"
          :disabled="single"
        >
          修改
        </el-button>

        <el-button
          type="danger"
          :icon="useRenderIcon(Delete)"
          @click="delByIds"
          :disabled="multiple"
        >
          删除
        </el-button>

        <el-button
          type="success"
          :icon="useRenderIcon(Export)"
          @click="exportExcel"
        >
          导出
        </el-button>
      </template>
      <template v-slot="{ size, dynamicColumns }">
        <pure-table
          :border="true"
          align-whole="center"
          showOverflowTooltip
          table-layout="auto"
          :loading="loading"
          :size="size"
          adaptive
          :data="dataList"
          :columns="dynamicColumns"
          :pagination="pagination"
          :paginationSmall="size === 'small' ? true : false"
          :header-cell-style="{
            background: 'var(--el-table-row-hover-bg-color)',
            color: 'var(--el-text-color-primary)'
          }"
          @selection-change="handleSelectionChange"
          @page-size-change="handleSizeChange"
          @page-current-change="handleCurrentChange"
        >
          <template #operation="{ row }">
            <el-button
              class="reset-margin"
              link
              type="primary"
              :size="size"
              :icon="useRenderIcon(EditPen)"
              @click="openDialog('编辑', row)"
            >
              修改
            </el-button>

            <el-popconfirm
              :title="`是否确认删除ID为${row.id}的这条数据`"
              @confirm="handleDelete(row)"
            >
              <template #reference>
                <el-button
                  class="reset-margin"
                  link
                  type="primary"
                  :size="size"
                  :icon="useRenderIcon(Delete)"
                >
                  删除
                </el-button>
              </template>
            </el-popconfirm>
          </template>
        </pure-table>
      </template>
    </PureTableBar>
  </div>
</template>

<style scoped lang="scss">
:deep(.el-dropdown-menu__item i) {
  margin: 0;
}

.search-form {
  :deep(.el-form-item) {
    margin-bottom: 12px;
  }
}
</style>
