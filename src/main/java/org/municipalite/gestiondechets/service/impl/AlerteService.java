package org.municipalite.gestiondechets.service.impl;

import org.municipalite.gestiondechets.model.Alerte;
import org.municipalite.gestiondechets.model.PointCollecte;
import org.municipalite.gestiondechets.repository.AlerteRepository;
import org.municipalite.gestiondechets.repository.PointCollecteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Service
public class AlerteService {

    @Autowired
    private AlerteRepository alerteRepository;

    @Autowired
    private PointCollecteRepository pointCollecteRepository;

    // 🔴 NOUVELLE MÉTHODE : Créer une alerte
    public Alerte createAlerte(String pointCollecteId, String typeAlerte, String message, int niveauRemplissage) {
        Alerte alerte = new Alerte();

        // Si un point de collecte est spécifié, le récupérer
        if (pointCollecteId != null && !pointCollecteId.isEmpty()) {
            PointCollecte point = pointCollecteRepository.findById(pointCollecteId)
                    .orElse(null);
            alerte.setPointCollecte(point);
        }

        // Définir les autres propriétés
        alerte.setDateAlerte(LocalDateTime.now());
        alerte.setTypeAlerte(typeAlerte);
        alerte.setMessage(message);
        alerte.setNiveauRemplissage(niveauRemplissage);
        alerte.setTraitee(false); // Par défaut, non traitée

        return alerteRepository.save(alerte);
    }

    // Liste des alertes urgentes non traitées
    public List<Alerte> getAlertesUrgentes() {
        List<String> typesUrgents = Arrays.asList(
                "Conteneur rempli à 100%",
                "Conteneur endommagé",
                "Aucun employé disponible",
                "Véhicule en panne",
                "Surcharge zone",
                "Véhicule en panne",
                "Point inaccessible",
                "Déchets dangereux",
                "Route bloquée"
        );
        return alerteRepository.findByTypeAlerteInAndTraiteeFalse(typesUrgents);
    }

    // Marquer une alerte comme traitée (après assignation / instruction)
    public Alerte traiterAlerte(String id) {
        Alerte alerte = alerteRepository.findById(id).orElse(null);
        if (alerte != null) {
            alerte.setTraitee(true);
            return alerteRepository.save(alerte);
        }
        return null;
    }

    // Escalader une alerte (exemple simple)
    public Alerte escaladerAlerte(String id) {
        Alerte alerte = alerteRepository.findById(id).orElse(null);
        if (alerte != null) {
            alerte.setMessage(alerte.getMessage() + " [ \uD83D\uDD3A Escaladée au chef d'équipe]");
            return alerteRepository.save(alerte);
        }
        return null;
    }

    public Alerte ignorerAlerte(String id) {
        Alerte a = alerteRepository.findById(id).orElse(null);
        if (a != null) {
            a.setTraitee(true);
            a.setMessage(a.getMessage() + " ❕ Ignorée");
            return alerteRepository.save(a);
        }
        return null;
    }

    public Alerte assignerEquipe(String id, String equipe) {
        Alerte a = alerteRepository.findById(id).orElse(null);
        if (a != null) {
            a.setMessage(a.getMessage() + " 👷 Équipe assignée : " + equipe);
            return alerteRepository.save(a);
        }
        return null;
    }

    public Alerte envoyerInstruction(String id, String instruction) {
        Alerte a = alerteRepository.findById(id).orElse(null);
        if (a != null) {
            a.setMessage(a.getMessage() + " 📩 Instruction envoyée : " + instruction);
            return alerteRepository.save(a);
        }
        return null;
    }

    public Alerte replanifierTournee(String id, String nouvelleDate) {
        Alerte a = alerteRepository.findById(id).orElse(null);
        if (a != null) {
            a.setMessage(a.getMessage() + " 🔄 Tournée replanifiée au " + nouvelleDate);
            return alerteRepository.save(a);
        }
        return null;
    }
}