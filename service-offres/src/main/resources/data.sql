CREATE TABLE Offre (
     INTEGER id PRIMARY KEY,
     VARCHAR nomStage,
     VARCHAR domaine,
     VARCHAR nomOrganisation,
     VARCHAR descriptionStage,
     VARCHAR datePublicationOffre,
     INTEGER niveauEtudesStage,
     INTEGER experienceRequiseStage,
     VARCHAR dateDebutStage,
     INTEGER dureeStage,
     long salaireStage,
     long indemnisation,
     INTEGER idOrganisation,
     INTEGER idLieuStage,

);
    
CREATE TABLE Organisation();
CREATE TABLE Adresse();
CREATE TABLE LieuStage();
CREATE TABLE Geo();
CREATE TABLE Personne();
