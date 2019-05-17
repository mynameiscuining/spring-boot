package cn.njyazheng.springboot.database.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/file")
public class UploadController {
    private static final Logger LOGGER = LoggerFactory.getLogger(UploadController.class);
    @GetMapping
    public String file(){
        return "upload";
    }
    //上传方式一
    @PostMapping("/upload1")
    @ResponseBody
    public Map<String,Object> upload1(HttpServletRequest request){
        Map<String,Object>map=new HashMap<>();
        MultipartHttpServletRequest multipartHttpServletRequest=null;
        if(request instanceof MultipartHttpServletRequest){
            multipartHttpServletRequest=(MultipartHttpServletRequest)request;
        }else {
            map.put("message","上传失败");
            return map;
        }
        //获取文件信息
        MultipartFile multipartFile=multipartHttpServletRequest.getFile("file1");
        //获取源文件名称
        String fileName=multipartFile.getOriginalFilename();
        LOGGER.info("-----------------------------------file1 OriginalFilename:"+fileName);
        File file=new File("upload/"+fileName);
        try {
            multipartFile.transferTo(file);
        } catch (IOException e) {
            LOGGER.error("----------------------------------- IOException"+e.getLocalizedMessage());
            e.printStackTrace();
            map.put("message","上传失败");
            return map;
        }
        map.put("message","上传成功");
        return map;
    }
    //方式二
    @ResponseBody
    @PostMapping("/upload2")
    public Map<String,Object>upload2(MultipartFile file2){
        Map<String,Object>map=new HashMap<>();
        String fileName=file2.getOriginalFilename();
        LOGGER.info("-----------------------------------file1 OriginalFilename:"+fileName);
        File file=new File("upload/"+fileName);
        try {
            file2.transferTo(file);
        } catch (IOException e) {
            LOGGER.error("----------------------------------- IOException"+e.getLocalizedMessage());
            e.printStackTrace();
            map.put("message","上传失败");
            return map;
        }
        map.put("message","上传成功");
        return map;
    }
    
    //方式三
    @PostMapping("/upload3")
    @ResponseBody
    public Map<String,Object>upload3(Part file3){
        Map<String,Object>map=new HashMap<>();
        String fileName=file3.getSubmittedFileName();
        LOGGER.info("-----------------------------------file1 SubmittedFileName:"+fileName);
        try {
            file3.write("upload/"+fileName);
        } catch (IOException e) {
            LOGGER.error("----------------------------------- IOException"+e.getLocalizedMessage());
            e.printStackTrace();
            map.put("message","上传失败");
            return map;
        }
        map.put("message","上传成功");
        return map;
    }
}
