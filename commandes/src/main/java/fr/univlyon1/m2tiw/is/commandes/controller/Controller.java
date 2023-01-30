package fr.univlyon1.m2tiw.is.commandes.controller;

import fr.univlyon1.m2tiw.is.commandes.dao.AbstractSQLDAO;
import org.picocontainer.Startable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.*;

public abstract class Controller implements Startable {
    private static final Logger LOG = LoggerFactory.getLogger(Controller.class);
    @Override
    public void start() {
        String message = "Composant Controleur démarré : " + this.toString();
        System.out.println(message);
        LOG.debug(message);
    }

    @Override
    public void stop() {
        String message = "Composant Controleur arrêté : " + this.toString();
        System.out.println(message);
        LOG.debug(message);
    }
}
