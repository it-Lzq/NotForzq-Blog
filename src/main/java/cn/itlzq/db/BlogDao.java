package cn.itlzq.db;

import cn.itlzq.bean.Blog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author 作者:李泽庆
 * @version 创建时间:2020/3/8 11:43
 * @email 邮箱:905866484@qq.com
 * @description 描述：
 */
public interface BlogDao extends JpaRepository<Blog,Long>, JpaSpecificationExecutor<Blog> {

    @Query("select b from Blog b where b.commentabled = true ")
    List<Blog> findTop(Pageable p);

    @Query("select b from Blog b where b.title like ?1 or b.content like ?1")
    Page<Blog> findByQuery(Pageable pageable,String query);

    @Query("select function('date_format',b.updateTime,'%Y') as year from Blog b group by function('date_format',b.updateTime,'%Y') order by year desc ")
    List<String> findGroupYear();

    @Query("select b from Blog b where function('date_format',b.updateTime,'%Y') = ?1")
    List<Blog> findByYear(String year);
}
