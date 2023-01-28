package fr.univlyon1.m2tiw.is.commandes.controller;
import fr.univlyon1.m2tiw.is.commandes.dao.NotFoundException;
import fr.univlyon1.m2tiw.is.commandes.model.Option;
import fr.univlyon1.m2tiw.is.commandes.model.Voiture;
import fr.univlyon1.m2tiw.is.commandes.services.*;

import java.sql.SQLException;

public class VoitureController {

    private VoitureService voitureService;
    public VoitureController() {
        try {
            voitureService = new VoitureServiceImpl();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Voiture creerVoiture(String modele){
        try{
            return voitureService.creerVoiture(modele);
        } catch (SQLException e){
            e.printStackTrace();
        }
         return null;
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