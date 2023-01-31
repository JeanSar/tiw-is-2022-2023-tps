package fr.univlyon1.m2tiw.is.commandes.controller;

import fr.univlyon1.m2tiw.is.commandes.services.OptionService;
import fr.univlyon1.m2tiw.is.commandes.services.OptionServiceImpl;
import fr.univlyon1.m2tiw.is.commandes.vue.Vue;

import java.sql.SQLException;

public class OptionController extends Controller {
    private OptionService optionService;
    private Vue vue;
    public OptionController() {
        try {
            optionService = new OptionServiceImpl();
            vue = new Vue();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public OptionController(OptionService _optionService, Vue _vue) {
        optionService =  _optionService;
        vue = _vue;
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
