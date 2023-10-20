<script setup lang="ts">
import { onMounted, ref } from "vue";
import { formRules } from "./utils/rule";
import { FormProps } from "./utils/types";
import { handleTree } from "@/utils/tree";
import { getList } from "@/api/system";
import { ElTree } from "element-plus";

const props = withDefaults(defineProps<FormProps>(), {
  formInline: () => ({
    id: 0,
    name: "",
    tag: "",
    createBy: "",
    updateBy: "",
    remark: "",
    menuId: 0
  })
});

const ruleFormRef = ref();
const newFormInline = ref(props.formInline);

const defaultProps = {
  children: "children",
  label: "title"
};

const treeData = ref([]);
const checkedKeys = ref([newFormInline.value.menuId]);
const treeRef = ref<InstanceType<typeof ElTree>>();

onMounted(async () => {
  const { data } = await getList("/menu/getMenuList");
  treeData.value = handleTree(data);
});

const setCheckedKeys = d => {
  treeRef.value!.setCheckedKeys([], false);
  treeRef.value!.setCheckedKeys([d.id], true);
  newFormInline.value.menuId = d.id;
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
    <el-form-item label="权限名称" prop="name">
      <el-input
        v-model="newFormInline.name"
        clearable
        placeholder="请输入权限名称"
      />
    </el-form-item>

    <el-form-item label="权限标签" prop="tag">
      <el-input
        v-model="newFormInline.tag"
        clearable
        placeholder="请输入权限标签"
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

    <el-form-item label="所属菜单" prop="parentId">
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
  </el-form>
</template>
