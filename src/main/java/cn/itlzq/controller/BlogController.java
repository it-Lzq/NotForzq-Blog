package cn.itlzq.controller;

import cn.itlzq.bean.Blog;
import cn.itlzq.bean.BlogQuery;
import cn.itlzq.bean.User;
import cn.itlzq.service.BlogService;
import cn.itlzq.service.TagService;
import cn.itlzq.service.TypeService;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.beans.Transient;
import java.io.*;
import java.util.Date;
import java.util.List;

/**
 * @author 作者:李泽庆
 * @version 创建时间:2020/3/6 20:24
 * @email 邮箱:905866484@qq.com
 * @description 描述：
 */
@Controller
@RequestMapping("admin")
public class BlogController {

    @Autowired
    private BlogService service;
    @Autowired
    private TypeService typeService;
    @Autowired
    private TagService tagService;

    /**
     * 跳转到博客列表
     */
    @GetMapping("/blogs")
    public String blogManager(@PageableDefault(size = 5,sort = {"updateTime"},direction = Sort.Direction.DESC) Pageable pageable,
                              BlogQuery blog,
                              Model model){
        Page<Blog> blogs = service.listBlog(pageable, blog);
        model.addAttribute("types",typeService.listType());
        model.addAttribute("page",blogs);
        return "admin/blogManager";
    }

    /**
     * 搜索博客列表
     */
    @RequestMapping("/blogs/search")
    public String search(@PageableDefault(size = 5,sort = {"updateTime"},direction = Sort.Direction.DESC) Pageable pageable,
                         BlogQuery blog,
                         Model model){
        Page<Blog> blogs = service.listBlog(pageable, blog);
        model.addAttribute("page",blogs);
        return "admin/blogManager :: blogList";
    }

    /**
     * 博客发布页面
     * @return 发布
     */
    @GetMapping("/blogRelease")
    public String blogRelease(Model model){
        model.addAttribute("types",typeService.listType());
        model.addAttribute("tags",tagService.listTag());
        model.addAttribute("blog",new Blog());
        return "admin/blogRelease";
    }

    /**
     * 删除博客
     * @param id 博客id
     * @return 页面
     */
    @GetMapping("/blog/{id}/delete")
    public String deleteType(@PathVariable Long id, RedirectAttributes attributes){
        service.deleteBlog(id);
        attributes.addFlashAttribute("message","删除成功");
        return "redirect:/admin/blogs";
    }

    /**
     * 编辑博客
     */
    @GetMapping("/blog/{id}/input")
    public String editInput(@PathVariable Long id, Model model) {
        model.addAttribute("types", typeService.listType());
        model.addAttribute("tags", tagService.listTag());
        Blog blog = service.getBlog(id);
        blog.init();
        //System.out.println(blog);
        model.addAttribute("blog",blog);
        return "admin/blogRelease";
    }


    /**
     * 发布博客
     */
    @PostMapping("/blogs")
    public String pulish(Blog blog, HttpSession session, RedirectAttributes attributes){
        blog.setUser((User)session.getAttribute("user"));
       // blog.setType(typeService.getType(blog.getType().getId()));
       // blog.setTags(tagService.listTag(blog.getTagIds()));
        System.out.println("blog:"+blog);
        Blog b;
        if (blog.getId() == null) {
            blog.setTags(tagService.listTag(blog.getTagIds()));
            b =  service.saveBlog(blog);
        } else {
            blog.setTags(tagService.listTag(blog.getTagIds()));
            b = service.updateBlog(blog.getId(), blog);
        }
        if(b != null){
            attributes.addFlashAttribute("message","发布成功");
        }else{
            attributes.addFlashAttribute("message","发布失败");
        }
        return "redirect:blogs";
    }

    /**
     * 图片上传
     */
    @PostMapping("/fileup")
    public String fileup(@RequestParam("file") MultipartFile file){
            //获取文件名
        String filename = file.getOriginalFilename();

        //获取文件名后缀
        System.out.println(filename);
        File f = new File("");
        FileOutputStream fos = null;
        try {
            InputStream is = file.getInputStream();
            String path = f.getCanonicalPath();
            String filePath =  path + "/src/main/resources/static/img/"+filename;
            fos = new FileOutputStream(new File(filePath));
            byte[] bytes= new byte[1024];
            int i = is.read(bytes);
            while(i != -1){
                fos.write(bytes,0,i);
                fos.flush();
                i = is.read(bytes);
            }
            fos.close();is.close();
//            file.transferTo(new File(filePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "redirect:blogs";
    }

    /**
     * to图片上传页面
     */
    @RequestMapping("/toFileup")
    public String imgUp(){
        return "admin/file_up";
    }

}
