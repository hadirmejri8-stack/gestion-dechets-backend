package org.municipalite.gestiondechets.repository;

import org.municipalite.gestiondechets.model.Vehicule;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface VehiculeRepository extends MongoRepository<Vehicule, String> {
    // Chercher les véhicules disponibles
    List<Vehicule> findByDisponibleTrue();
    List<Vehicule> findByDisponibleFalse();
    // Chercher par type de véhicule
    List<Vehicule> findByTypeVehicule(String typeVehicule);
}
