package com.homework18.service;

import com.homework18.exception.UserNotFoundException;
import com.homework18.model.Note;
import com.homework18.model.User;
import com.homework18.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    public List<Note> getAllUserNotesByUserId(UUID userId) {
        return userRepository.getReferenceById(userId).getCreatedNotes();
    }

    public User getUserById(UUID userId) {
        return userRepository.getReferenceById(userId);
    }

    public User getUserByUsername(String userName) throws UserNotFoundException {
//        return userRepository.findByUsername(userName).orElse(null);
        return userRepository.findByEmail(userName).orElseThrow(() -> new UserNotFoundException(userName));
    }

    public List<User> getUserList() {
        return userRepository.findAll();
    }
}
