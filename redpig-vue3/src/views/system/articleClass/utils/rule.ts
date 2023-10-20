import { reactive } from "vue";
import type { FormRules } from "element-plus";

/** 自定义文章类型规则校验 */
export const formRules = reactive(<FormRules>{
  className: [
    { required: true, message: "类型名称为必填项", trigger: "blur" },
    { min: 3, max: 50, message: "类型名称长度在3到50之间", trigger: "blur" }
  ],
  remark: [
    { required: true, message: "备注为必填项", trigger: "blur" },
    { min: 3, max: 200, message: "备注长度在3到200之间", trigger: "blur" }
  ],
});
