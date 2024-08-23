package com.makersharks.assessment.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LocationRequest {

    private double latitude;
    private double longitude;
    private String address;
    private String cityName;
    private String stateName;
    private String countryName;
}
