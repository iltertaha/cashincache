package com.cashincache.service;

import com.cashincache.dto.AccountDto;
import com.cashincache.dto.AccountDtoConverter;
import com.cashincache.dto.CreateAccountRequest;
import com.cashincache.dto.CreateCustomerRequest;
import com.cashincache.model.Account;
import com.cashincache.model.City;
import com.cashincache.model.Currency;
import com.cashincache.model.Customer;
import com.cashincache.repository.AccountRepository;
import com.cashincache.repository.CustomerRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.*;

public class AccountServiceTest {

    private AccountService accountService;

    private AccountRepository accountRepository;
    private CustomerService customerService;
    private AccountDtoConverter accountDtoConverter;

    @Before
    public void setUp() throws Exception {
        accountRepository = Mockito.mock(AccountRepository.class);
        customerService = Mockito.mock(CustomerService.class);
        accountDtoConverter = Mockito.mock(AccountDtoConverter.class);

        accountService = new AccountService(accountRepository,
                                            customerService,
                                            accountDtoConverter);
    }
    @Test
    public void whenCreateAccountCalledWithValidRequest_itShouldReturnValidAccountDto(){
        CreateAccountRequest createAccountRequest = new CreateAccountRequest("1234");
        createAccountRequest.setCustomerId("12345");
        createAccountRequest.setBalance(100.0);
        createAccountRequest.setCity(City.ISTANBUL);
        createAccountRequest.setCurrency(Currency.EUR);

        Customer customer = Customer.builder()
                .id("12345")
                .address("address")
                .City(City.ISTANBUL)
                .dateOfBirth(1998)
                .name("taha")
                .build();

        Account account = Account.builder()
                .id(createAccountRequest.getId())
                .balance(createAccountRequest.getBalance())
                .currency(createAccountRequest.getCurrency())
                .customerId(createAccountRequest.getCustomerId())
                .city(createAccountRequest.getCity())
                .build();

        AccountDto accountDto = AccountDto.builder()
                .id("1234")
                .customerId("12345")
                .currency(Currency.EUR)
                .balance(100.0)
                .build();

        // Return predefined object when called with 12345
        Mockito.when(customerService.getCustomerById("12345")).thenReturn(customer);
        Mockito.when(accountRepository.save(account)).thenReturn(account);
        Mockito.when(accountDtoConverter.convert(account)).thenReturn(accountDto);

        AccountDto result = accountService.createAccount(createAccountRequest);

        // Compare if result is expected
        Assert.assertEquals(result, accountDto);

        // Check if the methods below are called during createAccount
        Mockito.verify(customerService).getCustomerById("12345");
        Mockito.verify(accountRepository).save(account);
        Mockito.verify(accountDtoConverter).convert(account);


    }

    @Test(expected = RuntimeException.class)
    public void whenCreateAccountCalledWithCustomerWithoutId_itShouldReturnEmptyAccountDto(){
        CreateAccountRequest createAccountRequest = new CreateAccountRequest("1234");
        createAccountRequest.setCustomerId("12345");
        createAccountRequest.setBalance(100.0);
        createAccountRequest.setCity(City.ISTANBUL);
        createAccountRequest.setCurrency(Currency.EUR);

        Mockito.when(customerService.getCustomerById("12345")).thenReturn(Customer.builder().build());

        AccountDto expectedAccountDto = AccountDto.builder().build();

        AccountDto result = accountService.createAccount(createAccountRequest);

        Assert.assertEquals(result, expectedAccountDto);

        Mockito.verifyNoInteractions(accountRepository);
        Mockito.verifyNoInteractions(accountDtoConverter);
    }

    @Test(expected = RuntimeException.class)
    public void whenCreateAccountCalledAndRepositoryThrowException_itShouldThrowException(){
        CreateAccountRequest createAccountRequest = new CreateAccountRequest("1234");
        createAccountRequest.setCustomerId("12345");
        createAccountRequest.setBalance(100.0);
        createAccountRequest.setCity(City.ISTANBUL);
        createAccountRequest.setCurrency(Currency.EUR);

        Customer customer = Customer.builder()
                .id("12345")
                .address("address")
                .City(City.ISTANBUL)
                .dateOfBirth(1998)
                .name("taha")
                .build();

        Account account = Account.builder()
                .id(createAccountRequest.getId())
                .balance(createAccountRequest.getBalance())
                .currency(createAccountRequest.getCurrency())
                .customerId(createAccountRequest.getCustomerId())
                .city(createAccountRequest.getCity())
                .build();

        // Return predefined object when called with 12345
        Mockito.when(customerService.getCustomerById("12345")).thenReturn(customer);
        Mockito.when(accountRepository.save(account)).thenThrow(new RuntimeException("wubba lubba dub dub"));

        // no need to add dtoconverter since it wont be run
        //Mockito.when(accountDtoConverter.convert(account)).thenReturn(accountDto);

        accountService.createAccount(createAccountRequest);

        // Check if the methods below are called during createAccount
        Mockito.verify(customerService).getCustomerById("12345");
        Mockito.verify(accountRepository).save(account);

        // DtoConverter won't be executed
        Mockito.verifyNoInteractions(accountDtoConverter);

    }

    
}