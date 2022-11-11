package com.wsu.ltgb.persistence;

import com.wsu.ltgb.model.BoardEntity;
import com.wsu.ltgb.model.BoardTopicEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface BoardTopicRepository extends JpaRepository<BoardTopicEntity, Long> {
    @Query(value = "SELECT * FROM board ORDER BY uptime DESC LIMIT :limit OFFSET :offset", nativeQuery = true)
    ArrayList<BoardEntity> GetTopicList(@Param("limit")int limit, @Param("offset")long offset);
}
