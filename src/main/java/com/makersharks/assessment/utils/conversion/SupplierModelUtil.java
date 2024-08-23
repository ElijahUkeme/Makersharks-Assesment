package com.makersharks.assessment.utils.conversion;

import com.makersharks.assessment.entity.SupplierEntity;
import com.makersharks.assessment.model.supplier.SupplierModel;

public class SupplierModelUtil {


    //This method will take a Supplier entity and convert
    //it to a Supplier model class.
    //It's not a good practice to expose you entity class
    //as a return model so that's why I am converting it to another class
    //and then return it.
    public static SupplierModel getReturnedSupplierModel(SupplierEntity supplier){
        SupplierModel supplierModel = SupplierModel.builder()
                .id(supplier.getId())
                .companyName(supplier.getCompanyName())
                .website(supplier.getWebsite())
                .location(LocationModelUtil.getReturnedLocationModel(supplier.getLocation()))
                .manufacturingProcesses(supplier.getManufacturingProcess())
                .natureOfBusiness(supplier.getBusinessNature())
                .build();
        System.out.println("The supplier model is: "+supplierModel);
        return supplierModel;
    }
}
