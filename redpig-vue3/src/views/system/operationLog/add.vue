<script setup lang="ts">
import { ref } from "vue";
import { formRules } from "./utils/rule";
import { FormProps } from "./utils/types";

const props = withDefaults(defineProps<FormProps>(), {
  formInline: () => ({
        id: 0,
        operModul: "",
        operType: "",
        operDesc: "",
        operMethod: "",
        operRequParam: "",
        operRespParam: "",
        operUserId: "",
        operUserName: "",
        operIp: "",
        operUri: "",
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

      <el-form-item label="操作模块" prop="operModul">
        <el-input v-model="newFormInline.operModul" clearable placeholder="请输入操作模块" />
      </el-form-item>

      <el-form-item label="操作类型" prop="operType">
        <el-input v-model="newFormInline.operType" clearable placeholder="请输入操作类型" />
      </el-form-item>

      <el-form-item label="操作描述" prop="operDesc">
        <el-input v-model="newFormInline.operDesc" clearable placeholder="请输入操作描述" />
      </el-form-item>

      <el-form-item label="请求方法" prop="operMethod">
        <el-input v-model="newFormInline.operMethod" clearable placeholder="请输入请求方法" />
      </el-form-item>

      <el-form-item label="请求参数" prop="operRequParam">
        <el-input v-model="newFormInline.operRequParam" clearable placeholder="请输入请求参数" />
      </el-form-item>

      <el-form-item label="返回参数" prop="operRespParam">
        <el-input v-model="newFormInline.operRespParam" clearable placeholder="请输入返回参数" />
      </el-form-item>

      <el-form-item label="用户id" prop="operUserId">
        <el-input v-model="newFormInline.operUserId" clearable placeholder="请输入用户id" />
      </el-form-item>

      <el-form-item label="用户名称" prop="operUserName">
        <el-input v-model="newFormInline.operUserName" clearable placeholder="请输入用户名称" />
      </el-form-item>

      <el-form-item label="操作IP" prop="operIp">
        <el-input v-model="newFormInline.operIp" clearable placeholder="请输入操作IP" />
      </el-form-item>

      <el-form-item label="操作路径" prop="operUri">
        <el-input v-model="newFormInline.operUri" clearable placeholder="请输入操作路径" />
      </el-form-item>

  </el-form>
</template>
