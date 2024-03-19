package com.asgmt.versioncontrol.pojo.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FileMoveRequest {
    private int file_id;
    private String source_path;
    private String destination_path;

}
