package org.m2.services_personnes;

import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
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

import javax.persistence.EntityExistsException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

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
        Candidature c1 = new Candidature(1, 1, 1, "2023-03-29", true);
        Candidature c2 = new Candidature(2, 1, 2, "2023-03-19", true);
        Personne p = new Personne(1, "TONDON", "César", new ArrayList<>());
        Personne p2 = new Personne(2, "CISTERNINO", "Enzo", new ArrayList<>(List.of(c2)));
        p.setCandidatures(List.of(c1, c2));
        when(pr.save(Mockito.any(Personne.class))).thenReturn(p);
        when(pr.findById(1)).thenReturn(Optional.of(p));
        when(cr.getCandidatureByIdAndUser(1, 2)).thenReturn(List.of(c2));
        when(pr.findAll()).thenReturn(List.of(p, p2));
    }

    @Test
    public void ping() throws Exception {
        /**
         * url tested --> GET users/user_id/
         * url tested --> POST users/candidatures/create/
         * url tested --> DELETE users/user_id/candidatures/offre_id/
         * url tested --> GET users/
         * url tested --> GET users/user_id/offre_id/
         * url tested --> GET users/user_id/candidatures/
         * url tested --> GET users/candidatures/id_offre/
         * url tested --> PUT users/candidatures/id_candidature/
         */
        this.mvck.perform(get("/users/1/"))
                .andExpect(status().isOk());
        this.mvck.perform(post("/users/candidatures/create/"))
                .andExpect(status().isUnsupportedMediaType());
        this.mvck.perform(delete("/users/1/candidatures/2/"))
                .andExpect(status().isOk());
        this.mvck.perform(get("/users/"))
                .andExpect(status().isOk());
        this.mvck.perform(get("/users/1/2/"))
                .andExpect(status().isOk());
        this.mvck.perform(get("/users/1/candidatures/"))
                .andExpect(status().isOk());
        this.mvck.perform(get("/users/candidatures/1/"))
                .andExpect(status().isOk());
        this.mvck.perform(put("/users/candidatures/2/"))
                .andExpect(status().isBadRequest());
    }

    @Test
    /**
     * url tested --> GET users/user_id/
     */
    public void getPersonne() throws Exception {
        this.mvck.perform(get("/users/1/"))
                .andExpect(status().isOk());
        this.mvck.perform(get("/users/23232/"))
                .andExpect(status().isNotFound());
    }

    @Test
    /**
     * url tested --> POST users/candidatures/create/
     */
    public void createCandidature() throws Exception {
        Candidature c1 = new Candidature(3, 1, 1, "2023-03-29", true);
        when(cr.save(Mockito.any(Candidature.class))).thenReturn(c1);
        when(cr.save(c1)).thenReturn(c1);
        Personne p = new Personne(1, "TONDON", "César", new ArrayList<>());
        p.setCandidatures(List.of(c1));
        this.mvck.perform(
                        post("/users/candidatures/create/")
                                .content(c1.toString())
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    /**
     * url tested --> DELETE users/user_id/candidatures/offre_id/
     */
    public void deleteCandidature() throws Exception {
        Candidature c1 = new Candidature(3, 1, 1, "2023-03-29", true);
        when(cr.getCandidatureByIdAndUser(1, 1)).thenReturn(List.of(c1));
        this.mvck.perform(delete("/users/1/candidatures/1"))
                .andExpect(status().isOk());

        when(cr.getCandidatureByIdAndUser(1, 2)).thenReturn(new ArrayList<>());
        this.mvck.perform(delete("/users/1/candidatures/3/"))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof EntityExistsException));
    }

    @Test
    /**
     * url tested --> GET users/
     */
    public void getAllPersonnes() throws Exception {
        this.mvck.perform(get("/users/"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$..personneList.*", hasSize(2)));
    }

    @Test
    /**
     * url tested --> GET users/user_id/offre_id/
     */
    public void getCandidature() throws Exception {
        when(cr.getCandidatureByIdAndUser(1, 1)).thenReturn(List.of(new Candidature(1, 1, 1, "2023-02-13", true)));
        this.mvck.perform(get("/users/1/1/"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$..candidatureList[0].idPersonne").value(1))
                .andExpect(jsonPath("$..candidatureList[0].idOffre").value(1));
    }

    @Test
    /**
     * url tested --> GET users/user_id/candidatures/
     */
    public void getCandidaturesById() throws Exception {
        when(cr.getCandidaturesByUser(1)).thenReturn(List.of(
                new Candidature(1, 1, 1, "2023-02-13", true),
                new Candidature(2, 1, 2, "2023-07-13", true)
        ));

        this.mvck.perform(get("/users/1/candidatures/"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$..candidatureList.*", hasSize(2)));
    }

    @Test
    /**
     * url tested --> GET users/candidatures/id_offre/
     */
    public void getCandidaturesByIdOffre() throws Exception {
        when(cr.getCandidaturesByIdOffre(1)).thenReturn(List.of(
                new Candidature(1, 1, 1, "2023-02-13", true),
                new Candidature(2, 2, 1, "2023-07-13", true),
                new Candidature(3, 3, 1, "2023-07-12", true)
        ));
        this.mvck.perform(get("/users/candidatures/1/"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$..candidatureList.*", hasSize(3)));
    }

    @Test
    /**
     * url tested --> PUT users/candidatures/id_candidature/
     */
    public void putCandidature() throws Exception {
        Candidature c1 = new Candidature(1, 1, 1, "2023-03-29", true);
        when(cr.save(c1)).thenReturn(c1);
        when(cr.existsById(1)).thenReturn(Boolean.TRUE);
        Personne p = new Personne(1, "TONDON", "César", new ArrayList<>());
        p.setCandidatures(List.of(c1));

        this.mvck.perform(put("/users/candidatures/1/")
                        .content(c1.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
