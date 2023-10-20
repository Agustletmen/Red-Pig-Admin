import { reactive } from "vue";
import type { FormRules } from "element-plus";

/** 自定义表信息规则校验 */
export const formRules = reactive(<FormRules>{
  tableName: [
    { required: true, message: "表名为必填项", trigger: "blur" },
    { min: 3, max: 50, message: "表名长度在3到50之间", trigger: "blur" }
  ],
  tableComment: [
    { required: true, message: "表备注为必填项", trigger: "blur" },
    { min: 3, max: 50, message: "表备注长度在3到50之间", trigger: "blur" }
  ],
  className: [
    { required: true, message: "类名为必填项", trigger: "blur" },
    { min: 3, max: 50, message: "类名长度在3到50之间", trigger: "blur" }
  ],
  remark: [
    { required: true, message: "备注为必填项", trigger: "blur" },
    { min: 3, max: 50, message: "备注长度在3到50之间", trigger: "blur" }
  ],
});
