package com.wsu.ltgb.persistence;

import com.wsu.ltgb.model.BoardCommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface BoardCommentRepository extends JpaRepository<BoardCommentEntity,Long> {
    @Query(value = "SELECT COUNT(*) FROM board_comment WHERE board_id=:boardId", nativeQuery = true)
    Long GetCommentCount(@Param("boardId")long boardId);

    @Query(value = "SELECT * FROM board_comment WHERE board_id =:boardId ORDER BY uptime DESC LIMIT :limit OFFSET :offset", nativeQuery = true)
    ArrayList<BoardCommentEntity> GetCommentList(@Param("boardId")long boardId, @Param("limit")int limit, @Param("offset")long offset);

}
