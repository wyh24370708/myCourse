package com.course.server.dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.course.server.domain.Section;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

@Data
public class CourseDto {

    /**
     * id
     */
    private String id;

    /**
     * 课程名称
     */
    private String name;

    /**
     * 概述
     */
    private String summary;

    /**
     * 时长|单位秒
     */
    private Integer time;

    /**
     * 价格(元)
     */
    private BigDecimal price;

    /**
     * 封面
     */
    private String image;

    /**
     * 级别|ONE("1","初级"),TWO("2","中级"),THREE("3","高级")
     */
    private String level;

    /**
     * 收费|CHARGE("C","收费"),FREE("F","免费")
     */
    private String charge;

    /**
     * 状态|PUBLISH("P","发布"),DRAFT("D","草稿")
     */
    private String status;

    /**
     * 报名数
     */
    private Integer enroll;

    /**
     * 顺序
     */
    private Integer sort;

    /**
     * 创建时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createdAt;

    /**
     * 更新时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date updatedAt;

    /**
     * 课程分类
     * @return
     */
    List<CategoryDto> categorys;

    /**
     * 讲师id
     * @return
     */
    private String teacherId;


    /**
     * 课程内容
     */
    private String CourseContent;
    /**
     * 大章信息
     */
    private List<ChapterDto> chapters;
    /**
     * 小节信息
     */
    private List<SectionDto> sections;
    /**
     * 讲师信息
     */
    private TeacherDto teacher;


    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("CourseDto{");
        sb.append("id='").append(id).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", summary='").append(summary).append('\'');
        sb.append(", time=").append(time);
        sb.append(", price=").append(price);
        sb.append(", image='").append(image).append('\'');
        sb.append(", level='").append(level).append('\'');
        sb.append(", charge='").append(charge).append('\'');
        sb.append(", status='").append(status).append('\'');
        sb.append(", enroll=").append(enroll);
        sb.append(", sort=").append(sort);
        sb.append(", createdAt=").append(createdAt);
        sb.append(", updatedAt=").append(updatedAt);
        sb.append(", categorys=").append(categorys);
        sb.append(", teacherId='").append(teacherId).append('\'');
        sb.append(", CourseContent='").append(CourseContent).append('\'');
        sb.append(", chapters=").append(chapters);
        sb.append(", sections=").append(sections);
        sb.append(", teacher=").append(teacher);
        sb.append('}');
        return sb.toString();
    }
}