package org.municipalite.gestiondechets.model;

import lombok.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
@Getter
@Setter
@Document(collection = "points_collecte")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PointCollecte {
    @Id
    private String _id;

    private double latitude;
    private double longitude;
    private String adresse;
    private int niveauRemplissage;
    private String type; // type de déchet (Plastique, Verre, etc.)
    private int capaciteMax;
    private String etat; // Actif / Inactif
}
