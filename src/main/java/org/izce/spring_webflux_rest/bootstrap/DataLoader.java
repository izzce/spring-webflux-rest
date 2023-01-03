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
		loadCategories();
		loadCustomers();
		loadVendors();
	}

	private void loadCategories() {
		if (categoryRepo.count().block() > 0) {
			return;
		}
		
		Category fruits = new Category();
		fruits.setName("Fruits");

		Category dried = new Category();
		dried.setName("Dried");

		Category fresh = new Category();
		fresh.setName("Fresh");

		Category exotic = new Category();
		exotic.setName("Exotic");

		Category nuts = new Category();
		nuts.setName("Nuts");

		categoryRepo.save(fruits).block();
		categoryRepo.save(dried).block();
		categoryRepo.save(fresh).block();
		categoryRepo.save(exotic).block();
		categoryRepo.save(nuts).block();

		log.info("Categories Loaded: " + categoryRepo.count().block());
	}

	private void loadCustomers() {
		if (customerRepo.count().block() > 0) {
			return;
		}
		
		Customer customer1 = new Customer();
		//customer1.setId(1l);
		customer1.setFirstname("Michale");
		customer1.setLastname("Weston");
		customerRepo.save(customer1).block();

		Customer customer2 = new Customer();
		//customer2.setId(2l);
		customer2.setFirstname("Sam");
		customer2.setLastname("Axe");

		customerRepo.save(customer2).block();

		log.info("Customers Loaded: " + customerRepo.count().block());
	}
	
	private void loadVendors() {
		if (vendorRepo.count().block() > 0) {
			return;
		}
		
		Vendor vendor1 = new Vendor();
		//vendor1.setId(1L);
		vendor1.setName("Izce Fresh Fruits");
		vendorRepo.save(vendor1).block();
		
		Vendor vendor2 = new Vendor();
		//vendor2.setId(2L);
		vendor2.setName("Tema Shop");
		vendorRepo.save(vendor2).block();

		log.info("Vendors Loaded: " + vendorRepo.count().block());
		
	}

}
