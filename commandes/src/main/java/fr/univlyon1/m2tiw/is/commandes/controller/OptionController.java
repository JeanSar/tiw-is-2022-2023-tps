package fr.univlyon1.m2tiw.is.commandes.controller;

import fr.univlyon1.m2tiw.is.commandes.services.OptionService;
import fr.univlyon1.m2tiw.is.commandes.services.OptionServiceImpl;
import fr.univlyon1.m2tiw.is.commandes.vue.Vue;

import java.sql.SQLException;

public class OptionController {
    private OptionService optionService;
    private final Vue vue = new Vue();
    public OptionController() {
        try {
            optionService = new OptionServiceImpl();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public OptionController(OptionService _optionService) {
        optionService =  _optionService;
    }
    /**
     * @return String JSON des options
     */
    public String getAllOptions() {
        try {
            return vue.render(optionService.getAllOptions());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return vue.render();
    }

}
