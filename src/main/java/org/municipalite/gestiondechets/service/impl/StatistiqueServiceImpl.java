package org.municipalite.gestiondechets.service.impl;

import org.municipalite.gestiondechets.model.*;
import org.municipalite.gestiondechets.repository.*;
import org.municipalite.gestiondechets.service.StatistiqueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class StatistiqueServiceImpl implements StatistiqueService {

    @Autowired
    private PointCollecteRepository pointCollecteRepository;

    @Autowired
    private TourneeRepository tourneeRepository;

    @Autowired
    private AlerteRepository alerteRepository;

    @Override
    public StatistiquesDashboard getDashboard() {
        StatistiquesDashboard dashboard = new StatistiquesDashboard();

        // Total points collecte
        List<PointCollecte> points = pointCollecteRepository.findAll();
        dashboard.setTotalPointsCollecte(points.size());

        // Taux de remplissage moyen
        dashboard.setTauxRemplissageMoyen(
                points.stream().mapToInt(PointCollecte::getNiveauRemplissage).average().orElse(0));

        // Taux de recyclage
        int total = points.stream().mapToInt(PointCollecte::getNiveauRemplissage).sum();
        int recyclable = points.stream()
                .filter(p -> Arrays.asList("Plastique", "Verre", "Metal").contains(p.getType()))
                .mapToInt(PointCollecte::getNiveauRemplissage).sum();
        dashboard.setTauxRecyclage(total == 0 ? 0 : recyclable * 100.0 / total);

        // CO2 évitée (exemple simplifié)
        double co2 = tourneeRepository.findAll().stream()
                .mapToDouble(t -> t.getDistanceEstimee() * 0.21) // kg CO2/km
                .sum();
        dashboard.setCo2Evitee(co2);

        // Tournées
        List<Tournee> tournees = tourneeRepository.findAll();
        dashboard.setTourneesEnCours(tournees.stream().filter(t -> "En cours".equals(t.getStatus())).count());
        dashboard.setTourneesDuJour(tournees.stream().filter(t -> LocalDate.now().equals(t.getDate())).count());
        dashboard.setTourneesTerminees(tournees.stream().filter(t -> "Terminée".equals(t.getStatus())).count());
        dashboard.setTourneesAnnulees(tournees.stream().filter(t -> "Annulée".equals(t.getStatus())).count());

        // Performance par zone - SUPPRIMÉ TEMPORAIREMENT
        // Map<String, Double> perfZone = points.stream()
        //         .collect(Collectors.groupingBy(
        //                 p -> p.getZone().get_id(),  // <-- Ligne problématique
        //                 Collectors.averagingInt(PointCollecte::getNiveauRemplissage)
        //         ));
        // dashboard.setPerformanceParZone(perfZone);

        // Version simplifiée sans zone
        dashboard.setPerformanceParZone(new HashMap<>());

        // Quantité déchets par type
        Map<String, Integer> quantiteParType = points.stream()
                .collect(Collectors.groupingBy(PointCollecte::getType,
                        Collectors.summingInt(PointCollecte::getNiveauRemplissage)));
        dashboard.setQuantiteDechetsParType(quantiteParType);

        // Historique incidents
        List<String> incidents = alerteRepository.findAll(Sort.by(Sort.Direction.DESC, "dateAlerte"))
                .stream().map(a -> a.getTypeAlerte() + " - " + a.getDateAlerte())
                .collect(Collectors.toList());
        dashboard.setHistoriqueIncidents(incidents);

        // Comparatif mensuel
        Map<String, Long> comparatif = tournees.stream()
                .collect(Collectors.groupingBy(t -> YearMonth.from(t.getDate()).toString(), Collectors.counting()));
        dashboard.setComparatifMensuel(comparatif);

        return dashboard;
    }
}