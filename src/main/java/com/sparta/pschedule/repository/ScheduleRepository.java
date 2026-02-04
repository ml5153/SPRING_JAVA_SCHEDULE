package com.sparta.pschedule.repository;

import com.sparta.pschedule.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    // SELECT * FROM schedules WHERE author = ___ ORDER BY ModifiedAt DESC;
    List<Schedule> findAllByAuthorOrderByModifiedAtDesc(String author);

    // SELECT * FROM schedules ORDER BY ModifiedAt DESC;
    List<Schedule> findAllByOrderByModifiedAtDesc();


}
