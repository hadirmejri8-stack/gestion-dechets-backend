package org.municipalite.gestiondechets.algorithmes;

public class Arete {
    private String destination;
    private double poids;

    public Arete(String destination, double poids) {
        this.destination = destination;
        this.poids = poids;
    }

    public String getDestination() { return destination; }
    public double getPoids() { return poids; }
}
