import { reactive } from "vue";
import type { FormRules } from "element-plus";

/** 自定义文章规则校验 */
export const formRules = reactive(<FormRules>{
  iconTag: [
    { required: true, message: "图标为必填项", trigger: "blur" },
    { min: 3, max: 50, message: "图标长度在1到50之间", trigger: "blur" }
  ],
  remark: [
    { required: true, message: "备注为必填项", trigger: "blur" },
    { min: 1, max: 50, message: "备注长度在3到200之间", trigger: "blur" }
  ],
});
