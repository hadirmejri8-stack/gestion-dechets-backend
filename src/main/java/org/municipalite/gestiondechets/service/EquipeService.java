package org.municipalite.gestiondechets.service;

import org.municipalite.gestiondechets.model.Equipe;
import java.util.List;

public interface EquipeService {
    Equipe createEquipe(Equipe equipe);
    List<Equipe> getAllEquipes();
    Equipe getEquipeById(String id);
    Equipe getEquipeByChefId(String chefId);

    List<Equipe> getEquipesByZone(String zoneId);
    Equipe updateEquipe(String id, Equipe equipe);
    void deleteEquipe(String id);
}
