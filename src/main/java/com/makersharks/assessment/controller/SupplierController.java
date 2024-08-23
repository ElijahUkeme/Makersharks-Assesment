package com.makersharks.assessment.controller;
import com.makersharks.assessment.dto.LocationRequest;
import com.makersharks.assessment.dto.QueryDto;
import com.makersharks.assessment.dto.SearchDto;
import com.makersharks.assessment.exception.exeception.DataNotFoundException;
import com.makersharks.assessment.model.location.LocationModel;
import com.makersharks.assessment.model.supplier.SupplierModel;
import com.makersharks.assessment.response.PaginationResponse;
import com.makersharks.assessment.service.LocationService;
import com.makersharks.assessment.service.main.supplier.SupplierService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class SupplierController {

    private final SupplierService service;
    private final LocationService locationService;


    //These other endpoints was for me to be able to test the app
    //I needed to save a location, and supplier before making the
    //query request so please you can discard it.


    @PostMapping("/api/location/save")
    public ResponseEntity<LocationModel> saveLocation(@RequestBody LocationRequest locationRequest){
        return new ResponseEntity<>(locationService.saveLocation(locationRequest), HttpStatus.CREATED);
    }


    @PostMapping("/api/supplier/save")
    public ResponseEntity<SupplierModel> saveSupplier(@RequestBody QueryDto queryDto) throws DataNotFoundException {
        return new ResponseEntity<>(service.saveSupplier(queryDto),HttpStatus.CREATED);
    }

    @GetMapping("/api/location/all")
    public List<LocationModel> allLocations(){
        return locationService.getAllLocations();
    }


    @PostMapping("/api/supplier/query")
    public PaginationResponse getSuppliers(@RequestBody SearchDto searchDto, HttpServletRequest request) throws DataNotFoundException {
        return service.getAllSuppliersBasedOnQuery(searchDto,request);
    }
}
