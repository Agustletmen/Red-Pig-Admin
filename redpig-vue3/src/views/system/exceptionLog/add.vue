<script setup lang="ts">
import { ref } from "vue";
import { formRules } from "./utils/rule";
import { FormProps } from "./utils/types";

const props = withDefaults(defineProps<FormProps>(), {
  formInline: () => ({
        id: 0,
        excRequParam: "",
        operMethod: "",
        excName: "",
        excMessage: "",
        operUserId: "",
        operUserName: "",
        operUri: "",
        operIp: "",
        operCreateTime: "",
        createBy: "",
        updateBy: "",
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

      <el-form-item label="请求参数" prop="excRequParam">
        <el-input v-model="newFormInline.excRequParam" clearable placeholder="请输入请求参数" />
      </el-form-item>

      <el-form-item label="操作方法" prop="operMethod">
        <el-input v-model="newFormInline.operMethod" clearable placeholder="请输入操作方法" />
      </el-form-item>

      <el-form-item label="异常名称" prop="excName">
        <el-input v-model="newFormInline.excName" clearable placeholder="请输入异常名称" />
      </el-form-item>

      <el-form-item label="异常信息" prop="excMessage">
        <el-input v-model="newFormInline.excMessage" clearable placeholder="请输入异常信息" />
      </el-form-item>

      <el-form-item label="操作者用户ID" prop="operUserId">
        <el-input v-model="newFormInline.operUserId" clearable placeholder="请输入操作者用户ID" />
      </el-form-item>

      <el-form-item label="操作者用户名" prop="operUserName">
        <el-input v-model="newFormInline.operUserName" clearable placeholder="请输入操作者用户名" />
      </el-form-item>

      <el-form-item label="操作的地址" prop="operUri">
        <el-input v-model="newFormInline.operUri" clearable placeholder="请输入操作的地址" />
      </el-form-item>

      <el-form-item label="操作者IP" prop="operIp">
        <el-input v-model="newFormInline.operIp" clearable placeholder="请输入操作者IP" />
      </el-form-item>

      <el-form-item label="操作时间" prop="operCreateTime">
        <el-input v-model="newFormInline.operCreateTime" clearable placeholder="请输入操作时间" />
      </el-form-item>

  </el-form>
</template>
