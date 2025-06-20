package com.ecom.customer.dto;

import java.util.Map;

public record ErrorResponse(
        Map<String,String> errors
) {
}
