package com.wsu.ltgb.persistence;

import com.wsu.ltgb.model.BoardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface BoardRepository extends JpaRepository<BoardEntity, Long> {
    @Query(value = "SELECT COUNT(*) FROM board WHERE board_topic_id = :topicId", nativeQuery = true)
    Long GetBoardCount(@Param("topicId")long topicId);

    @Query(value = "SELECT * FROM board WHERE board_topic_id = :topicId ORDER BY uptime DESC LIMIT :limit OFFSET :offset", nativeQuery = true)
    ArrayList<BoardEntity> GetBoardList(@Param("topicId")long topicId, @Param("limit")int limit, @Param("offset")long offset);
}
