package org.municipalite.gestiondechets.service;

import org.municipalite.gestiondechets.model.Vehicule;

import java.util.List;

public interface VehiculeService {

    Vehicule createVehicule(Vehicule vehicule);

    List<Vehicule> getAllVehicules();

    Vehicule getVehiculeById(String id);

    Vehicule updateVehicule(String id, Vehicule vehicule);

    void deleteVehicule(String id);

    List<Vehicule> getVehiculesDisponibles();
}
