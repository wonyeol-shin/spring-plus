package org.example.expert.domain.todo.dto.response;

public record TodoInfoResponse
        (String title, int managerCount, int commentCount)
{
}
