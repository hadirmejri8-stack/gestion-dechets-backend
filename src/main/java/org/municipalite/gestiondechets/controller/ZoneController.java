package org.municipalite.gestiondechets.controller;

import org.municipalite.gestiondechets.model.Zone;
import org.municipalite.gestiondechets.service.impl.ZoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/zones")
@CrossOrigin("*")
public class ZoneController {

    @Autowired
    private ZoneService zoneService;

    @PostMapping
    public Zone create(@RequestBody Zone zone) {
        return zoneService.createZone(zone);
    }

    @GetMapping
    public List<Zone> getAll() {
        return zoneService.getAllZones();
    }

    @GetMapping("/{id}")
    public Zone getById(@PathVariable String id) {
        return zoneService.getZoneById(id);
    }

    @PutMapping("/{id}")
    public Zone update(@PathVariable String id, @RequestBody Zone zone) {
        return zoneService.updateZone(id, zone);
    }

    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable String id) {
        return zoneService.deleteZone(id);
    }
}
