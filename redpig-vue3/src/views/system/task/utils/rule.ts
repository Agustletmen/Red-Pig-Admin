import { reactive } from "vue";
import type { FormRules } from "element-plus";

/** 自定义流程任务规则校验 */
export const formRules = reactive(<FormRules>{
  // name: [{ required: true, message: "xxx为必填项", trigger: "blur" }],
  // code: [{ required: true, message: "xxx为必填项", trigger: "blur" }]
});
