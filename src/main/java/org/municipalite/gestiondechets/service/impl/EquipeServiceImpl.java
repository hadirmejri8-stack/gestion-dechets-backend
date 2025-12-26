package org.municipalite.gestiondechets.service.impl;

import org.municipalite.gestiondechets.model.Employe;
import org.municipalite.gestiondechets.model.Equipe;
import org.municipalite.gestiondechets.repository.EmployeRepository;
import org.municipalite.gestiondechets.repository.EquipeRepository;
import org.municipalite.gestiondechets.service.EquipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service  // Gardez cette annotation
public class EquipeServiceImpl implements EquipeService {

    @Autowired
    private EquipeRepository equipeRepository;

    @Autowired
    private EmployeRepository employeRepository;

    @Override
    public Equipe createEquipe(Equipe equipe) {
        // Vérifier s'il y a un chef
        if (equipe.getChefEquipe() != null && equipe.getChefEquipe().get_id() != null) {
            Employe chef = employeRepository.findById(equipe.getChefEquipe().get_id()).orElse(null);
            equipe.setChefEquipe(chef);
        }

        // Vérifier s'il y a des agents
        if (equipe.getAgents() != null) {
            List<Employe> agents = equipe.getAgents().stream()
                    .map(a -> employeRepository.findById(a.get_id()).orElse(null))
                    .filter(agent -> agent != null)  // Filtrer les null
                    .toList();
            equipe.setAgents(agents);
        }

        return equipeRepository.save(equipe);
    }

    @Override
    public List<Equipe> getAllEquipes() {
        return equipeRepository.findAll();
    }

    @Override
    public Equipe getEquipeById(String id) {
        return equipeRepository.findById(id).orElse(null);
    }

    @Override
    public Equipe getEquipeByChefId(String chefId) {
        return equipeRepository.findByChefEquipe__id(chefId)
                .stream()
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Equipe> getEquipesByZone(String zoneId) {
        // Si vous avez cette méthode dans le repository
        // return equipeRepository.findByZoneId(zoneId);
        // Sinon retournez une liste vide temporairement
        return List.of();
    }

    @Override
    public Equipe updateEquipe(String id, Equipe equipe) {
        return equipeRepository.findById(id).map(existing -> {
            // Mettre à jour le chef
            if (equipe.getChefEquipe() != null && equipe.getChefEquipe().get_id() != null) {
                Employe chef = employeRepository.findById(equipe.getChefEquipe().get_id()).orElse(null);
                existing.setChefEquipe(chef);
            }

            // Mettre à jour les agents
            if (equipe.getAgents() != null) {
                List<Employe> agents = equipe.getAgents().stream()
                        .map(a -> employeRepository.findById(a.get_id()).orElse(null))
                        .filter(agent -> agent != null)
                        .toList();
                existing.setAgents(agents);
            }

            // Mettre à jour les autres champs
            if (equipe.getNom() != null) existing.setNom(equipe.getNom());
            if (equipe.getStatut() != null) existing.setStatut(equipe.getStatut());

            return equipeRepository.save(existing);
        }).orElseThrow(() -> new RuntimeException("Équipe introuvable avec id: " + id));
    }

    @Override
    public void deleteEquipe(String id) {
        equipeRepository.deleteById(id);
    }
}