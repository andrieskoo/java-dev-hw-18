package com.homework18;
import com.homework18.dto.request.NoteRequest;
import com.homework18.dto.responce.MessageResponse;
import com.homework18.dto.responce.NoteResponse;
import com.homework18.exception.UserNotFoundException;
import com.homework18.mapper.NoteMapper;
import com.homework18.model.Note;
import com.homework18.model.User;
import com.homework18.repository.NoteRepository;
import com.homework18.service.NoteService;
import com.homework18.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@SpringBootTest
public class NoteServiceTest {

    @Mock
    private NoteRepository noteRepository;

    @Mock
    private UserService userService;

    @Mock
    private NoteMapper noteMapper;

    @InjectMocks
    private NoteService noteService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddNote_Success() throws UserNotFoundException {
        String username = "testuser";
        NoteRequest noteRequest = new NoteRequest();
        noteRequest.setTitle("Test Title");
        noteRequest.setContent("Test Content");
        User user = new User();
        user.setId(UUID.randomUUID());

        when(userService.getUserByUsername(anyString())).thenReturn(user);
        when(noteMapper.noteRequestToNoteCreate(any(NoteRequest.class))).thenReturn(new Note());
        when(noteRepository.save(any(Note.class))).thenReturn(new Note());
        when(noteMapper.noteToNoteResponseDto(any(Note.class))).thenReturn(new NoteResponse());

        ResponseEntity<NoteResponse> response = noteService.add(username, noteRequest);

        assertEquals(200, response.getStatusCodeValue());
        verify(userService, times(1)).getUserByUsername(anyString());
        verify(noteRepository, times(1)).save(any(Note.class));
        verify(noteMapper, times(1)).noteToNoteResponseDto(any(Note.class));
    }

    @Test
    public void testDeleteNote_Success() throws UserNotFoundException {
        String username = "testuser";
        Long noteId = 1L;
        User user = new User();
        user.setId(UUID.randomUUID());
        Note note = new Note();
        note.setAuthorId(user);

        when(userService.getUserByUsername(anyString())).thenReturn(user);
        when(noteRepository.findById(anyLong())).thenReturn(Optional.of(note));

        ResponseEntity<NoteResponse> response = noteService.deleteById(username, noteId);

        assertEquals(200, response.getStatusCodeValue());
        verify(noteRepository, times(1)).delete(any(Note.class));
    }

    @Test
    public void testUpdateNote_Success() throws UserNotFoundException {
        String username = "testuser";
        NoteRequest noteRequest = new NoteRequest();
        noteRequest.setId(1L);
        noteRequest.setTitle("Updated Title");
        noteRequest.setContent("Updated Content");
        User user = new User();
        user.setId(UUID.randomUUID());
        Note note = new Note();
        note.setAuthorId(user);

        when(userService.getUserByUsername(anyString())).thenReturn(user);
        when(noteRepository.findById(anyLong())).thenReturn(Optional.of(note));
        when(noteRepository.save(any(Note.class))).thenReturn(note);
        when(noteMapper.noteToNoteResponseDto(any(Note.class))).thenReturn(new NoteResponse());

        ResponseEntity<NoteResponse> response = noteService.update(username, noteRequest);

        assertEquals(200, response.getStatusCodeValue());
        verify(noteRepository, times(1)).save(any(Note.class));
        verify(noteMapper, times(1)).noteToNoteResponseDto(any(Note.class));
    }

    @Test
    public void testReadNote_Success() throws UserNotFoundException {
        String username = "testuser";
        Long noteId = 1L;
        User user = new User();
        user.setId(UUID.randomUUID());
        Note note = new Note();
        note.setAuthorId(user);

        when(userService.getUserByUsername(anyString())).thenReturn(user);
        when(noteRepository.findById(anyLong())).thenReturn(Optional.of(note));
        when(noteMapper.noteToNoteResponseDto(any(Note.class))).thenReturn(new NoteResponse());

        ResponseEntity<?> response = noteService.readNote(username, noteId);

        assertEquals(200, response.getStatusCodeValue());
        verify(noteMapper, times(1)).noteToNoteResponseDto(any(Note.class));
    }
}
