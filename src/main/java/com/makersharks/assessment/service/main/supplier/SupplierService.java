package com.makersharks.assessment.service.main.supplier;

import com.makersharks.assessment.dto.QueryDto;
import com.makersharks.assessment.dto.SearchDto;
import com.makersharks.assessment.exception.exeception.DataNotFoundException;
import com.makersharks.assessment.model.supplier.SupplierModel;
import com.makersharks.assessment.response.PaginationResponse;
import jakarta.servlet.http.HttpServletRequest;

public interface SupplierService {

    public PaginationResponse getAllSuppliersBasedOnQuery(SearchDto searchDto, HttpServletRequest request) throws DataNotFoundException;

    public SupplierModel saveSupplier(QueryDto queryDto) throws DataNotFoundException;
}
