package com.course.server.service;

        import com.course.server.domain.Chapter;
        import com.course.server.dto.ChapterDto;
        import com.course.server.dto.PageDto;

        import java.util.List;

public interface ChapterService {

    /**
     * 查询所有
     * @return
     */
    void findAll(PageDto<Chapter> pageDto);

    /**
     * 新增大章
     */
    void save(ChapterDto chapterDto);

    /**
     * 删除大章
     * @param id
     */
    void delete(String id);
}
