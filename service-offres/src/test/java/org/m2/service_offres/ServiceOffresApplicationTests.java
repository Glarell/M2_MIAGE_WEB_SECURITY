package org.m2.service_offres;

import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;
import org.m2.service_offres.boundary.*;
import org.m2.service_offres.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;

import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.*;

//@Sql({"data.sql"})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ServiceOffresApplicationTests {

	@LocalServerPort
	int port;

	@Autowired
	//@Resource
	OffreRessource or;
	@Autowired
	OrganisationRessource orgr;
	@Autowired
	AdresseRessource ar;
	@Autowired
	LieuStageRessource lr;
	@Autowired
	GeoRessource gr;

	@BeforeEach
	public void setupContext() {
		//or.deleteAll();
		RestAssured.port = port;
		/*Adresse ad1 = new Adresse(2, "France", "Epinal", 88300, "12 rue des étoiles");
		ar.save(ad1);
		Organisation org1 =  new Organisation(2,"Amazon Entreprise", ad1,
				"contact@amazon.com",
				"3275968898",
				"https://amazon.com");
		orgr.save(org1);
		Geo geo1 = new Geo(
				2,
				20,
				11
		);
		gr.save(geo1);
		LieuStage lieu1 = new LieuStage(
				2,
				ad1,
				geo1,
				720721440,
				"https://fake-recrute-jamais.com"
		);
		lr.save(lieu1);*/
	}

	@Test
	void ping() {
		when().get("/offres").then().statusCode(HttpStatus.SC_OK);
	}

	@Test
	void getAll() {
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
				false,
				new ArrayList<>()
		);
		or.save(o1);
//$..offreList[0]

		when().get("/offres").then().assertThat()
				//.body("$._embedded.offreList",hasItems());
				.body("$",arrayWithSize(equalTo(2)));
				//.and().assertThat().body(jsonPath("$..offreList[0]").isArray(), true);
	}
}
