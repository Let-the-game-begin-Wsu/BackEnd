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
    @Column(name = "board_comment_id", nullable = false, unique = true, updatable = false)
    private long boardCommentId;
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
