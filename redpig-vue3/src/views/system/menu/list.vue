<script setup lang="ts">
import { ref } from "vue";
import { useMenu } from "./utils/hook";
import { PureTableBar } from "@/components/RePureTableBar";
import { useRenderIcon } from "@/components/ReIcon/src/hooks";
import tree from "./tree.vue";
// import Database from "@iconify-icons/ri/database-2-line";
// import More from "@iconify-icons/ep/more-filled";
import Delete from "@iconify-icons/ep/delete";
import EditPen from "@iconify-icons/ep/edit-pen";
import Edit from "@iconify-icons/ep/edit";
import Search from "@iconify-icons/ep/search";
import Refresh from "@iconify-icons/ep/refresh";
import Menu from "@iconify-icons/ep/menu";
import AddFill from "@iconify-icons/ri/add-circle-line";
import Export from "@iconify-icons/ph/export";

defineOptions({
  // eslint-disable-next-line vue/no-reserved-component-names
  name: "Menu"
});

const formRef = ref();
const {
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
} = useMenu();

function page(parendId) {
  form.parendId = parendId;
  onSearch();
}

const tableRef = ref();
</script>

<template>
  <div class="main">
    <tree class="w-[12%] float-left" @page="page" />
    <div class="float-right w-[87%]">
      <el-form
        ref="formRef"
        :inline="true"
        :model="form"
        class="search-form bg-bg_color w-[99/100] pl-8 pt-[12px]"
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

      <PureTableBar title="菜单列表" :columns="columns" @refresh="onSearch">
        <template #buttons>
          <el-button
            type="primary"
            :icon="useRenderIcon(AddFill)"
            @click="openDialog()"
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
            ref="tableRef"
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

              <el-button
                class="reset-margin"
                link
                type="primary"
                :size="size"
                :icon="useRenderIcon(Menu)"
                @click="openAuthFormDialog('授权', row)"
              >
                授权
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
