package fr.univlyon1.m2tiw.is.commandes.serveur;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.univlyon1.m2tiw.is.commandes.model.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

class ServeurImplTest {
    private static Logger log = LoggerFactory.getLogger(ServeurImplTest.class);
    private static ServeurImpl serveurImpl;
    private static int counter = 0;

    @BeforeAll
    public static void before() {
        serveurImpl = new ServeurImpl();
    }
    private String createVoiture() {
        ObjectMapper mapper = new ObjectMapper();
        String modele = "modele" + counter++;
        try {
            modele = mapper.writeValueAsString(modele);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        String voitureJSON =  serveurImpl.creerVoiture(modele);
        assertNotEquals(voitureJSON, "");
        return voitureJSON;
    }
    @Test
    void getAllOptions() {
        ObjectMapper mapper = new ObjectMapper();
        String optionsJSON = serveurImpl.getAllOptions();
        assertNotEquals(optionsJSON, "");
        try {
            Collection<Option> options = mapper.readValue(optionsJSON, Collection.class);
            assertNotNull(options);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    @Test
    void creerVoiture() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            Voiture voiture;
            String voitureJSON = createVoiture();
            voiture = mapper.readValue(voitureJSON, Voiture.class);

            assertEquals(voiture.getModele(), "modele" + (counter - 1));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Test
    void ajouterConfiguration() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            Voiture voiture;
            String voitureJSON = createVoiture();
            voiture = mapper.readValue(voitureJSON, Voiture.class);

            Option option = new Option("Moteur", "200ch");
            String optionJSON = mapper.writeValueAsString(option);
            voitureJSON = mapper.writeValueAsString(voiture);
            voitureJSON = serveurImpl.ajouterConfiguration(voitureJSON, optionJSON);
            voiture = mapper.readValue(voitureJSON, Voiture.class);

            assertTrue(voiture.getOptions().containsKey(option.getNom()));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Test
    void supprimerConfiguration() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            Voiture voiture;
            String voitureJSON = createVoiture();
            voiture = mapper.readValue(voitureJSON, Voiture.class);

            Option option = new Option("Roue",  "Increvable");
            String optionJSON = mapper.writeValueAsString(option);
            voitureJSON = mapper.writeValueAsString(voiture);
            voitureJSON = serveurImpl.ajouterConfiguration(voitureJSON, optionJSON);
            voiture = mapper.readValue(voitureJSON, Voiture.class);

            optionJSON = mapper.writeValueAsString(voiture.getOptions().get(option.getNom()));
            voitureJSON = mapper.writeValueAsString(voiture);
            voitureJSON = serveurImpl.supprimerConfiguration(voitureJSON, optionJSON);
            voiture = mapper.readValue(voitureJSON, Voiture.class);

            assertFalse(voiture.getOptions().containsKey(option.getNom()));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    @Test
    void ajouterVoiture() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            Voiture voiture;
            String voitureJSON = createVoiture();
            voiture = mapper.readValue(voitureJSON, Voiture.class);

            String voitureIdJSON = mapper.writeValueAsString(voiture.getId());
            String commandeCouranteJSON = serveurImpl.ajouterVoiture(voitureIdJSON);
            Commande commandeCourante = mapper.readValue(commandeCouranteJSON, Commande.class);
            assertTrue(commandeCourante.getVoitures().contains(voiture));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    @Test
    void supprimerVoiture() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            Voiture voiture;
            String voitureJSON = createVoiture();
            voiture = mapper.readValue(voitureJSON, Voiture.class);

            String voitureIdJSON = mapper.writeValueAsString(voiture.getId());
            String commandeCouranteJSON = serveurImpl.ajouterVoiture(voitureIdJSON);
            Commande commandeCourante = mapper.readValue(commandeCouranteJSON, Commande.class);

            commandeCouranteJSON = serveurImpl.supprimerVoiture(voitureIdJSON);
            commandeCourante = mapper.readValue(commandeCouranteJSON, Commande.class);
            assertFalse(commandeCourante.getVoitures().contains(voiture));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Test
    void validerCommandeCourante(){
        try {
            ObjectMapper mapper = new ObjectMapper();
            Voiture voiture;
            String voitureJSON = createVoiture();
            voiture = mapper.readValue(voitureJSON, Voiture.class);

            // ajouts des options 1 et 2
            Option opt1 = new Option("Moteur", "200ch");
            String opt1JSON = mapper.writeValueAsString(opt1);
            voitureJSON = mapper.writeValueAsString(voiture);
            voitureJSON = serveurImpl.ajouterConfiguration(voitureJSON, opt1JSON);
            voiture = mapper.readValue(voitureJSON, Voiture.class);

            Option opt2 = new Option("Phare",  "Xenon");
            String opt2JSON = mapper.writeValueAsString(opt2);
            voitureJSON = mapper.writeValueAsString(voiture);
            voitureJSON = serveurImpl.ajouterConfiguration(voitureJSON, opt2JSON);
            voiture = mapper.readValue(voitureJSON, Voiture.class);

            // Ajout de la voiture avec les options
            String voitureIdJSON = mapper.writeValueAsString(voiture.getId());
            String commandeCouranteJSON = serveurImpl.ajouterVoiture(voitureIdJSON);
            Commande commandeCourante = mapper.readValue(commandeCouranteJSON, Commande.class);
            assertTrue(commandeCourante.getVoitures().contains(voiture));

            // Création et ajout d'une deuxième voiture sans options
            Voiture voiture2;
            String voiture2JSON = createVoiture();
            voiture2 = mapper.readValue(voiture2JSON, Voiture.class);
            String voiture2IdJSON = mapper.writeValueAsString(voiture2.getId());
            commandeCouranteJSON = serveurImpl.ajouterVoiture(voiture2IdJSON);
            commandeCourante = mapper.readValue(commandeCouranteJSON, Commande.class);
            assertTrue(commandeCourante.getVoitures().contains(voiture2));

            // On test d'abord de valider la commande
            String commandeCouranteIdJSON = serveurImpl.validerCommandeCourante();
            Long commandeCouranteId = mapper.readValue(commandeCouranteIdJSON, Long.class);
            assertNotNull(commandeCouranteId);

            // Puis on essaye de la récupérer
            commandeCouranteIdJSON = mapper.writeValueAsString(commandeCouranteId);
            String commandeArchiveJSON = serveurImpl.getCommande(commandeCouranteIdJSON);
            Commande commandeArchive = mapper.readValue(commandeArchiveJSON, Commande.class);

            assertTrue(commandeArchive.isFerme());
            assertTrue(commandeArchive.getVoitures().contains(voiture));
            assertTrue(commandeArchive.getVoitures().contains(voiture2));

        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Test
    void validerCommandeCouranteVide(){
        try {
            ObjectMapper mapper = new ObjectMapper();

            String messageJSON = serveurImpl.validerCommandeCourante();
            String message = mapper.readValue(messageJSON, String.class);
            assertEquals(message,"Commande vide");
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}