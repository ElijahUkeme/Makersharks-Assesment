package com.makersharks.assessment.response;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ServerResponse {

    private String terminus;
    private String status;
    private ResponseData response;
}
