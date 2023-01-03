package org.izce.spring_webflux_rest.controllers;

import static org.izce.spring_webflux_rest.Constants.CUSTOMER_CONTROLLER_BASE_URL;

import org.izce.spring_webflux_rest.domain.Customer;
import org.izce.spring_webflux_rest.repo.CustomerRepo;
import org.reactivestreams.Publisher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(CUSTOMER_CONTROLLER_BASE_URL)
public class CustomerController {

	private final CustomerRepo customerRepo;

	public CustomerController(CustomerRepo customerRepo) {
		this.customerRepo = customerRepo;
	}

	@GetMapping
	public Flux<Customer> getCustomerList() {
		return customerRepo.findAll();
	}

	@GetMapping({ "/{id}" })
	public Mono<Customer> getCustomerById(@PathVariable String id) {
		return customerRepo.findById(id);
	}

	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping
	public Mono<Void> create(@RequestBody Publisher<Customer> customerStream) {
		return customerRepo.saveAll(customerStream).then();
	}
	
	@PutMapping("/{id}")
	public Mono<Customer> update(@PathVariable String id, @RequestBody Customer customer) {
		customer.setId(id);
		return customerRepo.save(customer);
	}
}
