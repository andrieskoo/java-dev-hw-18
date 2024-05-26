package com.homework18.service;

import com.homework18.dto.request.NoteRequest;
import com.homework18.dto.responce.MessageResponse;
import com.homework18.dto.responce.NoteResponse;
import com.homework18.exception.UserNotFoundException;
import com.homework18.mapper.NoteMapper;
import com.homework18.model.Note;
import com.homework18.model.User;
import com.homework18.repository.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

import static org.springframework.web.servlet.function.ServerResponse.badRequest;


@Service
public class NoteService {

    @Autowired
    private NoteRepository noteRepository;
    @Autowired
    private UserService userService;

    @Autowired
    private NoteMapper noteMapper;


    public List<Note> listAll() {
        return noteRepository.findAll();
    }

    public ResponseEntity<NoteResponse> add(String username, NoteRequest createNoteRequest) throws UserNotFoundException {
        User author = userService.getUserByUsername(username);
        createNoteRequest.setAuthorId(author.getId());
        Note note = noteMapper.noteRequestToNoteCreate(createNoteRequest);
        Note createdNote = noteRepository.save(note);
        if (Objects.nonNull(createdNote)) {
            NoteResponse responseDto = noteMapper.noteToNoteResponseDto(createdNote);
            return ResponseEntity.ok(responseDto);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    public ResponseEntity<NoteResponse> deleteById(String username, Long id) throws UserNotFoundException {
        if (isUserAuthor(username, id)) {
            Note del = getById(id);
            if (del != null) {
                noteRepository.delete(del);
                return ResponseEntity.ok().build();
            }
            return ResponseEntity.notFound().eTag("User is not author of note.").build();
        }
        return ResponseEntity.badRequest().eTag("User is not author of note.").build();

    }

    public ResponseEntity<NoteResponse> update(String username, NoteRequest updateNoteRequest) throws UserNotFoundException {

        Note note = getById(updateNoteRequest.getId());

        if (isUserAuthor(username, updateNoteRequest.getId())) {

            if (!updateNoteRequest.getTitle().isEmpty()){
                note.setTitle(updateNoteRequest.getTitle());
            }
            if (!updateNoteRequest.getContent().isEmpty()){
                note.setContent(updateNoteRequest.getContent());
            }
            NoteResponse result = noteMapper.noteToNoteResponseDto(noteRepository.save(note));
            return ResponseEntity.ok(result);
        }
        else {
            return ResponseEntity.notFound().eTag("User is not author of note").build();
        }
    }

    public ResponseEntity<?> readNote(String username, long noteId) throws UserNotFoundException {
        if (isUserAuthor(username, noteId)){
            Note note = getById(noteId);
            if (note != null) {
                return ResponseEntity.ok(noteMapper.noteToNoteResponseDto(note));
            }
            else
            {
                return ResponseEntity.badRequest()
                        .body(new MessageResponse("Error: Node with id not exist"));
            }
        }
        return ResponseEntity.badRequest().build();
    }

    private boolean isUserAuthor(String username, long id) throws UserNotFoundException {
        User user = userService.getUserByUsername(username);
        Note note = getById(id);
        return user.getId().equals(note.getAuthorId().getId());
    }

    private Note getById(long id){
        return noteRepository.findById(id).orElse(null);
    }
}
