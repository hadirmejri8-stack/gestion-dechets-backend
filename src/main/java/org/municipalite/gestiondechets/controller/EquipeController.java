package org.municipalite.gestiondechets.controller;

import org.municipalite.gestiondechets.model.Equipe;
import org.municipalite.gestiondechets.service.EquipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/equipes")
public class EquipeController {

    @Autowired
    private EquipeService equipeService;

    @PostMapping
    public Equipe createEquipe(@RequestBody Equipe equipe) {
        return equipeService.createEquipe(equipe);
    }

    @GetMapping
    public List<Equipe> getAllEquipes() {
        return equipeService.getAllEquipes();
    }

    @GetMapping("/{id}")
    public Equipe getEquipeById(@PathVariable String id) {
        return equipeService.getEquipeById(id);
    }
    @GetMapping("/chef/{chefId}")
    public Equipe getEquipeByChef(@PathVariable String chefId) {
        return equipeService.getEquipeByChefId(chefId);
    }


    @GetMapping("/zone/{zoneId}")
    public List<Equipe> getEquipesByZone(@PathVariable String zoneId) {
        return equipeService.getEquipesByZone(zoneId);
    }

    @PutMapping("/{id}")
    public Equipe updateEquipe(@PathVariable String id, @RequestBody Equipe equipe) {
        return equipeService.updateEquipe(id, equipe);
    }

    @DeleteMapping("/{id}")
    public void deleteEquipe(@PathVariable String id) {
        equipeService.deleteEquipe(id);
    }
}
