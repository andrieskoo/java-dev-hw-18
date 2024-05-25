package com.homework18.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.UUID;

@Data
public class NoteRequest {
    private Long id;

    @NotBlank
    private String title;

    @NotBlank
    private String content;

    private UUID authorId;
}
