package fr.univlyon1.m2tiw.is.commandes.controller;

import org.picocontainer.Startable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class Controller implements Startable {
    private static final Logger LOG = LoggerFactory.getLogger(Controller.class);
    @Override
    public void start() {
        String message = "Composant Controleur démarré : " + this;
        System.out.println(message);
        LOG.debug(message);
    }

    @Override
    public void stop() {
        String message = "Composant Controleur arrêté : " + this;
        System.out.println(message);
        LOG.debug(message);
    }
}
