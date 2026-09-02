package org.example.expert.domain.todo.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.example.expert.domain.todo.dto.response.TodoInfoResponse;
import org.example.expert.domain.todo.entity.Todo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static org.example.expert.domain.comment.entity.QComment.comment;
import static org.example.expert.domain.manager.entity.QManager.manager;
import static org.example.expert.domain.todo.entity.QTodo.todo;
import static org.example.expert.domain.user.entity.QUser.user;

@Repository
public class TodoQueryDslRepositoryImpl implements TodoQueryDslRepository {

    private final JPAQueryFactory queryFactory;

    @Autowired
    public TodoQueryDslRepositoryImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    @Override
    public Optional<Todo> findByIdWithUser(Long todoId) {

        Todo result = queryFactory
                .selectFrom(todo)
                .leftJoin(todo.user, user).fetchJoin()
                .where(todo.id.eq(todoId))
                .fetchOne();

        return Optional.ofNullable(result);

    }

    private BooleanExpression nickNameContains(String nickname) {
        return (nickname !=null)
                ? user.nickname.contains(nickname)
                : null;
    }

    private BooleanExpression titleContains(String title) {
        return (title != null)
                ? todo.title.contains(title)
                : null;
    }

    @Override
    public Page<TodoInfoResponse> findWithConditionCreatedAtAsc
            (Pageable pageable, String nickname, String title)
    {

        List<TodoInfoResponse> todoResult = queryFactory
                .select(Projections.constructor(
                        TodoInfoResponse.class,
                        todo.title,
                        manager.countDistinct().intValue().as("managerCount"), // join 행 중복을 막으려고 사용
                        comment.countDistinct().intValue().as("commentCount") // join 행 중복을 막으려고 사용
                ))
                .from(todo)
                .leftJoin(manager).on(todo.id.eq(manager.todo.id)) // todo 의 관리자 개수를 구하기 위해 조인
                .leftJoin(comment).on(todo.id.eq(comment.todo.id)) // todo에 달린 댓글 갯수를 구하기 위해 조인
                .leftJoin(user).on(todo.user.id.eq(user.id)) // nickname 검색을 위한 조인
                .where(titleContains(title))
                .where(nickNameContains(nickname))
                .groupBy(todo.id)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = queryFactory
                .select(todo.count()) // 중복이 없으니 성능 좋은 count(*) 사용
                .from(todo)
                .leftJoin(user).on(todo.user.id.eq(user.id)) // nickname 검색을 위한 조인
                .where(titleContains(title))
                .where(nickNameContains(nickname));

        return PageableExecutionUtils.getPage(todoResult, pageable,countQuery::fetchOne);

    }
}
