package fr.univlyon1.m2tiw.is.commandes.resources;

import fr.univlyon1.m2tiw.is.commandes.dao.OptionDAO;
import fr.univlyon1.m2tiw.is.commandes.model.Option;

import java.sql.SQLException;
import java.util.Collection;

public class OptionResourceImpl implements OptionResource {
    private final OptionDAO dao;

    public OptionResourceImpl(OptionDAO _optionDAO) {
        dao = _optionDAO;
    }

    @Override
    public Collection<Option> getAllOptions() throws SQLException {
        return dao.getAllOptions();
    }
}
