package org.izce.spring_webflux_rest.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document
public class Customer {

	@Id
	private String id;
	private String firstname;
	private String lastname;

}
