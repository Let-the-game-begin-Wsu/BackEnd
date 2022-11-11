package com.wsu.ltgb.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class CommentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_comment_id")
    private long board_comment_id;
    @Column(name = "user_id")
    private long user_id;
    @Column(name = "uptime")
    private long uptime;
    @Column(name = "board_id")
    private long board_id;
    @Column(name = "content", nullable = false)
    private String content;
}
