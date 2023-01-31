package fr.univlyon1.m2tiw.is.commandes.controller;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.univlyon1.m2tiw.is.commandes.dao.NotFoundException;
import fr.univlyon1.m2tiw.is.commandes.model.Option;
import fr.univlyon1.m2tiw.is.commandes.model.Voiture;
import fr.univlyon1.m2tiw.is.commandes.resources.VoitureResource;
import fr.univlyon1.m2tiw.is.commandes.vue.Vue;

import java.sql.SQLException;
import java.util.Map;

public class VoitureController extends AbstractController {

    private final VoitureResource voitureResource;
    private final Vue vue;


    public VoitureController(VoitureResource _voitureService, Vue _vue) {
        voitureResource = _voitureService;
        vue = _vue;
    }

    @Override
    public Object process(String ressource, String methode, Map<String, Object> parametres) {
        switch (ressource) {
            case "voiture":
                switch (methode) {
                    case "create":
                        return creerVoiture(parametres);
                    case "add":
                        return ajouterConfiguration(parametres);
                    case "delete":
                        return supprimerConfiguration(parametres);
                    default:
                        return vue.renderMethodeNotFound();
                }
            default:
                return vue.renderResourceNotFound();
        }
    }

    private String creerVoiture(Map<String, Object> parametres){
        String modeleJSON = (String) parametres.get("modele");
        if(null == modeleJSON)
            return vue.renderParametreNotFound("modele");
        try{
            ObjectMapper mapper = new ObjectMapper();
            String modele = mapper.readValue(modeleJSON, String.class);
            return vue.render(voitureResource.creerVoiture(modele));
        } catch (SQLException | JsonProcessingException e){
            e.printStackTrace();
        }
         return vue.render();
    }

    private String ajouterConfiguration(Map<String, Object> parametres) {
        String voitureJSON = (String) parametres.get("voiture");
        if(null == voitureJSON)
            return vue.renderParametreNotFound("voiture");
        String optionJSON = (String) parametres.get("option");
        if(null == optionJSON)
            return vue.renderParametreNotFound("option");
        try{
            ObjectMapper mapper = new ObjectMapper();
            Voiture voiture = mapper.readValue(voitureJSON, Voiture.class);
            Option option = mapper.readValue(optionJSON, Option.class);
            voitureResource.ajouterConfiguration(voiture.getId(), option);
            return vue.render(voitureResource.getVoiture(voiture.getId()));
        }catch (SQLException | NotFoundException | JsonProcessingException e) {
            e.printStackTrace();
        }
        return vue.render();
    }

    private String supprimerConfiguration(Map<String, Object> parametres) {
        String voitureJSON = (String) parametres.get("voiture");
        if(null == voitureJSON)
            return vue.renderParametreNotFound("voiture");
        String optionJSON = (String) parametres.get("option");
        if(null == optionJSON)
            return vue.renderParametreNotFound("option");
        try {
            ObjectMapper mapper = new ObjectMapper();
            Voiture voiture = mapper.readValue(voitureJSON, Voiture.class);
            Option option = mapper.readValue(optionJSON, Option.class);
            voitureResource.supprimerConfiguration(voiture.getId(), option);
            return vue.render(voitureResource.getVoiture(voiture.getId()));
        }catch (SQLException
                | NotFoundException
                | JsonProcessingException e){
            e.printStackTrace();
        }
        return vue.render();
    }
}