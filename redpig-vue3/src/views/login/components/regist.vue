<script setup lang="ts">
import { useI18n } from "vue-i18n";
import { ref, reactive, watch } from "vue";
import Motion from "../utils/motion";
import { message } from "@/utils/message";
import { registRules } from "../utils/rule";
import type { FormInstance } from "element-plus";
import { useVerifyCode } from "../utils/verifyCode";
import { $t, transformI18n } from "@/plugins/i18n";
import { useUserStoreHook } from "@/store/modules/user";
import { useRenderIcon } from "@/components/ReIcon/src/hooks";
import Lock from "@iconify-icons/ri/lock-fill";
import Iphone from "@iconify-icons/ep/iphone";
import User from "@iconify-icons/ri/user-3-fill";
import { getRegister } from "@/api/user";
import { ReImageVerify } from "@/components/ReImageVerify";

const { t } = useI18n();

const imgCode = ref("");
const checked = ref(false);
const loading = ref(false);
const ruleForm = reactive({
  username: "",
  phone: "",
  verifyCode: "",
  password: "",
  repeatPassword: ""
});
const ruleFormRef = ref<FormInstance>();
const { isDisabled, text } = useVerifyCode();
const repeatPasswordRule = [
  {
    validator: (rule, value, callback) => {
      if (value === "") {
        callback(new Error(transformI18n($t("login.passwordSureReg"))));
      } else if (ruleForm.password !== value) {
        callback(new Error(transformI18n($t("login.passwordDifferentReg"))));
      } else {
        callback();
      }
    },
    trigger: "blur"
  }
];

const onUpdate = async (formEl: FormInstance | undefined) => {
  loading.value = true;
  if (!formEl) return;
  await formEl.validate((valid, fields) => {
    if (valid) {
      if (checked.value) {
        getRegister({
          username: ruleForm.username,
          password: ruleForm.password
        })
          .then(() => {
            message(transformI18n($t("login.registerSuccess")), {
              type: "success"
            });
            loading.value = false;
            useUserStoreHook().SET_CURRENTPAGE(0);
          })
          .catch(error => {
            setTimeout(() => {
              loading.value = false;
            }, 2000);
          });
      } else {
        loading.value = false;
        message(transformI18n($t("login.tickPrivacy")), { type: "warning" });
      }
    } else {
      loading.value = false;
      return fields;
    }
  });
};

function onBack() {
  useVerifyCode().end();
  useUserStoreHook().SET_CURRENTPAGE(0);
}

watch(imgCode, value => {
  useUserStoreHook().SET_VERIFYCODE(value);
});

watch(ruleForm, value => {
  useUserStoreHook().SET_PASSWORD(value.password);
});
</script>

<template>
  <el-form
    ref="ruleFormRef"
    :model="ruleForm"
    :rules="registRules"
    size="large"
  >
    <Motion>
      <el-form-item prop="username">
        <el-input
          clearable
          v-model="ruleForm.username"
          :placeholder="t('login.username')"
          :prefix-icon="useRenderIcon(User)"
        />
      </el-form-item>
    </Motion>

    <Motion :delay="200">
      <el-form-item prop="password">
        <el-input
          clearable
          show-password
          v-model="ruleForm.password"
          :placeholder="t('login.password')"
          :prefix-icon="useRenderIcon(Lock)"
        />
      </el-form-item>
    </Motion>

    <Motion :delay="250">
      <el-form-item :rules="repeatPasswordRule" prop="repeatPassword">
        <el-input
          clearable
          show-password
          v-model="ruleForm.repeatPassword"
          :placeholder="t('login.sure')"
          :prefix-icon="useRenderIcon(Lock)"
        />
      </el-form-item>
    </Motion>

    <Motion :delay="200">
      <el-form-item prop="verifyCode">
        <el-input
          clearable
          v-model="ruleForm.verifyCode"
          :placeholder="t('login.verifyCode')"
          :prefix-icon="useRenderIcon('ri:shield-keyhole-line')"
        >
          <template v-slot:append>
            <ReImageVerify v-model:code="imgCode" />
          </template>
        </el-input>
      </el-form-item>
    </Motion>

    <Motion :delay="300">
      <el-form-item>
        <el-checkbox v-model="checked">
          {{ t("login.readAccept") }}
        </el-checkbox>
        <el-button link type="primary">
          {{ t("login.privacyPolicy") }}
        </el-button>
      </el-form-item>
    </Motion>

    <Motion :delay="350">
      <el-form-item>
        <el-button
          class="w-full"
          size="default"
          type="primary"
          :loading="loading"
          @click="onUpdate(ruleFormRef)"
        >
          {{ t("login.definite") }}
        </el-button>
      </el-form-item>
    </Motion>

    <Motion :delay="400">
      <el-form-item>
        <el-button class="w-full" size="default" @click="onBack">
          {{ t("login.back") }}
        </el-button>
      </el-form-item>
    </Motion>
  </el-form>
</template>
