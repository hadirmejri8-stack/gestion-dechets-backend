package org.municipalite.gestiondechets.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StatistiquesDashboard {
    private long totalPointsCollecte;
    private double tauxRemplissageMoyen;
    private double tauxRecyclage;              // nouveau
    private double co2Evitee;                  // nouveau
    private long tourneesEnCours;
    private long tourneesDuJour;
    private long tourneesTerminees;            // nouveau
    private long tourneesAnnulees;             // nouveau
    private Map<String, Double> performanceParZone; // nouveau
    private Map<String, Integer> quantiteDechetsParType; // nouveau
    private List<String> historiqueIncidents;  // nouveau, on peut mettre String ou Alerte selon besoins
    private Map<String, Long> comparatifMensuel; // nouveau
}
