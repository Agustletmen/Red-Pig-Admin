package com.redpig.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

@Tag(name = "上传")
@CrossOrigin
@RestController
public class UploadController {

    @Value("${upload}")
    String upload;

    @Value("${serverPath}")
    String serverPath;

    /**
     * 单文件上传
     */
    @SneakyThrows
    @RequestMapping("/upload/ajaxFileuPload")
    @ResponseBody
    public Map<String, Object> ajaxFileuPload(MultipartFile myfile)  {
        //上传到nginx目录 通过nginx查看文件 或者访问文件
        String fileName = myfile.getOriginalFilename();
        File targetFile = new File(upload, fileName);

        if (!targetFile.exists()) {//如果上面的上传路径不存在 则创建
            targetFile.mkdirs();
        }

        // 上传
        myfile.transferTo(targetFile);
        Map<String, Object> map = new HashMap<>();
        map.put("serverPath",serverPath);
        map.put("fileName", fileName);

        return map;
    }

}
