<script setup lang="ts">
import {onMounted, ref} from "vue";
//import { formRules } from "./utils/rule";
import { FormProps } from "./utils/types";
import { getList } from "@/api/system";

import { reactive } from "vue";
import type { FormRules } from "element-plus";
import {checkMobile,checkEmail} from "@/utils/formValidate";
import {handleTree} from "@/utils/tree";
import {ElTree} from "element-plus";

const props = withDefaults(defineProps<FormProps>(), {
  formInline: () => ({
    id: 0,
    username: "",
    password: "",
    nickname: "",
    enabled: true,
    sex: 0,
    mobile: "",
    email: "",
    dept: null,
    deptId: 0
  })
});

const ruleFormRef = ref();
const newFormInline = ref(props.formInline);
newFormInline.value.password = "";

const repassword = ref();

const validatePass = (rule: any, value: any, callback: any) => {
  console.log(newFormInline.value.password);
  if (repassword.value === "") {
    callback(new Error("请输入密码"));
  } else if (repassword.value !== newFormInline.value.password) {
    callback(new Error("两次密码输入不匹配!"));
  } else {
    callback();
  }
};

/** 自定义表单规则校验 */
const formRules = reactive<FormRules>({
  username: [
    { required: true, message: "请输入用户名", trigger: "blur" },
    { min: 3, max: 50, message: "用户名长度在3到50之间", trigger: "blur" }
  ],
  nickname: [
    { required: true, message: "请输入昵称", trigger: "blur" },
    { min: 3, max: 50, message: "昵称长度在3到50之间", trigger: "blur" }
  ],
  password: [
    { required: true, message: "请输入密码", trigger: "blur" },
    { min: 3, max: 50, message: "密码长度在3到50之间", trigger: "blur" }
  ],
  repassword: [{ validator: validatePass, trigger: "blur" }],
  mobile: [
    { required: true, message: "请输入手机号", trigger: "blur" },
    {
      validator: checkMobile,
      trigger: "blur"
    }
  ],
  email: [
    {
      required: true,
      message: "请输入邮箱",
      trigger: "blur"
    },
    {
      validator: checkEmail,
      trigger: "blur"
    }
  ]
});

const depts = ref([]);

getList("/dept/getDeptList").then(res => {
  depts.value = res.data;
});

const treeData = ref([]);

onMounted(async () => {
  const { data } = await getList("/dept/getDeptList");
  treeData.value = handleTree(data);
});

const defaultProps = {
  children: "children",
  label: "deptName"
};

const checkedKeys = ref([newFormInline.value.deptId]);
const treeRef = ref<InstanceType<typeof ElTree>>();
const setCheckedKeys = d => {
  treeRef.value!.setCheckedKeys([], false);
  treeRef.value!.setCheckedKeys([d.id], true);
  newFormInline.value.deptId = d.id;
};

function getRef() {
  return ruleFormRef.value;
}

defineExpose({ getRef });
</script>

<template>
  <el-form
    ref="ruleFormRef"
    :model="newFormInline"
    :rules="formRules"
    label-width="82px"
  >
    <el-row>
      <el-col :span="12">
        <el-form-item label="用户名" prop="username">
          <el-input
            v-model="newFormInline.username"
            clearable
            placeholder="请输入用户名"
          />
        </el-form-item>
      </el-col>

      <el-col :span="12">
        <el-form-item label="昵称" prop="nickname">
          <el-input
            v-model="newFormInline.nickname"
            clearable
            placeholder="请输入昵称"
          />
        </el-form-item>
      </el-col>
    </el-row>

    <el-row>
      <el-col :span="12">
        <el-form-item label="密码" prop="password">
          <el-input
            v-model="newFormInline.password"
            type="password"
            clearable
            placeholder="请输入密码"
          />
        </el-form-item>
      </el-col>

      <el-col :span="12">
        <el-form-item label="重复密码" prop="repassword">
          <el-input
            clearable
            placeholder="请输入重复密码"
            v-model="repassword"
          />
        </el-form-item>
      </el-col>
    </el-row>

    <el-row>
      <el-col :span="12">
        <el-form-item label="用户状态" prop="enabled">
          <el-switch
            size="default"
            v-model="newFormInline.enabled"
            :active-value="true"
            :inactive-value="false"
            active-text="已开启"
            inactive-text="已关闭"
            inline-prompt
          />
        </el-form-item>
      </el-col>

      <el-col :span="12">
        <el-form-item label="性别">
          <el-radio-group v-model="newFormInline.sex" style="margin-bottom: 0">
            <el-radio :label="1">男</el-radio>
            <el-radio :label="0">女</el-radio>
          </el-radio-group>
        </el-form-item>
      </el-col>
    </el-row>

    <el-row>
      <el-col :span="12">
        <el-form-item label="手机号" prop="mobile">
          <el-input
            v-model="newFormInline.mobile"
            clearable
            placeholder="请输入手机号"
          />
        </el-form-item>
      </el-col>

      <el-col :span="12">
        <el-form-item label="邮箱" prop="email">
          <el-input
            v-model="newFormInline.email"
            clearable
            placeholder="请输入邮箱"
          />
        </el-form-item>
      </el-col>
    </el-row>

    <el-row>
      <el-col>

        <el-form-item label="所属部门" prop="deptId">
          <el-tree
            ref="treeRef"
            :check-strictly="true"
            :data="treeData"
            show-checkbox
            node-key="id"
            :props="defaultProps"
            @check="setCheckedKeys"
            :default-checked-keys="checkedKeys"
          />
        </el-form-item>
      </el-col>
    </el-row>
  </el-form>
</template>
