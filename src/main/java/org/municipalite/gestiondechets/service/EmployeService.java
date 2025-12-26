package org.municipalite.gestiondechets.service;

import org.municipalite.gestiondechets.model.Employe;
import java.util.List;

public interface EmployeService {

    Employe createEmploye(Employe employe);

    List<Employe> getAllEmployes();

    Employe getEmployeById(String id);


    List<Employe> getEmployesDisponibles();

    Employe updateEmploye(String id, Employe employe);

    void deleteEmploye(String id);
}
