package cn.itlzq.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author 作者:李泽庆
 * @version 创建时间:2020/3/6 15:36
 * @email 邮箱:905866484@qq.com
 * @description 描述：没有发现Blog
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundBlogException extends RuntimeException {
    public NotFoundBlogException() {

    }

    public NotFoundBlogException(Throwable cause) {
        super(cause);
    }

    public NotFoundBlogException(String message) {
        super(message);
    }

    public NotFoundBlogException(String message, Throwable cause) {
        super(message, cause);
    }
}
