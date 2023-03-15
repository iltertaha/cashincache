package com.cashincache;

import com.cashincache.model.Account;
import com.cashincache.model.City;
import com.cashincache.model.Customer;
import com.cashincache.repository.AccountRepository;
import com.cashincache.repository.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;

@SpringBootApplication
//@EnableRabbit
public class CashincacheApplication implements CommandLineRunner {

	private final AccountRepository accountRepository;
	private final CustomerRepository customerRepository;

	public CashincacheApplication(AccountRepository accountRepository, CustomerRepository customerRepository) {
		this.accountRepository = accountRepository;
		this.customerRepository = customerRepository;
	}

	public static void main(String[] args) {
		SpringApplication.run(CashincacheApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Customer c1 = Customer.builder()
				.id("1234568")
				.name("Ilter Taha")
				.City(City.ADANA)
				.address("ev")
				.dateOfBirth(1996)
				.build();

		Customer c2 = Customer.builder()
				.id("1234566")
				.name("Harry Potter")
				.City(City.ANKARA)
				.address("hogwarts")
				.dateOfBirth(1994)
				.build();

		Customer c3 = Customer.builder()
				.id("1234564")
				.name("Hermione Granger")
				.City(City.ISTANBUL)
				.address("hogwarts")
				.dateOfBirth(1993)
				.build();

		Customer c4 = Customer.builder()
				.id("5534566")
				.name("Sebastian Vettel")
				.City(City.ANKARA)
				.address("hogwarts")
				.dateOfBirth(1994)
				.build();

		customerRepository.saveAll(Arrays.asList(c1,c2,c3,c4));

		Account a1 = Account.builder()
				.id("100")
				.customerId("1234568")
				.city(City.ANKARA)
				.balance(1200.0)
				.build();


		Account a2 = Account.builder()
				.id("101")
				.customerId("1234566")
				.city(City.ANKARA)
				.balance(1400.0)
				.build();


		Account a3 = Account.builder()
				.id("102")
				.customerId("1234564")
				.city(City.ANKARA)
				.balance(1600.0)
				.build();

		Account a4 = Account.builder()
				.id("103")
				.customerId("5534566")
				.city(City.ANKARA)
				.balance(1600.0)
				.build();

		accountRepository.saveAll(Arrays.asList(a1,a2,a3,a4));

	}
}