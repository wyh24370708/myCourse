package com.course.file.controller.admin;

import com.course.server.dto.ResponseDto;
import com.course.server.util.UuidUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/admin")
public class UploadController {

    private static final Logger LOG = LoggerFactory.getLogger(UploadController.class);

    @RequestMapping("/upload")
    public ResponseDto upload(MultipartFile file) throws IOException {
        ResponseDto responseDto = new ResponseDto();
        LOG.info("上传文件开始:{}",file);
        LOG.info(file.getOriginalFilename());
        LOG.info(String.valueOf(file.getSize()));

        //指定上传的路径
        String pathDir = "D:/0-yayuanzi/upload_file/course/teacher/";
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
        LOG.info(dest.getAbsolutePath());

        return responseDto;
    }

}
