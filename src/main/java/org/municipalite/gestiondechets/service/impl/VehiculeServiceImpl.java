package org.municipalite.gestiondechets.service.impl;

import org.municipalite.gestiondechets.model.Vehicule;
import org.municipalite.gestiondechets.repository.VehiculeRepository;
import org.municipalite.gestiondechets.service.VehiculeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VehiculeServiceImpl implements VehiculeService {

    @Autowired
    private VehiculeRepository vehiculeRepository;

    @Override
    public Vehicule createVehicule(Vehicule vehicule) {
        return vehiculeRepository.save(vehicule);
    }

    @Override
    public List<Vehicule> getAllVehicules() {
        return vehiculeRepository.findAll();
    }

    @Override
    public Vehicule getVehiculeById(String id) {
        return vehiculeRepository.findById(id).orElse(null);
    }

    @Override
    public Vehicule updateVehicule(String id, Vehicule vehicule) {
        vehicule.set_id(id);
        return vehiculeRepository.save(vehicule);
    }

    @Override
    public void deleteVehicule(String id) {
        vehiculeRepository.deleteById(id);
    }

    @Override
    public List<Vehicule> getVehiculesDisponibles() {
        return vehiculeRepository.findByDisponibleTrue();
    }
}
