<script setup lang="ts">
import { ref } from "vue";
import { FormProps } from "./utils/types";
import { getFile } from "@/api/system";

const props = withDefaults(defineProps<FormProps>(), {
  formInline: () => ({
    deploymentId: ""
  })
});

const ruleFormRef = ref();
const newFormInline = ref(props.formInline);
const imageUrl = ref("");

const getImage = async deploymentId => {
  const ret = await getFile("/processDefinition/getById", {
    deploymentId: deploymentId
  });
  console.log(ret);
  imageUrl.value = URL.createObjectURL(ret);
};

getImage(newFormInline.value.deploymentId);

function getRef() {
  return ruleFormRef.value;
}

defineExpose({ getRef });
</script>

<template>
  <el-form ref="ruleFormRef" :model="newFormInline" label-width="82px">
    <img :src="imageUrl" />
  </el-form>
</template>
