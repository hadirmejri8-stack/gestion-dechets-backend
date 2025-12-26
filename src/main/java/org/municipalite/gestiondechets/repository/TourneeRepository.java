package org.municipalite.gestiondechets.repository;

import org.municipalite.gestiondechets.model.Tournee;
import org.municipalite.gestiondechets.model.Vehicule;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
@Repository
public interface TourneeRepository extends MongoRepository<Tournee, String> {
    long countByStatus(String status);
    long countByDate(LocalDate date);
    // Tournées d'une date spécifique
    List<Tournee> findByDate(LocalDate date);

    // Tournées par zone
    List<Tournee> findByZone(String zone);

    // Tournées par statut
    List<Tournee> findByStatus(String status);

    List<Tournee> findByVehiculeAndStatus(Vehicule vehicule, String status);
    List<Tournee> findByDateHeureFinEstimeeBefore(LocalDateTime dateTime);
}
