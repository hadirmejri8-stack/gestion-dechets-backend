package org.municipalite.gestiondechets.repository;

import org.municipalite.gestiondechets.model.Zone;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ZoneRepository extends MongoRepository<Zone, String> {

    // Permet de chercher une zone par nom
    Zone findByNom(String nom);
}
