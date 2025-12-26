package org.municipalite.gestiondechets.service.impl;

import org.municipalite.gestiondechets.model.Tournee;
import org.municipalite.gestiondechets.model.Zone;
import org.municipalite.gestiondechets.repository.TourneeRepository;
import org.municipalite.gestiondechets.repository.ZoneRepository;
import org.municipalite.gestiondechets.service.TourneeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class TourneeServiceImpl implements TourneeService {

    @Autowired
    private TourneeRepository repository;
    @Autowired
    private ZoneRepository zoneRepository;

    @Override
    public Tournee createTournee(Tournee tournee) {
        if (tournee.getZone() != null && tournee.getZone().get_id() != null) {
            Optional<Zone> zoneOpt = zoneRepository.findById(tournee.getZone().get_id());
            zoneOpt.ifPresent(tournee::setZone);
        }
        return repository.save(tournee);
    }

    @Override
    public List<Tournee> getAllTournees() {
        return repository.findAll();
    }

    @Override
    public Tournee getTourneeById(String id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public Tournee updateTournee(String id, Tournee tournee) {
        Optional<Tournee> existing = repository.findById(id);
        if (existing.isPresent()) {
            Tournee t = existing.get();
            t.setDate(tournee.getDate());
            t.setHeureDepart(tournee.getHeureDepart());

            t.setStatus(tournee.getStatus());
            t.setEmploye(tournee.getEmploye());
            t.setVehicule(tournee.getVehicule());
            t.setPointsCollecte(tournee.getPointsCollecte());
            t.setDureeEstimee(tournee.getDureeEstimee());
            if (tournee.getZone() != null && tournee.getZone().get_id() != null) {
                Optional<Zone> zoneOpt = zoneRepository.findById(tournee.getZone().get_id());
                zoneOpt.ifPresent(t::setZone);
            }
            return repository.save(t);
        }
        return null;
    }

    @Override
    public void deleteTournee(String id) {
        repository.deleteById(id);
    }

    @Override
    public List<Tournee> getTourneesByZone(String zone) {
        return repository.findByZone(zone);
    }

    @Override
    public List<Tournee> getTourneesByStatus(String status) {
        return repository.findByStatus(status);
    }

    @Override
    public List<Tournee> getTourneesByDate(LocalDate date) {
        return repository.findByDate(date);
    }
}
