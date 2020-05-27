package com.course.server.dto;

public class SectionPageDto extends PageDto {
    private String courseId;
    private String chapterId;

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getChapterId() {
        return chapterId;
    }

    public void setChapterId(String chapterId) {
        this.chapterId = chapterId;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("SectionPageDto{");
        sb.append("courseId='").append(courseId).append('\'');
        sb.append(", chapterId='").append(chapterId).append('\'');
        sb.append(", pageNum=").append(pageNum);
        sb.append(", PageSize=").append(PageSize);
        sb.append(", totalNum=").append(totalNum);
        sb.append(", list=").append(list);
        sb.append('}');
        return sb.toString();
    }
}
