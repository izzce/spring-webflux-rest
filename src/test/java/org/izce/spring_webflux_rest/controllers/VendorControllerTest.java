package org.izce.spring_webflux_rest.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.izce.spring_webflux_rest.domain.Vendor;
import org.izce.spring_webflux_rest.repo.VendorRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.reactive.server.WebTestClient;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


class VendorControllerTest {

	WebTestClient webTestClient;
	VendorRepo vendorRepo;
	VendorController vendorController;

	@BeforeEach
	void setUp() throws Exception {
		vendorRepo = mock(VendorRepo.class);
		vendorController = new VendorController(vendorRepo);
		webTestClient = WebTestClient.bindToController(vendorController).build();
	}

	@Test
	void testGetAllVendors() {
		when(vendorRepo.findAll()).thenReturn(Flux.just(new Vendor("Tema Manav")));

		var vendorList = webTestClient.get()
				.uri("/api/v1/vendors/")
				.exchange()
				.expectStatus().isOk()
				.expectBodyList(Vendor.class)
				.returnResult()
				.getResponseBody();

		assertEquals(1, vendorList.size());
		assertEquals("Tema Manav", vendorList.get(0).getName());
	}

	@Test
	void testGetById() {
		when(vendorRepo.findById("dummy-id")).thenReturn(Mono.just(new Vendor("Dummy")));

		var vendor = webTestClient.get().uri("/api/v1/vendors/dummy-id")
				.exchange()
				.expectStatus().isOk()
				.expectBody(Vendor.class)
				.returnResult()
				.getResponseBody();

		assertEquals("Dummy", vendor.getName());
	}

}
