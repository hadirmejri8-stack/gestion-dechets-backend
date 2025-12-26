package org.municipalite.gestiondechets.service.impl;

import org.municipalite.gestiondechets.model.PointCollecte;
import org.municipalite.gestiondechets.repository.PointCollecteRepository;
import org.municipalite.gestiondechets.service.PointCollecteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PointCollecteServiceImpl implements PointCollecteService {

    @Autowired
    private PointCollecteRepository repository;

    @Override
    public PointCollecte createPoint(PointCollecte point) {
        // Zone n'existe plus dans PointCollecte, on enlève la partie Zone
        return repository.save(point);
    }

    @Override
    public List<PointCollecte> getAllPoints() {
        // Plus besoin de charger les zones
        return repository.findAll();
    }

    @Override
    public PointCollecte getPointById(String id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public PointCollecte updatePoint(String id, PointCollecte point) {
        Optional<PointCollecte> existing = repository.findById(id);
        if (existing.isPresent()) {
            PointCollecte p = existing.get();
            p.setLatitude(point.getLatitude());
            p.setLongitude(point.getLongitude());
            p.setAdresse(point.getAdresse());
            // p.setZone(point.getZone()); // SUPPRIMÉ
            p.setType(point.getType());
            p.setNiveauRemplissage(point.getNiveauRemplissage());
            p.setCapaciteMax(point.getCapaciteMax());
            p.setEtat(point.getEtat());
            return repository.save(p);
        }
        return null;
    }

    @Override
    public void deletePoint(String id) {
        repository.deleteById(id);
    }

    // SUPPRIMÉ - plus de zone dans PointCollecte
    // public List<PointCollecte> getPointsByZone(String zoneId) {
    //     return repository.findByZoneId(zoneId);
    // }

    @Override
    public List<PointCollecte> getPointsByEtat(String etat) {
        return repository.findByEtat(etat);
    }
}