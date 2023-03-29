package org.m2.services_personnes;
import org.junit.Before;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;
import org.m2.services_personnes.boundary.CandidatureRessource;
import org.m2.services_personnes.boundary.PersonneRessource;
import org.m2.services_personnes.entity.Candidature;
import org.m2.services_personnes.entity.Personne;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;
import static org.mockito.Mockito.when;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class ServicesPersonnesApplicationTests {
	@LocalServerPort
	int port;

	@MockBean
	CandidatureRessource cr;
	@MockBean
	PersonneRessource pr;
	@Autowired
	MockMvc mvck;

	@Autowired
	private WebApplicationContext webApplicationContext;

	@Before
	public void setup() {
		RestAssured.port = port;
		this.mvck = webAppContextSetup(webApplicationContext).build();
	}

	@BeforeEach
	public void setupContext() {
		Candidature c1 = new Candidature(1,1,1,"2023-03-29",true);
		Candidature c2 = new Candidature(2,1,2,"2023-03-19",true);
		Personne p = new Personne(1,"TONDON","César", new ArrayList<>());
		p.setCandidatures(List.of(c1,c2));
	}

	@Test
	public void ping() throws Exception {
		/**
		 * url tested --> DELETE users/user_id/
		 * url tested --> POST users/candidatures/create/
		 * url tested --> DELETE users/user_id/candidatures/offre_id/
		 * url tested --> GET users/
		 * url tested --> GET users/user_id/offre_id/
		 * url tested --> GET users/user_id/candidatures/
		 * url tested --> GET users/candidatures/id_offre/
		 * url tested --> PUT users/candidatures/id_candidature/
		 */
		this.mvck.perform(delete("/users/1"))
				.andExpect(status().isOk());
		this.mvck.perform(post("/users/candidatures/create/"))
				.andExpect(status().isUnsupportedMediaType());
		this.mvck.perform(delete("/users/1/candidatures/1/"))
				.andExpect(status().isOk());
		this.mvck.perform(get("/users/"))
				.andExpect(status().isOk());
		this.mvck.perform(get("/users/1/2/"))
				.andExpect(status().isOk());
		this.mvck.perform(get("/users/1/candidatures/"))
				.andExpect(status().isOk());
		this.mvck.perform(get("/users/candidatures/1"))
				.andExpect(status().isOk());
		this.mvck.perform(put("/users/candidatures/2"))
				.andExpect(status().isUnsupportedMediaType());
	}
}
