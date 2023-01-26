package fr.univlyon1.m2tiw.is.commandes.controller;

import fr.univlyon1.m2tiw.is.commandes.dao.NotFoundException;
import fr.univlyon1.m2tiw.is.commandes.model.Commande;
import fr.univlyon1.m2tiw.is.commandes.model.Voiture;
import fr.univlyon1.m2tiw.is.commandes.services.CommandeCouranteServiceImpl;
import fr.univlyon1.m2tiw.is.commandes.services.EmptyCommandeException;
import fr.univlyon1.m2tiw.is.commandes.services.GestionCommandeServiceImpl;

import java.sql.SQLException;

public class CommandeController {
    private GestionCommandeServiceImpl gestionCommandeService;
    private CommandeCouranteServiceImpl commandeCouranteService;

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
            long id = commandeCouranteService.validerCommandeCourante();
            return id;
        } catch (EmptyCommandeException | SQLException | NotFoundException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public Commande getCommande(Long id) {
        try {
            Commande commande = gestionCommandeService.getCommande(id);
            return commande;
        } catch (SQLException | NotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }



}
