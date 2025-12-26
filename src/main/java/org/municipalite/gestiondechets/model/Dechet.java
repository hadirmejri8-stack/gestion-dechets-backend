package org.municipalite.gestiondechets.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "dechets")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Dechet {
    @Id
    private String _id;

    private String type; // Plastique, Verre, Métal, etc.
    private String description;
    private String conditionnement; // Sacs jaunes, bacs, etc.
    private boolean recyclable;
    private double valeurRecyclage; // en monnaie ou point
}
