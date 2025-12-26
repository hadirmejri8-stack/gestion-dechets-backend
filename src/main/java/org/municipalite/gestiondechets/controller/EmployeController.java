package org.municipalite.gestiondechets.controller;

import org.municipalite.gestiondechets.model.Employe;
import org.municipalite.gestiondechets.service.EmployeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "http://localhost:3000")
@RestController

@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("/api/employes")
public class EmployeController {

    @Autowired
    private EmployeService service;

    @PostMapping
    public Employe createEmploye(@RequestBody Employe employe) {
        return service.createEmploye(employe);
    }

    @GetMapping
    public List<Employe> getAllEmployes() {
        return service.getAllEmployes();
    }

    @GetMapping("/{id}")
    public Employe getEmployeById(@PathVariable String id) {
        return service.getEmployeById(id);
    }


    @GetMapping("/disponibles")
    public List<Employe> getEmployesDisponibles() {
        return service.getEmployesDisponibles();
    }

    @PutMapping("/{id}")
    public Employe updateEmploye(@PathVariable String id, @RequestBody Employe employe) {
        return service.updateEmploye(id, employe);
    }

    @DeleteMapping("/{id}")
    public void deleteEmploye(@PathVariable String id) {
        service.deleteEmploye(id);
    }
}
