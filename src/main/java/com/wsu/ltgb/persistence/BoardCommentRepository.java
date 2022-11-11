package com.wsu.ltgb.persistence;

import com.wsu.ltgb.model.BoardCommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardCommentRepository extends JpaRepository<BoardCommentEntity,Long> {
}
