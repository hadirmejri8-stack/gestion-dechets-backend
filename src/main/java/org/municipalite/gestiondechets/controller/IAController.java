// IAController.java - Créez ce fichier
package org.municipalite.gestiondechets.controller;

import org.municipalite.gestiondechets.service.IAService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/ia")
public class IAController {

    @Autowired
    private IAService iaService;

    @PostMapping("/optimiser")
    public ResponseEntity<?> optimiserAvecIA() {
        try {
            var tournees = iaService.optimiserAvecIASimple();

            Map<String, Object> response = Map.of(
                    "success", true,
                    "message", "Optimisation IA terminée",
                    "tournees", tournees,
                    "algorithme", "IA Simple - Priorisation + Proximité"
            );

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    Map.of("success", false, "message", e.getMessage())
            );
        }
    }

    @PostMapping("/optimiser-avance")
    public ResponseEntity<?> optimiserAvecIAAvancee() {
        try {
            var tournees = iaService.optimiserAvecIAAvancee();

            Map<String, Object> response = Map.of(
                    "success", true,
                    "message", "Optimisation IA avancée terminée",
                    "tournees", tournees,
                    "algorithme", "IA Avancée - Clustering géographique"
            );

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    Map.of("success", false, "message", e.getMessage())
            );
        }
    }

    @GetMapping("/predire/{pointId}")
    public ResponseEntity<?> predireRemplissage(@PathVariable String pointId) {
        try {
            var prediction = iaService.predireRemplissageFutur(pointId);
            return ResponseEntity.ok(prediction);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    Map.of("error", e.getMessage())
            );
        }
    }

    @GetMapping("/anomalies")
    public ResponseEntity<?> detecterAnomalies() {
        try {
            var anomalies = iaService.detecterAnomalies();
            return ResponseEntity.ok(anomalies);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    Map.of("error", e.getMessage())
            );
        }
    }
}