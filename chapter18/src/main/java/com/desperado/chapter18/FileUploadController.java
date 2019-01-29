package com.desperado.chapter18;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.Base64Utils;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/uploads")
public class FileUploadController {
    public static final Logger log = LoggerFactory.getLogger(FileUploadController.class);

    @GetMapping
    public String index(){
        return "index";
    }

    @PostMapping("/upload1")
    @ResponseBody
    public Map<String,String> upload1(@RequestParam("file")MultipartFile file) throws IOException {
        Map<String,String> result = new HashMap<>();
        log.info("文件类型-> {}",file.getContentType());
        log.info("文件名称-> {}",file.getOriginalFilename());
        log.info("文件大小-> {}",file.getSize());
        //保存文件
        file.transferTo(new File("C:\\code\\springboot\\chapter18\\"+file.getOriginalFilename()));
        result.put("contentType",file.getContentType());
        result.put("fileName",file.getOriginalFilename());
        result.put("fileSize",file.getSize()+"");
        return result;
    }

    @PostMapping("/upload2")
    @ResponseBody
    public List<Map<String,String>> upload2(@RequestParam("file") MultipartFile[] files) throws IOException{
        if(files == null || files.length == 0){
            return null;
        }
        List<Map<String,String>> list = new ArrayList<>();
        for (MultipartFile file: files){
            Map<String,String> result = new HashMap<>();
            file.transferTo(new File("C:\\code\\springboot\\chapter18\\"+file.getOriginalFilename()));
            result.put("contentType",file.getContentType());
            result.put("fileName",file.getOriginalFilename());
            result.put("fileSize",file.getSize()+"");
            list.add(result);
        }
        return list;
    }

    @PostMapping("/upload3")
    @ResponseBody
    public void upload3(String base64) throws IOException{
        // TODO BASE64 方式的 格式和名字需要自己控制（如 png 图片编码后前缀就会是 data:image/png;base64,）
        final File tempFile = new File("C:\\code\\springboot\\chapter18\\test.jpg");
        // TODO 防止有的传了 data:image/png;base64, 有的没传的情况
        String[] d = base64.split("base64,");
        final byte[] bytes = Base64Utils.decodeFromString(d.length > 1 ? d[1] : d[0]);
        FileCopyUtils.copy(bytes, tempFile);
    }
}
