package com.wsu.ltgb.persistence;

import com.wsu.ltgb.model.BoardTopicEntity;
import com.wsu.ltgb.model.ChatMemberEntity;
import com.wsu.ltgb.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface ChatMemberRepository extends JpaRepository<ChatMemberEntity, Long> {
    @Query(value = "SELECT * FROM chat_member WHERE chat_room_id = :roomId and user_id = :userId", nativeQuery = true)
    ChatMemberEntity isEntered(@Param("roomId") Long roomId, @Param("userId") Long userId);
}
