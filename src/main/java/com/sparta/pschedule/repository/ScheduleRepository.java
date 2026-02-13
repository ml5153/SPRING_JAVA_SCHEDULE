package com.sparta.pschedule.repository;

import com.sparta.pschedule.dto.schedule.SchedulePageResponse;
import com.sparta.pschedule.entity.Schedule;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    // 아래쿼리는 Schedule이 Comment를 리스트로 들고있어야하는데 엔티티에 List를 갖고있는게 이해가 되질 않는다...(원자성해침)
    // 그리고 양방향 매핑은 쓰지말라고 배웠는데 OnToMany를 하게되면 DB설계상 문제되는거 아닌가 의문이 든다..
//    @Query("SELECT new com.sparta.pschedule.dto.schedule.SchedulePageResponse(" +
//            "s.id, s.title, s.contents, u.name, COUNT(c), s.createdAt, s.modifiedAt) " +
//            "FROM Schedule s " +
//            "JOIN s.user u " +
//            "LEFT JOIN s.comments c " +
//            "GROUP BY s.id")


    // Q1) JOIN 조건에 id끼리 비교가아닌 왜 객체끼리 비교할까?
    // => 내부적으로 id값을 비교하도록 동작

    // Q2) 왜 JOIN 뒤엔 ON이 없는데 LEFT JOIN 뒤엔 있을까?
    // JOIN s.user u:           Schedule 엔티티 안에 User가 이미 연관관계로 필드에 있어 ON 조건을 자동으로 생성해줌.
    // LEFT JOIN Comment c:     Schedule 안에 Comment가 없어 JPA는 둘의 관계를 모르기 때문에 ON 조건이 필요
    @Query("SELECT new com.sparta.pschedule.dto.schedule.SchedulePageResponse(" +
            "s.id, s.title, s.contents, u.name, COUNT(c), s.createdAt, s.modifiedAt) " +
            "FROM Schedule s " +
            "JOIN s.user u " +
            "LEFT JOIN Comment c ON c.schedule = s " +
            "GROUP BY s.id")
    Page<SchedulePageResponse> findAllWithCommentCount(Pageable pageable);


    @Query("SELECT s FROM Schedule s JOIN FETCH s.user WHERE s.id = :id")
    Optional<Schedule> findByIdWithUser(Long id);
}
