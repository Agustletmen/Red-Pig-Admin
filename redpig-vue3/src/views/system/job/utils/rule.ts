import { reactive } from "vue";
import type { FormRules } from "element-plus";

/** 自定义任务类管理规则校验 */
export const formRules = reactive(<FormRules>{
  jobName: [
    { required: true, message: "类信息必填项", trigger: "blur" },
    { min: 3, max: 200, message: "类信息长度在3到200之间", trigger: "blur" }
  ],
  remark: [
    { required: true, message: "备注为必填项", trigger: "blur" },
    { min: 3, max: 200, message: "备注长度在3到200之间", trigger: "blur" }
  ],
});
