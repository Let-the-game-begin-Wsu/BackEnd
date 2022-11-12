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
@Table(name = "board_topic")
public class BoardTopicEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_topic_id", nullable = false, unique = true, updatable = false)
    private long boardTopicId;
    @Column(name = "title", nullable = false, unique = true)
    private String title;
    @Column(name = "description")
    private String description;
    @Column(name = "image")
    private String image;
    @Column(name = "uptime", nullable = false)
    private long uptime;
    @ManyToOne
    @JoinColumn(name="user_id")
    private UserEntity user;
}
