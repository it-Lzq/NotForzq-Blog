package cn.itlzq.controller;


import cn.itlzq.bean.Comment;
import cn.itlzq.bean.User;
import cn.itlzq.service.BlogService;
import cn.itlzq.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import java.util.Random;

/**
 * Created by limi on 2017/10/22.
 */
@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private BlogService blogService;

//    @Value("${comment.avatar}")
//    private String avatar;

    @GetMapping("/comments/{blogId}")
    public String comments(@PathVariable Long blogId, Model model) {
        model.addAttribute("comments", commentService.listCommentByBlogId(blogId));
        return "blog :: commentList";
    }


    @PostMapping("/comments")
    public String post(Comment comment, HttpSession session) {
        Long blogId = comment.getBlog().getId();

        comment.setBlog(blogService.getBlog(blogId));
        User user = (User) session.getAttribute("user");
        if (user != null) {
            comment.setAvatar(user.getAvatar());
            comment.setAdminComment(true);
        } else {
            Random r = new Random();
            int i = r.nextInt(1080);
            comment.setAvatar("https://i.picsum.photos/id/"+i+"/35/35.jpg");
        }
        commentService.saveComment(comment);
        return "redirect:/comments/" + blogId;
    }



}
