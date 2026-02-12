package com.sparta.pschedule.repository;

import com.sparta.pschedule.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

     // SELECT COUNT(*) FROM comments WHERE schedule_id = ___
    long countByScheduleId(Long scheduleId);

    // JOIN FETCH: Comment 객체를 가져올떄 User 객체를 한꺼번에 불러와준다.
    @Query("SELECT c " +
            "FROM Comment c " +
            "JOIN FETCH c.user " +
            "WHERE c.schedule.id = :scheduleId")
    List<Comment> findAllByScheduleIdWithUser(Long scheduleId);
}
