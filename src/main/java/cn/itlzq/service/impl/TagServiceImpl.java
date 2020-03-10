package cn.itlzq.service.impl;


import cn.itlzq.bean.Blog;
import cn.itlzq.bean.Tag;
import cn.itlzq.db.TagDao;
import cn.itlzq.exception.NotFoundBlogException;
import cn.itlzq.service.TagService;
import cn.itlzq.service.TagService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 作者:李泽庆
 * @version 创建时间:2020/3/7 19:35
 * @email 邮箱:905866484@qq.com
 * @description 描述：
 */
@Service
public class TagServiceImpl implements TagService {

    @Autowired
    private TagDao dao;

    @Override @Transactional
    public Tag insertTag(Tag tag) {
        return dao.save(tag);

    }

    @Override
    public Tag getTag(Long id) {
        return dao.getOne(id);
    }

    @Override
    public Page<Tag> listTag(Pageable pageable) {
        return dao.findAll(pageable);
    }



    @Override @Transactional
    public Tag updateTag(Long id, Tag tag) {
        Tag t = getTag(id);
        if(t == null){
            throw new NotFoundBlogException("不存在该类型");
        }
        BeanUtils.copyProperties(tag,t);
        return dao.save(t);
    }

    @Override @Transactional
    public void deleteTag(Long id) {
        dao.deleteById(id);
    }

    @Override
    public Tag getTagByName(String name) {
        return dao.findByName(name);
    }

    @Override
    public List<Tag> listTag() {
        return dao.findAll();
    }

    @Override
    public List<Tag> listTag(String ids) {
        List<Long> tags= new ArrayList<>();
        if(ids !=null &&!"".equals(ids)){

            String[] is = ids.split(",");
            for(int i = 0; i < is.length; i++) {
                tags.add(new Long(is[i]));
            }
        }
        System.out.println(tags);
        return  dao.findAllById(tags);
    }

    @Override
    public List<Tag> listTag(Integer size) {
        Sort sort =  Sort.by(Sort.Direction.DESC,"blogs.size");
        Pageable pageable = PageRequest.of(0,size,sort);
        return dao.findTop(pageable);
    }


}
