package fr.univlyon1.m2tiw.is.commandes.controller;

import fr.univlyon1.m2tiw.is.commandes.resources.OptionResource;
import fr.univlyon1.m2tiw.is.commandes.vue.Vue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.Map;

public class OptionController extends AbstractController {
    private final OptionResource optionResource;
    private final Vue vue;
    private static final Logger LOG = LoggerFactory.getLogger(OptionController.class);

    public OptionController(OptionResource _optionService, Vue _vue) {
        optionResource =  _optionService;
        vue = _vue;
    }

    @Override
    public Object process(String ressource, String methode, Map<String, Object> parametres) {
        switch (ressource) {
            case "option":
                switch (methode) {
                    case "get":
                        return getAllOptions();
                    default:
                        return vue.renderMethodeNotFound();
                }
            default:
                return vue.renderResourceNotFound();
        }
    }
    private String getAllOptions() {
        LOG.info("getAllOptions()");
        try {
            return vue.render(optionResource.getAllOptions());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return vue.render();
    }

}
