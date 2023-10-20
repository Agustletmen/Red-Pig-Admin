<script setup lang="ts">
import { ref } from "vue";
import { formRules } from "./utils/rule";
import { FormProps } from "./utils/types";

const props = withDefaults(defineProps<FormProps>(), {
  formInline: () => ({
        id: 0,
        name: "",
        createBy: "",
        updateBy: "",
        remark: "",
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

      <el-form-item label="商品名称" prop="name">
        <el-input v-model="newFormInline.name" clearable placeholder="请输入商品名称" />
      </el-form-item>

      <el-form-item label="商品描述" prop="remark">
        <el-input
                v-model="newFormInline.remark"
                maxlength="80"
                showWordLimit
                type="textarea"
                placeholder="请输入商品描述"
        />
      </el-form-item>
  </el-form>
</template>
