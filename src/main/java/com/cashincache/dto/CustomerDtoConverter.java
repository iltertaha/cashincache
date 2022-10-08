package com.cashincache.dto;

import com.cashincache.model.Customer;
import org.springframework.stereotype.Component;

@Component
public class CustomerDtoConverter {

    public CustomerDto convert(Customer customer){
        CustomerDto customerDto =  new CustomerDto();
        customerDto.setId(customer.getId());
        customerDto.setAddress(customer.getAddress());
        customerDto.setCity(CityDto.valueOf(customer.getCity().name()));
        customerDto.setDateOfBirth(customer.getDateOfBirth());
        customerDto.setName(customer.getName());

        return customerDto;
    }
}
