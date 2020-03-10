package cn.itlzq.controller;

import cn.itlzq.bean.Blog;
import cn.itlzq.bean.Tag;
import cn.itlzq.bean.Type;
import cn.itlzq.service.BlogService;
import cn.itlzq.service.TagService;
import cn.itlzq.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author 作者:李泽庆
 * @version 创建时间:2020/3/9 9:43
 * @email 邮箱:905866484@qq.com
 * @description 描述：
 */
@Controller
public class IndexController {
    @Autowired
    private BlogService blogService ;
    @Autowired
    private TypeService typeService;
    @Autowired
    private TagService tagService;

    /**
     * 首页
     * @return 首页
     */
    @GetMapping("/")
    public String index(@PageableDefault(size = 6,sort = {"updateTime"},direction = Sort.Direction.DESC) Pageable pageable,
                        Model model){
        List<Type> types = typeService.listType(6);
        List<Tag> tags = tagService.listTag(10);
        List<Blog> blogs = blogService.listRecommendBlogTop(8);
        model.addAttribute("page",blogService.listBlog(pageable));
        model.addAttribute("types", types);
        model.addAttribute("tags", tags);
        model.addAttribute("recommendBlogs",blogs);
        return "index";
    }

    /**
     * 搜索
     * @param pageable 分页
     * @param query 关键字
     * @param model 返回模型
     * @return 搜索结果
     */
    @PostMapping("/search")
    public String search(@PageableDefault(size = 6,sort = {"updateTime"},direction = Sort.Direction.DESC) Pageable pageable,
                         @RequestParam String query, Model model){
        model.addAttribute("page",blogService.listBlog(pageable,"%"+query+"%"));
        model.addAttribute("qy",query);
        return "search";
    }

    /**
     * 搜索翻页
     */
    @GetMapping("/search")
    public String searchPage(@PageableDefault(size = 6,sort = {"updateTime"},direction = Sort.Direction.DESC) Pageable pageable,
                             @RequestParam String query,
                              Model model){
        model.addAttribute("page",blogService.listBlog(pageable,"%"+query+"%"));
        model.addAttribute("qy",query);
        return "search";
    }

    /**
     * 博客详情
     * @param id 博客id
     * @param model model
     * @return 详情
     */
    @GetMapping("/blog/{id}")
    public String blog(@PathVariable Long id,Model model){
        Blog blog = blogService.getHtmlBlog(id);
        model.addAttribute("blog",blog);
        return "blog";
    }

    /**
     * 底部推荐
     */
    @GetMapping("/footer/newblog")
    public String newBlogs(Model model){
        List<Blog> blogs = blogService.listRecommendBlogTop(3);
        model.addAttribute("newblogs",blogs);
        return "blog_fragment :: newblogList";
    }

}
