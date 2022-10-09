package com.cashincache.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateCustomerRequest extends BaseCustomerRequest {
    private String id;
}