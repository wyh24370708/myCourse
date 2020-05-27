package com.course.server.dto;

public class ChapterPageDto extends PageDto {
    private String courseId;

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("ChapterPageDto{");
        sb.append("courseId='").append(courseId).append('\'');
        sb.append(", pageNum=").append(pageNum);
        sb.append(", PageSize=").append(PageSize);
        sb.append(", totalNum=").append(totalNum);
        sb.append(", list=").append(list);
        sb.append('}');
        return sb.toString();
    }
}
