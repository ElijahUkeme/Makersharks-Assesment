package com.makersharks.assessment.response;


import com.makersharks.assessment.model.supplier.SupplierModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ResponseData {

    private int code;
    private String title;
    private String message;
    private List<SupplierModel> data;
}
