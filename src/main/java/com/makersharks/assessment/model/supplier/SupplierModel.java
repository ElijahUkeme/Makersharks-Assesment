package com.makersharks.assessment.model.supplier;

import com.makersharks.assessment.model.location.LocationModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SupplierModel {

    private Long id;
    private String companyName;
    private String website;
    private LocationModel location;
    private String natureOfBusiness;
    private String manufacturingProcesses;
}
