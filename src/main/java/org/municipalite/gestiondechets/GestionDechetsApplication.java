package org.municipalite.gestiondechets;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class GestionDechetsApplication {

    public static void main(String[] args) {
        SpringApplication.run(GestionDechetsApplication.class, args);
        System.out.println("🚀 Gestion de déchets - Backend lancé !");}
    }