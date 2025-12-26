package org.municipalite.gestiondechets.service;

import org.municipalite.gestiondechets.model.Employe;

public interface AuthService {
    String login(String username, String password);
    Employe register(Employe employe);
    Employe registerByAdmin(Employe employe);
    Employe getEmployeByUsername(String username);
}