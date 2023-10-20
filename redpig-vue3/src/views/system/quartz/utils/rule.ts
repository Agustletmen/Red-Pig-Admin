import { reactive } from "vue";
import type { FormRules } from "element-plus";

/** 自定义请假流程规则校验 */
export const formRules = reactive(<FormRules>{
  jobClassName: [
    { required: true, message: "任务类必选项", trigger: "change" }
  ],
  jobGroup: [
    { required: true, message: "任务分组必填项", trigger: "blur" },
    { min: 3, max: 50, message: "任务分组长度在3到50之间", trigger: "blur" }
  ],

});
