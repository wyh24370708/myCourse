package com.course.file.controller.admin;

import com.course.server.dto.ResponseDto;
import com.course.server.util.UuidUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/admin")
public class UploadController {

    private static final Logger LOG = LoggerFactory.getLogger(UploadController.class);
    private static final String BUSINESS_NAME = "文件";

    //属性注入
    @Value("${file_up_path}")
    private String FILE_UP_PATH;
    @Value("${file_server_path}")
    private String FILE_SERVER_PATH;

    @RequestMapping("/upload")
    public ResponseDto upload(MultipartFile file) throws IOException {
        ResponseDto responseDto = new ResponseDto();
        LOG.info("上传文件开始:{}",file);
        LOG.info(file.getOriginalFilename());
        LOG.info(String.valueOf(file.getSize()));

        //指定上传的路径
        String pathDir =  FILE_UP_PATH + "teacher/";
        File fileDir = new File(pathDir);
        if (!fileDir.exists()){
            fileDir.mkdirs();//上传路径不存在就新创建路径
        }
        // 保存文件到本地
        String filename = file.getOriginalFilename();//文件名
        String uuid = UuidUtil.getShortUuid();//防止文件重复
        String fullPath = pathDir + uuid + "-" + filename;//目标路径
        File dest = new File(fullPath);
        //上传到目标位置
        file.transferTo(dest);
        LOG.info(dest.getAbsolutePath());//本机中图片存储的路径

        //配置静态资源之后, 路径对外暴露, 返回结果中存入访问地址 头像实时显示
        String url = FILE_SERVER_PATH + "f/teacher/" + uuid + "-" + filename;
        responseDto.setContent(url);
        return responseDto;
    }

}
