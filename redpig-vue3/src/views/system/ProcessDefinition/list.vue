<script setup lang="ts">
import { ref } from "vue";
import { useProcessDefinition } from "./utils/hook";
import { PureTableBar } from "@/components/RePureTableBar";
import { useRenderIcon } from "@/components/ReIcon/src/hooks";

import Delete from "@iconify-icons/ep/delete";
import VIEW from "@iconify-icons/ep/view";
import Search from "@iconify-icons/ep/search";
import Refresh from "@iconify-icons/ep/refresh";

import IconifyIconOnline from "@/components/ReIcon/src/iconifyIconOnline";
import { ElMessage, UploadRawFile } from "element-plus";
import { upload } from "@/api/system";

defineOptions({
  name: "ProcessDefinition"
});

const formRef = ref();
const {
  form,
  loading,
  columns,
  dataList,
  pagination,
  onSearch,
  resetForm,
  openDialog,
  handleDelete,
  handleSizeChange,
  handleCurrentChange,
  handleSelectionChange
} = useProcessDefinition();

function beforeUpload(file: UploadRawFile) {
  if (file.size / 1024 / 1024 > 5) {
    ElMessage.error("图片大小不能超过5MB!");
    return false;
  }
  return true;
}

function uploadAction(option: any) {
  const param = new FormData();
  param.append("processDefinitionFile", option.file);

  upload("/processDefinition/deploy", param).then(() => {
    setTimeout(() => {
      ElMessage.success("部署成功！");
      onSearch();
    }, 3000);
  });
}
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

    <PureTableBar title="流程定义" :columns="columns" @refresh="onSearch">
      <template #buttons>
        <el-upload
          ref="upload"
          class="upload-demo"
          :show-file-list="false"
          :before-upload="beforeUpload"
          :http-request="uploadAction"
        >
          <template #trigger>
            <el-button type="primary">
              <IconifyIconOnline
                icon="solar:upload-broken"
                width="30px"
                height="20px"
              />
              部署流程
            </el-button>
          </template>
        </el-upload>
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
              :icon="useRenderIcon(VIEW)"
              @click="openDialog('流程图', row)"
            >
              查看流程图
            </el-button>

            <el-popconfirm
              :title="`是否确认删除这条数据`"
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
