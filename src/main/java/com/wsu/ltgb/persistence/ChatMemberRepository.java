package com.wsu.ltgb.persistence;

import com.wsu.ltgb.model.ChatMemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatMemberRepository extends JpaRepository<ChatMemberEntity, Long> {
}
