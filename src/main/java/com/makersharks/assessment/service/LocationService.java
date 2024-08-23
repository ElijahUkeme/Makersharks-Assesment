package com.makersharks.assessment.service;

import com.makersharks.assessment.dto.LocationRequest;
import com.makersharks.assessment.model.location.LocationModel;

import java.util.List;

public interface LocationService {

    public LocationModel saveLocation(LocationRequest locationRequest);
    public List<LocationModel> getAllLocations();

}
