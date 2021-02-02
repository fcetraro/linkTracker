package com.ml.linkTracer;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class LinkTracerApplicationTests {
	@Autowired
	private MockMvc mockMvc;
	@Test
	void shouldGetBadRequest() throws Exception {
		String url = "/metrics/100";
		this.mockMvc.perform(get(url))
				.andDo(print())
				.andExpect(status().isBadRequest());
	}
	@Test
	void shouldGetBadRequestInvalid() throws Exception {
		String url = "/invalidate/100";
		this.mockMvc.perform(post(url))
				.andDo(print())
				.andExpect(status().isBadRequest());
	}
	@Test
	void shouldCreateNewURL() throws Exception {
		String requestJson = "{\"url\":\"https://www.google.com\",\"password\":\"somePassword\"}";
		String url = "/link";
		this.mockMvc.perform(post(url).contentType(MediaType.APPLICATION_JSON).content(requestJson))
				.andDo(print())
				.andExpect(status().isOk());
	}
	@Test
	void shouldInvalidate() throws Exception {
		shouldCreateNewURL();
		String url = "/invalidate/0";
		this.mockMvc.perform(post(url))
				.andDo(print())
				.andExpect(status().isOk());
	}
	@Test
	void shouldNotRedirectInvalidate() throws Exception {
		shouldInvalidate();
		String url = "/redirect/0/somePassword";
		this.mockMvc.perform(get(url))
				.andDo(print())
				.andExpect(status().isNotFound());
	}
	@Test
	void shouldRedirect() throws Exception {
		shouldCreateNewURL();
		String url = "/redirect/0/somePassword";
		this.mockMvc.perform(get(url))
				.andDo(print())
				.andExpect(status().isFound());
	}
	@Test
	void shouldRedirectWrongPassword() throws Exception {
		shouldCreateNewURL();
		String url = "/redirect/0/wrongpassword";
		this.mockMvc.perform(get(url))
				.andDo(print())
				.andExpect(status().isNotFound());
	}
}
