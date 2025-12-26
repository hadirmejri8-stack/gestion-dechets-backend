package org.municipalite.gestiondechets.controller;

import org.municipalite.gestiondechets.model.StatistiquesDashboard;
import org.municipalite.gestiondechets.service.StatistiqueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/api/statistiques")
@CrossOrigin(origins = "http://localhost:3000")
public class StatistiqueController {

    @Autowired
    private StatistiqueService statistiqueService;

    @GetMapping("/dashboard")
    public StatistiquesDashboard getDashboard() {
        return statistiqueService.getDashboard();
    }
}
