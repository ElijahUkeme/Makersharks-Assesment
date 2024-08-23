package com.makersharks.assessment.dto;


import com.makersharks.assessment.entity.Location;
import com.makersharks.assessment.utils.ManufacturingProcesses;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QueryDto {
    private String companyName;
    private String website;
    private String city;
    private String natureOfBussiness;
    private String manufacturingProcesses;
}
