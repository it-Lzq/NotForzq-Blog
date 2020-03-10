package cn.itlzq.service;

import cn.itlzq.bean.Blog;
import cn.itlzq.bean.BlogQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

/**
 * @author 作者:李泽庆
 * @version 创建时间:2020/3/8 11:37
 * @email 邮箱:905866484@qq.com
 * @description 描述：博客
 */
public interface BlogService {

    /**
     * 查询单个Blog
     * @param id 根据id
     * @return blog
     */
    Blog getBlog(Long id);

    /**
     * 分页查询根据条件查询
     * @param blog 根据条件查询
     */
    Page<Blog> listBlog(Pageable pageable, BlogQuery blog);

    /**
     *分页查询所有博客
     */
    Page<Blog> listBlog(Pageable pageable);

    /**
     * 分页查询根据关键字
     * @return 搜索结果
     */
    Page<Blog> listBlog(Pageable pageable,String query);

    /**
     * 分页查询根据标签
     */
    Page<Blog> listBlog(Long tagId, Pageable pageable);

    /**
     *  添加博客
     * @param blog 博客
     */
    Blog saveBlog(Blog blog);

    /**
     * 更新博客
     */
    Blog updateBlog(Long id,Blog blog);

    /**
     * 删除博客
     */
    void deleteBlog(Long id);

    /**
     * 获取博客顶部
     */
    List<Blog> listRecommendBlogTop(Integer size);

    /**
     * 转化html
     */
    Blog getHtmlBlog(Long id);

    /**
     * 年份map
     */
    Map<String,List<Blog>> yearBlogMap();

    /**
     * 数量
     * @return 数量
     */
    Long countBlog();




}
