package com.course.server.dto;

import java.util.Date;

import com.course.server.enums.ProfileUseEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;
@Data
@Accessors(chain = true)
@ToString
public class ProfileDto {

    /**
     * id
     */
    private String id;

    /**
     * 名称
     */
    private String name;

    /**
     * 相对路径
     */
    private String path;

    /**
     * 大小
     */
    private Integer size;

    /**
     * 后缀
     */
    private String suffix;

    /**
     * 用途|枚举[FileUseEnum]：COURSE("C", "课程"), TEACHER("T", "讲师")
     */
    private String use;

    /**
     * 创建时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createdAt;

    /**
     * 修改时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date updatedAt;

    /**
     * 分片索引 | 已上传的分片
     */
    private Integer shardIndex;

    /**
     * 分片大小|B
     */
    private Integer shardSize;

    /**
     * 分片总数
     */
    private Integer shardTotal;

    /**
     * 文件标识
     */
    private String key;

}