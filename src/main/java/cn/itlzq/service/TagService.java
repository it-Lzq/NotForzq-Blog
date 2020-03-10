package cn.itlzq.service;

import cn.itlzq.bean.Blog;
import cn.itlzq.bean.Tag;
import cn.itlzq.bean.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author 作者:李泽庆
 * @version 创建时间:2020/3/7 19:32
 * @email 邮箱:905866484@qq.com
 * @description 描述：类别
 */

public interface TagService {

    Tag insertTag(Tag tag);

    Tag getTag(Long id);

    Page<Tag> listTag(Pageable pageable);



    Tag updateTag(Long id, Tag tag);

    void deleteTag(Long id);

    Tag getTagByName(String name);

    List<Tag> listTag();

    List<Tag> listTag(String ids);

    List<Tag> listTag(Integer size);
}
