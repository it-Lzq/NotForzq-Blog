package cn.itlzq.service;

import cn.itlzq.bean.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 作者:李泽庆
 * @version 创建时间:2020/3/7 19:32
 * @email 邮箱:905866484@qq.com
 * @description 描述：类别
 */

public interface TypeService {

    Type insertType(Type type);

    Type getType(Long id);

    Page<Type> listType(Pageable pageable);

    Type updateType(Long id,Type type);

    void deleteType(Long id);

    Type getTypeByName(String name);

    List<Type> listType();

    List<Type> listType(Integer size);
}
