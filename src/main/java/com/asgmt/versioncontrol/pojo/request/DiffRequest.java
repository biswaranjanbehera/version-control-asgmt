package com.asgmt.versioncontrol.pojo.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DiffRequest {
    private String filePath;
    private String version1Id;
    private String version2Id;
}
