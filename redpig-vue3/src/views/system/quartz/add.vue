<script setup lang="ts">
import { ref, reactive } from "vue";
import { formRules } from "./utils/rule";
import { FormProps } from "./utils/types";
import { getList } from "@/api/system";
import { noVue3Cron } from 'no-vue3-cron'
import 'no-vue3-cron/lib/noVue3Cron.css'

const props = withDefaults(defineProps<FormProps>(), {
  formInline: () => ({
    jobName: "",
    jobGroup: "",
    jobClassName: "",
    triggerName: "",
    triggerGroup: "",
    cronExpression: ""
  })
});

const ruleFormRef = ref();
const newFormInline = ref(props.formInline);
const jobs = ref([]);
getList("/quartzJob/list").then(res=>{
  jobs.value = res.data;
});

const state = reactive({
  cronPopover: false,
  cron: ''
})

const changeCron = (val) => {
  if(typeof(val) !== 'string') return false
  state.cron = val
  newFormInline.value.cronExpression = val
}

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

    <el-form-item label="任务类" prop="jobClassName">
      <el-select
        v-model="newFormInline.jobClassName"
        placeholder="Select"
        style="width: 240px"
        :disabled="newFormInline.triggerName.length>0"
      >
        <el-option
          v-for="item in jobs"
          :key="item.id"
          :label="item.jobName"
          :value="item.jobName"
        />
      </el-select>
    </el-form-item>

    <el-form-item label="任务分组" prop="jobGroup">
      <el-input
        v-model="newFormInline.jobGroup"
        clearable
        placeholder="请输入任务分组"
        :disabled="newFormInline.triggerName.length>0"
      />
    </el-form-item>

    <el-form-item label="任务表达式" prop="cronExpression">
      <el-input v-model="state.cron" placeholder="cron表达式..." prop="cronExpression">
        <template #append>
          <el-popover v-model:visible="state.cronPopover" width="700px" trigger="manual">
            <noVue3Cron
              :cron-value="state.cron"
              @change="changeCron"
              @close="state.cronPopover=false"
              max-height="400px"
              i18n="cn"
            ></noVue3Cron>
            <template #reference>
              <el-button class="m-2" size="default" type="success" @click="state.cronPopover = !state.cronPopover">设置</el-button>
            </template>
          </el-popover>
        </template>
      </el-input>
    </el-form-item>

  </el-form>
</template>
