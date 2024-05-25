package com.homework18.web.v1;


import com.homework18.dto.request.NoteRequest;
import com.homework18.dto.responce.NoteResponse;
import com.homework18.exception.UserNotFoundException;
import com.homework18.mapper.NoteMapper;
import com.homework18.model.Note;
import com.homework18.service.NoteService;
import com.homework18.service.UserService;
import jakarta.annotation.Nonnull;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;


@Slf4j
@Validated
@RestController
@RequestMapping("/api/v1/notes")
public class NoteController {

    @Autowired
    private NoteService noteService;

    @Autowired
    private UserService userService;
    @Autowired
    private NoteMapper noteMapper;


    @PostMapping()
    public ResponseEntity<NoteResponse> createNote(@Valid @Nonnull @RequestBody NoteRequest createNoteRequest, Principal principal) throws UserNotFoundException {
        return noteService.add(principal.getName(), createNoteRequest);
    }

    @GetMapping()
    public ResponseEntity<?> getNote(@Validated @RequestParam(name = "id", required = true) @Nonnull Long noteId, Principal principal) throws UserNotFoundException {
        return noteService.readNote(principal.getName(), noteId);
    }

    @PatchMapping()
    public ResponseEntity<NoteResponse> editNote(@Validated @RequestBody NoteRequest updateNoteRequest, Principal principal) throws UserNotFoundException {
       return noteService.update(principal.getName(), updateNoteRequest);
    }

    @DeleteMapping
    public ResponseEntity<NoteResponse> deleteNote(@Validated @RequestParam(name = "id", required = true) @Nonnull Long noteId, Principal principal) throws UserNotFoundException {
        return noteService.deleteById(principal.getName(), noteId);
    }

    @GetMapping("/list")
    public List<Note> getNoteList() {
        return noteService.listAll();
    }

}
