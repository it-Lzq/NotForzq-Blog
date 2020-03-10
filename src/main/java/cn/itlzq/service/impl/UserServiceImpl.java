package cn.itlzq.service.impl;

import cn.itlzq.bean.User;
import cn.itlzq.db.UserDao;
import cn.itlzq.service.UserService;
import cn.itlzq.util.MD5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 作者:李泽庆
 * @version 创建时间:2020/3/7 13:02
 * @email 邮箱:905866484@qq.com
 * @description 描述：
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao dao;

    /**
     * 检查用户登录验证
     * @param username 用户名
     * @param password 密码
     * @return 真实用户
     */
    @Override
    public User checkUser(String username, String password) {
        User user = dao.findByUsernameAndPassword(username, MD5.getPassword(password));
        return user;
    }
}
