package org.example.expert.domain.todo.repository;

import lombok.RequiredArgsConstructor;
import org.example.expert.domain.todo.entity.Todo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class TodoFindCondition {

    private final TodoRepository todoRepository;


    public Page<Todo> findAllByOrderByModifiedAtDescWithCondition
            (Pageable pageable,
             String weather,
             LocalDateTime startModifiedAt,
             LocalDateTime endModifiedAt)
    {

        // 날씨와 날자 모두 없는 경우
        if (weather == null && startModifiedAt == null && endModifiedAt == null) {
              return todoRepository.findAllByOrderByModifiedAtDesc(pageable);
        }

        // 시작과 끝 날짜만 있는 경우
        if (weather == null && (startModifiedAt != null && endModifiedAt != null)) {
            // 시작일이 끝 날짜보다 앞서있는 경우
            if (startModifiedAt.isAfter(endModifiedAt)) {
                throw new IllegalArgumentException("startModifiedAt must be before endModifiedAt value");
            }

            return todoRepository.findAllByModifiedAtModifiedAtDesc
                    (pageable,startModifiedAt, endModifiedAt);
        }

        // 날씨 조건만 있는 경우
        if ( weather != null && (startModifiedAt == null && endModifiedAt == null) ) {
            return todoRepository.findAllByWeatherModifiedAtDesc(pageable,weather);
        }

        // 날씨와 날짜 모두 다 있는 경우
        if (weather != null && (startModifiedAt != null && endModifiedAt != null) ) {

            if (startModifiedAt.isAfter(endModifiedAt)) {
                throw new IllegalArgumentException("startModifiedAt must be before endModifiedAt value");
            }

            return todoRepository.findAllByWeatherAndModifiedAtModifiedAtDesc(
                    pageable,weather, startModifiedAt, endModifiedAt);
        }


        // 시작날짜와 끝 날짜 중 하나만 null 일 경우
        throw new IllegalArgumentException("startModifiedAt or endModifiedAt must not be null");


    }
}
