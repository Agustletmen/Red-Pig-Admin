<script setup lang="ts">
import { ref } from "vue";
import { formRules } from "./utils/rule";
import { FormProps } from "./utils/types";

const props = withDefaults(defineProps<FormProps>(), {
  formInline: () => ({
        id: 0,
        jobName: "",
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

      <el-form-item label="类信息" prop="jobName">
        <el-input v-model="newFormInline.jobName" clearable placeholder="请输入类信息" />
      </el-form-item>

      <el-form-item label="备注" prop="remark">
        <el-input
                v-model="newFormInline.remark"
                maxlength="200"
                showWordLimit
                type="textarea"
                placeholder="请输入备注"
        />
      </el-form-item>
  </el-form>
</template>
