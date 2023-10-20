<script setup lang="ts">
import { ref } from "vue";
import { formRules } from "./utils/rule";
import { FormProps } from "./utils/types";
import { getFile ,isEnd} from "@/api/system";
const props = withDefaults(defineProps<FormProps>(), {
  formInline: () => ({
    id: 0,
    userId: 0,
    leaveReason: "",
    startTime: "",
    endTime: "",
    processInstanceId: "",
    processDefinitionId: "",
    createBy: "",
    updateBy: ""
  })
});

const ruleFormRef = ref();
const newFormInline = ref(props.formInline);
const imageUrl = ref("");

const getImage = async processInstanceId => {
  const ret = await getFile("/leave/viewImage", {
    processInstanceId: processInstanceId
  });
  imageUrl.value = URL.createObjectURL(ret);
};

getImage(newFormInline.value.processInstanceId);

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
    <embed
      :src="imageUrl"
      width="800"
      height="400"
      type="image/svg+xml"
      pluginspage="http://www.adobe.com/svg/viewer/install/"
    />

  </el-form>
</template>
