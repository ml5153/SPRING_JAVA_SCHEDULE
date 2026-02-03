package com.sparta.pschedule.service;

import com.sparta.pschedule.dto.*;
import com.sparta.pschedule.entity.Schedule;
import com.sparta.pschedule.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

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
     * Q2) 함수명 명명규칙
     * =>
     * Controller 의 함수는 클라이언트에서 직접적으로 바라보니 직관적으로 지어야할 것같다.
     * Controller : get___(), post___(), update___(), delete___()
     * Service의 함수는 내부적으로 사용하니 비즈니스 중심으로 가독성을 생각해서 지어야할 것 같다.
     * Service : create(), findById(), findAll(), update(), delete()
     * =================================================================================================================
     * Q3) long 이 아닌 왜 Long 을 쓸까?
     * =>
     * long은 값이 없으면 0을 초기화하여 진짜 없는 값인지 구분하기 어려움
     * Long은 값이 없으면 null로 초기화하여 명확하게 판단 가능
     * =================================================================================================================
     * Q4) DTO에 @Getter가 없으면 어떻게 될까? (실제 코딩시 겪음...)
     *  =>
     *  런타임 에러는 나지 않지만, Jackson 라이브러리가 필드 값을 읽지 못해
     *  포스트맨 결과에 빈 객체 [{}] 만 나오게 됨.
     *  따라서 데이터를 주고받는 모든 DTO에는 @Getter를 반드시 붙여야 함!
     */

    private final ScheduleRepository repository;


    @Transactional
    public PostScheduleResponse create(PostScheduleRequest request) {
        Schedule schedule = new Schedule(
                request.getTitle(),
                request.getContents(),
                request.getAuthor(),
                request.getPassword()
        );
        Schedule response = repository.save(schedule);

        return new PostScheduleResponse(
                response.getId(),
                response.getTitle(),
                response.getContents(),
                response.getAuthor(),
                response.getCreatedAt(),
                response.getModifiedAt()
        );
    }

    @Transactional(readOnly = true)
    public GetScheduleResponse findById(Long id) {
        Schedule response = repository.findById(id).orElseThrow(
                () -> new IllegalStateException("잘못된 id입니다.")
        );
        return new GetScheduleResponse(
                response.getId(),
                response.getTitle(),
                response.getContents(),
                response.getAuthor(),
                response.getCreatedAt(),
                response.getModifiedAt()
        );
    }

    @Transactional(readOnly = true)
    public List<GetScheduleResponse> findAll() {
        List<Schedule> schedules = repository.findAll();
        List<GetScheduleResponse> dtos = new ArrayList<>();

        for (Schedule schedule : schedules) {
            GetScheduleResponse dto = new GetScheduleResponse(
                    schedule.getId(),
                    schedule.getTitle(),
                    schedule.getContents(),
                    schedule.getAuthor(),
                    schedule.getCreatedAt(),
                    schedule.getModifiedAt()
            );
            dtos.add(dto);
        }
        return dtos;
    }

    @Transactional
    public UpdateScheduleResponse update(Long id, UpdateScheduleRequest request) {
        // db에 접근했다.
        Schedule response = repository.findById(id).orElseThrow(
                () -> new IllegalStateException("잘못된 id입니다.")
        );
        // response : 영속상태 (Transactional 하고 db에 접근했으면 영속성인 상태)
        response.update(
                request.getTitle(),
                request.getContents(),
                request.getAuthor(),
                request.getPassword()
        );

        return new UpdateScheduleResponse(
                response.getId(),
                response.getTitle(),
                response.getContents(),
                response.getAuthor(),
                response.getCreatedAt(),
                response.getModifiedAt()
        );
    }

    @Transactional
    public void delete(Long id) {
        boolean isValid = repository.existsById(id);
        if (!isValid) {
            throw new IllegalStateException("잘못된 id입니다.");
        }
        repository.deleteById(id);
    }

}
