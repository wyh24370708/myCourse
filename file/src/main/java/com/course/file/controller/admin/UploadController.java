package com.course.file.controller.admin;

import com.course.server.dto.ProfileDto;
import com.course.server.dto.ResponseDto;
import com.course.server.enums.ProfileUseEnum;
import com.course.server.service.ProfileService;
import com.course.server.util.Base64ToMultipartFile;
import com.course.server.util.UuidUtil;
import org.bouncycastle.jcajce.provider.digest.MD5;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.*;

@RestController
@RequestMapping("/admin")
public class UploadController {

    private static final Logger LOG = LoggerFactory.getLogger(UploadController.class);
    private static final String BUSINESS_NAME = "文件";

    @Resource
    private ProfileService profileService;
    //属性注入
    @Value("${file_up_path}")
    private String FILE_UP_PATH;
    @Value("${file_server_path}")
    private String FILE_SERVER_PATH;

    /**
     * 上传文件--大文件
     * 对于分片上传,上传第一次后,后面都是针对同一条记录进行更新,
     *  shardIndex更新的字段,其他不变
     *  什么时候上传完成? shardIndex==shardTotal时,就是全部上传完成了
     *  每一步上传都要进行合并
     *
     *  如何定位是一个文件?  key作为文件的标志位
     * @return
     * @throws IOException
     */
    @RequestMapping("/uploadBigFile")
    public ResponseDto upload(@RequestBody ProfileDto profileDto) throws IOException {
        ResponseDto responseDto = new ResponseDto();
        LOG.info("上传文件开始...");
        //参数转换
        String use = profileDto.getUse();
        String key = profileDto.getKey();
        String suffix = profileDto.getSuffix();
        String shardBase64 = profileDto.getShard();
        MultipartFile shard = Base64ToMultipartFile.base64ToMultipart(shardBase64);

        //获取枚举类型
        ProfileUseEnum useEnum = ProfileUseEnum.getEnumByCode(use);
        String  teacher = useEnum.name().toLowerCase();
        //指定上传的路径
        String pathDir =  FILE_UP_PATH + teacher + File.separator;
        File fileDir = new File(pathDir);
        if (!fileDir.exists()){
            fileDir.mkdirs();//上传路径不存在就新创建路径
        }
        // 保存文件到本地
        String fullPath = pathDir + key + "." + suffix;//目标路径
        String profilePath = teacher + File.separator + key + "." + suffix;
        File dest = new File(fullPath);
        //上传到目标位置
        shard.transferTo(dest);

        LOG.info("文件保存记录开始...");
        //配置静态资源之后, 路径对外暴露, 返回结果中存入访问地址 头像实时显示
        String url = FILE_SERVER_PATH + profilePath;
        profileDto.setPath(url);
        profileService.save(profileDto);
        responseDto.setContent(profileDto);
        return responseDto;
    }

    /**
     * 上传文件 ---图片
     * @param file
     * @param use
     * @return
     * @throws IOException
     */
    @RequestMapping("/upload")
    public ResponseDto upload(MultipartFile file,String use) throws IOException {
        ResponseDto responseDto = new ResponseDto();
        LOG.info("上传文件开始...");
        LOG.info("文件名: ", file.getOriginalFilename());
        LOG.info("文件大小", String.valueOf(file.getSize()));

        //获取枚举类型
        ProfileUseEnum useEnum = ProfileUseEnum.getEnumByCode(use);
        String  teacher = useEnum.name().toLowerCase();
        //指定上传的路径
        String pathDir =  FILE_UP_PATH + teacher + File.separator;
        File fileDir = new File(pathDir);
        if (!fileDir.exists()){
            fileDir.mkdirs();//上传路径不存在就新创建路径
        }
        // 保存文件到本地
        String filename = file.getOriginalFilename();//文件名
        String uuid = UuidUtil.getShortUuid();//防止文件重复
        String fullPath = pathDir + uuid + "-" + filename;//目标路径
        String profilePath = teacher + File.separator + uuid + "-" + filename;
        File dest = new File(fullPath);
        //上传到目标位置
        file.transferTo(dest);
        LOG.info(dest.getAbsolutePath());//本机中图片存储的路径

        LOG.info("文件保存记录开始...");
        String suffix = filename.substring(filename.lastIndexOf(".") + 1).toLowerCase();
        ProfileDto profileDto = new ProfileDto();
        profileDto.setPath(profilePath);
        profileDto.setName(filename);
        profileDto.setSize(Integer.parseInt(String.valueOf(file.getSize())));
        profileDto.setSuffix(suffix);
        profileDto.setUse(use);
        profileService.save(profileDto);
        //配置静态资源之后, 路径对外暴露, 返回结果中存入访问地址 头像实时显示
        String url = FILE_SERVER_PATH + profilePath;
        profileDto.setPath(url);
        responseDto.setContent(profileDto);
        return responseDto;
    }

    /**
     * 分片测试
     */
    @GetMapping("/merge")
    public ResponseDto merge() throws IOException {
        ResponseDto responseDto = new ResponseDto();
        //文件输出的目标位置
        File file = new File(FILE_UP_PATH + "/course/test123.mp4");
        FileOutputStream fos = new FileOutputStream(file, true);
        FileInputStream fis = null;
        byte[] bytes = new byte[10 * 1024 * 1024];
        int len = 0;

        try {
            //第一个分片
            fis = new FileInputStream(new File(FILE_UP_PATH + "/course/2FHFqA8v-blob"));
            while ((len = fis.read(bytes))!= -1){
                fos.write(bytes,0, len);
            }
            //第二个分片
            fis = new FileInputStream(new File(FILE_UP_PATH + "/course/AAyu8Tn8-blob"));
            while ((len = fis.read(bytes))!= -1){
                fos.write(bytes,0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
            LOG.error("分片合并异常", e);
        }finally {
            try {
                if (fis != null){
                    fos.close();
                    fis.close();
                }
                LOG.info("IO流关闭");
            } catch (IOException e) {
                e.printStackTrace();
                LOG.error("IO流关闭错误");
            }
        }

        return responseDto;
    }

}
