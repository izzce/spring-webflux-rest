package org.izce.spring_webflux_rest.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.lang.NonNull;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@Document
@NoArgsConstructor
@RequiredArgsConstructor
public class Category {

	@Id
	private String id;
	@NonNull
	private String name;

}
