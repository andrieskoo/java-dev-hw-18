package com.homework18.mapper;

import com.homework18.dto.request.NoteRequest;
import com.homework18.dto.responce.NoteResponse;
import com.homework18.model.Note;
import com.homework18.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NoteMapper {
    @Autowired
    private UserService userService;
    public Note noteRequestToNoteCreate(NoteRequest dto) {
        Note entity = new Note();
        entity.setTitle(dto.getTitle());
        entity.setContent(dto.getContent());
        entity.setAuthorId(userService.getUserById(dto.getAuthorId()));
        return entity;
    }

    public Note noteRequestToNoteUpdate(NoteRequest dto) {
        Note entity = new Note();
        entity.setId(dto.getId());
        entity.setTitle(dto.getTitle());
        entity.setContent(dto.getContent());
        entity.setAuthorId(userService.getUserById(dto.getAuthorId()));
        return entity;
    }

    public NoteResponse noteToNoteResponseDto(Note note) {
        NoteResponse entityDto = new NoteResponse();
        entityDto.setId(note.getId());
        entityDto.setTitle(note.getTitle());
        entityDto.setContent(note.getContent());
        entityDto.setAuthorId(note.getAuthorId().getId());
        entityDto.setCreatedAt(note.getCreatedAt());
        return entityDto;
    }
}
