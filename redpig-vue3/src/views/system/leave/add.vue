<script setup lang="ts">
import { ref } from "vue";
import { formRules } from "./utils/rule";
import { FormProps } from "./utils/types";
import { getList } from "@/api/system";
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

// getList("/user/list").then(res=>{
//   console.log(res);
// })
interface ListItem {
  id: number;
  username: string;
}

const options = ref<ListItem[]>([]);
const loading = ref(false);

const remoteMethod = async (query: string) => {
  if (query) {
    loading.value = true;

    const ret = await getList("/leave/getByUsername", { username: query });
    loading.value = false;
    console.log(ret.data);
    options.value = ret.data;
  } else {
    options.value = [];
  }
};

interface ListPD {
  id: number;
  name: string;
}

const processDefinitions = ref<ListPD[]>([]);
getList("/leave/getProcessDefinitions").then(res => {
  processDefinitions.value = res.data;
});

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
    <el-form-item label="申请人" prop="userId">
      <el-select
        v-model="newFormInline.userId"
        filterable
        remote
        reserve-keyword
        placeholder="请输入请假申请人"
        :remote-method="remoteMethod"
        :loading="loading"
      >
        <el-option
          v-for="item in options"
          :key="item.id"
          :label="item.username"
          :value="item.id"
        />
      </el-select>
    </el-form-item>

    <el-form-item label="请假理由" prop="leaveReason">
      <el-input
        type="textarea"
        v-model="newFormInline.leaveReason"
        clearable
        maxlength="200"
        placeholder="请输入请假理由"
      />
    </el-form-item>

    <el-form-item label="开始时间" prop="startTime">
      <el-date-picker
        v-model="newFormInline.startTime"
        type="datetime"
        format="YYYY-MM-DD HH:mm:ss"
        value-format="YYYY-MM-DD HH:mm:ss"
        placeholder="请选择请假开始时间"
      />
    </el-form-item>

    <el-form-item label="结束时间" prop="endTime">
      <el-date-picker
        v-model="newFormInline.endTime"
        type="datetime"
        format="YYYY-MM-DD HH:mm:ss"
        value-format="YYYY-MM-DD HH:mm:ss"
        placeholder="请选择请假结束时间"
      />
    </el-form-item>

    <el-form-item label="选择流程" prop="processDefinitionId">
      <el-select
        v-model="newFormInline.processDefinitionId"
        placeholder="Select"
        size="large"
      >
        <el-option
          v-for="item in processDefinitions"
          :key="item.id"
          :label="item.name"
          :value="item.id"
        />
      </el-select>
    </el-form-item>
  </el-form>
</template>
