import { reactive } from "vue";
import type { FormRules } from "element-plus";

/** 自定义表单规则校验 */
export const formRules = reactive(<FormRules>{
  name: [
    { required: true, message: "角色名称为必填项", trigger: "blur" },
    { min: 3, max: 50, message: "角色名称长度在3到50之间", trigger: "blur" }
  ],
  tag: [
    { required: true, message: "角色标识为必填项", trigger: "blur" },
    { min: 3, max: 50, message: "角色标识长度在3到50之间", trigger: "blur" }
  ],
  remark: [
    { required: true, message: "角色描述为必填项", trigger: "blur" },
    { min: 3, max: 50, message: "角色描述长度在3到100之间", trigger: "blur" }
  ]
});
