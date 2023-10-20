package com.redpig.util;

import lombok.SneakyThrows;
import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.image.ProcessDiagramGenerator;
import org.activiti.image.impl.DefaultProcessDiagramGenerator;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ActivitiUtils {
    @Autowired
    RepositoryService repositoryService;

    @Autowired
    HistoryService historyService;

    @Autowired
    RuntimeService runtimeService;

    /**
     * 根据流程定义ID 获取结束节点坐标
     * @param processDefinitionId
     * @return
     */
    @SneakyThrows
    public int[] getEndPlace(String processDefinitionId){
        int x = 0;
        int y = 0;
        int width = 0;
        int height= 0;
        BpmnModel bpmnModel = repositoryService.getBpmnModel(processDefinitionId);

        String xmlContent = new String(new BpmnXMLConverter().convertToXML(bpmnModel), "UTF-8");
        Document document = DocumentHelper.parseText(xmlContent);
        Element element = document.getRootElement().element("process").element("endEvent");
        String endEventId = element.attributeValue("id");

        //sid-d424740e-24d0-4a80-82ba-94550a4e88a5
        Element ele = document.getRootElement();
        ele = ele.element("BPMNDiagram");
        ele = ele.element("BPMNPlane");
        for (Object bpmnShape : ele.elements("BPMNShape")) {
            Element e = (Element) bpmnShape;
            Attribute attribute = e.attribute("bpmnElement");
            if(attribute.getName().equals("bpmnElement") && attribute.getValue().equals(endEventId)){
                Element bounds = e.element("Bounds");
                List<Attribute> attributes = bounds.attributes();
                for (Attribute attr : attributes) {
                    if(attr.getName().equals("x")){
                        x = Integer.valueOf(attr.getValue().substring(0,attr.getValue().indexOf(".")));
                    }
                    if(attr.getName().equals("y")){
                        y = Integer.valueOf(attr.getValue().substring(0,attr.getValue().indexOf(".")));
                    }
                    if(attr.getName().equals("width")){
                        width = Integer.valueOf(attr.getValue().substring(0,attr.getValue().indexOf(".")));
                    }
                    if(attr.getName().equals("height")){
                        height = Integer.valueOf(attr.getValue().substring(0,attr.getValue().indexOf(".")));
                    }
                }
            }
        }

        return new int[]{x,y,width,height};
    }


    @SneakyThrows
    public InputStream getEndImage(String processInstanceId){
        HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery().processInstanceId(processInstanceId).list().get(0);

        String deploymentId = historicProcessInstance.getDeploymentId();
        //获取图片资源名称
        List<String> list = repositoryService.getDeploymentResourceNames(deploymentId);
        //定义图片资源的名称
        String resourceName = "";
        if (list != null && list.size() > 0) {
            for (String name : list) {
                if (name.indexOf(".png") >= 0) {
                    resourceName = name;
                }
            }
        }

        String processDefinitionId = historicProcessInstance.getProcessDefinitionId();

        int[] endPlace = getEndPlace(processDefinitionId);
        Arrays.stream(endPlace).forEach(System.out::println);
        int x = endPlace[0] * 2 - 23;
        int y = -endPlace[1] * 2 + 14;
        int width = endPlace[2] + 5;
        int height= endPlace[3] + 5;

        //获取图片的输入流
        InputStream in = repositoryService.getResourceAsStream(deploymentId, resourceName);

        Image srcImg = ImageIO.read(in);
        int srcImgWidth = srcImg.getWidth(null);
        int srcImgHeight = srcImg.getHeight(null);

        BufferedImage bufImg = new BufferedImage(srcImgWidth, srcImgHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = bufImg.createGraphics();
        g.drawImage(srcImg, 0, 0, srcImgWidth, srcImgHeight, null);

        g.setPaint(new Color(0, 255, 127));
        BasicStroke stokeLine = new BasicStroke(7.0f);
        g.setStroke(stokeLine);

        g.drawRoundRect(x, y, width,height,width,height);
        g.dispose();

        //待存储的地址
        String tarImgPath="redpig.png";
        // 输出图片
        FileOutputStream outImgStream = new FileOutputStream(tarImgPath);
        ImageIO.write(bufImg, "png", outImgStream);

        outImgStream.flush();
        outImgStream.close();

        return new FileInputStream(tarImgPath);
    }

    public InputStream getFlowImage(String processInstanceId) {
        HistoricTaskInstance historicTaskInstance = historyService
                .createHistoricTaskInstanceQuery().processInstanceId(processInstanceId)
                .orderByTaskCreateTime().desc()
                .list().get(0);

        BpmnModel model = repositoryService.getBpmnModel(historicTaskInstance.getProcessDefinitionId());

        ProcessDiagramGenerator generator = new DefaultProcessDiagramGenerator();

        //lastTask是当前任务执行到的位置
        List<HistoricActivityInstance> lastTasks =
                historyService.createHistoricActivityInstanceQuery()
                        .processInstanceId(historicTaskInstance.getProcessInstanceId())
                        .orderByHistoricActivityInstanceStartTime()
                        .desc()
                        .list();
        List<String> lastTask = lastTasks.stream()
                .map(HistoricActivityInstance::getActivityId)
                .limit(1)
                .collect(Collectors.toList());
        lastTask.add(lastTasks.get(0).getActivityId());

        //七个参数分别是：
        //  BPMNModel
        //  高光节点
        //  高光顺序流
        //  活动字体名称
        //  标签字体名称
        //  批注字体名称
        //  生成默认关系图
        //  默认关系图映像文件名
        InputStream inputStream = generator.generateDiagram(
                model, lastTask, Collections.emptyList(), "宋体", "宋体", "宋体", true, "test");

        return inputStream;
    }

}
