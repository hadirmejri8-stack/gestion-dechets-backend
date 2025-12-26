package org.municipalite.gestiondechets.algorithmes;

import java.util.*;

public class Graphe {
    private Map<String, List<Arete>> adjacence = new HashMap<>();

    public void ajouterNoeud(String id) {
        adjacence.putIfAbsent(id, new ArrayList<>());
    }

    public void ajouterArete(String src, String dest, double distance) {
        adjacence.putIfAbsent(src, new ArrayList<>());
        adjacence.putIfAbsent(dest, new ArrayList<>());
        adjacence.get(src).add(new Arete(dest, distance));
        adjacence.get(dest).add(new Arete(src, distance)); // bidirectionnel
    }

    public List<Arete> getVoisins(String id) {
        return adjacence.getOrDefault(id, new ArrayList<>());
    }

    public Set<String> getTousLesNoeuds() {
        return adjacence.keySet();
    }

    public boolean contientNoeud(String id) {
        return adjacence.containsKey(id);
    }
}
