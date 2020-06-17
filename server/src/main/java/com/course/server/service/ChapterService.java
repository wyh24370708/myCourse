package com.course.server.service;

        import com.course.server.domain.Chapter;
        import com.course.server.domain.Teacher;
        import com.course.server.dto.*;

        import java.util.List;

public interface ChapterService {

    /**
     * 查询所有
     * @return
     */
    void findAll(ChapterPageDto chapterPageDto);

    /**
     * 新增大章
     */
    void save(ChapterDto chapterDto);

    /**
     * 删除大章
     * @param id
     */
    void delete(String id);

    /**
     *查询大章内容
     */
    List<ChapterDto> findChapterByCourseId(String courseId);
}
