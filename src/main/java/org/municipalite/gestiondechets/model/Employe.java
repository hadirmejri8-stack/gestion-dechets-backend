package org.municipalite.gestiondechets.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "employes")
public class Employe implements UserDetails {

    @Id
    private String _id;

    private String nom;
    private String prenom;
    private String role; // Agent, Chef, Admin, etc.
    private String roleCode; // AGENT, CHEF_EQUIPE, ADMIN, TECHNICIEN, RECYCLAGE
    private String telephone;
    private String email;
    private boolean disponible;

    // Champs pour l'authentification
    private String username;
    private String password;
    private boolean enabled = true;

    @DocumentReference
    private Zone zone;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + roleCode));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public enum RoleUtilisateur {
        ADMIN("ROLE_ADMIN", "Responsable Propreté"),
        CHEF_EQUIPE("ROLE_CHEF_EQUIPE", "Chef d'Équipe"),
        AGENT_COLLECTE("ROLE_AGENT", "Agent de Collecte"),
        CENTRE_TECHNIQUE("ROLE_TECH", "Centre Technique"),
        RESPONSABLE_RECYCLAGE("ROLE_RECYCLAGE", "Responsable du Recyclage");

        private final String authority;
        private final String nomAffichage;

        RoleUtilisateur(String authority, String nomAffichage) {
            this.authority = authority;
            this.nomAffichage = nomAffichage;
        }

        public String getAuthority() { return authority; }
        public String getNomAffichage() { return nomAffichage; }
    }


}