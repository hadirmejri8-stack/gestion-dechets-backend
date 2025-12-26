package org.municipalite.gestiondechets.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Document(collection = "alertes")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Alerte {
    @Id
    private String _id;

    @DocumentReference(lazy = true)  // Changé de @DBRef à @DocumentReference
    private PointCollecte pointCollecte;

    private LocalDateTime dateAlerte;
    private String typeAlerte; // Niveau élevé, Conteneur endommagé, etc.
    private int niveauRemplissage;
    private String message;
    private boolean traitee;

    public int getStatut() {

        return 0;
    }
}