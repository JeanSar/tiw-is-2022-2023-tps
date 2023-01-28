package fr.univlyon1.m2tiw.is.commandes.controller;

import fr.univlyon1.m2tiw.is.commandes.dao.NotFoundException;
import fr.univlyon1.m2tiw.is.commandes.model.Commande;
import fr.univlyon1.m2tiw.is.commandes.services.*;

import java.sql.SQLException;

public class CommandeController {
    private GestionCommandeService gestionCommandeService;
    private CommandeCouranteService commandeCouranteService;

    public CommandeController() {
        try {
            gestionCommandeService = new GestionCommandeServiceImpl();
            commandeCouranteService = new CommandeCouranteServiceImpl();
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    public void ajouterVoiture(Long voitureId) {
        try {
            commandeCouranteService.ajouterVoiture(voitureId);
        } catch (SQLException | NotFoundException e) {
            e.printStackTrace();
        }
    }

    public void supprimerVoiture(Long voitureId) {
        try {
            commandeCouranteService.supprimerVoiture(voitureId);
        }  catch (SQLException | NotFoundException e) {
            e.printStackTrace();
        }
    }
    public long validerCommandeCourante()  {
        try {
            return commandeCouranteService.validerCommandeCourante();
        } catch (EmptyCommandeException | SQLException | NotFoundException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public Commande getCommande(Long id) {
        try {
            return gestionCommandeService.getCommande(id);
        } catch (SQLException | NotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }



}