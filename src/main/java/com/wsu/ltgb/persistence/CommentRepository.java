package com.wsu.ltgb.persistence;

import com.wsu.ltgb.model.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
    @Query(value = "SELECT * FROM board_comment WHERE board_comment_id = :board_comment_id", nativeQuery = true)
    CommentEntity board_comment_id(@Param("board_comment_id")Long board_comment_id);
}
