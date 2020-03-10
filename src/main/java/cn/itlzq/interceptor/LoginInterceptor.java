package cn.itlzq.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author 作者:李泽庆
 * @version 创建时间:2020/3/7 15:56
 * @email 邮箱:905866484@qq.com
 * @description 描述：
 */


public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        HttpSession session = request.getSession();
        if(session.getAttribute("user") == null){
            response.sendRedirect("/admin");
        }
        return true;
    }
}
