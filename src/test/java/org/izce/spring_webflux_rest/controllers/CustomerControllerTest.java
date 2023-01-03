package org.izce.spring_webflux_rest.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.izce.spring_webflux_rest.domain.Customer;
import org.izce.spring_webflux_rest.repo.CustomerRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.reactivestreams.Publisher;
import org.springframework.test.web.reactive.server.WebTestClient;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


class CustomerControllerTest {
	
	WebTestClient webTestClient;
	CustomerRepo customerRepo;
	CustomerController customerController;
	
	@BeforeEach
	void setUp() throws Exception {
		customerRepo = mock(CustomerRepo.class);
		customerController = new CustomerController(customerRepo);
		webTestClient = WebTestClient.bindToController(customerController).build();
	}

	@Test
	void testGetAllCustomers() {
		when(customerRepo.findAll()).thenReturn(Flux.just(new Customer("Abc", "Def"), new Customer("Xyz", "123")));
		
		var customerList = webTestClient.get()
			.uri("/api/v1/customers/")
			.exchange()
			.expectStatus().isOk()
			.expectBodyList(Customer.class)
			.returnResult()
			.getResponseBody();
		
		assertEquals(2, customerList.size());
		assertEquals("Abc", customerList.get(0).getFirstname());
		assertEquals("Def", customerList.get(0).getLastname());
		assertEquals("Xyz", customerList.get(1).getFirstname());
		assertEquals("123", customerList.get(1).getLastname());
	}

	@Test
	void testGetById() {
		when(customerRepo.findById("dummy-id")).thenReturn(Mono.just(new Customer("Dummy1", "Dummy2")));
		
		var customer = webTestClient.get()
		.uri("/api/v1/customers/dummy-id")
		.exchange()
		.expectStatus().isOk()
		.expectBody(Customer.class)
		.returnResult()
		.getResponseBody();
		
		assertEquals("Dummy1", customer.getFirstname());
		assertEquals("Dummy2", customer.getLastname());
	}

	@SuppressWarnings("unchecked")
	@Test
	void testCreate() {
		Customer dummy = new Customer("Dummy1", "Dummy2");
		
		when(customerRepo.saveAll(any(Publisher.class))).thenReturn(Flux.just(dummy));
		
		webTestClient.post()
			.uri("/api/v1/customers/")
			.bodyValue(dummy)
			.exchange()
			.expectStatus()
			.isCreated();
	}
	
	@Test
	void testUpdate() {
		when(customerRepo.save(any(Customer.class))).thenReturn(Mono.just(new Customer("Dummy1", "Dummy2")));
	
		Customer customerReturned = webTestClient.put()
			.uri("/api/v1/customers/dummy-id")
			.bodyValue(new Customer("DummyA", "DummyB"))
			.exchange()
			.expectStatus()
			.isOk()
			.expectBody(Customer.class)
			.returnResult()
			.getResponseBody();
		
		assertEquals("Dummy1", customerReturned.getFirstname());
		assertEquals("Dummy2", customerReturned.getLastname());
	}
	
	@Test
	void testPatch() {
		when(customerRepo.findById("dummy-id")).thenReturn(Mono.just(new Customer("Dummy1", "Dummy2")));
		when(customerRepo.save(any(Customer.class))).thenReturn(Mono.just(new Customer("Dummy1", "Dummy2")));
	
		Customer customerReturned = webTestClient.patch()
			.uri("/api/v1/customers/dummy-id")
			.bodyValue(new Customer("DummyA", "DummyB"))
			.exchange()
			.expectStatus()
			.isOk()
			.expectBody(Customer.class)
			.returnResult()
			.getResponseBody();
		
		assertEquals("Dummy1", customerReturned.getFirstname());
		assertEquals("Dummy2", customerReturned.getLastname());
	
	}
}
