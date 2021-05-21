package com.example.demo.service;

import com.example.demo.payload.ReqUser;
import com.example.demo.payload.ResUser;
import com.github.fge.jsonpatch.JsonPatch;
import org.springframework.http.HttpEntity;

import java.util.List;

public interface UserServiceInterface {
    //post
    public ResUser createUser(ReqUser reqUser);

    //get one
    public HttpEntity<?> getUser(Integer id);

    //get all
    public List<ResUser> getAllUsers();

    //put
    public HttpEntity<?> editUser(ReqUser reqUser);

    //patch
    public HttpEntity<?> editColumn(Integer id, JsonPatch jsonPatch);

    //delete
    public HttpEntity<?> deleteUser(Integer id);
}
