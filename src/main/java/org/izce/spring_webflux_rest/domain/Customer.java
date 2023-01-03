package org.izce.spring_webflux_rest.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.lang.NonNull;

import lombok.Data;

@Data
@Document
public class Customer {

	@Id
	private String id;
	@NonNull
	private String firstname;
	@NonNull
	private String lastname;

}
