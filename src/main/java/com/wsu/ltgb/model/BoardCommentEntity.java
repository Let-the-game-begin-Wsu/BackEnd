package com.wsu.ltgb.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "board_comment")
public class BoardCommentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long board_comment_id;
    @ManyToOne
    @JoinColumn(name="user_id")
    private UserEntity user;
    @ManyToOne
    @JoinColumn(name="board_id")
    private BoardEntity board;
    @Column(name = "uptime", nullable = false)
    private long uptime;
    @Column(name = "content", nullable = false)
    private String content;
}
