package org.izce.spring_webflux_rest.controllers.v1;

import static org.izce.spring_webflux_rest.Constants.CATEGORY_CONTROLLER_BASE_URL;

import org.izce.spring_webflux_rest.domain.Category;
import org.izce.spring_webflux_rest.repo.CategoryRepo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
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
}
