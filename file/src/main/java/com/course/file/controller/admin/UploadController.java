package com.course.file.controller.admin;

import com.course.server.dto.ProfileDto;
import com.course.server.dto.ResponseDto;
import com.course.server.enums.ProfileUseEnum;
import com.course.server.service.ProfileService;
import com.course.server.util.Base64ToMultipartFile;
import com.course.server.util.UuidUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/admin/loadfile")
public class UploadController {

    private static final Logger LOG = LoggerFactory.getLogger(UploadController.class);
    private static final String BUSINESS_NAME = "文件";
    private static Map<String,String> PATH_MAP = new HashMap<>();

    @Resource
    private ProfileService profileService;
    //属性注入
    @Value("${file_up_path}")
    private String FILE_UP_PATH;
    @Value("${file_server_path}")
    private String FILE_SERVER_PATH;


    /**
     * 分片检查 上传
     */
    @GetMapping("/shardCheck/{key}")
    public ResponseDto shardCheck(@PathVariable("key") String key){
        ResponseDto responseDto = new ResponseDto();
        ProfileDto profileDto = profileService.findByKey(key);
        if (profileDto!=null){
            profileDto.setPath(FILE_SERVER_PATH + profileDto.getPath());
        }
        responseDto.setContent(profileDto);
        return responseDto;
    }

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
    public ResponseDto upload(@RequestBody ProfileDto profileDto) throws Exception {
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
        String pathDir =  FILE_UP_PATH + teacher + "/";
        File fileDir = new File(pathDir);
        if (!fileDir.exists()){
            fileDir.mkdirs();//上传路径不存在就新创建路径
        }
        // 保存文件到本地
//        String fullPath = pathDir + key + "." + suffix;//目标路径
        String fullPath = new StringBuffer(pathDir).append(key)
                .append(".").append(suffix).toString();

        String shardPath = new StringBuffer(pathDir).append(key)
                .append("_").append(profileDto.getShardIndex())
                .append(".").append(suffix)
                .toString();
        PATH_MAP.put("PATH",fullPath);
        PATH_MAP.put("PATH_"+profileDto.getShardIndex(),shardPath);
        File dest = new File(shardPath);
        //上传到目标位置
        shard.transferTo(dest);

        LOG.info("文件保存记录开始...");
        //配置静态资源之后, 路径对外暴露, 返回结果中存入访问地址 头像实时显示
        String absolutePath = new StringBuffer(teacher).append("/").append(key)
                .append(".").append(suffix).toString();
        String server_url = FILE_SERVER_PATH + absolutePath;
        profileDto.setPath(absolutePath);
        profileService.save(profileDto);
        profileDto.setPath(server_url);
        responseDto.setContent(profileDto);

        //校验并合并分片
        if (profileDto.getShardIndex().equals(profileDto.getShardTotal())){
            this.merge(profileDto);
        }


        return responseDto;
    }

    /**
     * 分片合并
     */
    public void merge(ProfileDto profileDto) throws IOException, InterruptedException {
        LOG.info("合并分片开始......");
        Integer shardTotal = profileDto.getShardTotal();
        File file = new File(PATH_MAP.get("PATH"));//文件输出的目标位置
        FileOutputStream fos = new FileOutputStream(file, true);//true表示文件追加输出
        FileInputStream fis = null;//声明输入流
        byte[] bytes = new byte[10 * 1024 * 1024];//每次读写的字节数
        int len = 0;
        try {
            //循环合并
            for (int i = 1, LEN = shardTotal; i <= LEN; i++){
                fis = new FileInputStream(new File(PATH_MAP.get("PATH_"+i)));//分片
                while ((len = fis.read(bytes))!= -1){
                    fos.write(bytes,0, len);
                }
                //之前的代码只在finally里close掉了最后一个分片文件的输入流，每个分片输入流需要关闭一下。
                //这里每个分片的输入流都要关闭一下
                //transferTo方法也是使用输入输出字节流来完成传输的
                fis.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
            LOG.error("分片合并异常", e);
        }finally {
            try {
                fos.close();
                LOG.info("IO流关闭");
            } catch (IOException e) {
                e.printStackTrace();
                LOG.error("IO流关闭错误");
            }
        }
        LOG.info("合并分片结束......");

        //删除分片
        LOG.info("删除分片开始...");
        /*
           删除之前,调用System.gc();
           流不论是否关闭,垃圾不回收,堆里面还是存放了占用文件的信息,文件一直被占用
           System.gc();告诉虚拟机进行垃圾回收,解除文件的占用
         */
        System.gc();
        Thread.sleep(100);
        for (int i = 1; i <= shardTotal; i++){
            File shardFile = new File(PATH_MAP.get("PATH_" + i));
            if (shardFile.exists()){
                boolean result = shardFile.delete();
                LOG.info("删除{},{}", PATH_MAP.get("PATH_" + i), result? "成功":"失败");
            }
        }
        LOG.info("删除分片结束...");
    }

}
