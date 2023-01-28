package fr.univlyon1.m2tiw.is.commandes.controller;

import fr.univlyon1.m2tiw.is.commandes.dao.NotFoundException;
import fr.univlyon1.m2tiw.is.commandes.services.*;
import fr.univlyon1.m2tiw.is.commandes.vue.Vue;

import java.sql.SQLException;

public class CommandeController {
    private GestionCommandeService gestionCommandeService;
    private CommandeCouranteService commandeCouranteService;
    private final Vue vue = new Vue();

    public CommandeController() {
        try {
            commandeCouranteService = new CommandeCouranteServiceImpl();
            gestionCommandeService = new GestionCommandeServiceImpl();
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }
    public CommandeController(CommandeCouranteService _commandeCouranteService,
                              GestionCommandeService _gestionCommandeService) {
        commandeCouranteService = _commandeCouranteService;
        gestionCommandeService = _gestionCommandeService;
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
    /**
     * @param id
     * @return String JSON de la commande
     */
    public String getCommande(Long id) {
        try {
            return vue.render(gestionCommandeService.getCommande(id));
        } catch (SQLException | NotFoundException e) {
            e.printStackTrace();
        }
        return vue.render();
    }



}
