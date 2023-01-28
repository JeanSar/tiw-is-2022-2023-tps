package fr.univlyon1.m2tiw.is.commandes.serveur;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.*;

class ServeurImplTests {
    private static Logger log = LoggerFactory.getLogger(ServeurImplTests.class);
    private ServeurImpl serveurImpl;
    private static int counter = 0;

    @BeforeEach
    public void before() {
        serveurImpl = new ServeurImpl();
    }
    private String createVoiture() {
        String voitureJson = serveurImpl.creerVoiture("modele" + counter++);
        return voitureJson;
    }
    @Test
    void getAllOptions() {
        var options = serveurImpl.getAllOptions();
        assertNotSame("", options);
    }



}