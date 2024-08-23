package com.makersharks.assessment.service.impl.supplier;


import com.makersharks.assessment.dto.QueryDto;
import com.makersharks.assessment.dto.SearchDto;
import com.makersharks.assessment.entity.Location;
import com.makersharks.assessment.entity.SupplierEntity;
import com.makersharks.assessment.exception.exeception.DataNotFoundException;
import com.makersharks.assessment.model.supplier.SupplierModel;
import com.makersharks.assessment.repository.location.LocationRepository;
import com.makersharks.assessment.repository.supplier.SupplierRepository;
import com.makersharks.assessment.response.PaginationResponse;
import com.makersharks.assessment.response.ResponseData;
import com.makersharks.assessment.response.ServerResponse;
import com.makersharks.assessment.service.main.supplier.SupplierService;
import com.makersharks.assessment.utils.ManufacturingProcesses;
import com.makersharks.assessment.utils.NatureOfBusiness;
import com.makersharks.assessment.utils.conversion.LocationModelUtil;
import com.makersharks.assessment.utils.conversion.SupplierModelUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class SupplierServiceImpl implements SupplierService {

    private final SupplierRepository supplierRepository;
    private final LocationRepository locationRepository;
    @Value("${baseUrl}")
    private String baseUrl;

    @Override
    public PaginationResponse getAllSuppliersBasedOnQuery(SearchDto searchDto, HttpServletRequest request) throws DataNotFoundException {

        //First of all confirmed that the search data correspond with our enums
        //for Manufacturing process and Nature of business

        if (!validateManufacturingProcessInput(searchDto.getManufacturingProcesses())){
            throw new DataNotFoundException("There is no such manufacturing process");
        }
        if (!validateNatureOfBusinessInput(searchDto.getNatureOfBusiness())){
            throw new DataNotFoundException("There is no such nature of business");
        }

        Location location = locationRepository.findByCityName(searchDto.getCity());
        if (Objects.isNull(location)){
            throw new DataNotFoundException("There is no location with the provided city name");
        }

        List<SupplierEntity> queryForLocation = supplierRepository.findByLocation(location);
        if (Objects.isNull(queryForLocation)){
            throw new DataNotFoundException("There is no supplier with the provided location");
        }

        List<SupplierEntity> queryForManufacturingProcess = supplierRepository.findByManufacturingProcess(
                searchDto.getManufacturingProcesses());
        if (Objects.isNull(queryForManufacturingProcess)){
            throw new DataNotFoundException("There is no supplier attached to the provided manufacturing process");
        }

        List<SupplierEntity> queryForBusiness = supplierRepository.findByBusinessNature(
                searchDto.getNatureOfBusiness());

        if (Objects.isNull(queryForBusiness)){
            throw new DataNotFoundException("There is no supplier attached to the provided nature of business");

        }

        //In this other case, it means that every validation has passed
        //We now implement pagination for data retrieval

        Pageable pageable = PageRequest.of(searchDto.getPageNumber(), searchDto.getPageSize());
        Page<SupplierEntity> entityList = supplierRepository.queriedList(
                searchDto.getCity(), searchDto.getNatureOfBusiness(),
                searchDto.getManufacturingProcesses(),pageable);

        //initialize an empty arrayList for supplierModel
        List<SupplierModel> supplierModels = new ArrayList<>();

        for (SupplierEntity entity:entityList){
            //convert the supplier entity into a supplier model and store it in the list
            supplierModels.add(SupplierModelUtil.getReturnedSupplierModel(entity));

        }
        return new PaginationResponse(
                new ServerResponse(baseUrl+request.getRequestURI(),
                        "OK",new ResponseData(200,"Search Result",
                        "Suppliers returned Successfully",supplierModels)),
                searchDto.getPageNumber(),
                searchDto.getPageSize(),
                entityList.getTotalPages(),
                (int) entityList.getTotalElements(),
                entityList.isLast());
    }


    //This additional method enabled me to test the app
    //I couldn't be able to make a query without saving some data to the db
    @Override
    public SupplierModel saveSupplier(QueryDto queryDto) throws DataNotFoundException {
        Location location = locationRepository.findByCityName(queryDto.getCity());
        if (Objects.isNull(location)){
            throw new DataNotFoundException("City name not found");
        }
        if (!validateManufacturingProcessInput(queryDto.getManufacturingProcesses())){
            throw new DataNotFoundException("There is no such manufacturing process");
        }
        if (!validateNatureOfBusinessInput(queryDto.getNatureOfBussiness())){
            throw new DataNotFoundException("There is no such nature of business");
        }

        SupplierEntity supplier = SupplierEntity.builder()
                .companyName(queryDto.getCompanyName())
                .location(location)
                .website(queryDto.getWebsite())
                .businessNature(queryDto.getNatureOfBussiness())
                .manufacturingProcess(queryDto.getManufacturingProcesses())
                .build();
        supplierRepository.save(supplier);

        SupplierModel supplierModel = SupplierModel.builder()
                .id(supplier.getId())
                .natureOfBusiness(supplier.getBusinessNature())
                .manufacturingProcesses(supplier.getManufacturingProcess())
                .companyName(supplier.getCompanyName())
                .website(supplier.getWebsite())
                .location(LocationModelUtil.getReturnedLocationModel(supplier.getLocation()))
                .build();

        return supplierModel;
    }

    boolean validateNatureOfBusinessInput(String input) {

        boolean found = false;
        for (NatureOfBusiness nature: NatureOfBusiness.values()){

            if (nature.name().equalsIgnoreCase(input)){
                found = true;
                break;

            }
        }
        if (!found){
            found = false;
        }
        return found;
    }
    boolean validateManufacturingProcessInput(String input){
        boolean found = false;
        for (ManufacturingProcesses processes: ManufacturingProcesses.values()){


            if (processes.name().equalsIgnoreCase(input)){
                found = true;
                break;

            }
        }
        if (!found){
            found = false;
        }
        return found;
    }
}
