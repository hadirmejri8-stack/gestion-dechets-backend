// Tournee.java - VOICI LA VERSION COMPLÈTE AVEC distanceEstimee
package org.municipalite.gestiondechets.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonFormat;

@Getter
@Setter
@Document(collection = "tournees")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Tournee {
    @Id
    private String _id;

    private LocalDate date;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    private LocalTime heureDepart;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    private LocalTime heureFinEstimee;
    private LocalDateTime dateHeureFinEstimee;
    @DocumentReference
    private Zone zone;
    private String status; // Planifiée, En cours, Terminée

    @DocumentReference
    private Employe employe;

    @DocumentReference
    private Vehicule vehicule;

    @DocumentReference
    private List<PointCollecte> pointsCollecte;

    private int dureeEstimee; // en minutes
    private double distanceEstimee; // en km - NOUVEAU CHAMP

    // Constructeur pour Builder
    public static class TourneeBuilder {
        private String _id;
        private LocalDate date = LocalDate.now();
        private LocalTime heureDepart = LocalTime.now();
        private String status = "PLANIFIÉE";
        private int dureeEstimee = 0;
        private double distanceEstimee = 0.0;
    }
    public void calculerHeureFinEstimee() {
        if (this.heureDepart != null && this.dureeEstimee > 0) {
            this.heureFinEstimee = this.heureDepart.plusMinutes(this.dureeEstimee);
            this.dateHeureFinEstimee = LocalDateTime.of(this.date, this.heureFinEstimee);
        }
    }
}