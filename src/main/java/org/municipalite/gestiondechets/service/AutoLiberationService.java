// AutoLiberationService.java - CRÉEZ CE FICHIER
package org.municipalite.gestiondechets.service;

import org.municipalite.gestiondechets.model.Tournee;
import org.municipalite.gestiondechets.model.Vehicule;
import org.municipalite.gestiondechets.repository.TourneeRepository;
import org.municipalite.gestiondechets.repository.VehiculeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AutoLiberationService {

    @Autowired
    private TourneeRepository tourneeRepository;

    @Autowired
    private VehiculeRepository vehiculeRepository;

    /**
     * Vérifie toutes les minutes si une tournée est terminée
     * et libère son véhicule automatiquement
     */
    @Scheduled(fixedRate = 60000) // Toutes les 60 secondes
    public void libererVehiculesTourneesTerminees() {
        System.out.println("⏰ Vérification auto libération véhicules...");

        // Récupérer toutes les tournées PLANIFIÉES avec date de fin estimée dépassée
        List<Tournee> tournees = tourneeRepository.findByStatus("PLANIFIÉE");

        int vehiculesLiberes = 0;

        for (Tournee tournee : tournees) {
            if (tournee.getVehicule() != null &&
                    tournee.getDateHeureFinEstimee() != null &&
                    LocalDateTime.now().isAfter(tournee.getDateHeureFinEstimee())) {

                // Marquer la tournée comme TERMINÉE
                tournee.setStatus("TERMINÉE");
                tourneeRepository.save(tournee);

                // Libérer le véhicule
                Vehicule vehicule = tournee.getVehicule();
                vehicule.setDisponible(true);
                vehiculeRepository.save(vehicule);

                vehiculesLiberes++;

                System.out.println("✅ Véhicule auto-libéré: " + vehicule.getMatricule() +
                        " - Tournée: " + tournee.get_id());
            }
        }

        if (vehiculesLiberes > 0) {
            System.out.println("🎉 " + vehiculesLiberes + " véhicule(s) libéré(s) automatiquement");
        }
    }

    /**
     * Libération de secours - Vérifie les véhicules bloqués depuis longtemps
     * (au cas où la date de fin estimée n'est pas définie)
     */
    @Scheduled(fixedRate = 300000) // Toutes les 5 minutes
    public void libererVehiculesBloques() {
        System.out.println("🔧 Vérification véhicules bloqués...");

        // Récupérer tous les véhicules non disponibles
        List<Vehicule> vehiculesBloques = vehiculeRepository.findByDisponibleFalse();

        for (Vehicule vehicule : vehiculesBloques) {
            // Trouver la tournée associée au véhicule
            List<Tournee> tourneesVehicule = tourneeRepository.findByVehiculeAndStatus(vehicule, "PLANIFIÉE");

            if (tourneesVehicule.isEmpty()) {
                // Si pas de tournée PLANIFIÉE pour ce véhicule, le libérer
                vehicule.setDisponible(true);
                vehiculeRepository.save(vehicule);
                System.out.println("🔄 Véhicule débloqué (sans tournée): " + vehicule.getMatricule());
            }
        }
    }
}