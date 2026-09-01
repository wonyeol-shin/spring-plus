package org.example.expert.domain.todo.repository;

import org.example.expert.domain.todo.entity.Todo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Optional;

public interface TodoRepository extends JpaRepository<Todo, Long> {

    @Query("SELECT t FROM Todo t LEFT JOIN FETCH t.user u ORDER BY t.modifiedAt DESC")
    Page<Todo> findAllByOrderByModifiedAtDesc(Pageable pageable);

    @Query("""
    SELECT t
    FROM Todo t
    LEFT JOIN FETCH t.user u
    WHERE t.weather = :weather
    ORDER BY t.modifiedAt DESC
""")
    Page<Todo> findAllByWeatherModifiedAtDesc(Pageable pageable, @Param("weather") String weather);

    @Query("""
    SELECT t
    FROM Todo t
    LEFT JOIN FETCH t.user u
    WHERE t.modifiedAt >= :startModifiedAt AND t.modifiedAt <= :endModifiedAt
    ORDER BY t.modifiedAt DESC
""")
    Page<Todo> findAllByModifiedAtModifiedAtDesc
            (Pageable pageable,
             @Param("startModifiedAt") LocalDateTime startModifiedAt,
             @Param("endModifiedAt") LocalDateTime endModifiedAt);

    @Query("""
    SELECT t
    FROM Todo t
    LEFT JOIN FETCH t.user u
    WHERE t.weather = :weather AND t.modifiedAt >= :startModifiedAt AND t.modifiedAt <= :endModifiedAt
    ORDER BY t.modifiedAt DESC
""")
    Page<Todo> findAllByWeatherAndModifiedAtModifiedAtDesc
            (Pageable pageable,
             @Param("weather") String weather,
             @Param("startModifiedAt") LocalDateTime startModifiedAt,
             @Param("endModifiedAt") LocalDateTime endModifiedAt);

    @Query("SELECT t FROM Todo t " +
            "LEFT JOIN t.user " +
            "WHERE t.id = :todoId")
    Optional<Todo> findByIdWithUser(@Param("todoId") Long todoId);
}
