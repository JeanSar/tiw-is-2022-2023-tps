package fr.univlyon1.m2tiw.is.commandes.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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

    public void ajouterVoiture(String voitureIdJSON) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            Long id = mapper.readValue(voitureIdJSON, Long.class);
            commandeCouranteService.ajouterVoiture(id);
        } catch (SQLException | NotFoundException | JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    public void supprimerVoiture(String voitureIdJSON) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            Long id = mapper.readValue(voitureIdJSON, Long.class);
            commandeCouranteService.supprimerVoiture(id);
        }  catch (SQLException | NotFoundException | JsonProcessingException e) {
            e.printStackTrace();
        }
    }
    public String validerCommandeCourante()  {
        try {
            return vue.render(commandeCouranteService.validerCommandeCourante());
        } catch (EmptyCommandeException | SQLException | NotFoundException e) {
            e.printStackTrace();
        }
        return vue.render();
    }
    /**
     * @param commandeIdJSON
     * @return String JSON de la commande
     */
    public String getCommande(String commandeIdJSON) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            Long id = mapper.readValue(commandeIdJSON, Long.class);
            return vue.render(gestionCommandeService.getCommande(id));
        } catch (SQLException | NotFoundException | JsonProcessingException e) {
            e.printStackTrace();
        }
        return vue.render();
    }



}
