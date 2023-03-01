package com.cashincache.dto;

import com.cashincache.model.City;
import com.cashincache.model.Currency;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BaseAccountRequest {

    @NotBlank(message = "Customer Id must not be null")
    private String customerId;

    @NotNull
    @Min(0)
    private Double balance;

    @NotNull(message = "Currency can not be null")
    private Currency currency;

    @NotNull(message = "City can not be null")
    private City city;

}