package com.wsu.ltgb.persistence;

import com.wsu.ltgb.model.BoardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepository extends JpaRepository<BoardEntity, Long> {
}
