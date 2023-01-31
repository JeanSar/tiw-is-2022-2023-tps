package fr.univlyon1.m2tiw.is.commandes.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.univlyon1.m2tiw.is.commandes.dao.NotFoundException;
import fr.univlyon1.m2tiw.is.commandes.resources.CommandeArchiveeResource;
import fr.univlyon1.m2tiw.is.commandes.resources.CommandeCouranteResource;
import fr.univlyon1.m2tiw.is.commandes.services.*;
import fr.univlyon1.m2tiw.is.commandes.vue.Vue;

import java.sql.SQLException;
import java.util.Map;

public class CommandeController extends AbstractController {
    private final CommandeArchiveeResource commandeArchiveeResource;
    private final CommandeCouranteResource commandeCouranteResource;
    private final CommandeCouranteService commandeCouranteService;
    private final Vue vue;


    public CommandeController(CommandeArchiveeResource _commandeArchiveeResource,
                              CommandeCouranteResource _commandeCouranteResource,
                              CommandeCouranteService _commandeCouranteService,
                              Vue _vue) {
        this.commandeArchiveeResource = _commandeArchiveeResource;
        this.commandeCouranteResource = _commandeCouranteResource;
        this.commandeCouranteService = _commandeCouranteService;
        vue = _vue;
    }

    @Override
    public Object process(String ressource, String methode, Map<String, Object> parametres) {
        switch (ressource) {
            case "commandeArchivee":
                switch (methode) {
                    case "get":
                        return getCommande(parametres);
                    default:
                        return vue.renderMethodeNotFound();
                }
            case "commandeCourante":
                switch (methode) {
                    case "add":
                        return ajouterVoiture(parametres);
                    case "delete":
                        return supprimerVoiture(parametres);
                    case "validate":
                        return validerCommandeCourante();
                    default:
                        return vue.renderMethodeNotFound();
                }
            default:
                return vue.renderResourceNotFound();
        }
    }

    private String ajouterVoiture(Map<String, Object> parametres) {
        String voitureIdJSON = (String) parametres.get("id");
        if(null == voitureIdJSON)
            return vue.renderParametreNotFound("id");
        try {
            ObjectMapper mapper = new ObjectMapper();
            Long id = mapper.readValue(voitureIdJSON, Long.class);
            commandeCouranteService.ajouterVoiture(id);
            return vue.render(commandeCouranteService.getCommandeCourante());
        } catch (SQLException | NotFoundException | JsonProcessingException e) {
            e.printStackTrace();
        }
        return vue.render();
    }

    private String supprimerVoiture(Map<String, Object> parametres) {
        String voitureIdJSON = (String) parametres.get("id");
        if(null == voitureIdJSON)
            return vue.renderParametreNotFound("id");
        try {
            ObjectMapper mapper = new ObjectMapper();
            Long id = mapper.readValue(voitureIdJSON, Long.class);
            commandeCouranteService.supprimerVoiture(id);
            return vue.render(commandeCouranteService.getCommandeCourante());
        }  catch (SQLException | NotFoundException | JsonProcessingException e) {
            e.printStackTrace();
        }
        return vue.render();
    }
    private String validerCommandeCourante()  {
        try {
            return vue.render(commandeCouranteResource.validerCommandeCourante());
        } catch ( SQLException | NotFoundException e) {
            e.printStackTrace();
        } catch (EmptyCommandeException e) {
            return vue.render(e.getMessage());
        }
        return vue.render();
    }

    private String getCommande(Map<String, Object> parametres) {
        String commandeIdJSON = (String) parametres.get("id");
        if(null == commandeIdJSON)
            return vue.renderParametreNotFound("id");
        try {
            ObjectMapper mapper = new ObjectMapper();
            Long id = mapper.readValue(commandeIdJSON, Long.class);
            return vue.render(commandeArchiveeResource.getCommande(id));
        } catch (SQLException | NotFoundException | JsonProcessingException e) {
            e.printStackTrace();
        }
        return vue.render();
    }



}
