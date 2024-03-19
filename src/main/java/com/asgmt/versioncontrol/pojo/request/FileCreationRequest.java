package com.asgmt.versioncontrol.pojo.request;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FileCreationRequest {
    private String file_path;
    private int size;
    private String type;
    private String content;

}
