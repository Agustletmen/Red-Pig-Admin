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
    deptName: "",
    orderNum: 0,
    manager: "",
    phone: "",
    email: "",
    status: null,
    createBy: "",
    updateBy: "",
    parentId: 0
  })
});

const ruleFormRef = ref();
const newFormInline = ref(props.formInline);

const defaultProps = {
  children: "children",
  label: "deptName"
};

const treeData = ref([]);
const checkedKeys = ref([newFormInline.value.parentId]);
const treeRef = ref<InstanceType<typeof ElTree>>();

onMounted(async () => {
  const { data } = await getList("/dept/getDeptList");
  treeData.value = handleTree(data);
});

const setCheckedKeys = d => {
  treeRef.value!.setCheckedKeys([], false);
  treeRef.value!.setCheckedKeys([d.id], true);
  newFormInline.value.parentId = d.id;
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
    <el-form-item label="部门名称" prop="deptName">
      <el-input
        v-model="newFormInline.deptName"
        clearable
        placeholder="请输入部门名称"
      />
    </el-form-item>

    <el-form-item label="显示顺序" prop="orderNum">
      <el-input-number v-model="newFormInline.orderNum" :min="1"  />
    </el-form-item>

    <el-form-item label="负责人" prop="manager">
      <el-input
        v-model="newFormInline.manager"
        clearable
        placeholder="请输入负责人"
      />
    </el-form-item>

    <el-form-item label="联系电话" prop="phone">
      <el-input
        v-model="newFormInline.phone"
        clearable
        placeholder="请输入联系电话"
      />
    </el-form-item>

    <el-form-item label="邮箱" prop="email">
      <el-input
        v-model="newFormInline.email"
        clearable
        placeholder="请输入邮箱"
      />
    </el-form-item>

    <el-form-item label="部门状态">
      <el-switch
        v-model="newFormInline.status"
        inline-prompt
        :active-value="true"
        :inactive-value="false"
        active-text="启用"
        inactive-text="停用"
      />
    </el-form-item>

    <el-form-item label="所属部门" prop="parentId">
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
