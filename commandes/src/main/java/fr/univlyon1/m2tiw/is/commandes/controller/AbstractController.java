package fr.univlyon1.m2tiw.is.commandes.controller;

import fr.univlyon1.m2tiw.is.commandes.vue.Vue;
import org.picocontainer.Startable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public abstract class AbstractController implements Startable {
    private static final Logger LOG = LoggerFactory.getLogger(AbstractController.class);

    protected Vue vue;
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

    public abstract Object process(String ressource, String methode, Map<String, Object> parametres);
}
