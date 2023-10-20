<script setup lang="ts">
import { ref } from "vue";
import { formRules } from "./utils/rule";
import { FormProps } from "./utils/types";

const props = withDefaults(defineProps<FormProps>(), {
  formInline: () => ({
    id: 0,
    iconTag: "",
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
    <el-form-item label="图标" prop="iconTag">
      <el-input
        v-model="newFormInline.iconTag"
        clearable
        placeholder="请输入图标"
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

    <el-form-item label="图标官网">
      <el-button>
        <el-link href="https://icon-sets.iconify.design" target="_blank"
          >图标地址</el-link
        >
      </el-button>
    </el-form-item>
  </el-form>
</template>
