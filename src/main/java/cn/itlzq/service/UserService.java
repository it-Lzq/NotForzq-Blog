package cn.itlzq.service;

import cn.itlzq.bean.User;

/**
 * @author 作者:李泽庆
 * @version 创建时间:2020/3/7 13:00
 * @email 邮箱:905866484@qq.com
 * @description 描述：用户Service
 */
public interface UserService {

    /**
     * 检查用户登录验证
     * @param username 用户名
     * @param password 密码
     * @return 真实用户
     */
    User checkUser(String username, String password);
}
