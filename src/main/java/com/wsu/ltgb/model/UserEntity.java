package com.wsu.ltgb.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "user")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false, unique = true, updatable = false)
    private long userId;
    @Column(name = "id", nullable = false, unique = true, updatable = false)
    private String id;
    @Column(name = "password", nullable = false)
    private String password;
    @Column(name = "nickname", nullable = false, unique = true)
    private String nickname;
    @Column(name = "phone", nullable = false)
    private String phone;
    @Column(name = "image")
    private String image;
    @Column(name = "uptime")
    private long uptime;
}
