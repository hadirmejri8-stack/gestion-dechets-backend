package org.municipalite.gestiondechets.repository;

import org.municipalite.gestiondechets.model.PointCollecte;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PointCollecteRepository extends MongoRepository<PointCollecte, String> {

    // SUPPRIMEZ CETTE MÉTHODE (elle cherche zoneId qui n'existe plus)
    // List<PointCollecte> findByNiveauRemplissageGreaterThanAndZoneId(int niveau, ObjectId zoneId);

    // GARDEZ CETTE MÉTHODE (sans zone)
    List<PointCollecte> findByNiveauRemplissageGreaterThan(int niveau);

    List<PointCollecte> findByEtat(String etat);

    // Si vous avez besoin de filtrer par type
    List<PointCollecte> findByType(String type);

    // Si vous avez besoin de points avec niveau élevé
    @Query("{ 'niveauRemplissage': { $gt: ?0 } }")
    List<PointCollecte> findPointsCritiques(int seuil);

    // Autres méthodes utiles
    List<PointCollecte> findByNiveauRemplissageBetween(int min, int max);
}