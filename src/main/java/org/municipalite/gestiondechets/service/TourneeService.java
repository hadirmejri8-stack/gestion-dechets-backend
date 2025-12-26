package org.municipalite.gestiondechets.service;

import org.municipalite.gestiondechets.model.Tournee;

import java.time.LocalDate;
import java.util.List;

public interface TourneeService {

    Tournee createTournee(Tournee tournee);

    List<Tournee> getAllTournees();

    Tournee getTourneeById(String id);

    Tournee updateTournee(String id, Tournee tournee);

    void deleteTournee(String id);

    List<Tournee> getTourneesByZone(String zone);

    List<Tournee> getTourneesByStatus(String status);

    List<Tournee> getTourneesByDate(LocalDate date);
}
