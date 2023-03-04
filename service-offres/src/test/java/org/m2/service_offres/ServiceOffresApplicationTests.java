package org.m2.service_offres;

import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;
import org.m2.service_offres.boundary.*;
import org.m2.service_offres.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;

import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@Sql({"data.sql"})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class ServiceOffresApplicationTests {

	@LocalServerPort
	int port;

	@Autowired
	OffreRessource or;
	@Autowired
	OrganisationRessource orgr;
	@Autowired
	AdresseRessource ar;
	@Autowired
	LieuStageRessource lr;
	@Autowired
	GeoRessource gr;
	@Autowired
	MockMvc mvck;

	@BeforeEach
	public void setupContext() {
		//or.deleteAll();
		RestAssured.port = port;
	}

	@Test
	/**
	 * url tested --> GET /offres/{idOffre}
	 * url tested --> GET /offres/
	 * url tested --> POST /offres/create/
	 */
	void ping() {
		when().get("/offres/").then().statusCode(HttpStatus.SC_OK);
		when().get("/offres/1/").then().statusCode(HttpStatus.SC_OK);
		when().post("/offres/create/").then().statusCode(HttpStatus.SC_UNSUPPORTED_MEDIA_TYPE);
	}

	@Test
	/**
	 * url tested --> GET /offres/{idOffre}
	 */
	void getOffreById() throws Exception {
		this.mvck.perform(get("/offres/1/"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.idOffre").value(1));

	}

	@Test
	/**
	 * url tested --> GET /offres/
	 */
	void getOffres() throws Exception {
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
		or.save(o1);
		//this.mvck.perform(get("/offres/")).andExpect(status().isOk()).andExpect(jsonPath("$..offreList.*",hasSize(3)));
	}

	@Test
	/**
	 * url tested --> POST /offres/create/
	 */
	void postOffre() throws Exception {
		this.mvck.perform(
				post("/offres/create/")
						.content("{\n" +
								"  \"nomStage\": \"Développeur Python\",\n" +
								"  \"domaine\": \"Développement Informatique\",\n" +
								"  \"descriptionStage\": \"Automatisation de processus\",\n" +
								"  \"datePublicationOffre\": \"2023-01-23\",\n" +
								"  \"niveauEtudesStage\": 5,\n" +
								"  \"experienceRequiseStage\": 2,\n" +
								"  \"dateDebutStage\": \"2023-04-01\",\n" +
								"  \"dureeStage\": 6,\n" +
								"  \"salaireStage\": 1263,\n" +
								"  \"indemnisation\": 125,\n" +
								"  \"organisation\": {\n" +
								"    \"idOrganisation\": 1,\n" +
								"    \"nomOrganisation\": \"Sample Entreprise\",\n" +
								"    \"adresse\": {\n" +
								"      \"idAdresse\": 1,\n" +
								"      \"adressePays\": \"France\",\n" +
								"      \"adresseVille\": \"Nancy\",\n" +
								"      \"codePostal\": 54000,\n" +
								"      \"adresseRue\": \"53 rue des jardiniers\"\n" +
								"    },\n" +
								"    \"email\": \"sample@entreprise.com\",\n" +
								"    \"telephone\": \"387998898\",\n" +
								"    \"url\": \"https://sample-entreprise.com\"\n" +
								"  },\n" +
								"  \"lieuStage\": {\n" +
								"    \"idLieuStage\": 1,\n" +
								"    \"adresse\": {\n" +
								"      \"idAdresse\": 1,\n" +
								"      \"adressePays\": \"France\",\n" +
								"      \"adresseVille\": \"Nancy\",\n" +
								"      \"codePostal\": 54000,\n" +
								"      \"adresseRue\": \"53 rue des jardiniers\"\n" +
								"    },\n" +
								"    \"geo\": {\n" +
								"      \"idGeo\": 1,\n" +
								"      \"latitude\": 50,\n" +
								"      \"longitude\": 79\n" +
								"    },\n" +
								"    \"telephone\": 630771158,\n" +
								"    \"url\": \"https://fake-recrute.com\"\n" +
								"  },\n" +
								"  \"active\": true\n" +
								"}")
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated());

		this.mvck.perform(
		post("/offres/create/")
				.content("{\n" +
						"  \"domaine\": \"Développement Informatique\",\n" +
						"  \"descriptionStage\": \"Automatisation de processus\",\n" +
						"  \"datePublicationOffre\": \"2023-01-23\",\n" +
						"  \"niveauEtudesStage\": 5,\n" +
						"  \"experienceRequiseStage\": 2,\n" +
						"  \"dateDebutStage\": \"2023-04-01\",\n" +
						"  \"dureeStage\": 6,\n" +
						"  \"salaireStage\": 1263,\n" +
						"  \"indemnisation\": 125,\n" +
						"  \"organisation\": {\n" +
						"    \"idOrganisation\": 1,\n" +
						"    \"nomOrganisation\": \"Sample Entreprise\",\n" +
						"    \"adresse\": {\n" +
						"      \"idAdresse\": 1,\n" +
						"      \"adressePays\": \"France\",\n" +
						"      \"adresseVille\": \"Nancy\",\n" +
						"      \"codePostal\": 54000,\n" +
						"      \"adresseRue\": \"53 rue des jardiniers\"\n" +
						"    },\n" +
						"    \"email\": \"sample@entreprise.com\",\n" +
						"    \"telephone\": \"387998898\",\n" +
						"    \"url\": \"https://sample-entreprise.com\"\n" +
						"  },\n" +
						"  \"lieuStage\": {\n" +
						"    \"idLieuStage\": 1,\n" +
						"    \"adresse\": {\n" +
						"      \"idAdresse\": 1,\n" +
						"      \"adressePays\": \"France\",\n" +
						"      \"adresseVille\": \"Nancy\",\n" +
						"      \"codePostal\": 54000,\n" +
						"      \"adresseRue\": \"53 rue des jardiniers\"\n" +
						"    },\n" +
						"    \"geo\": {\n" +
						"      \"idGeo\": 1,\n" +
						"      \"latitude\": 50,\n" +
						"      \"longitude\": 79\n" +
						"    },\n" +
						"    \"telephone\": 630771158,\n" +
						"    \"url\": \"https://fake-recrute.com\"\n" +
						"  },\n" +
						"  \"active\": true\n" +
						"}")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isBadRequest());

	}

	@Test
	/**
	 * url tested --> PUT /offres/{idOffre}/
	 */
	void putOffre() {}

	@Test
	/**
	 * url tested --> DELETE /offres/{idOffre}/
	 */
	void deleteOffre() {}
}
