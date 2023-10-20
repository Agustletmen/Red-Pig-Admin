package com.redpig.controller.index;

import com.redpig.util.Result;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Base64;

@Tag(name = "系统首页")
@RestController
public class IndexController {
    @GetMapping("/index")
    public String index(){
        return "test";
    }

    @GetMapping("/unAuth/{username}/{url}")
    public Result unAuth(@PathVariable("username")String username,@PathVariable("url")String url){
        return Result.error(username+",没有访问"+ new String(Base64.getDecoder().decode(url)) +"的权限");
    }
}
