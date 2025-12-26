package org.municipalite.gestiondechets.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDate;

@Document(collection = "vehicules")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Vehicule {
    @Id
    private String _id;

    private String typeVehicule;
    private String matricule;
    private int capacite;
    private int kilometrage;
    private LocalDate dernierEntretien;
    private boolean disponible;

    private double latitude;   // Ajout localisation
    private double longitude;  // Ajout localisation
}
