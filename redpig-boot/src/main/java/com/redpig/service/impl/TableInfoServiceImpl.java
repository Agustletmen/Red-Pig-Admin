package com.redpig.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redpig.common.Constant;
import com.redpig.entity.*;
import com.redpig.generator.util.StringUtils;
import com.redpig.generator.util.TypeUtils;
import com.redpig.generator.vo.ClassVO;
import com.redpig.generator.vo.FieldVO;
import com.redpig.mapper.TableInfoMapper;
import com.redpig.mapper.TablePropertyMapper;
import com.redpig.service.*;
import com.redpig.util.Asserts;
import com.redpig.util.DateUtils;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

/**
 * 表信息 实现类
 *
 * @author zqd
 *
 * @date 2023-07-13 08:59:10
 */
@Slf4j
@Service
public class TableInfoServiceImpl extends ServiceImpl<TableInfoMapper, TableInfo> implements ITableInfoService {

    @Autowired
    TableInfoMapper tableInfoMapper;

    @Autowired
    DataSource dataSource;

    @Autowired
    TablePropertyMapper tablePropertyMapper;

    @Autowired
    ITablePropertyService tablePropertyService;

    @Autowired
    IMenuService menuService;

    @Autowired
    IUserService userService;

    @Autowired
    IRoleService roleService;

    @Autowired
    IUserRoleService userRoleService;

    @Autowired
    IPermService permService;

    @Autowired
    IUserPermService userPermService;

    @Autowired
    IMenuRoleService menuRoleService;

    @Autowired
    IMenuPermService menuPermService;


    @SneakyThrows
    @Override
    public void createTableById(Long tableInfoId) {
        TableInfo tbi = tableInfoMapper.selectTableInfoWithTableProperties(tableInfoId);

        boolean b = tableInfoMapper.existTable(dataSource.getConnection().getCatalog(), tbi.getTableName());

        Asserts.isTrue(b,tbi.getTableName()+"表已经存在!");

        tableInfoMapper.createTable(tbi);
    }

    @Override
    public List<FieldVO> getFieldByTableInfoId(TableInfo tbi) {
        List<FieldVO> fieldVOS = new ArrayList<>();
        if(ObjUtil.isNotNull(tbi) && CollUtil.isNotEmpty(tbi.getTableProperties())){
            List<TableProperty> tableProperties = tbi.getTableProperties();
            for (TableProperty tableProperty : tableProperties) {

                FieldVO fieldVO = new FieldVO();
                fieldVO.setFieldType(tableProperty.getFieldType());
                fieldVO.setFieldName(tableProperty.getFieldName());
                fieldVO.setColumnName(tableProperty.getColumnName());
                fieldVO.setComments(tableProperty.getColumnComment());
                fieldVO.setColumnType(TypeUtils.getMySqlType(tableProperty.getColumnType()));
                fieldVO.setJsType(TypeUtils.getJavaScriptTypeByMysqlType(TypeUtils.getMySqlType(tableProperty.getColumnType())));

                fieldVO.setGetMethodName("get"+fieldVO.getFieldName().substring(0,1).toUpperCase()+fieldVO.getFieldName().substring(1));
                fieldVO.setSetMethodName("set"+fieldVO.getFieldName().substring(0,1).toUpperCase()+fieldVO.getFieldName().substring(1));
                fieldVOS.add(fieldVO);

            }
        }
        return fieldVOS;
    }

    @Autowired
    Constant constant;

    //类信息
    ClassVO classVO = new ClassVO();
    List<FieldVO> fieldVOS = null;

    @Override
    public void generatorByTableInfoId(Long tableInfoId) {
        TableInfo tbi = tableInfoMapper.selectTableInfoWithTableProperties(tableInfoId);
        fieldVOS = getFieldByTableInfoId(tbi);

        //代码的包package
        String basePackageName = "com.redpig";

        //生成的文件存放目录
        String basePackageFolder = constant.filePlace +File.separator+ basePackageName.replace(".","/");

        classVO.setBasePackageName(basePackageName);
        classVO.setClassName(tbi.getClassName());
        classVO.setEntityName(classVO.getClassName().substring(0,1).toLowerCase()+classVO.getClassName().substring(1));
        classVO.setTableName(tbi.getTableName());
        classVO.setDate(DateUtils.format(new Date()));
        classVO.setAuthor("zqd");
        classVO.setVersion("v1.0");
        classVO.setCopyright("Copyright: Copyright (c) 2023");
        classVO.setWebSite("www.redpig.vip");
        classVO.setComment(tbi.getTableComment());

        generatorVM(basePackageFolder+"/entity",  "/server/Entity.vm",classVO.getClassName()+".java");
        generatorVM(basePackageFolder+"/mapper",  "/server/Mapper.vm",classVO.getClassName()+"Mapper.java");
        generatorVM(basePackageFolder+"/service",  "/server/IService.vm","I"+classVO.getClassName()+"Service.java");
        generatorVM(basePackageFolder+"/service/impl",  "/server/ServiceImpl.vm",classVO.getClassName()+"ServiceImpl.java");
        generatorVM(basePackageFolder+"/controller",  "/server/Controller.vm",classVO.getClassName()+"Controller.java");

        basePackageFolder = constant.filePlace;// /tmp

        generatorVM(basePackageFolder+"/mapper",  "/server/Mapping.vm",classVO.getClassName()+"Mapping.xml");

        generatorVM(basePackageFolder+"/views/"+classVO.getEntityName(),"/client/add.vm","add.vue");
        generatorVM(basePackageFolder+"/views/"+classVO.getEntityName()+"/utils","/client/hook.vm","hook.tsx");
        generatorVM(basePackageFolder+"/views/"+classVO.getEntityName(),"/client/list.vm","list.vue");
        generatorVM(basePackageFolder+"/views/"+classVO.getEntityName()+"/utils","/client/rule.vm","rule.ts");
        generatorVM(basePackageFolder+"/views/"+classVO.getEntityName()+"/utils","/client/types.vm","types.ts");

        generatorVM(basePackageFolder+"/sql","/sql/SQL.vm",classVO.getClassName()+".sql");
    }

    @Override
    public void generatorMenu(Long tableInfoId) {

        TableInfo tbi = tableInfoMapper.selectTableInfoWithTableProperties(tableInfoId);
        ClassVO classVO = new ClassVO();
        String basePackageName = "com.redpig";
        classVO.setBasePackageName(basePackageName);
        classVO.setClassName(tbi.getClassName());
        classVO.setEntityName(classVO.getClassName().substring(0,1).toLowerCase()+classVO.getClassName().substring(1));
        classVO.setTableName(tbi.getTableName());
        classVO.setDate(DateUtils.format(new Date()));
        classVO.setAuthor("zqd");
        classVO.setVersion("v1.0");
        classVO.setCopyright("Copyright: Copyright (c) 2023");
        classVO.setWebSite("www.redpig.vip");
        classVO.setComment(tbi.getTableComment());

        Role role = roleService.getOne(new QueryWrapper<Role>().eq("tag", "admin"));

        Perm perm = permService.getOne(new QueryWrapper<Perm>().eq("tag", "perm_all"));

        Menu menuRoot = new Menu();
        menuRoot.setName(classVO.getClassName()+"Manage");
        //顶级菜单这里需要注意 不能和子目录有重复
        //比如顶级菜单:/goods 子菜单/goods/list /goodsType/list 这时候/goodsType/list找不到 这是前端模板的bug
        menuRoot.setPath("/"+classVO.getClassName()+"Manage");
        menuRoot.setTitle(classVO.getComment()+"管理");
        menuRoot.setType(0);
        menuRoot.setIcon("ph:user");
        menuRoot.setShowLink(true);
        menuRoot.setRank(1);
        menuRoot.setParentId(0L);

        menuService.save(menuRoot);
        MenuRole menuRoleRoot = new MenuRole();
        menuRoleRoot.setMenuId(menuRoot.getId());
        menuRoleRoot.setRoleId(role.getId());
        menuRoleService.save(menuRoleRoot);

        MenuPerm menuPermRoot = new MenuPerm();
        menuPermRoot.setMenuId(menuRoot.getId());
        menuPermRoot.setPermId(perm.getId());
        menuPermService.save(menuPermRoot);

        Menu menuList = new Menu();
        menuList.setName(classVO.getClassName()+"List");
        menuList.setPath("/system/"+classVO.getEntityName()+"/list");
        menuList.setTitle(classVO.getComment()+"列表");
        menuList.setType(1);
        menuList.setIcon("ph:user");
        menuList.setShowLink(true);
        menuList.setRank(1);
        menuList.setParentId(menuRoot.getId());

        menuService.save(menuList);
        MenuRole menuRoleList = new MenuRole();
        menuRoleList.setMenuId(menuList.getId());
        menuRoleList.setRoleId(role.getId());
        menuRoleService.save(menuRoleList);

        MenuPerm menuPermList = new MenuPerm();
        menuPermList.setMenuId(menuList.getId());
        menuPermList.setPermId(perm.getId());
        menuPermService.save(menuPermList);

        Menu menu_page = new Menu();
        menu_page.setName(classVO.getClassName()+"_page");
        menu_page.setPath("/"+classVO.getEntityName()+"/page");
        menu_page.setTitle(classVO.getComment()+"分页查询");
        menu_page.setType(2);
        menu_page.setIcon("ph:user");
        menu_page.setShowLink(true);
        menu_page.setRank(1);
        menu_page.setParentId(menuList.getId());

        menuService.save(menu_page);
        MenuRole menuRole_page = new MenuRole();
        menuRole_page.setMenuId(menu_page.getId());
        menuRole_page.setRoleId(role.getId());
        menuRoleService.save(menuRole_page);

        MenuPerm menuPerm_page = new MenuPerm();
        menuPerm_page.setMenuId(menu_page.getId());
        menuPerm_page.setPermId(perm.getId());
        menuPermService.save(menuPerm_page);

        Menu menu_saveOrUpdate = new Menu();
        menu_saveOrUpdate.setName(classVO.getClassName()+"_saveOrUpdate");
        menu_saveOrUpdate.setPath("/"+classVO.getEntityName()+"/saveOrUpdate");
        menu_saveOrUpdate.setTitle("新增或者更新"+classVO.getComment());
        menu_saveOrUpdate.setType(2);
        menu_saveOrUpdate.setIcon("ph:user");
        menu_saveOrUpdate.setShowLink(true);
        menu_saveOrUpdate.setRank(1);
        menu_saveOrUpdate.setParentId(menuList.getId());

        menuService.save(menu_saveOrUpdate);
        MenuRole menuRole_saveOrUpdate = new MenuRole();
        menuRole_saveOrUpdate.setMenuId(menu_saveOrUpdate.getId());
        menuRole_saveOrUpdate.setRoleId(role.getId());
        menuRoleService.save(menuRole_saveOrUpdate);

        MenuPerm menuPerm_saveOrUpdate = new MenuPerm();
        menuPerm_saveOrUpdate.setMenuId(menu_saveOrUpdate.getId());
        menuPerm_saveOrUpdate.setPermId(perm.getId());
        menuPermService.save(menuPerm_saveOrUpdate);

        Menu menu_delById = new Menu();
        menu_delById.setName(classVO.getClassName()+"_delById");
        menu_delById.setPath("/"+classVO.getEntityName()+"/delById");
        menu_delById.setTitle("根据ID删除"+classVO.getComment());
        menu_delById.setType(2);
        menu_delById.setIcon("ph:user");
        menu_delById.setShowLink(true);
        menu_delById.setRank(1);
        menu_delById.setParentId(menuList.getId());

        menuService.save(menu_delById);
        MenuRole menuRole_delById = new MenuRole();
        menuRole_delById.setMenuId(menu_delById.getId());
        menuRole_delById.setRoleId(role.getId());
        menuRoleService.save(menuRole_delById);

        MenuPerm menuPerm_delById = new MenuPerm();
        menuPerm_delById.setMenuId(menu_delById.getId());
        menuPerm_delById.setPermId(perm.getId());
        menuPermService.save(menuPerm_delById);

        Menu menu_getById = new Menu();
        menu_getById.setName(classVO.getClassName()+"getById");
        menu_getById.setPath("/"+classVO.getEntityName()+"/getById");
        menu_getById.setTitle("根据ID查询"+classVO.getComment());
        menu_getById.setType(2);
        menu_getById.setIcon("ph:user");
        menu_getById.setShowLink(true);
        menu_getById.setRank(1);
        menu_getById.setParentId(menuList.getId());

        menuService.save(menu_getById);
        MenuRole menuRole_getById = new MenuRole();
        menuRole_getById.setMenuId(menu_getById.getId());
        menuRole_getById.setRoleId(role.getId());
        menuRoleService.save(menuRole_getById);

        MenuPerm menuPerm_getById = new MenuPerm();
        menuPerm_getById.setMenuId(menu_getById.getId());
        menuPerm_getById.setPermId(perm.getId());
        menuPermService.save(menuPerm_getById);

        Menu menu_removeBatchByIds = new Menu();
        menu_removeBatchByIds.setName(classVO.getClassName()+"_removeBatchByIds");
        menu_removeBatchByIds.setPath("/"+classVO.getEntityName()+"/removeBatchByIds");
        menu_removeBatchByIds.setTitle("批量删除"+classVO.getComment());
        menu_removeBatchByIds.setType(2);
        menu_removeBatchByIds.setIcon("ph:user");
        menu_removeBatchByIds.setShowLink(true);
        menu_removeBatchByIds.setRank(1);
        menu_removeBatchByIds.setParentId(menuList.getId());

        menuService.save(menu_removeBatchByIds);
        MenuRole menuRole_removeBatchByIds = new MenuRole();
        menuRole_removeBatchByIds.setMenuId(menu_removeBatchByIds.getId());
        menuRole_removeBatchByIds.setRoleId(role.getId());
        menuRoleService.save(menuRole_removeBatchByIds);

        MenuPerm menuPerm_removeBatchByIds = new MenuPerm();
        menuPerm_removeBatchByIds.setMenuId(menu_removeBatchByIds.getId());
        menuPerm_removeBatchByIds.setPermId(perm.getId());
        menuPermService.save(menuPerm_removeBatchByIds);
    }

    @Override
    public List<String> selectAllTableNames() {
        try {
            return tableInfoMapper.selectAllTableNames(dataSource.getConnection().getCatalog());
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("获取数据库表失败!");
        }
    }

    Connection conn = null;

    @SneakyThrows
    public void createTableProperties(){
        tableInfoMapper.delete(new QueryWrapper<>());
        tablePropertyMapper.delete(new QueryWrapper<>());

        conn = dataSource.getConnection();
        String databaseName = conn.getCatalog();

        List<String> tableNames = selectAllTableNames();

        for (String tableName : tableNames) {
            TableInfo tableInfo = tableInfoMapper.selectOne(new QueryWrapper<TableInfo>().eq("tableName", tableName));

            if(tableInfo==null){
                tableInfo = new TableInfo();
                tableInfo.setTableName(tableName);
                String className = "";
                for (String n : tableName.split("_")) {
                    className += n.substring(0,1).toUpperCase()+n.substring(1);
                }

                tableInfo.setClassName(className);
                tableInfo.setTableComment(getTableComment(databaseName,tableName));
                tableInfo.setRemark(tableInfo.getTableComment());

                //保存表信息
                tableInfoMapper.insert(tableInfo);
            }
            //tablePropertyMapper.delete(new UpdateWrapper<TableProperty>().eq("tableInfo_id",tableInfo.getId()));

            List<TableProperty> tableProperties = getTableProperties(databaseName, tableName, tableInfo);
            tablePropertyService.saveBatch(tableProperties);
        }

    }


    public  List<TableProperty> getTableProperties(String databaseName,String tableName,TableInfo tableInfo) throws Exception {
        List<TableProperty> tableProperties = tablePropertyMapper.getTablePropertiesFromInformationSchema(databaseName, tableName);
        for (TableProperty tableProperty : tableProperties) {
            String fieldName = StringUtils.getName(tableProperty.getColumnName());
            String fieldType = TypeUtils.getJavaTypeByMysqlType(tableProperty.getColumnType());

            tableProperty.setFieldName(fieldName);
            tableProperty.setFieldType(fieldType);
            tableProperty.setTableInfoId(tableInfo.getId());

        }
        return tableProperties;
    }


    @SneakyThrows
    public String getTableComment(String databaseName,String tableName){
        DatabaseMetaData dbmd=conn.getMetaData();

        ResultSet resultSet = dbmd.getTables(databaseName, "%", "%", new String[] { "TABLE" });

        while (resultSet.next()) {
            String tn=resultSet.getString("TABLE_NAME");
            if(tn.equals(tableName)){
                ResultSet rs = dbmd.getColumns(databaseName, "%", tableName, "%");
                return resultSet.getString("REMARKS");
            }
        }
        return "未设置备注";
    }

    /**
     * @param folder 生成的文件存放位置(包目录名)【tmp下面的目录】
     * @param vm 模板位置+模板名称【不需要前缀generator】
     * @param fileName 生成的文件名
     * @throws Exception
     */
    private void generatorVM(String folder,String vm,String fileName) {
        //生成的文件存放位置:tmp/com/redpig
        classVO.setPackageFolder(folder);

        doRelease(vm,fileName);
    }

    /**
     *
     * @param vm 模板名
     * @param fileName 生成的文件名
     */
    public void doRelease(String vm,String fileName){
        try {
            Properties p = new Properties();
            p.setProperty(Velocity.FILE_RESOURCE_LOADER_PATH, "");
            p.setProperty(Velocity.ENCODING_DEFAULT, "UTF-8");
            p.setProperty(Velocity.INPUT_ENCODING, "UTF-8");

            Velocity.init(p);

            //模板位置
            Template template = getVelocityTemplate("src/main/resources/"+constant.vmPlace+vm);
            VelocityContext context = initVelocityContext();
            BufferedWriter writer = getWriter(fileName);

            template.merge(context, writer);
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 初始化VelocityContext
     * @author zqd
     */
    public VelocityContext initVelocityContext() throws Exception{
        VelocityContext context = new VelocityContext();
        try{
            context.put("fields", fieldVOS);
            context.put("classVO", classVO);
        }catch(Exception e){
            e.printStackTrace();
        }
        return context;
    }

    /**
     * 获取Velocity模板
     * @author zqd
     * @param  velocityTemplateSource
     */
    public Template getVelocityTemplate(String velocityTemplateSource) throws Exception{
        Template template = new Template();
        try{
            template = Velocity.getTemplate(velocityTemplateSource);
        }catch(Exception e){
            e.printStackTrace();
        }
        return template;
    }

    /**
     * 获取Velocity写入流
     * @author zqd
     * @param
     */
    public BufferedWriter getWriter(String fileName) throws Exception{
        try{
            String packageFolder = "src/main/resources/"+classVO.getPackageFolder();
            File f = new File(packageFolder);
            f.mkdirs();

            FileOutputStream fos = new FileOutputStream(packageFolder+"/"+fileName);
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(fos, "UTF-8"));
            return writer;
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

}
