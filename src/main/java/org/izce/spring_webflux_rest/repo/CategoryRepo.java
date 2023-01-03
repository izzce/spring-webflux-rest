package org.izce.spring_webflux_rest.repo;

import org.izce.spring_webflux_rest.domain.Category;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;


public interface CategoryRepo extends ReactiveMongoRepository<Category, String> {

    Category findByName(String name);

}