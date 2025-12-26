package org.municipalite.gestiondechets.algorithmes;

import java.util.*;

public class Dijkstra {

    public static ResultatDijkstra calculerCheminPlusCourt(Graphe graphe, String depart, String arrivee) {
        if (!graphe.contientNoeud(depart) || !graphe.contientNoeud(arrivee)) {
            return new ResultatDijkstra(new ArrayList<>(), Double.MAX_VALUE);
        }

        Map<String, Double> distances = new HashMap<>();
        Map<String, String> predecesseurs = new HashMap<>();
        PriorityQueue<Noeud> file = new PriorityQueue<>();
        Set<String> visites = new HashSet<>();

        for (String noeud : graphe.getTousLesNoeuds()) distances.put(noeud, Double.MAX_VALUE);
        distances.put(depart, 0.0);
        file.add(new Noeud(depart, 0.0));

        while (!file.isEmpty()) {
            Noeud current = file.poll();
            if (visites.contains(current.getId())) continue;
            visites.add(current.getId());
            if (current.getId().equals(arrivee)) break;

            for (Arete arete : graphe.getVoisins(current.getId())) {
                String voisin = arete.getDestination();
                if (visites.contains(voisin)) continue;
                double nouvDist = distances.get(current.getId()) + arete.getPoids();
                if (nouvDist < distances.get(voisin)) {
                    distances.put(voisin, nouvDist);
                    predecesseurs.put(voisin, current.getId());
                    file.add(new Noeud(voisin, nouvDist));
                }
            }
        }

        List<String> chemin = new ArrayList<>();
        String current = arrivee;
        while (current != null && !current.equals(depart)) {
            chemin.add(current);
            current = predecesseurs.get(current);
        }
        if (current == null) return ResultatDijkstra.aucunChemin();


        chemin.add(depart);
        Collections.reverse(chemin);

        return new ResultatDijkstra(chemin, distances.get(arrivee));
    }
}
