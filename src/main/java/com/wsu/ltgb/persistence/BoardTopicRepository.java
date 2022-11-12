package com.wsu.ltgb.persistence;

import com.wsu.ltgb.model.BoardTopicEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface BoardTopicRepository extends JpaRepository<BoardTopicEntity, Long> {
    @Query(value = "SELECT COUNT(*) FROM board_topic", nativeQuery = true)
    Long GetTopicCount();
    @Query(value = "SELECT COUNT(*) FROM board_topic WHERE title = :title", nativeQuery = true)
    Long CheckTitle(@Param("title")String title);
    @Query(value = "SELECT * FROM board_topic ORDER BY uptime DESC LIMIT :limit OFFSET :offset", nativeQuery = true)
    ArrayList<BoardTopicEntity> GetTopicList(@Param("limit")int limit, @Param("offset")long offset);
}
