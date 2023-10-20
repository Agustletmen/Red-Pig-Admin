<script setup lang="ts">
import { onBeforeUnmount, ref, shallowRef } from "vue";
import { getToken } from "@/utils/auth";
import { formRules } from "./utils/rule";
import { FormProps } from "./utils/types";
import "@wangeditor/editor/dist/css/style.css";
import { Editor, Toolbar } from "@wangeditor/editor-for-vue";

const props = withDefaults(defineProps<FormProps>(), {
  formInline: () => ({
        id: 0,
        author: "",
        content: "",
        createBy: "",
        updateBy: "",
        remark: "",
  })
});

const ruleFormRef = ref();
const newFormInline = ref(props.formInline);


const mode = "default";
// 编辑器实例，必须用 shallowRef
const editorRef = shallowRef();

const editorConfig = { placeholder: "请输入内容...", MENU_CONF: {} };

console.log(getToken().accessToken);

// 更多详细配置看 https://www.wangeditor.com/v5/menu-config.html#%E4%B8%8A%E4%BC%A0%E5%9B%BE%E7%89%87
editorConfig.MENU_CONF["uploadImage"] = {
  // 服务端上传地址，根据实际业务改写
  server: "/api/upload/ajaxFileuPload",
  // form-data 的 fieldName，根据实际业务改写
  fieldName: "myfile",
  // 选择文件时的类型限制，根据实际业务改写
  allowedFileTypes: ["image/png", "image/jpg", "image/jpeg"],
  // 自定义增加 http  header
  headers: {
    Authorization: getToken().accessToken
  },
  // 自定义插入图片
  customInsert(res: any, insertFn) {
    console.log(res);
    // res.data.url是后端返回的图片地址，根据实际业务改写
    if (res.serverPath) {
      setTimeout(() => {
        // insertFn插入图片进编辑器
        insertFn(res.serverPath+res.fileName);
      }, 2000);
    }
  }
};

const handleCreated = editor => {
  // 记录 editor 实例，重要！
  editorRef.value = editor;
};

// 组件销毁时，也及时销毁编辑器
onBeforeUnmount(() => {
  const editor = editorRef.value;
  if (editor == null) return;
  editor.destroy();
});

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

      <el-form-item label="作者" prop="author">
        <el-input v-model="newFormInline.author" clearable placeholder="请输入作者" />
      </el-form-item>

      <el-form-item label="内容" prop="content">
        <Toolbar
          :editor="editorRef"
          :mode="mode"
          style="border: 1px solid #d0c0c0"
        />
        <Editor
          v-model="newFormInline.content"
          :defaultConfig="editorConfig"
          :mode="mode"
          style="height: 200px;width: 100%; overflow-y: hidden;border: 1px solid #d0c0c0;margin-top: 1px"
          @onCreated="handleCreated"
        />
      </el-form-item>

      <el-form-item label="备注" prop="remark">
        <el-input
                v-model="newFormInline.remark"
                maxlength="80"
                showWordLimit
                type="textarea"
                placeholder="请输入备注"
        />
      </el-form-item>

  </el-form>
</template>
