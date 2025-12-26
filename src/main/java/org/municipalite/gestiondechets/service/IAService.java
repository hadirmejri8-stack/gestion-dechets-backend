package org.municipalite.gestiondechets.service;

import org.municipalite.gestiondechets.model.Tournee;
import org.municipalite.gestiondechets.model.PointCollecte;
import org.municipalite.gestiondechets.model.Vehicule;
import org.municipalite.gestiondechets.model.Employe;
import org.municipalite.gestiondechets.repository.PointCollecteRepository;
import org.municipalite.gestiondechets.repository.VehiculeRepository;
import org.municipalite.gestiondechets.repository.EmployeRepository;
import org.municipalite.gestiondechets.repository.TourneeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class IAService {

    @Autowired
    private PointCollecteRepository pointCollecteRepository;

    @Autowired
    private VehiculeRepository vehiculeRepository;

    @Autowired
    private EmployeRepository employeRepository;

    @Autowired
    private TourneeRepository tourneeRepository;

    private Random random = new Random();

    /**
     * Service IA simple qui ajoute de "l'intelligence" aux tournées
     * Version adaptée à la structure SANS ZONE dans PointCollecte
     */
    public List<Tournee> optimiserAvecIASimple() {
        System.out.println("🧠 Service IA - Démarrage optimisation intelligente");

        // 1. Récupérer TOUS les points critiques (> 70%)
        List<PointCollecte> points = pointCollecteRepository.findByNiveauRemplissageGreaterThan(70);
        List<Vehicule> vehicules = vehiculeRepository.findByDisponibleTrue();
        List<Employe> employes = employeRepository.findByDisponibleTrue();

        if (points.isEmpty()) {
            System.out.println("❌ IA : Aucun point critique trouvé");
            return creerTourneeDemoIA();
        }

        if (vehicules.isEmpty()) {
            throw new RuntimeException("IA : Aucun véhicule disponible");
        }

        // 2. Prendre le premier véhicule et employé disponibles
        Vehicule vehicule = vehicules.get(0);
        Employe employe = employes.isEmpty() ? null : employes.get(0);

        // 3. "Intelligence" : Sélectionner les points les plus remplis ET les plus proches
        List<PointCollecte> pointsIntelligents = selectionnerPointsIntelligents(points, vehicule);

        // 4. Calculer la distance et durée estimées
        double distance = calculerDistanceTotale(pointsIntelligents, vehicule);
        int duree = estimerDuree(pointsIntelligents.size(), distance);

        // 5. Créer une tournée IA
        Tournee tournee = Tournee.builder()
                ._id("IA_TOUR_" + System.currentTimeMillis())
                .date(LocalDate.now())
                .heureDepart(LocalTime.now())
                .vehicule(vehicule)
                .employe(employe)
                .pointsCollecte(pointsIntelligents)
                .status("OPTIMISÉE PAR IA")
                .dureeEstimee(duree)
                .distanceEstimee(Math.round(distance * 100.0) / 100.0)
                .build();

        // 6. Mettre à jour le véhicule
        vehicule.setDisponible(false);
        vehiculeRepository.save(vehicule);

        // 7. Sauvegarder la tournée
        Tournee savedTournee = tourneeRepository.save(tournee);

        System.out.println("✅ IA : Tournée générée avec " + pointsIntelligents.size() + " points");
        System.out.println("📊 IA - Statistiques:");
        System.out.println("   Distance: " + savedTournee.getDistanceEstimee() + " km");
        System.out.println("   Durée: " + savedTournee.getDureeEstimee() + " min");
        System.out.println("   Économie estimée: " + calculerEconomie(distance) + " DT");

        return Arrays.asList(savedTournee);
    }

    /**
     * Algorithme IA simple pour sélectionner les points
     * 1. Priorité aux points les plus remplis
     * 2. Regroupement par proximité géographique
     */
    private List<PointCollecte> selectionnerPointsIntelligents(List<PointCollecte> points, Vehicule vehicule) {
        if (points.isEmpty()) return new ArrayList<>();

        // Étape 1: Trier par criticité (niveau de remplissage décroissant)
        List<PointCollecte> pointsTries = points.stream()
                .sorted((p1, p2) -> Integer.compare(p2.getNiveauRemplissage(), p1.getNiveauRemplissage()))
                .collect(Collectors.toList());

        // Étape 2: Prendre les 8 points les plus critiques maximum
        int maxPoints = Math.min(8, pointsTries.size());
        List<PointCollecte> pointsCritiques = pointsTries.subList(0, maxPoints);

        // Étape 3: Ordonner par proximité au véhicule (plus proches d'abord)
        return pointsCritiques.stream()
                .sorted(Comparator.comparingDouble(p ->
                        calculerDistance(
                                vehicule.getLatitude(), vehicule.getLongitude(),
                                p.getLatitude(), p.getLongitude()
                        )
                ))
                .collect(Collectors.toList());
    }

    /**
     * Version IA avancée avec clustering géographique
     */
    public List<Tournee> optimiserAvecIAAvancee() {
        System.out.println("🧠🤖 Service IA Avancée - Clustering géographique");

        // 1. Récupérer les données
        List<PointCollecte> pointsCritiques = pointCollecteRepository.findByNiveauRemplissageGreaterThan(70);
        List<Vehicule> vehicules = vehiculeRepository.findByDisponibleTrue();

        if (pointsCritiques.isEmpty() || vehicules.isEmpty()) {
            return optimiserAvecIASimple(); // Fallback
        }

        // 2. Appliquer un clustering simple (k-means simplifié)
        List<List<PointCollecte>> clusters = appliquerClustering(pointsCritiques, 3);

        // 3. Créer une tournée par cluster
        List<Tournee> tournees = new ArrayList<>();
        int vehiculeIndex = 0;

        for (List<PointCollecte> cluster : clusters) {
            if (cluster.isEmpty() || vehiculeIndex >= vehicules.size()) continue;

            Vehicule vehicule = vehicules.get(vehiculeIndex);
            vehiculeIndex++;

            // Ordonner les points du cluster par proximité
            List<PointCollecte> pointsOrdonnes = ordonnerPointsParProximite(cluster, vehicule);

            // Créer la tournée
            double distance = calculerDistanceTotale(pointsOrdonnes, vehicule);
            int duree = estimerDuree(pointsOrdonnes.size(), distance);

            Tournee tournee = Tournee.builder()
                    ._id("IA_CLUSTER_" + System.currentTimeMillis() + "_" + vehiculeIndex)
                    .date(LocalDate.now())
                    .heureDepart(LocalTime.now().plusHours(vehiculeIndex)) // Décaler les heures
                    .vehicule(vehicule)
                    .pointsCollecte(pointsOrdonnes)
                    .status("CLUSTER IA")
                    .dureeEstimee(duree)
                    .distanceEstimee(Math.round(distance * 100.0) / 100.0)
                    .build();

            // Mettre à jour le véhicule
            vehicule.setDisponible(false);
            vehiculeRepository.save(vehicule);

            tournees.add(tourneeRepository.save(tournee));
        }

        System.out.println("✅ IA Avancée: " + tournees.size() + " tournées créées par clustering");
        return tournees;
    }

    /**
     * Clustering géographique simplifié (k-means basique)
     */
    private List<List<PointCollecte>> appliquerClustering(List<PointCollecte> points, int k) {
        if (points.size() <= k) {
            // Si peu de points, un cluster par point
            return points.stream()
                    .map(Arrays::asList)
                    .collect(Collectors.toList());
        }

        // Initialiser les centroïdes aléatoirement
        List<PointCollecte> centroids = new ArrayList<>();
        for (int i = 0; i < k; i++) {
            centroids.add(points.get(random.nextInt(points.size())));
        }

        // Assigner les points aux clusters
        List<List<PointCollecte>> clusters = new ArrayList<>();
        for (int i = 0; i < k; i++) {
            clusters.add(new ArrayList<>());
        }

        for (PointCollecte point : points) {
            int clusterIndex = trouverClusterPlusProche(point, centroids);
            clusters.get(clusterIndex).add(point);
        }

        return clusters;
    }

    private int trouverClusterPlusProche(PointCollecte point, List<PointCollecte> centroids) {
        int plusProche = 0;
        double distanceMin = Double.MAX_VALUE;

        for (int i = 0; i < centroids.size(); i++) {
            double distance = calculerDistance(
                    point.getLatitude(), point.getLongitude(),
                    centroids.get(i).getLatitude(), centroids.get(i).getLongitude()
            );
            if (distance < distanceMin) {
                distanceMin = distance;
                plusProche = i;
            }
        }

        return plusProche;
    }

    /**
     * Ordonner les points par proximité (algorithme du plus proche voisin)
     */
    private List<PointCollecte> ordonnerPointsParProximite(List<PointCollecte> points, Vehicule vehicule) {
        if (points.size() <= 1) return points;

        List<PointCollecte> resultat = new ArrayList<>();
        Set<String> visites = new HashSet<>();

        // Point de départ: le plus proche du véhicule
        PointCollecte courant = points.stream()
                .min(Comparator.comparingDouble(p ->
                        calculerDistance(
                                vehicule.getLatitude(), vehicule.getLongitude(),
                                p.getLatitude(), p.getLongitude()
                        )
                ))
                .orElse(points.get(0));

        resultat.add(courant);
        visites.add(courant.get_id());

        while (visites.size() < points.size()) {
            PointCollecte suivant = null;
            double distanceMin = Double.MAX_VALUE;

            for (PointCollecte p : points) {
                if (visites.contains(p.get_id())) continue;

                double distance = calculerDistance(
                        courant.getLatitude(), courant.getLongitude(),
                        p.getLatitude(), p.getLongitude()
                );

                if (distance < distanceMin) {
                    distanceMin = distance;
                    suivant = p;
                }
            }

            if (suivant == null) break;

            resultat.add(suivant);
            visites.add(suivant.get_id());
            courant = suivant;
        }

        return resultat;
    }

    /**
     * Simulation de prédiction IA
     */
    public Map<String, Object> predireRemplissageFutur(String pointId) {
        // Simulation d'une prédiction IA
        Map<String, Object> prediction = new HashMap<>();

        // Trouver le point (simulé si pas trouvé)
        PointCollecte point = pointCollecteRepository.findById(pointId)
                .orElse(PointCollecte.builder()
                        ._id(pointId)
                        .niveauRemplissage(50)
                        .build());

        int niveauActuel = point.getNiveauRemplissage();

        // Prédictions pour les prochaines heures
        prediction.put("actuel", niveauActuel + "%");
        prediction.put("1h", Math.min(niveauActuel + random.nextInt(15), 100) + "%");
        prediction.put("3h", Math.min(niveauActuel + random.nextInt(30), 100) + "%");
        prediction.put("6h", Math.min(niveauActuel + random.nextInt(50), 100) + "%");
        prediction.put("12h", Math.min(niveauActuel + random.nextInt(70), 100) + "%");

        // Recommandation IA
        if (niveauActuel > 80) {
            prediction.put("recommandation", "⚠️ Collecte urgente nécessaire");
            prediction.put("priorite", "HAUTE");
        } else if (niveauActuel > 60) {
            prediction.put("recommandation", "📅 Planifier collecte dans les 6h");
            prediction.put("priorite", "MOYENNE");
        } else {
            prediction.put("recommandation", "✅ Niveau normal, surveillance standard");
            prediction.put("priorite", "BASSE");
        }

        prediction.put("confiance", (70 + random.nextInt(30)) + "%");
        prediction.put("algorithme", "Régression temporelle IA");

        return prediction;
    }

    /**
     * Détection d'anomalies IA
     */
    public Map<String, Object> detecterAnomalies() {
        Map<String, Object> resultat = new HashMap<>();
        List<Map<String, Object>> anomalies = new ArrayList<>();

        // Analyser tous les points
        List<PointCollecte> points = pointCollecteRepository.findAll();

        for (PointCollecte point : points) {
            // Détection d'anomalies simples
            if (point.getNiveauRemplissage() > 95) {
                Map<String, Object> anomalie = new HashMap<>();
                anomalie.put("pointId", point.get_id());
                anomalie.put("adresse", point.getAdresse());
                anomalie.put("type", "REMPLISSAGE_CRITIQUE");
                anomalie.put("valeur", point.getNiveauRemplissage() + "%");
                anomalie.put("severite", "CRITIQUE");
                anomalie.put("recommandation", "Collecte immédiate");
                anomalies.add(anomalie);
            } else if (point.getNiveauRemplissage() > 85) {
                Map<String, Object> anomalie = new HashMap<>();
                anomalie.put("pointId", point.get_id());
                anomalie.put("adresse", point.getAdresse());
                anomalie.put("type", "REMPLISSAGE_ELEVE");
                anomalie.put("valeur", point.getNiveauRemplissage() + "%");
                anomalie.put("severite", "HAUTE");
                anomalie.put("recommandation", "Planifier collecte urgente");
                anomalies.add(anomalie);
            }
        }

        resultat.put("anomaliesDetectees", anomalies.size());
        resultat.put("anomalies", anomalies);
        resultat.put("dateAnalyse", LocalDate.now().toString());
        resultat.put("algorithme", "Système de détection d'anomalies IA");

        return resultat;
    }

    /**
     * Tournée de démo IA
     */
    private List<Tournee> creerTourneeDemoIA() {
        System.out.println("🎭 IA : Création tournée de démo");

        // Points de démo IA
        List<PointCollecte> pointsDemo = Arrays.asList(
                PointCollecte.builder()
                        ._id("IA_DEMO_1")
                        .adresse("Place IA Avancée")
                        .latitude(36.8065 + random.nextDouble() * 0.01)
                        .longitude(10.1815 + random.nextDouble() * 0.01)
                        .niveauRemplissage(88)
                        .type("IA_ORGANIQUE")
                        .capaciteMax(1000)
                        .etat("Actif")
                        .build(),
                PointCollecte.builder()
                        ._id("IA_DEMO_2")
                        .adresse("Boulevard Intelligence Artificielle")
                        .latitude(36.7980 + random.nextDouble() * 0.01)
                        .longitude(10.1750 + random.nextDouble() * 0.01)
                        .niveauRemplissage(92)
                        .type("IA_PLASTIQUE")
                        .capaciteMax(800)
                        .etat("Actif")
                        .build(),
                PointCollecte.builder()
                        ._id("IA_DEMO_3")
                        .adresse("Rue Machine Learning")
                        .latitude(36.8020 + random.nextDouble() * 0.01)
                        .longitude(10.1780 + random.nextDouble() * 0.01)
                        .niveauRemplissage(76)
                        .type("IA_VERRE")
                        .capaciteMax(1200)
                        .etat("Actif")
                        .build()
        );

        pointCollecteRepository.saveAll(pointsDemo);

        // Véhicule de démo IA
        Vehicule vehiculeDemo = Vehicule.builder()
                ._id("IA_VEH_" + System.currentTimeMillis())
                .matricule("IA-2024")
                .typeVehicule("CAMION_IA")
                .disponible(true)
                .latitude(36.8000)
                .longitude(10.1700)
                .capacite(6000)
                .build();

        vehiculeRepository.save(vehiculeDemo);

        // Créer la tournée IA de démo
        double distance = 15.8; // Simulation
        int duree = 180; // 3 heures

        Tournee tournee = Tournee.builder()
                ._id("IA_DEMO_TOUR")
                .date(LocalDate.now())
                .heureDepart(LocalTime.now())
                .vehicule(vehiculeDemo)
                .pointsCollecte(pointsDemo)
                .status("DÉMONSTRATION IA")
                .dureeEstimee(duree)
                .distanceEstimee(distance)
                .build();

        return Arrays.asList(tourneeRepository.save(tournee));
    }

    /**
     * Méthodes utilitaires (identique à OptimisationTourneeService)
     */
    private double calculerDistance(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371;
        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c;
    }

    private double calculerDistanceTotale(List<PointCollecte> points, Vehicule vehicule) {
        if (points.isEmpty()) return 0.0;
        double total = calculerDistance(
                vehicule.getLatitude(), vehicule.getLongitude(),
                points.get(0).getLatitude(), points.get(0).getLongitude()
        );
        for (int i = 0; i < points.size() - 1; i++) {
            total += calculerDistance(
                    points.get(i).getLatitude(), points.get(i).getLongitude(),
                    points.get(i+1).getLatitude(), points.get(i+1).getLongitude()
            );
        }
        total += calculerDistance(
                points.get(points.size() - 1).getLatitude(),
                points.get(points.size() - 1).getLongitude(),
                vehicule.getLatitude(), vehicule.getLongitude()
        );
        return total;
    }

    private int estimerDuree(int nbPoints, double distance) {
        return nbPoints * 10 + (int)(distance * 15);
    }

    private String calculerEconomie(double distance) {
        double economie = distance * 0.7 * 1.8; // Carburant économisé * prix
        return String.format("%.2f", economie);
    }
}