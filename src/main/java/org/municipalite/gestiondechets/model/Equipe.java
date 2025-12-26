package org.municipalite.gestiondechets.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "equipes")
public class Equipe {

    @Id
    private String _id;


    private Employe chefEquipe;  // référence au chef
    @DocumentReference
    private List<Employe> membres;

    private List<Employe> agents;  // liste des agents

    private String specialite; // "Collecte", "Tri", "Maintenance"
    private String statut;
    private String Nom;

    public void getMembresId() {
    };
}
