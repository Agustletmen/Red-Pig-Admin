<script setup lang="ts">
import { ref } from "vue";
import { formRules } from "./utils/rule";
import { FormProps } from "./utils/types";

const props = withDefaults(defineProps<FormProps>(), {
  formInline: () => ({
        id: 0,
        taskId: "",
        taskName: "",
        taskExecutionId: "",
        taskDescription: "",
        taskAssignee: "",
        taskProcessInstanceId: 0,
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

      <el-form-item label="任务ID" prop="taskId">
        <el-input v-model="newFormInline.taskId" clearable placeholder="请输入任务ID" />
      </el-form-item>

      <el-form-item label="任务名称" prop="taskName">
        <el-input v-model="newFormInline.taskName" clearable placeholder="请输入任务名称" />
      </el-form-item>

      <el-form-item label="执行ID" prop="taskExecutionId">
        <el-input v-model="newFormInline.taskExecutionId" clearable placeholder="请输入执行ID" />
      </el-form-item>

      <el-form-item label="任务描述" prop="taskDescription">
        <el-input v-model="newFormInline.taskDescription" clearable placeholder="请输入任务描述" />
      </el-form-item>

      <el-form-item label="任务处理人" prop="taskAssignee">
        <el-input v-model="newFormInline.taskAssignee" clearable placeholder="请输入任务处理人" />
      </el-form-item>

      <el-form-item label="流程实例ID" prop="taskProcessInstanceId">
        <el-input v-model="newFormInline.taskProcessInstanceId" clearable placeholder="请输入流程实例ID" />
      </el-form-item>

  </el-form>
</template>
