package cn.itlzq.controller;

import cn.itlzq.bean.Blog;
import cn.itlzq.bean.BlogQuery;
import cn.itlzq.bean.Tag;
import cn.itlzq.bean.Type;
import cn.itlzq.service.BlogService;
import cn.itlzq.service.TagService;
import cn.itlzq.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Map;

/**
 * @author 作者:李泽庆
 * @version 创建时间:2020/3/7 13:10
 * @email 邮箱:905866484@qq.com
 * @description 描述：转发请求的路径
 */
@Controller
public class ReportController {
    @Autowired
    private TypeService typeService;
    @Autowired
    private BlogService blogService;
    @Autowired
    private TagService tagService;

    /**
     * 分类展示
     * @return 分类
     */
    @GetMapping("/type/{id}")
    public String blogClass(@PageableDefault(size = 8,sort = {"updateTime"},direction = Sort.Direction.DESC)Pageable pageable,
                            @PathVariable Long id,
                            Model model){
        List<Type> types = typeService.listType(30);
        if(id == -1){
            id = types.get(0).getId();
        }
        BlogQuery bq = new BlogQuery();bq.setTypeId(id);
        Page<Blog> page = blogService.listBlog(pageable, bq);
        model.addAttribute("page",page);
        model.addAttribute("types",types);
        model.addAttribute("activeId",id);
        return "blogClass";
    }



    /**
     * 标签展示
     * @return 标签
     */
    @GetMapping("/tag/{id}")
    public String blogTag(@PageableDefault(size = 8 ,sort = {"updateTime"},direction = Sort.Direction.DESC)Pageable pageable,
                          @PathVariable Long id,
                          Model model){
        List<Tag> tags = tagService.listTag(100);
        if(id == -1){
            id = tags.get(0).getId();
        }
        Page<Blog> blogs = blogService.listBlog(id, pageable);
        model.addAttribute("tags",tags);
        model.addAttribute("page",blogs);
        model.addAttribute("activeId",id);
        return "blogTag";
    }



    /**
     * 归档展示
     * @return 归档
     */
    @GetMapping("/home")
    public String blogHome(Model model){
        Map<String, List<Blog>> yearBlogMap = blogService.yearBlogMap();
        Long blogCount = blogService.countBlog();
        model.addAttribute("yearBlogMap",yearBlogMap);
        model.addAttribute("blogCount",blogCount);
        return "blogHome";
    }



    /**
     * 关于我展示
     * @return 关于我
     */
    @GetMapping("/me")
    public String blogMy(){
        return "blogMy";
    }





}
