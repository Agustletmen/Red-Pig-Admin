import { reactive } from "vue";
import type { FormRules } from "element-plus";
import {checkMobile,checkEmail} from "@/utils/formValidate";

/** 自定义表单规则校验 */
export const formRules = reactive(<FormRules>{
  deptName: [
    { required: true, message: "部门名称为必填项", trigger: "blur" },
    { min: 2, max: 50, message: "部门名称长度在3到50之间", trigger: "blur" }
  ],
  manager: [
    { required: true, message: "负责人为必填项", trigger: "blur" },
    { min: 2, max: 50, message: "负责人长度在3到50之间", trigger: "blur" }
  ],
  phone: [
    { required: true, message: "请输入手机号", trigger: "blur" },
    { validator: checkMobile, trigger: "blur" }
  ],
  email: [
    { required: true, message: "请输入邮箱", trigger: "blur" },
    { validator: checkEmail, trigger: "blur" }
  ]
});
