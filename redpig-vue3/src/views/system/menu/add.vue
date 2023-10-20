<script setup lang="ts">
import { ref, onMounted } from "vue";
import { formRules } from "./utils/rule";
import { FormProps } from "./utils/types";
import { getList } from "@/api/system";
import { handleTree } from "@/utils/tree";
import { ElTree } from "element-plus";

import IconifyIconOnline from "@/components/ReIcon/src/iconifyIconOnline";

const props = withDefaults(defineProps<FormProps>(), {
  formInline: () => ({
    id: 0,
    name: "",
    path: "",
    component: "",
    title: "",
    type: 0,
    icon: "",
    showLink: false,
    rank: 1,
    parentId: 0
  })
});

const ruleFormRef = ref();
const newFormInline = ref(props.formInline);

const visible = ref(false);

const defaultProps = {
  children: "children",
  label: "title"
};

const treeData = ref([]);
const checkedKeys = ref([newFormInline.value.parentId]);
const treeRef = ref<InstanceType<typeof ElTree>>();
const icons = ref([]);
onMounted(async () => {
  const { data } = await getList("/menu/getMenuList", { types: "0,1" });
  treeData.value = handleTree(data);

  const iconsResult = await getList("/icon/getIconList");
  icons.value = iconsResult.data;
});

const setCheckedKeys = d => {
  treeRef.value!.setCheckedKeys([], false);
  treeRef.value!.setCheckedKeys([d.id], true);
  newFormInline.value.parentId = d.id;
};

const defaultIcon = ref("uil:setting");

if(newFormInline.value.icon){
  defaultIcon.value = newFormInline.value.icon
}

const setIcon = i => {
  defaultIcon.value = i;
  newFormInline.value.icon=i;
  visible.value=false;
}

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
    <el-row >
      <el-col :span="12">
        <el-form-item label="菜单名称" prop="name">

          <el-input
            v-model="newFormInline.name"
            clearable
            placeholder="请输入菜单名称"
          />

        </el-form-item>
      </el-col>

      <el-col :span="12">
        <el-form-item label="标题" prop="title">
          <el-input
            v-model="newFormInline.title"
            clearable
            placeholder="请输入标题"
          />
        </el-form-item>
      </el-col>
    </el-row>

    <el-row :gutter="30">
      <el-col :span="12">
        <el-form-item label="类型" prop="type">
          <el-radio-group v-model="newFormInline.type">
            <el-radio :label="0">目录</el-radio>
            <el-radio :label="1">菜单</el-radio>
            <el-radio :label="2">接口</el-radio>
          </el-radio-group>
        </el-form-item>
      </el-col>
    </el-row>

    <el-row :gutter="30">
      <el-col :span="12">
        <el-form-item label="图标" prop="icon">
          <el-popover
            :visible="visible"
            placement="bottom"
            title="选择图标"
            :width="300"
          >
            <template #reference>
              <el-button clearable placeholder="选择图标" @click="visible = !visible">
                <IconifyIconOnline :icon="defaultIcon" width="30px" height="20px" style="float: left" />
              </el-button>
            </template>

            <template v-for="(item, index) in icons" >
              <IconifyIconOnline :icon="item.iconTag" @click="setIcon(item.iconTag)" width="30px" height="20px" style="float: left;cursor:pointer" />
              <br v-if="(index+1) % 10 ==0"/>
            </template>
          </el-popover>

        </el-form-item>

      </el-col>

      <el-col :span="12" v-if="newFormInline.type == 2">
        <el-form-item label="后端接口" prop="path">
          <el-input
            v-model="newFormInline.path"
            clearable
            placeholder="请输入后端接口"
          />
        </el-form-item>
      </el-col>
    </el-row>

    <el-row :gutter="30">
      <el-col :span="12" v-if="newFormInline.type == 1">
        <el-form-item label="组件名" prop="component">
          <el-input
            v-model="newFormInline.component"
            clearable
            placeholder="请输入组件名"
          />
        </el-form-item>
      </el-col>

      <el-col :span="12" v-if="newFormInline.type == 1 || newFormInline.type == 0">
        <el-form-item label="菜单路径" prop="path">
          <el-input
            v-model="newFormInline.path"
            clearable
            placeholder="请输入菜单路径"
          />
        </el-form-item>
      </el-col>
    </el-row>

    <el-row :gutter="30">
      <el-col :span="12">
        <el-form-item label="排序" prop="rank">
          <el-input-number v-model="newFormInline.rank" :min="1"  />
        </el-form-item>
      </el-col>


      <el-col :span="12">
        <el-form-item label="链接" prop="showLink">
          <el-switch
            size="default"
            v-model="newFormInline.showLink"
            :active-value="true"
            :inactive-value="false"
            active-text="已开启"
            inactive-text="已关闭"
            inline-prompt
          />
        </el-form-item>
      </el-col>
    </el-row>

    <el-row :gutter="30">
      <el-col>
        <el-form-item label="父菜单" prop="parentId">
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
