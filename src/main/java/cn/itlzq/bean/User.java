package cn.itlzq.bean;



import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author 作者:李泽庆
 * @version 创建时间:2020/3/7 0:37
 * @email 邮箱:905866484@qq.com
 * @description 描述：用户
 */

@Entity @Data @Table(name="zq_user")
public class User {
    @Id @GeneratedValue
    private Long id;
    private String nickname;
    private String username;
    private String password;
    private String email;
    private String avatar;
    private Integer type;
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime;
    @OneToMany
    private List<Blog> blogs = new ArrayList<>();

    private String phone;
    private String qq;

}
