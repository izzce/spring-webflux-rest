package org.izce.spring_webflux_rest.controllers.v1;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.izce.spring_webflux_rest.domain.Category;
import org.izce.spring_webflux_rest.repo.CategoryRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
		
		webTestClient.get()
			.uri("/api/v1/categories/")
			.exchange()
			.expectStatus().isOk()
			.expectBodyList(Category.class)
			.hasSize(2);
	}

	@Test
	void testGetById() {
		when(categoryRepo.findById("dummy-id")).thenReturn(Mono.just(new Category("Dummy")));
		
		webTestClient.get()
		.uri("/api/v1/categories/dummy-id")
		.exchange()
		.expectStatus().isOk()
		.expectBody(Category.class);
	}

}
