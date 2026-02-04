package com.sparta.pschedule.repository;

import com.sparta.pschedule.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

     // SELECT COUNT(*) FROM comments WHERE schedule_id = ___
    long countByScheduleId(Long scheduleId);

     // SELECT * FROM comments WHERE schedule_id = ___
    List<Comment> findAllByScheduleId(Long scheduleId);
}
