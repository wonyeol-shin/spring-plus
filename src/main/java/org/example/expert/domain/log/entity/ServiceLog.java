package org.example.expert.domain.log.entity;

import jakarta.persistence.*;
import lombok.Getter;
import org.example.expert.domain.log.enums.LogType;
import org.example.expert.domain.log.enums.Result;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "service_logs")
public class ServiceLog {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private LogType logType;

    @Enumerated(EnumType.STRING)
    private Result result;

    private String comment;

    private LocalDateTime createAt;

    private ServiceLog(LogType logType, Result result, String comment) {
        this.logType = logType;
        this.result = result;
        this.comment = comment;
        this.createAt = LocalDateTime.now();
    }

    public static ServiceLog of(LogType logType, Result result, String comment) {
        return new ServiceLog(logType, result, comment);
    }

}
