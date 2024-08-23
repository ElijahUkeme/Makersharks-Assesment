package com.makersharks.assessment.utils.conversion;

import com.makersharks.assessment.entity.Location;
import com.makersharks.assessment.model.location.LocationModel;

public class LocationModelUtil {

    public static LocationModel getReturnedLocationModel(Location location){
        LocationModel locationModel = LocationModel.builder()
                .id(location.getId())
                .address(location.getAddress())
                .longitude(location.getLongitude())
                .latitude(location.getLatitude())
                .cityName(location.getCityName())
                .stateName(location.getStateName())
                .countryName(location.getCountryName())
                .build();
        return locationModel;
    }
}
