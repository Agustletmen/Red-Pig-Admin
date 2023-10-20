<script setup lang="ts">
import { ref, onMounted } from "vue";
import { formRules } from "./utils/rule";
import { FormMenuRolePermProps } from "./utils/types";
import { getList } from "@/api/system";

import { handleTree } from "@/utils/tree";
import { ElTree } from "element-plus";

const props = withDefaults(defineProps<FormMenuRolePermProps>(), {
  formInline: () => ({
    id: 0,
    roles: [],
    perms: [],
    name: "",
    title: ""
  })
});

const ruleFormRef = ref();
const newFormInline = ref(props.formInline);

const defaultProps = {
  children: "children",
  label: "name",
  disabled: "disabled"
};

// const selectedRoles = ref([]);
//const selectedPerms = ref([]);

const roles = ref([]);
const perms = ref([]);

onMounted(async () => {
  const { data } = await getList("/role/getRoleList");
  roles.value = data;
  getList("/perm/getPermList").then(p => {
    perms.value = handleTree(p.data);
  });

  getList("/role/getSelectedRolesByMenuId", {
    menuId: newFormInline.value.id
  }).then(res => {
    console.log(res.data);
    newFormInline.value.roles = res.data.map(e => e.id);
  });

  getList("/perm/getSelectedPermsByMenuId", {
    menuId: newFormInline.value.id
  }).then(res => {
    console.log(res.data);
    newFormInline.value.perms = res.data.map(e => e.id);
  });
});

function checkedKeys(val, data) {
  console.log(val);
  newFormInline.value.perms = data.checkedKeys.filter(function (x: any) {
    return x < 10000;
  });
}

const permTree = ref<InstanceType<typeof ElTree>>();

const clearSelectedKeys = () => {
  newFormInline.value.perms = [];
  permTree.value.setCheckedKeys([]);
};

function getRef() {
  return newFormInline.value;
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
        <el-form-item label="菜单名称" prop="name">
          <el-input
            v-model="newFormInline.name"
            clearable
            placeholder="请输入菜单名称"
            disabled
          />
        </el-form-item>
      </el-col>

      <el-col :span="12">
        <el-form-item label="标题" prop="title">
          <el-input
            v-model="newFormInline.title"
            clearable
            placeholder="请输入标题"
            disabled
          />
        </el-form-item>
      </el-col>
    </el-row>

    <el-row :gutter="30">
      <el-col>
        <el-form-item label="角色">
          <el-select
            v-model="newFormInline.roles"
            multiple
            placeholder="Select"
            style="width: 240px"
          >
            <el-option
              v-for="item in roles"
              :key="item.id"
              :label="item.name"
              :value="item.id"
            />
          </el-select>
        </el-form-item>
      </el-col>
    </el-row>

    <el-row>
      <el-col :span="12">
        <el-form-item label="权限">
          <el-tree
            ref="permTree"
            :data="perms"
            show-checkbox
            node-key="id"
            :default-checked-keys="newFormInline.perms"
            :props="defaultProps"
            @check="checkedKeys"
          />
        </el-form-item>
      </el-col>

      <el-col :span="12">
        <el-form-item>
          <el-button type="danger" @click="clearSelectedKeys"
            >清空权限</el-button
          >
        </el-form-item>
      </el-col>
    </el-row>
  </el-form>
</template>
