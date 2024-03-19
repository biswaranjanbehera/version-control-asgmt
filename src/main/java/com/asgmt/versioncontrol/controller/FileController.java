package com.asgmt.versioncontrol.controller;

import com.asgmt.versioncontrol.pojo.request.FileCreationRequest;
import com.asgmt.versioncontrol.pojo.response.SimpleApiResponse;
import com.asgmt.versioncontrol.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
public class FileController {
    @Autowired
    FileService fileService;
    @PostMapping("/file")
    public ResponseEntity<SimpleApiResponse> createFile(@RequestBody FileCreationRequest fileCreationRequest){
        SimpleApiResponse simpleApiResponse =  fileService.fileCreation(fileCreationRequest);
        return new ResponseEntity<>(simpleApiResponse, HttpStatus.OK);
    }

    /*@PostMapping("/diff")
    public ResponseEntity<SimpleApiResponse> diffFile(@RequestBody DiffRequest diffRequest){
        SimpleApiResponse simpleApiResponse = fileService.getFileDiff(diffRequest);
        return new ResponseEntity<>(simpleApiResponse,HttpStatus.OK);
    }*/

    @GetMapping("/file/{id}")
    public ResponseEntity<SimpleApiResponse> getFile(@PathVariable int id){
        SimpleApiResponse simpleApiResponse = fileService.getVersionDetailsByFileId(id);
        return new ResponseEntity<>(simpleApiResponse,HttpStatus.OK);
    }

    @DeleteMapping("/file/{id}")
    public ResponseEntity<SimpleApiResponse> deleteFile(@PathVariable int id){
        SimpleApiResponse simpleApiResponse = fileService.deleteFileById(id);
        return new ResponseEntity<>(simpleApiResponse,HttpStatus.NO_CONTENT);
    }

    @GetMapping("/diff/{filePath}")
    public ResponseEntity<SimpleApiResponse> getFileDiff(
                                            @PathVariable String filePath,
                                            @RequestParam String version1Id,
                                            @RequestParam String version2Id) {
        SimpleApiResponse simpleApiResponse = fileService.getFileDiff(filePath,version1Id,version2Id);
        return new ResponseEntity<>(simpleApiResponse,HttpStatus.OK);
    }

    @GetMapping("/conflict/{filePath}")
    public ResponseEntity<SimpleApiResponse> detectConflict(
            @PathVariable String filePath,
            @RequestParam String version1Id,
            @RequestParam String version2Id) {
        SimpleApiResponse simpleApiResponse = fileService.getConflict(filePath,version1Id,version2Id);
        return new ResponseEntity<>(simpleApiResponse,HttpStatus.OK);
    }




}
