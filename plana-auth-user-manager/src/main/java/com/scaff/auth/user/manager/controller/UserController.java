package com.scaff.auth.user.manager.controller;

import com.scaff.common.web.argsresolver.FastJson;
import com.scaff.common.web.result.JSONResult;
import com.scaff.entity.User;

import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;
import org.mongodb.morphia.query.UpdateOperator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by xyl on 1/16/18.
 */
@RestController
@Slf4j
@RequestMapping("/users")
public class UserController {

    @Autowired
    Datastore datastore;

    @GetMapping("/{name}")
    public JSONResult getUser(@PathVariable("name") String username){
        return JSONResult.ok(datastore.createQuery(User.class).field("username").equal(username).get());
    }

    @PostMapping
    public JSONResult addUser(@FastJson User user){
        datastore.save(user);
        return JSONResult.ok();
    }

    @GetMapping("/{id}/roles")
    public JSONResult getUserRoles(@PathVariable("id") String id){

        User user = datastore.createQuery(User.class).field("id").equal(new ObjectId(id)).get();

        return JSONResult.ok(user.getRoleType());
    }

    @PostMapping("/{id}/roles")
    public JSONResult assignUserRoles(@PathVariable("id") String id, @FastJson List<String> roles){
        Query query = datastore.createQuery(User.class)
                .field("id").equal(new ObjectId(id));
        UpdateOperations updateOperations = datastore.createUpdateOperations(User.class).;
        datastore.update(query,);
        return JSONResult.ok();

    }




}
