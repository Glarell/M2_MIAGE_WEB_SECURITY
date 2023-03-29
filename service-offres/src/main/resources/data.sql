drop table if exists adresse cascade;
drop table if exists candidature cascade;
drop table if exists geo cascade;
drop table if exists lieustage cascade;
drop table if exists offre cascade;
drop table if exists organisation cascade;
drop table if exists personne cascade;

DROP SEQUENCE hibernate_sequence;
CREATE  SEQUENCE hibernate_sequence START 4;

CREATE TABLE Adresse(
                        idAdresse SERIAL PRIMARY KEY NOT NULL,
                        adressePays VARCHAR NOT NULL,
                        adresseVille VARCHAR NOT NULL,
                        codePostal INTEGER NOT NULL,
                        adresseRue VARCHAR NOT NULL
);

CREATE TABLE Geo(
                    idGeo SERIAL PRIMARY KEY NOT NULL,
                    latitude FLOAT NOT NULL,
                    longitude FLOAT NOT NULL
);
CREATE TABLE Personne(
                         idPersonne SERIAL PRIMARY KEY NOT NULL,
                         nom VARCHAR NOT NULL,
                         prenom VARCHAR NOT NULL
);

CREATE TABLE Organisation(
                             idOrganisation SERIAL PRIMARY KEY NOT NULL,
                             nomOrganisation VARCHAR NOT NULL,
                             idAdresse INTEGER,
                             email VARCHAR NOT NULL,
                             telephone VARCHAR NOT NULL,
                             url VARCHAR NOT NULL,
                             FOREIGN KEY (idAdresse) REFERENCES Adresse (idAdresse)
);

CREATE TABLE LieuStage(
                          idLieuStage SERIAL PRIMARY KEY NOT NULL,
                          idAdresse INTEGER,
                          telephone INTEGER NOT NULL,
                          url VARCHAR NOT NULL,
                          idGeo INTEGER NOT NULL,
                          FOREIGN KEY (idAdresse) REFERENCES Adresse (idAdresse),
                          FOREIGN KEY (idGeo) REFERENCES Geo (idGeo)
);

CREATE TABLE Offre (
                       idOffre SERIAL PRIMARY KEY NOT NULL,
                       nomStage VARCHAR NOT NULL,
                       domaine VARCHAR NOT NULL,
                       descriptionStage VARCHAR NOT NULL,
                       datePublicationOffre VARCHAR NOT NULL,
                       niveauEtudesStage INTEGER NOT NULL,
                       experienceRequiseStage INTEGER NOT NULL,
                       dateDebutStage VARCHAR NOT NULL,
                       dureeStage INTEGER NOT NULL,
                       salaireStage FLOAT NOT NULL,
                       indemnisation FLOAT NOT NULL,
                       idOrganisation INTEGER NOT NULL,
                       idLieuStage INTEGER NOT NULL,
                       isActive BOOLEAN DEFAULT TRUE,
                       FOREIGN KEY (idOrganisation) REFERENCES Organisation (idOrganisation),
                       FOREIGN KEY (idLieuStage) REFERENCES LieuStage (idLieuStage)
);

CREATE TABLE Candidature(
                            idCandidature SERIAL PRIMARY KEY NOT NULL,
                            isActive BOOLEAN DEFAULT TRUE,
                            idPersonne INTEGER NOT NULL,
                            idOffre INTEGER NOT NULL,
                            dateCandidature VARCHAR NOT NULL,
                            FOREIGN KEY (idPersonne) REFERENCES Personne(idPersonne),
                            FOREIGN KEY (idOffre) REFERENCES Offre(idOffre)
);

INSERT INTO geo(idGeo, latitude, longitude) VALUES (1,50.23,79.23);
INSERT INTO geo(idGeo, latitude, longitude) VALUES (2,20.26,11.01);
INSERT INTO geo(idGeo, latitude, longitude) VALUES (3,90.29,89.72);

INSERT INTO personne(idPersonne, nom, prenom) VALUES (1,'TONDON','César');
INSERT INTO personne(idPersonne, nom, prenom) VALUES (2,'CISTERNINO','Enzo');

INSERT INTO Adresse(idAdresse, adressePays, adresseVille, codePostal, adresseRue) VALUES (1,'France','Nancy','54000','53 rue des jardiniers');
INSERT INTO Adresse(idAdresse, adressePays, adresseVille, codePostal, adresseRue) VALUES (2,'France','Epinal','88300','12 rue des étoiles');

INSERT INTO LieuStage(idLieuStage, idAdresse, telephone, url, idGeo) VALUES (1,1,0630771158,'https://fake-recrute.com',1);
INSERT INTO LieuStage(idLieuStage, idAdresse, telephone, url, idGeo) VALUES (2,2,0720721440,'https://fake-recrute-jamais.com',2);

INSERT INTO Organisation(idOrganisation,nomOrganisation, idAdresse, email, telephone, url) VALUES (1,'Sample Entreprise',1,'sample@entreprise.com',0387998898,'https://sample-entreprise.com');
INSERT INTO Organisation(idOrganisation,nomOrganisation, idAdresse, email, telephone, url) VALUES (2,'Amazon Entreprise',2,'contact@amazon.com',03275968898,'https://amazon.com');

INSERT INTO Offre (idOffre, nomStage, domaine, descriptionStage, datePublicationOffre, niveauEtudesStage, experienceRequiseStage, dateDebutStage, dureeStage, salaireStage, indemnisation, idOrganisation, idLieuStage)
VALUES (1,'Développeur Python','Développement Informatique','Automatisation de processus','2023-01-23',5,2,'2023-04-01',6,1263.56,125.98,1,1);

INSERT INTO Offre (idOffre, nomStage, domaine, descriptionStage, datePublicationOffre, niveauEtudesStage, experienceRequiseStage, dateDebutStage, dureeStage, salaireStage, indemnisation, idOrganisation, idLieuStage)
VALUES (2,'Architecte SI','Conception SI informatique','Refonte du SI','2023-01-10',5,10,'2023-09-01',12,2263.56,525.98,2,2);

INSERT INTO Candidature(idCandidature,idPersonne,idOffre,dateCandidature) VALUES (1,1,1,'2023-02-28');
INSERT INTO Candidature(idCandidature,idPersonne,idOffre,dateCandidature) VALUES (2,2,2,'2023-03-01');
INSERT INTO Candidature(idCandidature,idPersonne,idOffre,dateCandidature) VALUES (3,1,2,'2023-03-02');