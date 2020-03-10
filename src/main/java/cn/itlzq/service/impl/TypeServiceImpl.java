package cn.itlzq.service.impl;


import cn.itlzq.bean.Type;
import cn.itlzq.db.TypeDao;
import cn.itlzq.exception.NotFoundBlogException;
import cn.itlzq.service.TypeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author 作者:李泽庆
 * @version 创建时间:2020/3/7 19:35
 * @email 邮箱:905866484@qq.com
 * @description 描述：
 */
@Service
public class TypeServiceImpl implements TypeService {

    @Autowired
    private TypeDao dao;

    @Override @Transactional
    public Type insertType(Type type) {
        return dao.save(type);

    }

    @Override
    public Type getType(Long id) {
        return dao.getOne(id);
    }

    @Override
    public Page<Type> listType(Pageable pageable) {
        return dao.findAll(pageable);
    }

    @Override @Transactional
    public Type updateType(Long id, Type type) {
        Type t = getType(id);
        if(t == null){
            throw new NotFoundBlogException("不存在该类型");
        }
        BeanUtils.copyProperties(type,t);
        return dao.save(t);
    }

    @Override @Transactional
    public void deleteType(Long id) {
        dao.deleteById(id);
    }

    @Override
    public Type getTypeByName(String name) {
        return dao.findByName(name);
    }

    @Override
    public List<Type> listType() {
        return dao.findAll();
    }

    @Override
    public List<Type> listType(Integer size) {
        Sort sort =  Sort.by(Sort.Direction.DESC,"blogs.size");
        Pageable p = PageRequest.of(0,size,sort);
        return dao.findTop(p);
    }
}
