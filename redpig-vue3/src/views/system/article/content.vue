<script setup lang="ts">
import { onBeforeUnmount, ref, shallowRef } from "vue";
import { getToken } from "@/utils/auth";
import { formRules } from "./utils/rule";
import { FormProps } from "./utils/types";

const props = withDefaults(defineProps<FormProps>(), {
  formInline: () => ({
        id: 0,
        author: "",
        content: "",
        createBy: "",
        updateBy: "",
        remark: "",
  })
});

const ruleFormRef = ref();
const newFormInline = ref(props.formInline);

console.log(newFormInline);

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

    <div v-html="newFormInline.content">

    </div>

  </el-form>
</template>
