package org.izce.spring_webflux_rest.controllers;

import static org.izce.spring_webflux_rest.Constants.VENDOR_CONTROLLER_BASE_URL;

import org.izce.spring_webflux_rest.domain.Vendor;
import org.izce.spring_webflux_rest.repo.VendorRepo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(VENDOR_CONTROLLER_BASE_URL)
public class VendorController {

	private final VendorRepo vendorRepo;

	public VendorController(VendorRepo vendorRepo) {
		this.vendorRepo = vendorRepo;
	}

	@GetMapping
	public Flux<Vendor> getAllVendors() {
		return vendorRepo.findAll();
	}

	@GetMapping("/{id}")
	public Mono<Vendor> getById(@PathVariable String id) {
		return vendorRepo.findById(id);
	}
}
