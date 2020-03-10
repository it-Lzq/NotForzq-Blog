package cn.itlzq.service.impl;

import cn.itlzq.bean.Blog;
import cn.itlzq.bean.BlogQuery;
import cn.itlzq.bean.Type;
import cn.itlzq.db.BlogDao;
import cn.itlzq.exception.NotFoundBlogException;
import cn.itlzq.service.BlogService;
import cn.itlzq.util.MarkdownUtils;
import cn.itlzq.util.MyBeanUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.Transient;
import javax.persistence.criteria.*;
import java.util.*;

/**
 * @author 作者:李泽庆
 * @version 创建时间:2020/3/8 11:41
 * @email 邮箱:905866484@qq.com
 * @description 描述：
 */
@Service
public class BlogServiceImpl implements BlogService {

    @Autowired
    private BlogDao dao;
    /**
     * 查询单个Blog
     * @param id 根据id
     * @return blog
     */
    @Override
    public Blog getBlog(Long id) {
        return dao.getOne(id);
    }

    /**
     * 分页查询
     */
    @Override
    public Page<Blog> listBlog(Pageable pageable, BlogQuery blog) {
        return  dao.findAll(new Specification<Blog>() {
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
                List<Predicate> list = new ArrayList<>();
                if(blog.getTitle() !=null && !"".equals(blog.getTitle())){
                    list.add(cb.like(root.get("title"),"%"+blog.getTitle()+"%"));
                }
                if(blog.getTypeId() !=null ){
                    list.add(cb.equal(root.<Type>get("type").get("id"),blog.getTypeId()));
                }
                if(blog.isRecommend()){
                    list.add(cb.equal(root.<Boolean>get("recommend"),blog.isRecommend()));
                }
                cq.where(list.toArray(new Predicate[list.size()]));
                return null;
            }
        },pageable);

    }

    @Override
    public Page<Blog> listBlog(Pageable pageable) {
        return dao.findAll(pageable);
    }

    /**
     * 分页查询根据关键字
     * @return 搜索结果
     */
    @Override
    public Page<Blog> listBlog(Pageable pageable, String query) {

        return dao.findByQuery(pageable,query);
    }

    @Override
    public Page<Blog> listBlog(Long tagId, Pageable pageable) {
        return dao.findAll(new Specification<Blog>() {
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
                Join join = root.join("tags");
                return cb.equal(join.get("id"),tagId);
            }
        },pageable);

    }
    /**
     * 添加博客
     * @param blog 博客
     */
    @Override
    public Blog saveBlog(Blog blog) {
        blog.setCreateTime(new Date());
        blog.setUpdateTime(new Date());
        blog.setViews(0);
        return dao.save(blog);
    }

    /**
     * 更新博客
     */
    @Override  @Transient
    public Blog updateBlog(Long id, Blog blog) {
        Blog b = dao.getOne(id);
        blog.setUser(b.getUser());
        if(b == null){
            throw new NotFoundBlogException("该博客不存在");
        }
        BeanUtils.copyProperties(blog,b, MyBeanUtils.getNullPropertyNames(blog));
        b.setUpdateTime(new Date());
         return dao.saveAndFlush(b);
       // return dao.save(b);
    }

    /**
     * 删除博客
     */
    @Override @Transient
    public void deleteBlog(Long id) {
        dao.deleteById(id);
    }

    /**
     * 获取博客顶部
     *
     * @param size
     */
    @Override
    public List<Blog> listRecommendBlogTop(Integer size) {
        Sort sort = Sort.by(Sort.Direction.DESC,"updateTime");
         Pageable pageable = PageRequest.of(0, size, sort);
        return dao.findTop(pageable);
    }

    /**
     * 转化html
     */
    @Override
    public Blog getHtmlBlog(Long id) {
        Blog blog = dao.getOne(id);
        if(blog == null){
            throw new NotFoundBlogException("博客不存在");
        }
        blog.setViews(blog.getViews()+1);
        dao.save(blog);
        Blog b = new Blog();
        BeanUtils.copyProperties(blog,b);
        String content = b.getContent();
        b.setContent(MarkdownUtils.markdownToHtmlExtensions(content));
        return b;
    }

    /**
     * 年份map
     */
    @Override
    public Map<String, List<Blog>> yearBlogMap() {
        List<String> years = dao.findGroupYear();
        Map<String, List<Blog>> map = new HashMap<>();
        for (String year : years) {
            List<Blog> byYear = dao.findByYear(year);
            map.put(year,byYear);
        }
        return map;
    }

    /**
     * 数量
     *
     * @return 数量
     */
    @Override
    public Long countBlog() {
        return dao.count();
    }
}
