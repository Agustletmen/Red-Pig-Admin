<script setup lang="ts">
import { ref } from "vue";
import { formRules } from "./utils/rule";
import { FormProps } from "./utils/types";

const props = withDefaults(defineProps<FormProps>(), {
  formInline: () => ({
    id: 0,
    name: "",
    tag: "",
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
    <el-form-item label="角色名称" prop="name">
      <el-input
        v-model="newFormInline.name"
        clearable
        placeholder="请输入角色名称"
      />
    </el-form-item>

    <el-form-item label="角色标签" prop="tag">
      <el-input
        v-model="newFormInline.tag"
        clearable
        placeholder="请输入角色标签"
      />
    </el-form-item>

    <el-form-item label="角色描述" prop="remark">
      <el-input
        v-model="newFormInline.remark"
        maxlength="100"
        showWordLimit
        type="textarea"
        placeholder="请输入角色描述"
      />
    </el-form-item>
  </el-form>
</template>
