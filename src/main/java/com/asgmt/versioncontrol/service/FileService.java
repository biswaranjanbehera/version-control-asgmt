package com.asgmt.versioncontrol.service;

import com.asgmt.versioncontrol.pojo.request.DiffRequest;
import com.asgmt.versioncontrol.pojo.request.FileCreationRequest;
import com.asgmt.versioncontrol.pojo.response.SimpleApiResponse;

public interface FileService {
    public SimpleApiResponse fileCreation(FileCreationRequest fileCreationRequest);
    //public SimpleApiResponse getFileDiff(DiffRequest diffRequest);
    public SimpleApiResponse getVersionDetailsByFileId(int fileId);

    public SimpleApiResponse deleteFileById(int fileId);
    public SimpleApiResponse getFileDiff(String filePath,String version1Id,String version2Id);
    public SimpleApiResponse getConflict(String filePath,String version1Id,String version2Id);

}
