package org.municipalite.gestiondechets.controller;

import org.municipalite.gestiondechets.model.Tournee;
import org.municipalite.gestiondechets.service.OptimisationTourneeService;
import org.municipalite.gestiondechets.service.TourneeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/tournees")
public class TourneeController {

    @Autowired
    private TourneeService service;

    @Autowired
    private OptimisationTourneeService optimisationTourneeService;

    @GetMapping
    public List<Tournee> getAllTournees() {
        return service.getAllTournees();
    }

    @GetMapping("/{id}")
    public Tournee getTournee(@PathVariable String id) {
        return service.getTourneeById(id);
    }

    @PostMapping
    public Tournee createTournee(@RequestBody Tournee tournee) {
        return service.createTournee(tournee);
    }

    @PutMapping("/{id}")
    public Tournee updateTournee(@PathVariable String id, @RequestBody Tournee tournee) {
        return service.updateTournee(id, tournee);
    }

    @DeleteMapping("/{id}")
    public void deleteTournee(@PathVariable String id) {
        service.deleteTournee(id);
    }

    @GetMapping("/zone/{zone}")
    public List<Tournee> getTourneesByZone(@PathVariable String zone) {
        return service.getTourneesByZone(zone);
    }

    @GetMapping("/status/{status}")
    public List<Tournee> getTourneesByStatus(@PathVariable String status) {
        return service.getTourneesByStatus(status);
    }

    @GetMapping("/date/{date}")
    public List<Tournee> getTourneesByDate(@PathVariable String date) {
        return service.getTourneesByDate(LocalDate.parse(date));
    }
    // TourneeController.java - Mettez à jour la méthode optimiser
    @PostMapping("/optimiser")
    public ResponseEntity<?> optimiserTournees() {
        try {
            List<Tournee> tournees = optimisationTourneeService.genererTourneesOptimisees();

            if (tournees.isEmpty()) {
                return ResponseEntity.ok(Map.of(
                        "success", true,
                        "message", "Aucun point critique à collecter"
                ));
            }

            Tournee tournee = tournees.get(0);

            // Calculer les statistiques
            Map<String, Object> statistiques = optimisationTourneeService.calculerStatistiques(tournee);

            // Réponse complète
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Tournée optimisée générée avec succès");
            response.put("tournee", tournee);
            response.put("statistiques", statistiques);

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(
                    Map.of("success", false, "message", e.getMessage())
            );
        }
    }

}
