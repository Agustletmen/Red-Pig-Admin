import { reactive } from "vue";
import type { FormRules } from "element-plus";

/** 自定义表单规则校验 */
export const formRules = reactive(<FormRules>{
  username: [
    {
      required: true,
      message: "用户名必填:长度最低3，最大50",
      trigger: "blur",
      min: 3,
      max: 50
    }
  ],
  nickname: [
    {
      required: true,
      message: "昵称名必填:长度最低3，最大50",
      trigger: "blur",
      min: 3,
      max: 50
    }
  ]
});
