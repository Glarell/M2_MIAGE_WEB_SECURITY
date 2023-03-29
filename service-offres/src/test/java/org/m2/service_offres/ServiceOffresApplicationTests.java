package org.m2.service_offres;

import org.junit.Before;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;
import org.m2.service_offres.boundary.*;
import org.m2.service_offres.entity.*;
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

//@Sql({"data.sql"})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class ServiceOffresApplicationTests {

	@LocalServerPort
	int port;

	@MockBean
	OffreRessource or;
	@MockBean
	OrganisationRessource orgr;
	@MockBean
	AdresseRessource ar;
	@MockBean
	LieuStageRessource lr;
	@MockBean
	GeoRessource gr;
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
		Adresse ad1 = new Adresse(2, "France", "Epinal", 88300, "12 rue des étoiles");
		Organisation org1 =  new Organisation(2,"Amazon Entreprise", ad1,
				"contact@amazon.com",
				"3275968898",
				"https://amazon.com");
		Geo geo1 = new Geo(
				2,
				20,
				11
		);
		LieuStage lieu1 = new LieuStage(
				2,
				ad1,
				geo1,
				720721440,
				"https://fake-recrute-jamais.com"
		);
		Offre o1 = new Offre(188329932,
				"Nouvelle Offre Bac+5",
				"Conception SI informatique",
				"Refonte du SI",
				"2023-01-10",
				5,
				10,
				"2023-09-01",
				12,
				2263,
				525,
				org1,
				lieu1,
				false
		);
		//MOCK
		when(or.save(Mockito.any(Offre.class))).thenReturn(o1);
		//doNothing().when(Mockito.any(Offre.class));
		// pour l'id 188329932 ==> Offre 1
		when(or.findById(188329932)).thenReturn(Optional.of(o1));
		when(or.findAll()).thenReturn(List.of(o1));
		when(or.findAllActive()).thenReturn(List.of(o1,o1));
		when(or.existsById(Mockito.any(Integer.class))).thenReturn(Boolean.TRUE);
	}

	@Test
	/**
	 * url tested --> GET /offres/{idOffre}
	 * url tested --> GET /offres/
	 * url tested --> POST /offres/create/
	 * url tested --> PUT /offres/{idOffre}/
	 * url tested --> DELETE /offres/{idOffre}/
	 */
	void ping() throws Exception {
		this.mvck.perform(get("/offres/"))
				.andExpect(status().isOk());
		this.mvck.perform(get("/offres/188329932/"))
				.andExpect(status().isOk());
		this.mvck.perform(post("/offres/create/"))
				.andExpect(status().isUnsupportedMediaType());
		this.mvck.perform(put("/offres/188329932/"))
				.andExpect(status().isUnsupportedMediaType());
		this.mvck.perform(delete("/offres/188329932/"))
				.andExpect(status().isOk());
	}

	@Test
	/**
	 * url tested --> GET /offres/{idOffre}
	 */
	void getOffreById() throws Exception {
		this.mvck.perform(get("/offres/188329932"))
				.andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.idOffre").value(188329932));
	}

	@Test
	/**
	 * url tested --> GET /offres/
	 */
	void getOffres() throws Exception {
		this.mvck.perform(get("/offres/"))
				.andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$..offreList.*",hasSize(2)));
	}

	@Test
	/**
	 * url tested --> POST /offres/create/
	 */
	void postOffre() throws Exception {
		Adresse ad1 = new Adresse(2, "France", "Epinal", 88300, "12 rue des étoiles");
		Organisation org1 =  new Organisation(2,"Amazon Entreprise", ad1,
				"contact@amazon.com",
				"3275968898",
				"https://amazon.com");
		Geo geo1 = new Geo(
				2,
				20,
				11
		);
		LieuStage lieu1 = new LieuStage(
				2,
				ad1,
				geo1,
				720721440,
				"https://fake-recrute-jamais.com"
		);
		Offre o1 = new Offre(1,
				"Nouvelle Offre Bac+11111",
				"Conception SI informatique",
				"Refonte du SI",
				"2023-01-10",
				5,
				10,
				"2023-09-01",
				12,
				2263,
				525,
				org1,
				lieu1,
				false
		);
		this.mvck.perform(
				post("/offres/create/")
						.content(o1.toString())
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated());
		o1 = new Offre(1,
				null,
				"Conception SI informatique",
				"Refonte du SI",
				"2023-01-10",
				5,
				10,
				"2023-09-01",
				12,
				2263,
				525,
				org1,
				lieu1,
				false
		);
		this.mvck.perform(
		post("/offres/create/")
				.content(o1.toString())
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isBadRequest());
	}

	@Test
	/**
	 * url tested --> PUT /offres/{idOffre}/
	 */
	void putOffre() throws Exception {
		Adresse ad1 = new Adresse(2, "France", "Epinal", 88300, "12 rue des étoiles");
		Organisation org1 =  new Organisation(2,"Amazon Entreprise", ad1,
				"contact@amazon.com",
				"3275968898",
				"https://amazon.com");
		Geo geo1 = new Geo(
				2,
				20,
				11
		);
		LieuStage lieu1 = new LieuStage(
				2,
				ad1,
				geo1,
				720721440,
				"https://fake-recrute-jamais.com"
		);
		Offre o1 = new Offre(1,
				"Nouvelle Offre Bac+11111",
				"Conception SI informatique",
				"Refonte du SI",
				"2023-01-10",
				5,
				10,
				"2023-09-01",
				12,
				2263,
				525,
				org1,
				lieu1,
				false
		);
		this.mvck.perform(
		put("/offres/188329932/")
				.contentType(MediaType.APPLICATION_JSON)
				.content(o1.toString()))
			.andExpect(status().isOk());
	}

	@Test
	/**
	 * url tested --> DELETE /offres/{idOffre}/
	 */
	void deleteOffre() throws Exception {
		this.mvck.perform(delete("/offres/188329932/"))
				.andExpect(status().isOk());
	}
}
