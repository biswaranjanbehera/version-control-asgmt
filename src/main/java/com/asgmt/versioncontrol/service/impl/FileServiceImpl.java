package com.asgmt.versioncontrol.service.impl;

import com.asgmt.versioncontrol.pojo.entity.File;
import com.asgmt.versioncontrol.pojo.entity.Version;
import com.asgmt.versioncontrol.pojo.request.DiffRequest;
import com.asgmt.versioncontrol.pojo.request.FileCreationRequest;
import com.asgmt.versioncontrol.pojo.response.SimpleApiResponse;
import com.asgmt.versioncontrol.pojo.response.VersionDTO;
import com.asgmt.versioncontrol.repositories.CurrentVersionRepositories;
import com.asgmt.versioncontrol.repositories.FileRepositories;
import com.asgmt.versioncontrol.repositories.VersionRepositories;
import com.asgmt.versioncontrol.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
public class FileServiceImpl  implements FileService {

    @Autowired
    FileRepositories fileRepositories;
    @Autowired
    VersionRepositories versionRepositories;
    @Autowired
    CurrentVersionRepositories currentVersionRepositories;
    @Override
    public SimpleApiResponse fileCreation(FileCreationRequest fileCreationRequest) {
        String filePath = fileCreationRequest.getFile_path();
        File file = fileRepositories.findByFilePath(filePath);
        if(file!=null){
            int fileId = file.getFileId();
            int versionNumber = currentVersionRepositories.getVersionIdByFileId(fileId);
            versionNumber++;
            currentVersionRepositories.updateFileIdByVersionId(fileId,versionNumber);
            String newVersion = "Version "+versionNumber;

            Version saveVersion = new Version();
            saveVersion.setVersionId(newVersion);
            saveVersion.setFileId(file.getFileId());
            saveVersion.setContent(fileCreationRequest.getContent());
            versionRepositories.save(saveVersion);
        }else{
            File saveFile = new File();
            saveFile.setFilePath(fileCreationRequest.getFile_path());
            saveFile.setSize(fileCreationRequest.getSize());
            saveFile.setType(fileCreationRequest.getType());;
            fileRepositories.save(saveFile);
            File savedFile = fileRepositories.findByFilePath(filePath);
            Version saveVersion = new Version();
            saveVersion.setVersionId("Version 1");
            saveVersion.setFileId(savedFile.getFileId());
            saveVersion.setContent(fileCreationRequest.getContent());
            versionRepositories.save(saveVersion);
            currentVersionRepositories.insertCurrentVersion(savedFile.getFileId(),1);
        }
        return new SimpleApiResponse(true,"File Creation Successful");

    }

    @Override
    public SimpleApiResponse  getVersionDetailsByFileId(int fileId) {
        List<Object[]> results = versionRepositories.findVersionDetailsByFileId(fileId);
        List<VersionDTO> dtos = new ArrayList<>();
        for (Object[] result : results) {
            dtos.add(new VersionDTO((String) result[0], (String) result[1], (Instant) result[2]));
        }
        return new SimpleApiResponse(true,dtos);
    }

    @Override
    public SimpleApiResponse deleteFileById(int fileId) {
        fileRepositories.deleteByFileId(fileId);
        return new SimpleApiResponse(true,"File is deleted");
    }

    @Override
    public SimpleApiResponse getFileDiff(String filePath, String version1Id, String version2Id) {
        String content1 = currentVersionRepositories.getContentByVersionIdAndFilePath(filePath, version1Id);
        String content2 = currentVersionRepositories.getContentByVersionIdAndFilePath(filePath, version2Id);

        if (content1 == null || content2 == null) {
            return null; // File or version not found
        }

        // Calculate the difference (diff) between the contents of the two versions
        String diff = calculateDiff(content1, content2);

        return new SimpleApiResponse(true,diff);
    }

    @Override
    public SimpleApiResponse getConflict(String filePath, String version1Id, String version2Id) {
        String content1 = currentVersionRepositories.getContentByVersionIdAndFilePath(filePath, version1Id);
        String content2 = currentVersionRepositories.getContentByVersionIdAndFilePath(filePath, version2Id);
        String[] baseLines = content1.split("\n");
        String[] userLines = content2.split("\n");

        boolean hasConflict = false;

        for (int i = 0; i < Math.min(baseLines.length, userLines.length); i++) {
            String baseLine = baseLines[i];
            String userLine = userLines[i];

            if (!baseLine.equals(userLine)) {
                hasConflict = true;
                break;
            }
        }
        if (hasConflict) {
            return new SimpleApiResponse(true,"Conflicts detected");
        }

        return new SimpleApiResponse(true,"No conflicts detected.");
    }

    private String calculateDiff(String content1, String content2) {
        StringBuilder diff = new StringBuilder();
        String[] lines1 = content1.split("\n");
        String[] lines2 = content2.split("\n");
        int length = Math.min(lines1.length, lines2.length);
        for (int i = 0; i < length; i++) {
            if (!lines1[i].equals(lines2[i])) {
                diff.append("- ").append(lines1[i]).append("\n");
                diff.append("+ ").append(lines2[i]).append("\n");
            }
        }
        return diff.toString();
    }

    /*@Override
    public SimpleApiResponse getFileDiff(DiffRequest diffRequest) {

    }*/
}
