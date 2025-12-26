package org.municipalite.gestiondechets.repository;

import org.municipalite.gestiondechets.model.Dechet;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface DechetRepository extends MongoRepository<Dechet, String> {
    // Chercher par type de déchet
    List<Dechet> findByType(String type);

    // Chercher les déchets recyclables
    List<Dechet> findByRecyclableTrue();
}
