package fr.univlyon1.m2tiw.is.commandes.controller;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
     * @param modeleJSON modèle en JSON
     * @return String JSON de la voiture créée
     */
    public String creerVoiture(String modeleJSON){
        ObjectMapper mapper = new ObjectMapper();
        try{
            String modele = mapper.readValue(modeleJSON, String.class);
            return vue.render(voitureService.creerVoiture(modele));
        } catch (SQLException | JsonProcessingException e){
            e.printStackTrace();
        }
         return vue.render();
    }

    public String ajouterConfiguration(String voitureJSON, String optionJSON) {
        ObjectMapper mapper = new ObjectMapper();
        try{
            Voiture voiture = mapper.readValue(voitureJSON, Voiture.class);
            Option option = mapper.readValue(optionJSON, Option.class);
            voitureService.ajouterConfiguration(voiture.getId(), option);
            return vue.render(voitureService.getVoiture(voiture.getId()));
        }catch (SQLException | NotFoundException | JsonProcessingException e) {
            e.printStackTrace();
        }
        return vue.render();
    }

    public String supprimerConfiguration(String voitureJSON, String optionJSON) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            Voiture voiture = mapper.readValue(voitureJSON, Voiture.class);
            Option option = mapper.readValue(optionJSON, Option.class);
            voitureService.supprimerConfiguration(voiture.getId(), option);
            return vue.render(voitureService.getVoiture(voiture.getId()));
        }catch (InvalidConfigurationException
                | SQLException
                | NotFoundException
                | JsonProcessingException e){
            e.printStackTrace();
        }
        return vue.render();
    }
}