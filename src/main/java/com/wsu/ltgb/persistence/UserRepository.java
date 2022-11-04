package com.wsu.ltgb.persistence;

import com.wsu.ltgb.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    @Query(value = "SELECT * FROM user WHERE id = :id AND password = :pw", nativeQuery = true)
    UserEntity login(@Param("id") String id, @Param("pw") String pw);
}
