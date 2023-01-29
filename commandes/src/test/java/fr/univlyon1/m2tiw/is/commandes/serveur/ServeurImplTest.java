package fr.univlyon1.m2tiw.is.commandes.serveur;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.univlyon1.m2tiw.is.commandes.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

class ServeurImplTest {
    private static Logger log = LoggerFactory.getLogger(ServeurImplTest.class);
    private ServeurImpl serveurImpl;
    private static int counter = 0;

    @BeforeEach
    public void before() {
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

            assert(voiture.getModele().equals("modele" + (counter-1)));
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

            assert(voiture.getOptions().containsKey(option.getNom()));
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

            assert(!voiture.getOptions().containsKey(option.getNom()));
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
            assert(commandeCourante.getVoitures().contains(voiture));
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
            assert(!commandeCourante.getVoitures().contains(voiture));
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

            String voitureIdJSON = mapper.writeValueAsString(voiture.getId());
            String commandeCouranteJSON = serveurImpl.ajouterVoiture(voitureIdJSON);
            Commande commandeCourante = mapper.readValue(commandeCouranteJSON, Commande.class);
            assert(commandeCourante.getVoitures().contains(voiture));

            String commandeCouranteIdJSON = serveurImpl.validerCommandeCourante();
            Long commandeCouranteId = mapper.readValue(commandeCouranteIdJSON, Long.class);
            assertNotNull(commandeCouranteId);
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