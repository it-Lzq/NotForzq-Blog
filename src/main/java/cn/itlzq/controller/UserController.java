package cn.itlzq.controller;

import cn.itlzq.bean.User;
import cn.itlzq.service.UserService;
import cn.itlzq.util.MD5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

/**
 * @author 作者:李泽庆
 * @version 创建时间:2020/3/7 13:14
 * @email 邮箱:905866484@qq.com
 * @description 描述：
 */
@Controller
@RequestMapping("/admin")
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * 跳转登录页面
     * @return 登录页面
     */
    @GetMapping()
    public String toLogin(){
      return "admin/login";
    }

    /**
     * 登录验证
     * @param username 用户名
     * @param password 密码
     * @return 登录页 或者控制台主页
     */
    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        HttpSession session,
                        RedirectAttributes attributes){
        User user = userService.checkUser(username, password);
        System.out.println(user);
        if(user != null){
            user.setPassword(null);
            session.setAttribute("user",user);
            return "admin/index";
        }else{
            attributes.addFlashAttribute("message","用户名或密码错误");
            return "redirect:../admin";
        }
    }
    @GetMapping("/index")
    public String index(HttpSession session){
        return "admin/index";
    }

    @GetMapping("/exit")
    public String exit(HttpSession session){
        session.removeAttribute("user");
        return "redirect:../admin";

    }
}
