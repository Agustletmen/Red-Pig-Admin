import { reactive } from "vue";
import type { FormRules } from "element-plus";

/** 自定义表单规则校验 */
export const formRules = reactive(<FormRules>{
  name: [
    { required: true, message: "菜单名称为必填项", trigger: "blur" },
    { min: 3, max: 50, message: "菜单名称长度在3到50之间", trigger: "blur" }
  ],
  title: [
    { required: true, message: "标题为必填项", trigger: "blur" },
    { min: 3, max: 50, message: "标题长度在3到50之间", trigger: "blur" }
  ],
  type: [
    {required: true,message: "类型必须选选择一个",trigger: 'change'}
  ],
  icon: [
    { required: true, message: "图标必须选择",trigger: "change"}
  ],
  path: [
    { required: true, message: "必填项",trigger: "blur"}
  ],

});
