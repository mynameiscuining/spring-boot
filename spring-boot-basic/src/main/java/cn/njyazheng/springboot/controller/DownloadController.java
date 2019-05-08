package cn.njyazheng.springboot.controller;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

@Controller
@RequestMapping("/download")
public class DownloadController {
    private static final Logger LOGGER = LoggerFactory.getLogger(DownloadController.class);
    @GetMapping
    public String file() {
        return "download";
    }
    
    //方式一
    @GetMapping("/file1")
    public ResponseEntity<byte[]> testRespnseEntity() {
        
        byte[] body = null;
        try (InputStream in = new FileInputStream("D:\\image\\kg.jpg")){
            body = new byte[in.available()];
            in.read(body);
        } catch (Exception e) {
            LOGGER.error("------------------------Exception"+e);
            e.printStackTrace();
        }
        HttpHeaders headers = new HttpHeaders();
        //下载时会显示的文件名称
        headers.add("Content-Disposition", "attachment;filename=kg.jpg");
        HttpStatus statusCode = HttpStatus.OK;
        ResponseEntity<byte[]> response = new ResponseEntity<byte[]>(body, headers, statusCode);
        return response;
    }
    
    //方式二
    @GetMapping("/file2")
    public void upload2(HttpServletResponse response){
        try (InputStream in = new FileInputStream("D:\\image\\kg.jpg");
             OutputStream outputStream=response.getOutputStream();) {
            response.setContentType("application/x-download");
            response.setHeader("Content-Disposition", "attachment;filename=kg.jpg");
            IOUtils.copy(in,outputStream);
        }catch (Exception e) {
            LOGGER.error("------------------------Exception"+e);
            e.printStackTrace();
        }
    }
}
