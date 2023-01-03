package org.izce.spring_webflux_rest.bootstrap;

import org.izce.spring_webflux_rest.domain.Category;
import org.izce.spring_webflux_rest.domain.Customer;
import org.izce.spring_webflux_rest.domain.Vendor;
import org.izce.spring_webflux_rest.repo.CategoryRepo;
import org.izce.spring_webflux_rest.repo.CustomerRepo;
import org.izce.spring_webflux_rest.repo.VendorRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

@Slf4j
@Component
public class DataLoader implements CommandLineRunner {

	private final CategoryRepo categoryRepo;
	private final CustomerRepo customerRepo;
	private final VendorRepo vendorRepo;

	@Autowired
	public DataLoader(CategoryRepo categoryRepo, CustomerRepo customerRepo, VendorRepo vendorRepo) {
		log.debug("Initializing DataLoader...");
		this.categoryRepo = categoryRepo;
		this.customerRepo = customerRepo;
		this.vendorRepo = vendorRepo;
	}

	@Override
	public void run(String... args) throws Exception {
		Flux.concat(loadCategories(), loadCustomers(), loadVendors()).subscribe(null, null, () -> {
			// TODO how to convert count().block() to reactive calls?
			log.info("Category count: " + categoryRepo.count().block());
			log.info("Customer count: " + customerRepo.count().block());
			log.info("Vendor count: " + vendorRepo.count().block());
		});
		
	}

	private Flux<Category> loadCategories() {
		return this.categoryRepo
				.deleteAll()
		        .thenMany(
		        		Flux.just("Fruits", "Nuts", "Exotic")
		        		.flatMap(name -> this.categoryRepo.save(new Category(name))))
		        .log();
	}
	
	private Flux<Customer> loadCustomers() {
		return this.customerRepo
		        .deleteAll()
		        .thenMany(
		        		Flux.just(new Customer("Micky", "West"), new Customer("Sam", "Axe"))
		        		.flatMap(cust -> this.customerRepo.save(cust)))
		        .log();
	}
	
	private Flux<Vendor> loadVendors() {
		return this.vendorRepo
		        .deleteAll()
		        .thenMany(
		        		Flux.just("Izce Fresh Fruits", "Tema Shop")
		        		.flatMap(name -> this.vendorRepo.save(new Vendor(name))))
		        .log();
	}

}
