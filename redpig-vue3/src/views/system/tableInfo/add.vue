<script setup lang="ts">
import { ref } from "vue";
import { formRules } from "./utils/rule";
import { FormProps } from "./utils/types";

const props = withDefaults(defineProps<FormProps>(), {
  formInline: () => ({
    id: 0,
    tableName: "",
    tableComment: "",
    className: "",
    createBy: "",
    updateBy: "",
    remark: ""
  })
});

const ruleFormRef = ref();
const newFormInline = ref(props.formInline);

function getRef() {
  return ruleFormRef.value;
}

defineExpose({ getRef });
</script>

<template>
  <el-form
    ref="ruleFormRef"
    :model="newFormInline"
    :rules="formRules"
    label-width="82px"
  >
    <el-form-item label="表名" prop="tableName">
      <el-input
        v-model="newFormInline.tableName"
        clearable
        placeholder="请输入表名"
      />
    </el-form-item>

    <el-form-item label="表备注" prop="tableComment">
      <el-input
        v-model="newFormInline.tableComment"
        clearable
        placeholder="请输入表备注"
      />
    </el-form-item>

    <el-form-item label="类名" prop="className">
      <el-input
        v-model="newFormInline.className"
        clearable
        placeholder="请输入类名"
      />
    </el-form-item>

    <el-form-item label="备注" prop="remark">
      <el-input
        v-model="newFormInline.remark"
        maxlength="80"
        showWordLimit
        type="textarea"
        placeholder="请输入备注"
      />
    </el-form-item>
  </el-form>
</template>
