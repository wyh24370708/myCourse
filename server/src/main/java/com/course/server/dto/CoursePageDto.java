package com.course.server.dto;

import lombok.Data;

@Data
public class CoursePageDto extends PageDto {

    private String status;

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("CoursePageDto{");
        sb.append("status='").append(status).append('\'');
        sb.append(", pageNum=").append(pageNum);
        sb.append(", PageSize=").append(PageSize);
        sb.append(", totalNum=").append(totalNum);
        sb.append(", list=").append(list);
        sb.append('}');
        return sb.toString();
    }
}
