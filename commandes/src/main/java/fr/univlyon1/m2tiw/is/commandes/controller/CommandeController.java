package fr.univlyon1.m2tiw.is.commandes.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.univlyon1.m2tiw.is.commandes.dao.NotFoundException;
import fr.univlyon1.m2tiw.is.commandes.services.*;
import fr.univlyon1.m2tiw.is.commandes.vue.Vue;

import java.sql.SQLException;

public class CommandeController extends Controller {
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

    public String ajouterVoiture(String voitureIdJSON) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            Long id = mapper.readValue(voitureIdJSON, Long.class);
            commandeCouranteService.ajouterVoiture(id);
            return vue.render(commandeCouranteService.getCommandeCourante());
        } catch (SQLException | NotFoundException | JsonProcessingException e) {
            e.printStackTrace();
        }
        return vue.render();
    }

    public String supprimerVoiture(String voitureIdJSON) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            Long id = mapper.readValue(voitureIdJSON, Long.class);
            commandeCouranteService.supprimerVoiture(id);
            return vue.render(commandeCouranteService.getCommandeCourante());
        }  catch (SQLException | NotFoundException | JsonProcessingException e) {
            e.printStackTrace();
        }
        return vue.render();
    }
    public String validerCommandeCourante()  {
        try {
            return vue.render(commandeCouranteService.validerCommandeCourante());
        } catch ( SQLException | NotFoundException e) {
            e.printStackTrace();
        } catch (EmptyCommandeException e) {
            return vue.render(e.getMessage());
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
