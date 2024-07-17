package com.cydeo.controller;

import com.cydeo.dto.ResponseWrapper;
import com.cydeo.dto.UserDTO;
import com.cydeo.service.UserService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping
    @RolesAllowed({"Manager","Admin"})
    public ResponseEntity<ResponseWrapper>getAllUsers(){
        List<UserDTO> userList = userService.listAllUsers();
        return ResponseEntity.ok(new ResponseWrapper("Users are successfully retrieved", userList,HttpStatus.OK));

    }


    @GetMapping("/{username}")
    @RolesAllowed("Admin")
    public ResponseEntity<ResponseWrapper> getUserByUsername(@PathVariable("username") String username){
        UserDTO currentUser = userService.findByUserName(username);

        return ResponseEntity.ok(new ResponseWrapper("User "+username+" is successfully retrieved",currentUser,HttpStatus.OK));
    }

    @PostMapping
    @RolesAllowed("Admin")
    public ResponseEntity<ResponseWrapper> createUser(@RequestBody UserDTO user){

        userService.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseWrapper("User "+user.getUserName()+" is successfully created",HttpStatus.OK));
    }


    @PutMapping
    @RolesAllowed("Admin")
    public ResponseEntity<ResponseWrapper> updateUser(@RequestBody UserDTO user){
        userService.update(user);
        return ResponseEntity.ok(new ResponseWrapper("User "+user.getUserName()+" is updated",HttpStatus.OK));
    }

    @DeleteMapping("/{username}")
    @RolesAllowed("Admin")
    public ResponseEntity<ResponseWrapper> deleteUser (@PathVariable("username") String username){

        userService.delete(username);
        return ResponseEntity.ok(new ResponseWrapper("User "+username+" is successfully deleted",HttpStatus.OK));
    }





}
