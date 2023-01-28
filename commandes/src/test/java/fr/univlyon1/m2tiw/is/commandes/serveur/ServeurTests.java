package fr.univlyon1.m2tiw.is.commandes.serveur;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.*;

class ServeurTests {
    private static Logger log = LoggerFactory.getLogger(fr.univlyon1.m2tiw.is.commandes.serveur.ServeurTests.class);
    private Serveur serveur;

    @BeforeEach
    public void before() {
        serveur = new Serveur();
    }

    @Test
    public void getAllOptions() {
        var options = serveur.getAllOptions();
        assertNotSame("", options);
    }

}