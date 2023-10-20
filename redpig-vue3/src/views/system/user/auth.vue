<script setup lang="ts">
import { ref, onMounted } from "vue";
import { formRules } from "./utils/rule";
import { FormUserRolePermProps } from "./utils/types";
import { getList } from "@/api/system";
import { handleTree } from "@/utils/tree";
import { ElTree } from "element-plus";

const props = withDefaults(defineProps<FormUserRolePermProps>(), {
  formInline: () => ({
    id: 0,
    roles: [],
    perms: [],
    username: ""
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

let permIds = [];

onMounted(async () => {
  const { data } = await getList("/role/getRoleList");
  roles.value = data;
  getList("/perm/getPermList").then(p => {
    perms.value = handleTree(p.data);
    permIds = p.data.map(e => e.id);
  });

  getList("/role/getSelectedRolesByUserId", {
    userId: newFormInline.value.id
  }).then(res => {
    console.log(res.data);
    newFormInline.value.roles = res.data.map(e => e.id);
  });

  getList("/perm/getSelectedPermsByUserId", {
    userId: newFormInline.value.id
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

const setAllPerms = () => {
  if (newFormInline.value.perms.length > 0) {
    newFormInline.value.perms = [];
    permTree.value.setCheckedKeys([]);
  } else {
    newFormInline.value.perms = permIds.filter(pid => pid < 10000);
    permTree.value.setCheckedKeys(permIds);
  }
};

const setExpandAll = () => {
  for (let i = 0; i < permIds.length; i++) {
    permTree.value.store.nodesMap[permIds[i]].expanded =
      !permTree.value.store.nodesMap[permIds[i]].expanded;
  }
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
        <el-form-item label="用户名" prop="username">
          <el-input
            v-model="newFormInline.username"
            clearable
            placeholder="请输入用户名"
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
      <el-col :span="4">
        <el-form-item>
          <el-button type="success" @click="setAllPerms">全选/全不选</el-button>
        </el-form-item>
      </el-col>

      <el-col :span="4">
        <el-form-item>
          <el-button type="danger" @click="setExpandAll">展开/合并</el-button>
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
    </el-row>
  </el-form>
</template>
