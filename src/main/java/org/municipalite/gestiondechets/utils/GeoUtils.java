package org.municipalite.gestiondechets.utils;

/**
 * Utilitaires pour les calculs géographiques
 */
public class GeoUtils {

    private static final int RAYON_TERRE_KM = 6371;

    /**
     * Calcule la distance entre deux coordonnées GPS (formule de Haversine)
     * Cette formule prend en compte la courbure de la Terre
     *
     * @param lat1 Latitude du point 1
     * @param lon1 Longitude du point 1
     * @param lat2 Latitude du point 2
     * @param lon2 Longitude du point 2
     * @return Distance en kilomètres
     */
    public static double calculerDistance(double lat1, double lon1, double lat2, double lon2) {
        // Conversion en radians
        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);

        // Formule de Haversine
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return RAYON_TERRE_KM * c;
    }

    /**
     * Calcule la distance en mètres
     */
    public static double calculerDistanceEnMetres(double lat1, double lon1, double lat2, double lon2) {
        return calculerDistance(lat1, lon1, lat2, lon2) * 1000;
    }
}
