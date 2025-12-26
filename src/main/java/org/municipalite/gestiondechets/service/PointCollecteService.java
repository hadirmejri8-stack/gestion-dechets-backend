package org.municipalite.gestiondechets.service;

import org.municipalite.gestiondechets.model.PointCollecte;
import java.util.List;

public interface PointCollecteService {

    // Créer un nouveau point
    PointCollecte createPoint(PointCollecte point);

    // Récupérer tous les points
    List<PointCollecte> getAllPoints();

    // Récupérer un point par son id
    PointCollecte getPointById(String id);

    // Mettre à jour un point
    PointCollecte updatePoint(String id, PointCollecte point);

    // Supprimer un point
    void deletePoint(String id);



    // Filtrer par état
    List<PointCollecte> getPointsByEtat(String etat);
}
