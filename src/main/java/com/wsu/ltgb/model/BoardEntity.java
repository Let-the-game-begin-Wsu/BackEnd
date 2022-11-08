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
@Table(name = "board")
public class BoardEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long board_id;
    @Column(name = "title", nullable = false)
    private String title;
    @ManyToOne
    @JoinColumn(name="user_id")
    private UserEntity user;
    @Column(name = "uptime", nullable = false)
    private long uptime;
    @Column(name = "content", nullable = false)
    private String content;
}
