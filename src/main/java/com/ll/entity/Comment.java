package com.ll.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Data
@Entity
@Table(name ="t_comment")
public class Comment {
    @Id
    @GeneratedValue
    private Long id;
    private String nickname;
    private String email;
    private String content;
    private String avatar;//头像
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;

    @ManyToOne
    private Blog blog;
    @OneToMany(mappedBy = "parentComments")
    private List<Comment> replyComments = new ArrayList<>();
    @ManyToOne
    private Comment parentComments;

    private boolean adminComment;

}