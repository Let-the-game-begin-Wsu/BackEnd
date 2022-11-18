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
@Table(name = "chat_member")
public class ChatMemberEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chat_member_id", nullable = false, unique = true, updatable = false)
    private long chatMemberId;
    @ManyToOne
    @JoinColumn(name="chat_room_id", nullable = false)
    private ChatRoomEntity chatRoom;
    @ManyToOne
    @JoinColumn(name="user_id", nullable = false)
    private UserEntity user;
    @Column(name = "uptime", nullable = false)
    private Long uptime;

}
