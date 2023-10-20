package com.redpig.controller;

import cn.hutool.core.util.ObjUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.redpig.vo.ProcessDefinitionVO;
import com.redpig.service.IProcessDefinitionService;
import com.redpig.util.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.ProcessDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.*;
import java.util.zip.ZipInputStream;

@Tag(name = "流程定义")
@RestController
public class ProcessDefinitionController {

    @Autowired
    private IProcessDefinitionService processDefinitionService;

    @Autowired
    RepositoryService repositoryService;

    @Autowired
    RuntimeService runtimeService;

    @Autowired
    TaskService taskService;

    @Autowired
    HistoryService historyService;

    @Parameters({
            @Parameter(name = "currentPage",description = "页码",required = true,in = ParameterIn.QUERY),
            @Parameter(name = "pageSize",description = "每页条数",required = true,in = ParameterIn.QUERY),
            @Parameter(name = "startTime",description = "开始时间：格式yyyy-mm-dd",required = false,in = ParameterIn.QUERY),
            @Parameter(name = "endTime",description = "结束时间：格式yyyy-mm-dd",required = false,in = ParameterIn.QUERY),
    })
    @Operation(summary = "流程定义分页查询")
    @GetMapping("/processDefinition/page")
    public Result page(
            @RequestParam(name = "currentPage",defaultValue = "1") Long currentPage,
            @RequestParam(name = "pageSize",defaultValue = "10") Long pageSize,
            @DateTimeFormat(pattern = "yyyy-MM-dd") Date startTime,
            @DateTimeFormat(pattern = "yyyy-MM-dd") Date endTime){

        QueryWrapper<ProcessDefinitionVO> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("updateTime");

        if(ObjUtil.isNotEmpty(startTime) && ObjUtil.isNotEmpty(endTime)){
            wrapper.ge("updateTime",startTime);
            wrapper.le("updateTime",endTime);
        }

        List<ProcessDefinition> processDefinitions = repositoryService.createProcessDefinitionQuery().listPage((currentPage.intValue() - 1) * pageSize.intValue(), pageSize.intValue());

        List<ProcessDefinitionVO> processDefinitionVOS = new ArrayList<>();
        for (ProcessDefinition processDefinition : processDefinitions) {
            ProcessDefinitionVO processDefinitionVO = new ProcessDefinitionVO();
            processDefinitionVO.setId(processDefinition.getId());
            processDefinitionVO.setName(processDefinition.getName());
            processDefinitionVO.setDeploymentId(processDefinition.getDeploymentId());
            processDefinitionVOS.add(processDefinitionVO);
        }

        IPage<ProcessDefinitionVO> iPage = new Page<>();
        iPage.setRecords(processDefinitionVOS);
        long total = repositoryService.createDeploymentQuery().count();
        iPage.setPages(( total +  pageSize  - 1) / pageSize);
        iPage.setCurrent(currentPage);
        iPage.setSize(pageSize);
        iPage.setTotal(total);

        return Result.success(iPage);
    }

    @GetMapping("/processDefinition/processImage/{deploymentId}")
    public void processImage(HttpServletResponse response, @PathVariable("deploymentId") String deploymentId){
        //获取图片资源名称
        List<String> list = repositoryService.getDeploymentResourceNames(deploymentId);
        //定义图片资源的名称
        String resourceName = "";
        if(list!=null && list.size()>0){
            for(String name:list){
                if(name.indexOf(".png")>=0){
                    resourceName = name;
                }
            }
        }

        //获取图片的输入流
        InputStream in = repositoryService.getResourceAsStream(deploymentId, resourceName);

        try {
            //filePath:图片完整路径
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[2048];
            int len = 0;
            while( (len=in.read(buffer)) != -1 ){
                outStream.write(buffer, 0, len);
            }
            in.close();
            byte data[] = outStream.toByteArray();
            response.setContentType("image/jpg");
            OutputStream os = response.getOutputStream();
            os.write(data);
            os.flush();
            os.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Operation(summary = "新增流程定义",description = "新增或者更新流程定义")
    @PostMapping("/processDefinition/saveOrUpdate")
    public Result saveOrUpdate(@RequestBody ProcessDefinitionVO processDefinition){
        return Result.success(processDefinitionService.saveOrUpdate(processDefinition));
    }

    @Operation(summary = "删除流程定义",description = "根据ID删除流程定义")
    @PostMapping("/processDefinition/delById")
    public Result delById(@RequestBody ProcessDefinitionVO processDefinition){
        try {
            repositoryService.deleteDeployment(processDefinition.getDeploymentId());
        }catch (Exception e){
            throw new RuntimeException("删除失败：还有未完成的流程");
        }
        return Result.ok();
    }

    @Operation(summary = "查询流程定义",description = "根据ID查询流程定义")
    @PostMapping("/processDefinition/getById")
    public void getById(@RequestBody ProcessDefinitionVO processDefinition,HttpServletResponse response) throws IOException {
        /**将生成图片放到文件夹下*/
        //获取图片资源名称
        List<String> list = repositoryService.getDeploymentResourceNames(processDefinition.getDeploymentId());
        //定义图片资源的名称
        String resourceName = "";
        if(list!=null && list.size()>0){
            for(String name:list){
                if(name.indexOf(".png")>=0){
                    resourceName = name;
                }
            }
        }

        //获取图片的输入流
        InputStream in = repositoryService.getResourceAsStream(processDefinition.getDeploymentId(), resourceName);
        ServletOutputStream out = response.getOutputStream();

        int len=0;
        byte[]buffer=new byte[1024];
        while((len=in.read(buffer))>0) {
            out.write(buffer, 0, len);
        }

        in.close();
        out.close();
    }

    @Operation(summary = "批量删除",description = "根据ID批量删除流程定义")
    @PostMapping("/processDefinition/removeBatchByIds")
    public Result removeBatchByIds(@RequestBody List<Long> ids) {
        return Result.success(processDefinitionService.removeBatchByIds(ids));
    }

    /**
     * 上传流程文件 并部署
     */
    @RequestMapping("/processDefinition/deploy")
    @ResponseBody
    public Result deploy(MultipartFile processDefinitionFile) throws IOException {

        InputStream in = processDefinitionFile.getInputStream();
        ZipInputStream zipInputStream = new ZipInputStream(in);
        repositoryService.createDeployment()
                .addZipInputStream(zipInputStream)
                .deploy();

        return Result.ok();
    }
}