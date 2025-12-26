package org.municipalite.gestiondechets.repository;

import org.municipalite.gestiondechets.model.Employe;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeRepository extends MongoRepository<Employe, String> {

    // AJOUTEZ UNIQUEMENT CETTE LIGNE
    Optional<Employe> findByUsername(String username);

    // Gardez vos autres méthodes
    List<Employe> findByRole(String role);
    List<Employe> findByDisponibleTrue();
    List<Employe> findByRoleAndDisponibleTrue(String role);
}