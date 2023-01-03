package org.izce.spring_webflux_rest.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.izce.spring_webflux_rest.domain.Category;
import org.izce.spring_webflux_rest.repo.CategoryRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.reactivestreams.Publisher;
import org.springframework.test.web.reactive.server.WebTestClient;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


class CategoryControllerTest {
	
	WebTestClient webTestClient;
	CategoryRepo categoryRepo;
	CategoryController categoryController;
	
	@BeforeEach
	void setUp() throws Exception {
		categoryRepo = mock(CategoryRepo.class);
		categoryController = new CategoryController(categoryRepo);
		webTestClient = WebTestClient.bindToController(categoryController).build();
	}

	@Test
	void testGetAllCategories() {
		when(categoryRepo.findAll()).thenReturn(Flux.just(new Category("Nuts"), new Category("Fruits")));
		
		var categoryList =  webTestClient.get()
			.uri("/api/v1/categories/")
			.exchange()
			.expectStatus().isOk()
			.expectBodyList(Category.class)
			.returnResult()
			.getResponseBody();
		
		assertEquals(2, categoryList.size());
		assertEquals("Nuts", categoryList.get(0).getName());
		assertEquals("Fruits", categoryList.get(1).getName());
	}

	@Test
	void testGetById() {
		when(categoryRepo.findById("dummy-id")).thenReturn(Mono.just(new Category("Dummy")));
		
		var category = webTestClient.get()
			.uri("/api/v1/categories/dummy-id")
			.exchange()
			.expectStatus().isOk()
			.expectBody(Category.class)
			.returnResult()
			.getResponseBody();
		
		assertEquals("Dummy", category.getName());
	}
	
	@SuppressWarnings("unchecked")
	@Test
	void testCreate() {
		Category exotic = new Category("Exotic");
		
		when(categoryRepo.saveAll(any(Publisher.class))).thenReturn(Flux.just(exotic));
		
		webTestClient.post()
			.uri("/api/v1/categories/")
			.bodyValue(exotic)
			.exchange()
			.expectStatus()
			.isCreated();
	}
	
	@Test
	void testUpdate() {
		when(categoryRepo.save(any(Category.class))).thenReturn(Mono.just(new Category("Exotic")));
	
		Category exoticReturned = webTestClient.put()
			.uri("/api/v1/categories/dummy-id")
			.bodyValue(new Category("Exotic-xyz"))
			.exchange()
			.expectStatus()
			.isOk()
			.expectBody(Category.class)
			.returnResult()
			.getResponseBody();
		
		assertEquals("Exotic", exoticReturned.getName());
	
	}
	
}

