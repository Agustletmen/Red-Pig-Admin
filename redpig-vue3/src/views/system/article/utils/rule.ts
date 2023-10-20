import { reactive } from "vue";
import type { FormRules } from "element-plus";

/** 自定义文章规则校验 */
export const formRules = reactive(<FormRules>{
  author: [
    { required: true, message: "作者为必填项", trigger: "blur" },
    { min: 3, max: 50, message: "作者长度在3到50之间", trigger: "blur" }
  ],
  content: [
    { required: true, message: "内容为必填项", trigger: "blur" }
  ],
  remark: [
    { required: true, message: "备注为必填项", trigger: "blur" },
    { min: 3, max: 50, message: "备注长度在3到50之间", trigger: "blur" }
  ],
});
