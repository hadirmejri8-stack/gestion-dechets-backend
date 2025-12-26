package org.municipalite.gestiondechets.service.impl;

import org.municipalite.gestiondechets.model.Employe;
import org.municipalite.gestiondechets.model.Zone;
import org.municipalite.gestiondechets.repository.EmployeRepository;
import org.municipalite.gestiondechets.repository.ZoneRepository;
import org.municipalite.gestiondechets.service.EmployeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service // ← ENLEVEZ "abstract" et gardez seulement @Service
public class EmployeServiceImpl implements EmployeService {

    @Autowired
    private EmployeRepository employeRepository;

    @Autowired
    private ZoneRepository zoneRepository;

    @Override
    public Employe createEmploye(Employe employe) {
        // Hydrater la zone si seulement l'ID est fourni
        if (employe.getZone() != null && employe.getZone().get_id() != null) {
            Zone zone = zoneRepository.findById(employe.getZone().get_id()).orElse(null);
            employe.setZone(zone);
        }
        return employeRepository.save(employe);
    }

    @Override
    public List<Employe> getAllEmployes() {
        List<Employe> employes = employeRepository.findAll();

        // Hydrater les zones pour chaque employé
        employes.forEach(employe -> {
            if (employe.getZone() != null && employe.getZone().get_id() != null) {
                Zone zone = zoneRepository.findById(employe.getZone().get_id()).orElse(null);
                employe.setZone(zone);
            }
        });

        return employes;
    }

    @Override
    public Employe getEmployeById(String id) {
        Optional<Employe> employeOpt = employeRepository.findById(id);
        if (employeOpt.isPresent()) {
            Employe employe = employeOpt.get();
            // Hydrater la zone
            if (employe.getZone() != null && employe.getZone().get_id() != null) {
                Zone zone = zoneRepository.findById(employe.getZone().get_id()).orElse(null);
                employe.setZone(zone);
            }
            return employe;
        }
        return null;
    }

    // MODIFIEZ CETTE MÉTHODE - Supprimez ou adaptez


    @Override
    public List<Employe> getEmployesDisponibles() {
        List<Employe> employes = employeRepository.findByDisponibleTrue();

        // Hydrater les zones
        employes.forEach(employe -> {
            if (employe.getZone() != null && employe.getZone().get_id() != null) {
                Zone zone = zoneRepository.findById(employe.getZone().get_id()).orElse(null);
                employe.setZone(zone);
            }
        });

        return employes;
    }

    @Override
    public Employe updateEmploye(String id, Employe employe) {
        Optional<Employe> existingOpt = employeRepository.findById(id);
        if (existingOpt.isPresent()) {
            Employe existing = existingOpt.get();

            existing.setNom(employe.getNom());
            existing.setPrenom(employe.getPrenom());
            existing.setRole(employe.getRole());
            existing.setRoleCode(employe.getRoleCode());
            existing.setTelephone(employe.getTelephone());
            existing.setEmail(employe.getEmail());
            existing.setDisponible(employe.isDisponible());

            // Gérer la zone
            if (employe.getZone() != null && employe.getZone().get_id() != null) {
                Zone zone = zoneRepository.findById(employe.getZone().get_id()).orElse(null);
                existing.setZone(zone);
            } else {
                existing.setZone(null);
            }

            return employeRepository.save(existing);
        }
        return null;
    }

    @Override
    public void deleteEmploye(String id) {
        employeRepository.deleteById(id);
    }
}