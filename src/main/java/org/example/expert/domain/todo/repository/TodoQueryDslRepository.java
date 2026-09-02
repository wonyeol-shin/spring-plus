package org.example.expert.domain.todo.repository;

import org.example.expert.domain.todo.dto.response.TodoInfoResponse;
import org.example.expert.domain.todo.entity.Todo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface TodoQueryDslRepository {

    Optional<Todo> findByIdWithUser(Long todoId);

    Page<TodoInfoResponse> findWithConditionCreatedAtAsc
            (Pageable pageable, String nickname, String title );
}
