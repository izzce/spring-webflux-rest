package org.izce.spring_webflux_rest.repo;

import org.izce.spring_webflux_rest.domain.Customer;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface CustomerRepo extends ReactiveMongoRepository<Customer, String>{
}