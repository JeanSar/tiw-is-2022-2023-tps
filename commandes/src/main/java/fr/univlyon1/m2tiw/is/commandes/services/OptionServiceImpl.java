package fr.univlyon1.m2tiw.is.commandes.services;

import fr.univlyon1.m2tiw.is.commandes.dao.OptionDAO;
import fr.univlyon1.m2tiw.is.commandes.dao.OptionDAOImpl;
import fr.univlyon1.m2tiw.is.commandes.model.Option;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.Collection;

public class OptionServiceImpl implements OptionService {
    private static final Logger LOG = LoggerFactory.getLogger(OptionServiceImpl.class);

    private final OptionDAO dao;

    public OptionServiceImpl() throws SQLException {
        dao = new OptionDAOImpl();
        dao.init();
    }
    public OptionServiceImpl(OptionDAO _optionDAO) {
        dao = _optionDAO;
    }

    @Override
    public Collection<Option> getAllOptions() throws SQLException {
        return dao.getAllOptions();
    }
}