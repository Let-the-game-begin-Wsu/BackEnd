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
@Table(name = "chat_room")
public class ChatRoomEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chat_room_id", nullable = false, unique = true, updatable = false)
    private long chatRoomId;
    @Column(name = "title", nullable = false)
    private String title;
    @ManyToOne
    @JoinColumn(name="board_id", nullable = false)
    private BoardEntity board;
    @Column(name = "image")
    private String image;
    @Column(name = "comment")
    private String comment;
    @Column(name = "uptime", nullable = false)
    private Long uptime;

}
