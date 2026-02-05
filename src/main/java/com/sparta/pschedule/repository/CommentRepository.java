package com.sparta.pschedule.repository;

import com.sparta.pschedule.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

// 쿼리가 길어지면... 이걸 함수규격을 어떻게 맞춰야하지? 더 복잡해지지않을까요? => 찾아보니 쿼리 DSL 있는것같다?
@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

     // SELECT COUNT(*) FROM comments WHERE schedule_id = ___
    long countByScheduleId(Long scheduleId);

     // SELECT * FROM comments WHERE schedule_id = ___
    List<Comment> findAllByScheduleId(Long scheduleId);
}
