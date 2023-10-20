import { reactive } from "vue";
import type { FormRules } from "element-plus";

/** 自定义表单规则校验 */
export const formRules = reactive(<FormRules>{
  name: [
    { required: true, message: "权限名称为必填项", trigger: "blur" },
    { min: 3, max: 50, message: "权限名称长度在3到50之间", trigger: "blur" }
  ],
  tag: [
    { required: true, message: "权限标签为必填项", trigger: "blur" },
    { min: 3, max: 50, message: "权限标签长度在3到50之间", trigger: "blur" }
  ],
  remark: [
    { required: true, message: "备注为必填项", trigger: "blur" },
    { min: 3, max: 50, message: "备注长度在3到100之间", trigger: "blur" }
  ]
});
