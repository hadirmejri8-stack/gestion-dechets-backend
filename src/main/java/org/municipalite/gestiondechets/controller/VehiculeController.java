package org.municipalite.gestiondechets.controller;

import org.municipalite.gestiondechets.model.Vehicule;
import org.municipalite.gestiondechets.service.VehiculeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vehicules")
@CrossOrigin("*")
public class VehiculeController {

    @Autowired
    private VehiculeService vehiculeService;

    @PostMapping
    public Vehicule createVehicule(@RequestBody Vehicule vehicule) {
        return vehiculeService.createVehicule(vehicule);
    }

    @GetMapping
    public List<Vehicule> getAllVehicules() {
        return vehiculeService.getAllVehicules();
    }

    @GetMapping("/{id}")
    public Vehicule getVehiculeById(@PathVariable String id) {
        return vehiculeService.getVehiculeById(id);
    }

    @PutMapping("/{id}")
    public Vehicule updateVehicule(@PathVariable String id, @RequestBody Vehicule vehicule) {
        return vehiculeService.updateVehicule(id, vehicule);
    }

    @DeleteMapping("/{id}")
    public void deleteVehicule(@PathVariable String id) {
        vehiculeService.deleteVehicule(id);
    }

    @GetMapping("/disponibles")
    public List<Vehicule> getVehiculesDisponibles() {
        return vehiculeService.getVehiculesDisponibles();
    }
}

