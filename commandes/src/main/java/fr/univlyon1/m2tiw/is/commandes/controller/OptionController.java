package fr.univlyon1.m2tiw.is.commandes.controller;

import fr.univlyon1.m2tiw.is.commandes.model.Option;
import fr.univlyon1.m2tiw.is.commandes.services.OptionService;
import fr.univlyon1.m2tiw.is.commandes.services.OptionServiceImpl;

import java.sql.SQLException;
import java.util.Collection;

public class OptionController {
    private OptionService optionService;
    public OptionController() {
        try {
            optionService = new OptionServiceImpl();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public Collection<Option> getAllOptions() {
        try {
            return optionService.getAllOptions();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
