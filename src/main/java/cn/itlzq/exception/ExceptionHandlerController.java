package cn.itlzq.exception;

import cn.itlzq.exception.NotFoundBlogException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * @author 作者:李泽庆
 * @version 创建时间:2020/3/6 14:54
 * @email 邮箱:905866484@qq.com
 * @description 描述：
 */
@ControllerAdvice
public class ExceptionHandlerController {

    private Logger logger =  LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler(Exception.class)
    public ModelAndView errorHander(HttpServletRequest req,Exception e){
        //logger.error("\n请求路径：{},异常：{}",req.getRequestURL(),e);
        if(AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class) != null){
            throw  new NotFoundBlogException(e);
        }

        //传送错误参数到错误页面
        ModelAndView mv = new ModelAndView();
        mv.addObject("url",req.getRequestURL());
        mv.addObject("exception",e);
        mv.setViewName("error/error");

        return mv;
    }


}
