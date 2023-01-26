package fr.univlyon1.m2tiw.is.commandes.controller;
import fr.univlyon1.m2tiw.is.commandes.dao.CommandeDAOImpl;
import fr.univlyon1.m2tiw.is.commandes.dao.NotFoundException;
import fr.univlyon1.m2tiw.is.commandes.dao.VoitureDAO;
import fr.univlyon1.m2tiw.is.commandes.dao.VoitureDAOImpl;
import fr.univlyon1.m2tiw.is.commandes.model.Commande;
import fr.univlyon1.m2tiw.is.commandes.model.Option;
import fr.univlyon1.m2tiw.is.commandes.model.Voiture;
import fr.univlyon1.m2tiw.is.commandes.services.CommandeCouranteServiceImpl;
import fr.univlyon1.m2tiw.is.commandes.services.VoitureService;
import fr.univlyon1.m2tiw.is.commandes.services.VoitureServiceImpl;

import java.sql.SQLException;
import java.util.Collection;

public class VoitureController {

    private static int counter;
    private VoitureServiceImpl voitureService;
    private CommandeCouranteServiceImpl commandeCouranteService;
    private VoitureDAOImpl voitureDAO;
    private CommandeDAOImpl commandeDAO;


    public Voiture creerVoiture(String modele){
        try{
            return voitureService.creerVoiture(modele);
        } catch (SQLException e){
            e.printStackTrace();
        }
         return null;
    }

    public void ajouterConfiguration(Voiture voiture, Option option) throws SQLException, NotFoundException {
        try{
            voitureService.ajouterConfiguration(voiture.getId(), option);
        }catch (SQLException | NotFoundException e) {
            e.printStackTrace();
        }
    }

    public void supprimerConfiguration(Voiture voiture, Option option) throws SQLException, NotFoundException {
        try {
            voitureService.supprimerConfiguration(voiture.getId(), option);
        }catch (SQLException | NotFoundException e){
            e.printStackTrace();
        }
    }

    public void supprimerVoiture(Voiture voiture) throws SQLException, NotFoundException {
        voitureService.supprimerVoiture(voiture.getId());
    }



}