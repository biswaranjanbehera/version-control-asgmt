package com.asgmt.versioncontrol.pojo.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SimpleApiResponse<T> {
    private boolean status;
    private T data;
}
