package com.cydeo.controller;

import com.cydeo.dto.ProjectDTO;
import com.cydeo.dto.ResponseWrapper;
import com.cydeo.service.ProjectService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.io.PipedWriter;
import java.util.List;

@RestController
@RequestMapping("/api/v1/project")
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }


    @GetMapping
    @RolesAllowed("Manager")
    public ResponseEntity<ResponseWrapper> getProjects(){
        List<ProjectDTO> projectDTOList = projectService.listAllProjects();
        return ResponseEntity.ok(new ResponseWrapper("Projects are successfully retrieved",projectDTOList, HttpStatus.OK));
    }


    @GetMapping("/{projectCode}")
    @RolesAllowed("Manager")
    public ResponseEntity<ResponseWrapper> getProjectByCode (@PathVariable("projectCode") String projectCode){
      ProjectDTO project =   projectService.getByProjectCode(projectCode);
      return ResponseEntity.ok(new ResponseWrapper("Project is successfully retrieved",project,HttpStatus.OK));
    }

    @PostMapping
    @RolesAllowed({"Manager","Admin"})

    public ResponseEntity<ResponseWrapper> createProject (@RequestBody ProjectDTO projectDTO){
        projectService.save(projectDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseWrapper("Project is successfully created",HttpStatus.CREATED));

    }


    @PutMapping
    @RolesAllowed("Manager")
    public ResponseEntity<ResponseWrapper> updateProject(@RequestBody ProjectDTO projectDTO){
        projectService.update(projectDTO);
        return ResponseEntity.ok(new ResponseWrapper("Project is successfully updated",HttpStatus.OK));
    }

    @DeleteMapping("/{code}")
    @RolesAllowed("Manager")
    public ResponseEntity<ResponseWrapper> deleteProject (@PathVariable("code") String code){

        projectService.delete(code);
        return ResponseEntity.ok(new ResponseWrapper("Project is successfully deleted",HttpStatus.OK));
    }


    @GetMapping("/manager/projectStatus")
    @RolesAllowed("Manager")
    public ResponseEntity<ResponseWrapper> getProjectByManager (){

        List<ProjectDTO> projectDTOList = projectService.listAllProjectDetails();
        return ResponseEntity.ok(new ResponseWrapper("Projects are successfully retrieved",projectDTOList,HttpStatus.OK));
    }


    @GetMapping("/manager/complete/{projectCode}")
    @RolesAllowed("Manager")
    public ResponseEntity<ResponseWrapper> managerCompleteProject (@PathVariable("projectCode") String code){
        projectService.complete(code);
        return ResponseEntity.ok(new ResponseWrapper("Projects are successfully completed",HttpStatus.OK));

    }




}
