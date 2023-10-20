<script setup lang="ts">
import { ref } from "vue";
import { formRules } from "./utils/rule";
import { FormPropertyProps } from "./utils/types";

import { useTableInfo } from "./utils/hook";

const props = withDefaults(defineProps<FormPropertyProps>(), {
  formInline: () => ({
    id: 0,
    tableProperties: []
  })
});

const ruleFormRef = ref();
const newFormInline = ref(props.formInline);

const columnTypes = [
  "FLOAT",
  "LONGTEXT",
  "DECIMAL",
  "CHAR",
  "BIGINT",
  "TIME",
  "TEXT",
  "MEDIUMINT",
  "BIT",
  "MEDIUMTEXT",
  "INT",
  "DATE",
  "YEAR",
  "DATETIME",
  "SMALLINT",
  "TIMESTAMP",
  "VARCHAR",
  "DOUBLE",
  "TINYINT",
  "INTEGER"
];

const propertyTypes = [
  "Float",
  "java.math.BigDecimal",
  "Long",
  "Boolean",
  "java.util.Date",
  "String",
  "Double",
  "Integer"
];

const selectTypeSymbols = [
  "=",
  "!=",
  ">",
  ">=",
  "<",
  "<=",
  "like",
  "left like",
  "right like"
];

const formTypeSymbols = [
  {
    key: "0",
    value: "单行文本"
  },
  {
    key: "1",
    value: "多行文本"
  },
  {
    key: "2",
    value: "富文本编辑器"
  },
  {
    key: "3",
    value: "下拉框"
  },
  {
    key: "4",
    value: "单选按钮"
  },
  {
    key: "5",
    value: "复选框"
  },
  {
    key: "6",
    value: "日期"
  },
  {
    key: "7",
    value: "日期时间"
  }
];

const now = new Date();

const deleteRow = (index: number) => {
  newFormInline.value.tableProperties.splice(index, 1);
};

let index = 0;
const onAddItem = () => {
  index += 1;
  now.setDate(now.getDate() + 1);
  newFormInline.value.tableProperties.push({
    columnName: "",
    columnType: "VARCHAR",
    columnLength: 0,
    columnComment: "",
    notNull: "",
    primarykey: false,
    fieldName: "",
    fieldType: "String",
    selectIs: true,
    selectType: "=",
    formIs: true,
    inputMust: true,
    formType: "0",
    seq: index,
    tableInfoId: newFormInline.value.id,
    remark: ""
  });
};

const { addPropertyColumn } = useTableInfo();

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
    <el-table :data="newFormInline.tableProperties">
      <el-table-column
        v-for="col in addPropertyColumn"
        :key="col.prop"
        :prop="col.prop"
        :label="col.label"
        :width="col.width"
        align="center"
      >
        <template #default="scope">
          <!-- 自定义单元格渲染 -->
          <el-input
            v-model="scope.row.columnName"
            v-if="col.prop === 'columnName'"
            class="w-50 m-2"
            placeholder="请输入字段名称"
          />

          <el-select
            v-if="col.prop === 'columnType'"
            v-model="scope.row.columnType"
            placeholder="列类型"
          >
            <el-option
              v-for="item in columnTypes"
              :key="item"
              :label="item"
              :value="item"
            />
          </el-select>

          <el-input-number
            v-if="col.prop === 'columnLength'"
            v-model="scope.row.columnLength"

            :min="0"
            :max="255"
          />

          <el-input
            v-model="scope.row.columnComment"
            v-if="col.prop === 'columnComment'"
            class="w-20 m-2"
            placeholder="请输入字段说明"
          />

          <el-checkbox
            v-if="col.prop === 'notNull'"
            v-model="scope.row.notNull"
            size="large"
          />

          <el-checkbox
            v-if="col.prop === 'primarykey'"
            v-model="scope.row.primarykey"
            size="large" />

          <el-input
            v-model="scope.row.fieldName"
            v-if="col.prop === 'fieldName'"
            class="w-50 m-2"
            placeholder="请输入属性名"
          />

          <el-select
            v-if="col.prop === 'fieldType'"
            v-model="scope.row.fieldType"
            placeholder="属性类型"
          >
            <el-option
              v-for="item in propertyTypes"
              :key="item"
              :label="item"
              :value="item"
            />
          </el-select>

          <el-switch
            v-if="col.prop === 'selectIs'"
            v-model="scope.row.selectIs"
            :active-value="true"
            :inactive-value="false"
            active-text="已开启"
            inactive-text="已关闭"
            inline-prompt
          />

          <el-select
            v-if="col.prop === 'selectType'"
            v-model="scope.row.selectType"
            placeholder="查询方式"
          >
            <el-option
              v-for="item in selectTypeSymbols"
              :key="item"
              :label="item"
              :value="item"
            />
          </el-select>

          <el-checkbox
            v-if="col.prop === 'formIs'"
            v-model="scope.row.formIs"
            size="large"
          />

          <el-checkbox
            v-if="col.prop === 'inputMust'"
            v-model="scope.row.inputMust"
            size="large"
          />

          <el-select
            v-if="col.prop === 'formType'"
            v-model="scope.row.formType"
            placeholder="表单类型"
          >
            <el-option
              v-for="item in formTypeSymbols"
              :key="item.key"
              :label="item.value"
              :value="item.key"
            />
          </el-select>

          <el-input
            v-model="scope.row.seq"
            v-if="col.prop === 'seq'"
            class="w-50 m-2"
            placeholder="排序"
          />

          <el-input
            v-model="scope.row.remark"
            v-if="col.prop === 'remark'"
            class="w-50 m-2"
            placeholder="备注"
            type="textarea"
            rows="1"
            maxlength="30"
          />

          <el-button
            v-if="col.label === '操作'"
            link
            type="primary"
            size="small"
            @click.prevent="deleteRow(scope.$index)"
          >
            Remove
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-button class="mt-4" style="width: 100%" @click="onAddItem">
      新增列
    </el-button>
  </el-form>
</template>
