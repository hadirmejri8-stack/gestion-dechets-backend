package org.municipalite.gestiondechets.service.impl;

import org.municipalite.gestiondechets.model.Zone;
import org.municipalite.gestiondechets.repository.ZoneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ZoneService {

    @Autowired
    private ZoneRepository zoneRepository;

    public Zone createZone(Zone zone) {
        return zoneRepository.save(zone);
    }

    public List<Zone> getAllZones() {
        return zoneRepository.findAll();
    }

    public Zone getZoneById(String id) {
        return zoneRepository.findById(id).orElse(null);
    }

    public Zone updateZone(String id, Zone newZone) {
        Zone zone = getZoneById(id);
        if (zone == null) return null;

        zone.setNom(newZone.getNom());
        return zoneRepository.save(zone);
    }

    public boolean deleteZone(String id) {
        Zone zone = getZoneById(id);
        if (zone == null) return false;

        zoneRepository.delete(zone);
        return true;
    }
}
