package org.municipalite.gestiondechets.controller;

import org.springframework.security.core.Authentication;
import org.municipalite.gestiondechets.model.Employe;
import org.municipalite.gestiondechets.model.Equipe;
import org.municipalite.gestiondechets.repository.EmployeRepository;
import org.municipalite.gestiondechets.service.EquipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/chef")
@PreAuthorize("hasRole('CHEF_EQUIPE')")
public class ChefEquipeController {

    @Autowired
    private EmployeRepository employeRepository;

    @Autowired
    private EquipeService equipeService;

    @GetMapping("/mon-equipe")
    public ResponseEntity<?> getMonEquipe(Authentication authentication) {
        try {
            // 1. Récupérer le chef connecté
            String username = authentication.getName();
            Optional<Employe> chefOptional = employeRepository.findByUsername(username);

            if (chefOptional.isEmpty()) {
                return ResponseEntity.badRequest()
                        .body(Map.of("error", "Chef non trouvé: " + username));
            }

            Employe chef = chefOptional.get();

            // 2. Trouver l'équipe de ce chef
            Equipe equipe = equipeService.getEquipeByChefId(chef.get_id());

            if (equipe == null) {
                return ResponseEntity.ok(Map.of(
                        "message", "Aucune équipe trouvée pour ce chef",
                        "chef", chef
                ));
            }

            // 3. Récupérer les membres de l'équipe
            // VERSION 1: Si Equipe a une liste d'Employe (DocumentReference)
            List<Employe> membres = new ArrayList<>();
            if (equipe.getMembres() != null && !equipe.getMembres().isEmpty()) {
                membres = equipe.getMembres();
            }




            // 4. Préparer la réponse
            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "chef", chef,
                    "equipe", equipe,
                    "membres", membres,
                    "nombreMembres", membres.size()
            ));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest()
                    .body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/employes-disponibles")
    public ResponseEntity<?> getEmployesDisponibles() {
        try {
            List<Employe> employes = employeRepository.findByDisponibleTrue();

            // Filtrer seulement les agents (pas les chefs)
            List<Employe> agents = employes.stream()
                    .filter(e -> "AGENT".equals(e.getRoleCode()) || "Agent".equals(e.getRole()))
                    .collect(Collectors.toList());

            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "employes", agents,
                    "nombre", agents.size()
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/affecter-tournee/{employeId}")
    public ResponseEntity<?> affecterEmployeATournee(
            @PathVariable String employeId,
            @RequestParam String tourneeId) {
        try {
            // Logique d'affectation
            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "message", "Employé affecté à la tournée",
                    "employeId", employeId,
                    "tourneeId", tourneeId
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", e.getMessage()));
        }
    }
}