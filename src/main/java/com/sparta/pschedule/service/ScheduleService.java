package com.sparta.pschedule.service;

import com.sparta.pschedule.dto.comment.CommentGetResponse;
import com.sparta.pschedule.dto.schedule.*;
import com.sparta.pschedule.entity.Schedule;
import com.sparta.pschedule.entity.User;
import com.sparta.pschedule.exception.CommonError;
import com.sparta.pschedule.exception.CommonException;
import com.sparta.pschedule.repository.ScheduleRepository;
import com.sparta.pschedule.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    /**
     * =================================================================================================================
     * Q1) repository 가 static이 아닌데도 인스턴스 초기화 없이 바로 접근할 수 있는 이유? (어떻게 new없이 바로 사용 가능?? )
     * =>
     * 스프링이 서버 부트온하면 @Service, @Repository가 붙은 클래스를 찾아서 직접 싱글톤 객체를 만들어서 전역으로 공유하면서 사용하게끔 함
     * 내가 클래스에서 변수를 선언만 해두면, 스프링이 미리 만들어둔 이 '싱글톤 객체'를 찾아서 자동으로 연결(주입)해 줍니다.
     * 즉, 내가 'new'를 해서 직접 조립할 필요 없이, 스프링이 이미 완성된 부품을 내 손에 쥐여주기 때문에 바로 사용할 수 있는 것입니다.
     * 이 과정을 '제어의 역전(IoC)'과 '의존성 주입(DI)'이라고 부릅니다.
     * =================================================================================================================
     * =================================================================================================================
     * Q2) long 이 아닌 왜 Long 을 쓸까?
     * =>
     * long은 값이 없으면 0을 초기화하여 진짜 없는 값인지 구분하기 어려움
     * Long은 값이 없으면 null로 초기화하여 명확하게 판단 가능
     * =================================================================================================================
     * Q3) DTO에 @Getter가 없으면 어떻게 될까? (실제 코딩시 겪음...)
     * =>
     * 런타임 에러는 나지 않지만, Jackson 라이브러리가 필드 값을 읽지 못해
     * 포스트맨 결과에 빈 객체 [{}] 만 나오게 됨.
     * 따라서 데이터를 주고받는 모든 DTO에는 @Getter를 반드시 붙여야 함!
     * =================================================================================================================
     */

    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;
    private final CommentService commentService;

    @Transactional
    public ScheduleCreateResponse createSchedule(ScheduleCreateRequest request, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new CommonException(CommonError.NOT_FOUND_USER)
        );

        Schedule newSchedule = new Schedule(
                request.getTitle(),
                request.getContents(),
                request.getAuthor(),
                user
        );
        Schedule schedule = scheduleRepository.save(newSchedule);
        return ScheduleCreateResponse.from(schedule);
    }

    @Transactional(readOnly = true)
    public ScheduleGetResponse findById(Long id) {
        Schedule schedule = scheduleRepository.findById(id).orElseThrow(
                () -> new CommonException(CommonError.NOT_FOUND_SCHEDULE)
        );

        List<CommentGetResponse> comments = commentService.findAll(id);
        return ScheduleGetResponse.from(schedule, comments);
    }

    @Transactional(readOnly = true)
    public List<ScheduleGetResponse> findAll(String author) {
        List<Schedule> schedules = (author != null)
                ? scheduleRepository.findAllByAuthorOrderByModifiedAtDesc(author)
                : scheduleRepository.findAllByOrderByModifiedAtDesc();
        return schedules.stream()
                .map(schedule -> {
                    List<CommentGetResponse> comments = commentService.findAll(schedule.getId());
                    return ScheduleGetResponse.from(schedule, comments);
                }).collect(Collectors.toList());
        // .toCollect() <Legacy>: java 16미만
        // .toList() <Latest>: java 16이상
    }

    @Transactional
    public ScheduleUpdateResponse update(Long id, ScheduleUpdateRequest request, Long loginUserId) {

        Schedule schedule = scheduleRepository.findById(id).orElseThrow(
                () -> new CommonException(CommonError.NOT_UPDATE_SCHEDULE)
        );
        if (!Objects.equals(schedule.getUser().getId(), loginUserId)) {
            throw new CommonException(CommonError.UNAUTHORIZED_ACCESS);
        }

        schedule.update(
                request.getTitle(),
                request.getAuthor()
        );
        return new ScheduleUpdateResponse(
                schedule.getId(),
                schedule.getTitle(),
                schedule.getContents(),
                schedule.getAuthor(),
                schedule.getCreatedAt(),
                schedule.getModifiedAt()
        );
    }

    @Transactional
    public void delete(Long id, ScheduleDeleteRequest request, Long loginUserId) {
        Schedule schedule = scheduleRepository.findById(id).orElseThrow(
                () -> new CommonException(CommonError.NOT_DELETE_SCHEDULE)
        );
        if (!Objects.equals(schedule.getUser().getId(), loginUserId)) {
            throw new CommonException(CommonError.UNAUTHORIZED_ACCESS);
        }

        scheduleRepository.delete(schedule);
    }

}
