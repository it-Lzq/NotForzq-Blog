package cn.itlzq.bean;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 作者:李泽庆
 * @version 创建时间:2020/3/7 0:23
 * @email 邮箱:905866484@qq.com
 * @description 描述：类别
 */
@Data @Entity @Table(name = "zq_type")
public class Type {

    //类别id
    @Id @GeneratedValue
    private Long id;
    //类名
    @NotBlank(message = "分类名称不能为空")
    private String name;

    @OneToMany(mappedBy = "type")
    private List<Blog> blogs = new ArrayList<>();


}
