package cn.itlzq.bean;

import lombok.Data;

import javax.persistence.Column;

/**
 * @author 作者:李泽庆
 * @version 创建时间:2020/3/8 13:52
 * @email 邮箱:905866484@qq.com
 * @description 描述：查询博客对象
 */
@Data
public class BlogQuery {

    private String title;
    @Column
    private Long typeId;
    private boolean recommend;
}
