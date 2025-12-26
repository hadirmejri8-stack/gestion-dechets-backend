package org.municipalite.gestiondechets.service;

import org.municipalite.gestiondechets.model.Employe;
import org.municipalite.gestiondechets.repository.EmployeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private EmployeRepository employeRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Employe> employe = employeRepository.findByUsername(username);
        if (employe.isEmpty()) {
            throw new UsernameNotFoundException("Utilisateur non trouvé: " + username);
        }

        Employe e = employe.get();

        return User.builder()
                .username(e.getUsername())
                .password(e.getPassword())
                .roles(e.getRole())  // Spring ajoute automatiquement "ROLE_"
                .disabled(!e.isEnabled())
                .build();
    }
}