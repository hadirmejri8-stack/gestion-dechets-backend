// Dans AuthServiceImpl.java
package org.municipalite.gestiondechets.service.impl;

import org.municipalite.gestiondechets.model.Employe;
import org.municipalite.gestiondechets.repository.EmployeRepository;
import org.municipalite.gestiondechets.service.AuthService;
import org.municipalite.gestiondechets.service.CustomUserDetailsService;
import org.municipalite.gestiondechets.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private EmployeRepository employeRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Override
    public String login(String username, String password) {
        var userDetails = userDetailsService.loadUserByUsername(username);

        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new RuntimeException("Mot de passe incorrect");
        }

        if (!userDetails.isEnabled()) {
            throw new RuntimeException("Compte désactivé");
        }

        // Récupère l'employé pour générer le token
        Employe employe = getEmployeByUsername(username);
        return jwtService.generateToken(employe);
    }

    @Override
    public Employe register(Employe employe) {
        Optional<Employe> existingByUsername = employeRepository.findByUsername(employe.getUsername());
        if (existingByUsername.isPresent()) {
            throw new RuntimeException("Nom d'utilisateur déjà existant");
        }



        employe.setPassword(passwordEncoder.encode(employe.getPassword()));
        employe.setEnabled(true);
        return employeRepository.save(employe);
    }

    @Override
    public Employe registerByAdmin(Employe employe) {
        return register(employe);  // Même logique
    }

    @Override
    public Employe getEmployeByUsername(String username) {
        return employeRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Employé non trouvé: " + username));
    }
}