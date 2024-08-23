package com.makersharks.assessment.dto;

import com.makersharks.assessment.entity.Location;
import com.makersharks.assessment.utils.ManufacturingProcesses;
import com.makersharks.assessment.utils.NatureOfBusiness;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SearchDto {

    private String city;
    private String natureOfBusiness;
    private String manufacturingProcesses;
    private int pageNumber;
    private int pageSize;
}
