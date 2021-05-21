package com.example.demo.service;

import com.example.demo.entity.User;
import com.example.demo.payload.ReqUser;
import com.example.demo.payload.ResUser;
import com.example.demo.repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserServiceInterface {
    @Autowired
    UserRepository userRepository;


    @Override
    public ResUser createUser(ReqUser reqUser) {
        User savedUser = userRepository.save(new User(reqUser.getUsername(), reqUser.getPassword()));
        return new ResUser(savedUser.getId(), savedUser.getUserName());
    }

    @Override
    public List<ResUser> getAllUsers() {
        List<ResUser> resUsers = new ArrayList<>();
        userRepository.findAll().forEach(user -> {
            resUsers.add(getResUser(user));
        });
        return resUsers;
        //return userRepository.findAll().stream().map(this::getResUser).collect(Collectors.toList());
    }

    //1ta userni olish
    @Override
    public HttpEntity<?> getUser(Integer id) {
        Optional<User> byId = userRepository.findById(id);
        if (byId.isPresent()) {
            return ResponseEntity.ok().body(getResUser(byId.get()));
        } else {
            return ResponseEntity.status(400).body("id not found");
        }
    }

    @Override
    public HttpEntity<?> editUser(ReqUser reqUser) {
        Optional<User> byId = userRepository.findById(reqUser.getId());
        if (byId.isPresent()) {
            User user = byId.get();
            user.setPassword(reqUser.getPassword());
            user.setUserName(reqUser.getUsername());
            User savedUser = userRepository.save(user);
            return ResponseEntity.ok().body(getResUser(savedUser));
        } else {
            return ResponseEntity.status(400).body("id not found");
        }
    }

    @Override
    public HttpEntity<?> editColumn(Integer id, JsonPatch jsonPatch) {
        Optional<User> byId = userRepository.findById(id);
        if (byId.isPresent()) {
            User user = byId.get();
            try {
                User editedUser = applyPatchToUser(jsonPatch, user);
                User save = userRepository.save(editedUser);
                return ResponseEntity.ok().body(getResUser(save));
            } catch (JsonPatchException e) {
                e.printStackTrace();
                return ResponseEntity.status(500).body("JsonPatchException");
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                return ResponseEntity.status(500).body("JsonProcessingException");
            }

        } else {
            return ResponseEntity.status(400).body("id not found");
        }

    }

    @Override
    public HttpEntity<?> deleteUser(Integer id) {
        Optional<User> byId = userRepository.findById(id);
        if (byId.isPresent()) {
            userRepository.delete(byId.get());
            return ResponseEntity.ok().body("O'chirildi");
        } else {
            return ResponseEntity.status(400).body("id not found");
        }
    }

    public ResUser getResUser(User user) {
        return new ResUser(user.getId(), user.getUserName());
    }

    private User applyPatchToUser(
            JsonPatch patch, User targetCustomer) throws JsonPatchException, JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode patched = patch.apply(objectMapper.convertValue(targetCustomer, JsonNode.class));
        return objectMapper.treeToValue(patched, User.class);
    }

}
