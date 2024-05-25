package com.homework18.dto.responce;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class NoteResponse {
    private Long id;

    private String title;

    private String content;

    private UUID authorId;

    private LocalDateTime createdAt;

}
