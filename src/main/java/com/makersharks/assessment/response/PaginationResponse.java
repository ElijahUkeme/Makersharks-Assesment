package com.makersharks.assessment.response;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class PaginationResponse {
    private ServerResponse serverResponse;
    private int pageNumber;
    private int pageSize;
    private int totalPages;
    private int totalElements;
    private boolean isLast;
}
