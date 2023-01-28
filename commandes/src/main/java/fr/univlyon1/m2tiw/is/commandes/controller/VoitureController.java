package fr.univlyon1.m2tiw.is.commandes.controller;
import fr.univlyon1.m2tiw.is.commandes.dao.NotFoundException;
import fr.univlyon1.m2tiw.is.commandes.model.Option;
import fr.univlyon1.m2tiw.is.commandes.model.Voiture;
import fr.univlyon1.m2tiw.is.commandes.services.*;
import fr.univlyon1.m2tiw.is.commandes.vue.Vue;

import java.sql.SQLException;

public class VoitureController {

    private VoitureService voitureService;
    private final Vue vue = new Vue();

    public VoitureController() {
        try {
            voitureService = new VoitureServiceImpl();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public VoitureController(VoitureService _voitureService) {
        voitureService = _voitureService;
    }

    /**
     * @param modele
     * @return String JSON de la voiture créée
     */
    public String creerVoiture(String modele){
        try{
            return vue.render(voitureService.creerVoiture(modele));
        } catch (SQLException e){
            e.printStackTrace();
        }
         return vue.render();
    }

    public void ajouterConfiguration(Voiture voiture, Option option) {
        try{
            voitureService.ajouterConfiguration(voiture.getId(), option);
        }catch (SQLException | NotFoundException e) {
            e.printStackTrace();
        }
    }

    public void supprimerConfiguration(Voiture voiture, Option option) {
        try {
            voitureService.supprimerConfiguration(voiture.getId(), option);
        }catch (InvalidConfigurationException | SQLException | NotFoundException e){
            e.printStackTrace();
        }
    }
}