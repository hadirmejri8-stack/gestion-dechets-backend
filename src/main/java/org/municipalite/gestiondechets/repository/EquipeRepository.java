package org.municipalite.gestiondechets.repository;

import org.municipalite.gestiondechets.model.Equipe;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EquipeRepository extends MongoRepository<Equipe, String> {

    // Chercher équipe par ID du chef
    List<Equipe> findByChefEquipe__id(String chefId);

    // Chercher équipes par nom
    List<Equipe> findByNomContainingIgnoreCase(String nom);

    // Chercher équipes par statut
    List<Equipe> findByStatut(String statut);
}