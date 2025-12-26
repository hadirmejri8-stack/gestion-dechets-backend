package org.municipalite.gestiondechets.algorithmes;
import java.util.ArrayList;
import java.util.List;

public class ResultatDijkstra {
    private List<String> chemin;
    private double distanceTotale;
    public ResultatDijkstra(List<String> chemin, double distanceTotale) { this.chemin = chemin; this.distanceTotale = distanceTotale; }
    public List<String> getChemin() { return chemin; }
    public double getDistanceTotale() { return distanceTotale; }
    public boolean cheminTrouve() { return chemin != null && !chemin.isEmpty(); }
    public static ResultatDijkstra aucunChemin() {
        return new ResultatDijkstra(new ArrayList<>(), Double.MAX_VALUE);
    }

}
