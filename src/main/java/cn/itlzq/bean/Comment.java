package cn.itlzq.bean;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * @author 作者:李泽庆
 * @version 创建时间:2020/3/7 0:30
 * @email 邮箱:905866484@qq.com
 * @description 描述：评论类
 */
@Entity
@Table(name = "zq_comment")
@Data
public class Comment {

    @Id @GeneratedValue
    private Long id;
    //昵称
    private String nickname;
    //邮箱/手机
    private String email;

    private String content;
    //头像
    private String avatar;
    //更新时间
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;
    @ManyToOne
    private Blog blog;
    @OneToMany(mappedBy = "parentComment")
    private List<Comment> replyComments = new ArrayList<>();
    @ManyToOne
    private Comment parentComment;

    private boolean adminComment;



}
