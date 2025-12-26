package org.municipalite.gestiondechets.repository;

import org.municipalite.gestiondechets.model.Alerte;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
@Repository
public interface AlerteRepository extends MongoRepository<Alerte, String> {
    long countByTraiteeFalse();
    // Chercher les alertes non traitées
    List<Alerte> findByTraiteeFalse();
    List<Alerte> findByTypeAlerteInAndTraiteeFalse(List<String> types);
    // Chercher par type d'alerte
    List<Alerte> findByTypeAlerte(String typeAlerte);
}
