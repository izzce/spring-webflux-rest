package org.izce.spring_webflux_rest.controllers;

import static org.izce.spring_webflux_rest.Constants.CATEGORY_CONTROLLER_BASE_URL;

import org.izce.spring_webflux_rest.domain.Category;
import org.izce.spring_webflux_rest.repo.CategoryRepo;
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
@RequestMapping(CATEGORY_CONTROLLER_BASE_URL)
public class CategoryController {

	private final CategoryRepo categoryRepo;

	public CategoryController(CategoryRepo categoryRepo) {
		this.categoryRepo = categoryRepo;
	}

	@GetMapping
	public Flux<Category> getAllCategories() {
		return categoryRepo.findAll();
	}

	@GetMapping("/{id}")
	public Mono<Category> getById(@PathVariable String id) {
		return categoryRepo.findById(id);
	}
	
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping
	public Mono<Void> create(@RequestBody Publisher<Category> categoryStream) {
		return categoryRepo.saveAll(categoryStream).then();
	}
	
	@PutMapping("/{id}")
	public Mono<Category> update(@PathVariable String id, @RequestBody Category category) {
		category.setId(id);
		return categoryRepo.save(category);
	}
}
