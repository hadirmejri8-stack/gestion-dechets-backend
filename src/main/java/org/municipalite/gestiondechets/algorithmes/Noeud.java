package org.municipalite.gestiondechets.algorithmes;

public class Noeud implements Comparable<Noeud> {
    private String id;
    private double distance;
    public Noeud(String id, double distance) { this.id = id; this.distance = distance; }
    public String getId() { return id; }
    public double getDistance() { return distance; }
    @Override
    public int compareTo(Noeud autre) { return Double.compare(this.distance, autre.distance); }
}
