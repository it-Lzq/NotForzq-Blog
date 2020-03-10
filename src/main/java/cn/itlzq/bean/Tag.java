package cn.itlzq.bean;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 作者:李泽庆
 * @version 创建时间:2020/3/7 0:29
 * @email 邮箱:905866484@qq.com
 * @description 描述：标签
 */
@Data @Entity @Table(name="zq_tag")
public class Tag {

    @Id @GeneratedValue
    private Long id;
    @NotBlank(message = "标签名称不能为空")
    private String name;

    @ManyToMany(mappedBy = "tags")
    private List<Blog> blogs = new ArrayList<>();



}
