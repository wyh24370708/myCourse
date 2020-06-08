package com.course.file.controller.admin;

import com.alibaba.fastjson.JSONObject;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.AppendObjectRequest;
import com.aliyun.oss.model.AppendObjectResult;
import com.aliyun.oss.model.ObjectMetadata;
import com.course.server.dto.ProfileDto;
import com.course.server.dto.ResponseDto;
import com.course.server.enums.ProfileUseEnum;
import com.course.server.service.ProfileService;
import com.course.server.util.Base64ToMultipartFile;
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
@RequestMapping("/admin")
public class OssController {

    private static final Logger LOG = LoggerFactory.getLogger(UploadController.class);
    private static final String BUSINESS_NAME = "文件";
    private static Map<String,String> PATH_MAP = new HashMap<>();

    @Resource
    private ProfileService profileService;
    //属性注入
    @Value("${accessKeyId}")
    private String ACCESS_KEY_ID;
    @Value("${accessKeySecret}")
    private String ACCESS_KEY_SECRET;
    @Value("${endpoint}")
    private String END_POINT;
    @Value("${ossDomain}")
    private String OSS_DOMAIN;
    @Value("${bucket}")
    private String BUCKET;


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
    @RequestMapping("/oss-append")
    public ResponseDto upload(@RequestBody ProfileDto profileDto) throws Exception {
        ResponseDto responseDto = new ResponseDto();
        LOG.info("上传文件开始...");
        //参数转换
        String use = profileDto.getUse();
        String key = profileDto.getKey();
        String suffix = profileDto.getSuffix();
        Integer shardIndex = profileDto.getShardIndex();
        Integer shardSize = profileDto.getShardSize();
        String shardBase64 = profileDto.getShard();
        MultipartFile shard = Base64ToMultipartFile.base64ToMultipart(shardBase64);

        //获取枚举类型
        ProfileUseEnum useEnum = ProfileUseEnum.getEnumByCode(use);
        String  useDir = useEnum.name().toLowerCase();
        //指定上传的路径
        String fullPath = new StringBuffer(useDir)
                .append("/")
                .append(key)
                .append(".")
                .append(suffix)
                .toString();

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(END_POINT, ACCESS_KEY_ID, ACCESS_KEY_SECRET);

        ObjectMetadata meta = new ObjectMetadata();
        // 指定上传的内容类型。
        meta.setContentType("text/plain");

        // 通过AppendObjectRequest设置多个参数。
        AppendObjectRequest appendObjectRequest =
                new AppendObjectRequest(BUCKET,fullPath, new ByteArrayInputStream(shard.getBytes()),meta);

        // 通过AppendObjectRequest设置单个参数。
        // 设置存储空间名称。
        //appendObjectRequest.setBucketName("<yourBucketName>");
        // 设置文件名称。
        //appendObjectRequest.setKey("<yourObjectName>");
        // 设置待追加的内容。有两种可选类型：InputStream类型和File类型。这里为InputStream类型。
        //appendObjectRequest.setInputStream(new ByteArrayInputStream(content1.getBytes()));
        // 设置待追加的内容。有两种可选类型：InputStream类型和File类型。这里为File类型。
        //appendObjectRequest.setFile(new File("<yourLocalFile>"));
        // 指定文件的元信息，第一次追加时有效。
        //appendObjectRequest.setMetadata(meta);

        // 第一次追加。
        // 设置文件的追加位置。
        appendObjectRequest.setPosition((long) ((shardIndex -1) * shardSize));//
        AppendObjectResult appendObjectResult = ossClient.appendObject(appendObjectRequest);
        // 文件的64位CRC值。此值根据ECMA-182标准计算得出。
        System.out.println(appendObjectResult.getObjectCRC());
        System.out.println(JSONObject.toJSONString(appendObjectResult));

//        // 第二次追加。
//        // nextPosition指明下一次请求中应当提供的Position，即文件当前的长度。
//        appendObjectRequest.setPosition(appendObjectResult.getNextPosition());
//        appendObjectRequest.setInputStream(new ByteArrayInputStream(content2.getBytes()));
//        appendObjectResult = ossClient.appendObject(appendObjectRequest);
//
//        // 第三次追加。
//        appendObjectRequest.setPosition(appendObjectResult.getNextPosition());
//        appendObjectRequest.setInputStream(new ByteArrayInputStream(content3.getBytes()));
//        appendObjectResult = ossClient.appendObject(appendObjectRequest);

        // 关闭OSSClient。
        ossClient.shutdown();

        //保存到文件管理
        profileDto.setPath(fullPath);
        profileService.save(profileDto);
        //保存到业务页面显示
        profileDto.setPath(OSS_DOMAIN +fullPath);
        responseDto.setContent(profileDto);

        return responseDto;
    }
    //简单上传
    // 创建OSSClient实例。   file为multipartFile参数
//    OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
//    PutObjectRequest putObjectRequest = new PutObjectRequest(bucket, path, new ByteArrayInputStream(file.getBytes()));
//    // 上传字符串。
//    ossClient.putObject(putObjectRequest);

}
