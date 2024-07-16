package com.cydeo.controller;

import com.cydeo.dto.ProjectDTO;
import com.cydeo.dto.ResponseWrapper;
import com.cydeo.dto.TaskDTO;
import com.cydeo.enums.Status;
import com.cydeo.service.TaskService;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/task")
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }


    @GetMapping
    @RolesAllowed("Manager")
    public ResponseEntity<ResponseWrapper> getTasks(){
        List<TaskDTO> taskList = taskService.listAllTasks();
        return ResponseEntity.ok(new ResponseWrapper("Tasks are successfully retrieved",taskList, HttpStatus.OK));
    }

    @GetMapping("/{id}")
    @RolesAllowed("Manager")
    public ResponseEntity<ResponseWrapper> getByTaskId(@PathVariable("id") long id){
       TaskDTO task  = taskService.findById(id);
       return ResponseEntity.ok(new ResponseWrapper("Task is successfully retrieved",task,HttpStatus.OK));
    }

    @PostMapping
    @RolesAllowed("Manager")
    public ResponseEntity<ResponseWrapper> createTask(@RequestBody TaskDTO taskDTO){
        taskService.save(taskDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseWrapper("Task is successfully created",HttpStatus.CREATED));
    }

    @DeleteMapping("{id}")
    @RolesAllowed("Manager")
    public ResponseEntity<ResponseWrapper> deleteTask(@PathVariable("id") long taskId){
        taskService.delete(taskId);
        return ResponseEntity.ok(new ResponseWrapper("Task is successfully deleted",HttpStatus.OK));

    }


    @PutMapping
    @RolesAllowed("Manager")
    public ResponseEntity<ResponseWrapper> updateTask(@RequestBody TaskDTO taskDTO){
        taskService.update(taskDTO);
        return ResponseEntity.ok(new ResponseWrapper("Task is successfully updated",HttpStatus.OK));
    }


    @GetMapping("/employee/pending-tasks")
    @RolesAllowed("Employee")
    public ResponseEntity<ResponseWrapper> employeePendingTask(){
        List<TaskDTO> taskDTOList = taskService.listAllTasksByStatusIsNot(Status.COMPLETE);

        return ResponseEntity.ok(new ResponseWrapper("Tasks are successfully retrieved",taskDTOList,HttpStatus.OK));

    }

    @GetMapping("/employee/update/")
    @RolesAllowed("Employee")
    public ResponseEntity<ResponseWrapper> employeeUpdateTask(@RequestBody TaskDTO taskDTO){
        taskService.update(taskDTO);
        return ResponseEntity.ok(new ResponseWrapper("Task is successfully updated",HttpStatus.OK));
    }


    @GetMapping("/employee/archive")
    @RolesAllowed("Employee")
    public ResponseEntity<ResponseWrapper> employeeArchivedTasks(){
       List<TaskDTO> taskDTOList = taskService.listAllTasksByStatus(Status.COMPLETE);
       return ResponseEntity.ok(new ResponseWrapper("Task is successfully retrieved",taskDTOList,HttpStatus.OK));

    }



}
