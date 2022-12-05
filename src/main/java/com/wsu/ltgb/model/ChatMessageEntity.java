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
@Table(name = "chat_message")
public class ChatMessageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chat_message_id", nullable = false, unique = true, updatable = false)
    private long chatMessageId;
    @Column(name = "content", nullable = false)
    private String content;
    @Column(name = "uptime", nullable = false)
    private Long uptime;
    @ManyToOne
    @JoinColumn(name="user_id")
    private UserEntity user;
    @ManyToOne
    @JoinColumn(name="chat_room_id", nullable = false)
    private ChatRoomEntity chatRoom;
    @Column(name = "type_code", nullable = false)
    private Integer typeCode;
}
