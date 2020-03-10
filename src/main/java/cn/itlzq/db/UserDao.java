package cn.itlzq.db;

import cn.itlzq.bean.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author 作者:李泽庆
 * @version 创建时间:2020/3/7 13:04
 * @email 邮箱:905866484@qq.com
 * @description 描述：
 */
public interface UserDao extends JpaRepository<User,Long> {


    User findByUsernameAndPassword(String username,String password);
}
