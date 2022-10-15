package com.srjons.fileupload;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;

import java.io.File;
import java.time.Duration;

@Slf4j
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ReactiveUploadApplicationTests {

	@Autowired
	private WebTestClient webTestClient;

	@Test
	void contextLoads() {
	}

	@BeforeEach
	public void setUp() {
		webTestClient = webTestClient
				.mutate()
				.responseTimeout(Duration.ofSeconds(3))
				.build();
	}

	@Test
	@Order(1)
	void reactiveUploadTest() throws Exception {
		File file = new File(
				this.getClass()
						.getClassLoader()
						.getResource("test.txt")
						.getFile()
		);
		webTestClient.post()
				.uri("/file/upload")
				.contentType(MediaType.MULTIPART_FORM_DATA)
				.body(BodyInserters.fromMultipartData(fromFile(file)))
				.exchange()
				.expectStatus().isOk()
				.expectBody(String.class)
				.consumeWith(stringEntityExchangeResult -> System.out.println(stringEntityExchangeResult.getResponseBody()))
		;
	}

	public MultiValueMap<String, HttpEntity<?>> fromFile(File file) {
		MultipartBodyBuilder builder = new MultipartBodyBuilder();
		builder.part("file", new FileSystemResource(file));
		return builder.build();
	}
}
