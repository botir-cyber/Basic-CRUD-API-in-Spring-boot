
package com.example.demo.controller;

import com.example.demo.payload.ReqUser;
import com.example.demo.service.UserService;
import com.github.fge.jsonpatch.JsonPatch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "api/user")
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping
    public HttpEntity<?> getAllUsers() {
        return ResponseEntity.ok().body(userService.getAllUsers());
    }

    @PostMapping
    public HttpEntity<?> createUser(@RequestBody ReqUser reqUser) {
        return ResponseEntity.ok().body(userService.createUser(reqUser));
    }


    @GetMapping("{id}")
    public HttpEntity<?> getUser(@PathVariable Integer id) {
        return userService.getUser(id);
    }

    @PutMapping
    public HttpEntity<?> editUser(@RequestBody ReqUser reqUser) {
        return userService.editUser(reqUser);
    }

    @PatchMapping("{id}")
    public HttpEntity<?> editColumn(@PathVariable Integer id, @RequestBody JsonPatch jsonPatch) {
        return userService.editColumn(id, jsonPatch);
    }

    @DeleteMapping("{id}")
    public HttpEntity<?> deleteUser(@PathVariable Integer id) {
        return userService.deleteUser(id);
    }
}
