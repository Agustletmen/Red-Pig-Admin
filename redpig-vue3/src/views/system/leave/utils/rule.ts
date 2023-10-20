import { reactive } from "vue";
import type { FormRules } from "element-plus";

/** 自定义请假流程规则校验 */
export const formRules = reactive(<FormRules>{
  userId: [
    { required: true, message: "申请人必选项", trigger: "change" }
  ],
  leaveReason: [
    { required: true, message: "请假理由必填项", trigger: "blur" },
    { min: 3, max: 50, message: "请假理由长度在3到200之间", trigger: "blur" }
  ],
  startTime: [
    { required: true, message: "开始时间必选项", trigger: "change" }
  ],
  endTime: [
    { required: true, message: "结束时间必选项", trigger: "change" }
  ],
  processDefinitionId: [
    { required: true, message: "流程必选项", trigger: "change" }
  ],
});
