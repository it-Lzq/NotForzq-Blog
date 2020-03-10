package cn.itlzq.db;

import cn.itlzq.bean.Type;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import java.util.List;

/**
 * @author 作者:李泽庆
 * @version 创建时间:2020/3/7 19:35
 * @email 邮箱:905866484@qq.com
 * @description 描述：类别dao
 */

public interface TypeDao extends JpaRepository<Type,Long> {

    Type findByName(String name);

    @Query("select t from Type t")
    List<Type> findTop(Pageable pageable);
}
