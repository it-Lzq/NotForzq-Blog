package cn.itlzq.bean;

import lombok.Data;
import org.hibernate.mapping.ToOne;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * @author 作者:李泽庆
 * @version 创建时间:2020/3/7 0:14
 * @email 邮箱:905866484@qq.com
 * @description 描述：博客类
 */
@Data
@Entity
@Table(name = "zq_blog")
public class Blog {
    //博客id
    @Id @GeneratedValue
    private Long id;
    //主图
    private String img;
    //博客标题
    private String title;
    //博客内容
    private String content;
    //博客标记
    private String flag;
    //浏览次数
    private Integer views;
    //赞赏是否开启
    private boolean appreciation;
    //是否可分享
    private boolean shareStatement;
    //是否评论
    private boolean commentabled;
    //是否推荐
    private boolean recommend;
    //创建时间
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;
    //更新时间
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime;

    @ManyToOne
    private Type type;//类别
    @ManyToMany(cascade = {CascadeType.PERSIST})
    private List<Tag> tags = new ArrayList<>();
    @ManyToOne
    private User user;
    @OneToMany(mappedBy = "blog")
    private List<Comment> comments = new ArrayList<>();

    @Transient
    private String tagIds;

    private String description;

    public void init() {
        this.tagIds = tagsToIds(this.getTags());
    }

    //1,2,3
    private String tagsToIds(List<Tag> tags) {
        if (!tags.isEmpty()) {
            StringBuffer ids = new StringBuffer();
            boolean flag = false;
            for (Tag tag : tags) {
                if (flag) {
                    ids.append(",");
                } else {
                    flag = true;
                }
                ids.append(tag.getId());
            }
            return ids.toString();
        } else {
            return tagIds;
        }
    }

}
