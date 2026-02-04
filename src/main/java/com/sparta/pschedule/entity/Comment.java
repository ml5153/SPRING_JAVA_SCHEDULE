package com.sparta.pschedule.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "comments")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // FK : 일정 릴레이션의 기본id 값을 참조함
    private Long scheduleId;

    @Column(length = 300)
    private String contents;

    @Column(nullable = false)
    private String author;

    @Column(nullable = false)
    private String password;

    public Comment(Long scheduleId, String contents, String author, String password) {
        this.contents = contents;
        this.author = author;
        this.password = password;
    }

    public void update(String contents, String author) {
        this.contents = contents;
        this.author = author;
    }
}
