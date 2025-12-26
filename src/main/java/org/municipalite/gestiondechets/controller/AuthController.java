package org.municipalite.gestiondechets.controller;

import org.municipalite.gestiondechets.model.Employe;
import org.municipalite.gestiondechets.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credentials) {
        try {
            String username = credentials.get("username");
            String password = credentials.get("password");

            System.out.println("🔐 Login attempt: " + username);

            // Utilise ton AuthServiceImpl
            String token = authService.login(username, password);

            // Récupère l'employé pour les infos supplémentaires
            Employe employe = authService.getEmployeByUsername(username);

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Connexion réussie");
            response.put("token", token);
            response.put("username", employe.getUsername());
            response.put("role", employe.getRole());
            response.put("roleCode", employe.getRoleCode());
            response.put("nom", employe.getNom());
            response.put("prenom", employe.getPrenom());
            response.put("email", employe.getEmail());

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                    "success", false,
                    "message", e.getMessage()
            ));
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Employe employe) {
        try {
            System.out.println("📝 Register: " + employe.getUsername());

            Employe savedEmploye = authService.register(employe);

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Employé créé avec succès");
            response.put("data", savedEmploye);

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                    "success", false,
                    "message", e.getMessage()
            ));
        }
    }

    // Endpoint de test
    @GetMapping("/test")
    public ResponseEntity<?> test() {
        return ResponseEntity.ok(Map.of(
                "status", "OK",
                "message", "API fonctionne",
                "timestamp", System.currentTimeMillis()
        ));
    }
}