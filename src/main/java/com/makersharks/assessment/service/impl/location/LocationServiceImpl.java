package com.makersharks.assessment.service.impl.location;

import com.makersharks.assessment.dto.LocationRequest;
import com.makersharks.assessment.entity.Location;
import com.makersharks.assessment.model.location.LocationModel;
import com.makersharks.assessment.repository.location.LocationRepository;
import com.makersharks.assessment.service.LocationService;
import com.makersharks.assessment.utils.conversion.LocationModelUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LocationServiceImpl implements LocationService {

    private final LocationRepository locationRepository;


    @Override
    public LocationModel saveLocation(LocationRequest locationRequest) {

        Location location = Location.builder()
                .address(locationRequest.getAddress())
                .cityName(locationRequest.getCityName())
                .stateName(locationRequest.getStateName())
                .latitude(locationRequest.getLatitude())
                .longitude(locationRequest.getLongitude())
                .countryName(locationRequest.getCountryName())
                .build();
        locationRepository.save(location);
        LocationModel locationModel = LocationModel.builder()
                .id(location.getId())
                .stateName(location.getStateName())
                .countryName(location.getCountryName())
                .cityName(location.getCityName())
                .latitude(location.getLatitude())
                .longitude(location.getLongitude())
                .address(location.getAddress())
                .build();
        return locationModel;
    }

    @Override
    public List<LocationModel> getAllLocations() {
        List<Location> locations = locationRepository.findAll();
        List<LocationModel> locationModels = new ArrayList<>();
        for (Location location: locations){
            locationModels.add(LocationModelUtil.getReturnedLocationModel(location));
        }
        return locationModels;
    }

}
