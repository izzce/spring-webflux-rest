package org.izce.spring_webflux_rest;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = SpringWebfluxRestApp.class)
//@DataMongoTest(
//		  excludeAutoConfiguration = org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration.class
//		)
class SpringWebfluxRestAppTests {

	@Test
	void contextLoads() {
	}

}
