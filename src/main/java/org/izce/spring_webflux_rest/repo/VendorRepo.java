package org.izce.spring_webflux_rest.repo;

import org.izce.spring_webflux_rest.domain.Vendor;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface VendorRepo extends ReactiveMongoRepository<Vendor, String>{
}