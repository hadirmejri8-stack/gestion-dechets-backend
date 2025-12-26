package org.municipalite.gestiondechets.controller;

import org.municipalite.gestiondechets.model.PointCollecte;
import org.municipalite.gestiondechets.service.PointCollecteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/points")
public class PointCollecteController {

    @Autowired
    private PointCollecteService service;

    @GetMapping
    public List<PointCollecte> getAllPoints() {
        return service.getAllPoints();
    }

    @GetMapping("/{id}")
    public PointCollecte getPoint(@PathVariable String id) {
        return service.getPointById(id);
    }

    @PostMapping
    public PointCollecte createPoint(@RequestBody PointCollecte point) {
        return service.createPoint(point);
    }

    @PutMapping("/{id}")
    public PointCollecte updatePoint(@PathVariable String id, @RequestBody PointCollecte point) {
        return service.updatePoint(id, point);
    }

    @DeleteMapping("/{id}")
    public void deletePoint(@PathVariable String id) {
        service.deletePoint(id);
    }



    @GetMapping("/etat/{etat}")
    public List<PointCollecte> getPointsByEtat(@PathVariable String etat) {
        return service.getPointsByEtat(etat);
    }
}
