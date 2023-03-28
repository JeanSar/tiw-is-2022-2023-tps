package fr.univlyon1.m2tiw.is.commandes.serveur;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.univlyon1.m2tiw.is.commandes.model.Commande;
import fr.univlyon1.m2tiw.is.commandes.model.Option;
import fr.univlyon1.m2tiw.is.commandes.model.Voiture;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ServeurImplTest {

    private static final Logger LOG = LoggerFactory.getLogger(ServeurImplTest.class);
    private static Serveur serveurImpl;
    private static int counter = 0;

    @BeforeAll
    public static void before() throws IOException, NoSuchFieldException, ClassNotFoundException, IllegalAccessException {
        serveurImpl = new ServeurImpl();
    }
    private String createVoiture() {
        ObjectMapper mapper = new ObjectMapper();
        String modele = "modele" + counter++;
        try {
            modele = mapper.writeValueAsString(modele);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        Map<String, Object> param = new HashMap<>();
        param.put("modele", modele);
        String voitureJSON =  (String) serveurImpl.processRequest("voiture", "create", param);
        assertNotNull(voitureJSON);
        assertNotEquals(voitureJSON, "");
        return voitureJSON;
    }
    @Test
    void getAllOptions() {
        LOG.info("//////////// getAllOptions() /////////////");
        ObjectMapper mapper = new ObjectMapper();
        String optionsJSON = (String) serveurImpl.processRequest("option","get", null);
        assertNotNull(optionsJSON);
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
        LOG.info("//////////// ajouterVoiture() /////////////");
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
        LOG.info("//////////// ajouterConfiguration() /////////////");
        try {
            ObjectMapper mapper = new ObjectMapper();
            Voiture voiture;
            String voitureJSON = createVoiture();
            voiture = mapper.readValue(voitureJSON, Voiture.class);

            Option option = new Option("Moteur", "200ch");
            String optionJSON = mapper.writeValueAsString(option);
            voitureJSON = mapper.writeValueAsString(voiture);
            Map<String, Object> param = new HashMap<>();
            param.put("voiture", voitureJSON);
            param.put("option", optionJSON);
            voitureJSON = (String) serveurImpl.processRequest("voiture", "add", param);
            assertNotNull(voitureJSON);
            assertNotEquals(voitureJSON, "");
            voiture = mapper.readValue(voitureJSON, Voiture.class);

            assertTrue(voiture.getOptions().containsKey(option.getNom()));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Test
    void supprimerConfiguration() {
        LOG.info("//////////// supprimerConfiguration() /////////////");
        try {
            ObjectMapper mapper = new ObjectMapper();
            Voiture voiture;
            String voitureJSON = createVoiture();
            voiture = mapper.readValue(voitureJSON, Voiture.class);

            Option option = new Option("Roue",  "Increvable");
            String optionJSON = mapper.writeValueAsString(option);
            voitureJSON = mapper.writeValueAsString(voiture);
            Map<String, Object> param = new HashMap<>();
            param.put("voiture", voitureJSON);
            param.put("option", optionJSON);
            voitureJSON = (String) serveurImpl.processRequest("voiture", "add", param);
            assertNotNull(voitureJSON);
            assertNotEquals(voitureJSON, "");
            voiture = mapper.readValue(voitureJSON, Voiture.class);

            optionJSON = mapper.writeValueAsString(voiture.getOptions().get(option.getNom()));
            voitureJSON = mapper.writeValueAsString(voiture);
            param = new HashMap<>();
            param.put("voiture", voitureJSON);
            param.put("option", optionJSON);
            voitureJSON = (String) serveurImpl.processRequest("voiture", "delete", param);
            assertNotNull(voitureJSON);
            assertNotEquals(voitureJSON, "");
            voiture = mapper.readValue(voitureJSON, Voiture.class);

            assertFalse(voiture.getOptions().containsKey(option.getNom()));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    @Test
    void ajouterVoiture() {
        LOG.info("//////////// ajouterVoiture() /////////////");
        try {

            ObjectMapper mapper = new ObjectMapper();
            Voiture voiture;
            String voitureJSON = createVoiture();
            voiture = mapper.readValue(voitureJSON, Voiture.class);

            String voitureIdJSON = mapper.writeValueAsString(voiture.getId());
            Map<String, Object> param = new HashMap<>();
            param.put("id", voitureIdJSON);
            String commandeCouranteJSON = (String) serveurImpl.processRequest("commandeCourante", "add", param);
            assertNotNull(commandeCouranteJSON);
            assertNotEquals(commandeCouranteJSON, "");
            Commande commandeCourante = mapper.readValue(commandeCouranteJSON, Commande.class);

            assertTrue(commandeCourante.getVoitures().contains(voiture));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    @Test
    void supprimerVoiture() {
        LOG.info("//////////// supprimerVoiture() /////////////");
        try {
            ObjectMapper mapper = new ObjectMapper();
            Voiture voiture;
            String voitureJSON = createVoiture();
            voiture = mapper.readValue(voitureJSON, Voiture.class);

            String voitureIdJSON = mapper.writeValueAsString(voiture.getId());
            Map<String, Object> param = new HashMap<>();
            param.put("id", voitureIdJSON);
            String commandeCouranteJSON = (String) serveurImpl.processRequest("commandeCourante", "add", param);
            assertNotNull(commandeCouranteJSON);
            assertNotEquals(commandeCouranteJSON, "");
            Commande commandeCourante = mapper.readValue(commandeCouranteJSON, Commande.class);
            assertTrue(commandeCourante.getVoitures().contains(voiture));

            commandeCouranteJSON = (String) serveurImpl.processRequest("commandeCourante", "delete", param);
            assertNotNull(commandeCouranteJSON);
            assertNotEquals(commandeCouranteJSON, "");
            commandeCourante = mapper.readValue(commandeCouranteJSON, Commande.class);
            assertFalse(commandeCourante.getVoitures().contains(voiture));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Test
    void validerCommandeCourante(){
        LOG.info("//////////// validerCommandeCourante() /////////////");
        try {
            ObjectMapper mapper = new ObjectMapper();
            Voiture voiture;
            String voitureJSON = createVoiture();
            voiture = mapper.readValue(voitureJSON, Voiture.class);

            // ajouts des options 1 et 2
            Option opt1 = new Option("Moteur", "200ch");
            String opt1JSON = mapper.writeValueAsString(opt1);
            voitureJSON = mapper.writeValueAsString(voiture);
            Map<String, Object> param = new HashMap<>();
            param.put("voiture", voitureJSON);
            param.put("option", opt1JSON);
            voitureJSON = (String) serveurImpl.processRequest("voiture", "add", param);
            assertNotNull(voitureJSON);
            assertNotEquals(voitureJSON, "");
            voiture = mapper.readValue(voitureJSON, Voiture.class);

            Option opt2 = new Option("Phare",  "Xenon");
            String opt2JSON = mapper.writeValueAsString(opt2);
            voitureJSON = mapper.writeValueAsString(voiture);
            param = new HashMap<>();
            param.put("voiture", voitureJSON);
            param.put("option", opt2JSON);
            voitureJSON = (String) serveurImpl.processRequest("voiture", "add", param);
            assertNotNull(voitureJSON);
            assertNotEquals(voitureJSON, "");
            voiture = mapper.readValue(voitureJSON, Voiture.class);

            // Ajout de la voiture avec les options
            String voitureIdJSON = mapper.writeValueAsString(voiture.getId());
            param = new HashMap<>();
            param.put("id", voitureIdJSON);
            String commandeCouranteJSON = (String) serveurImpl.processRequest("commandeCourante", "add", param);
            assertNotNull(commandeCouranteJSON);
            assertNotEquals(commandeCouranteJSON, "");
            Commande commandeCourante = mapper.readValue(commandeCouranteJSON, Commande.class);
            assertTrue(commandeCourante.getVoitures().contains(voiture));

            // Création et ajout d'une deuxième voiture sans options
            Voiture voiture2;
            String voiture2JSON = createVoiture();
            voiture2 = mapper.readValue(voiture2JSON, Voiture.class);
            String voiture2IdJSON = mapper.writeValueAsString(voiture2.getId());
            param = new HashMap<>();
            param.put("id", voiture2IdJSON);
            commandeCouranteJSON = (String) serveurImpl.processRequest("commandeCourante", "add", param);
            assertNotNull(commandeCouranteJSON);
            assertNotEquals(commandeCouranteJSON, "");
            commandeCourante = mapper.readValue(commandeCouranteJSON, Commande.class);
            assertTrue(commandeCourante.getVoitures().contains(voiture2));

            // On test d'abord de valider la commande
            String commandeCouranteIdJSON = (String) serveurImpl.processRequest("commandeCourante", "validate", null);
            assertNotNull(commandeCouranteIdJSON);
            assertNotEquals(commandeCouranteIdJSON, "");
            Long commandeCouranteId = mapper.readValue(commandeCouranteIdJSON, Long.class);
            assertNotNull(commandeCouranteId);

            // Puis on essaye de la récupérer
            commandeCouranteIdJSON = mapper.writeValueAsString(commandeCouranteId);
            param = new HashMap<>();
            param.put("id", commandeCouranteIdJSON);
            String commandeArchiveJSON = (String) serveurImpl.processRequest("commandeArchivee", "get", param);
            assertNotNull(commandeCouranteIdJSON);
            assertNotEquals(commandeCouranteIdJSON, "");
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
        LOG.info("//////////// validerCommandeCouranteVide() /////////////");
        try {
            ObjectMapper mapper = new ObjectMapper();

            String messageJSON = (String) serveurImpl.processRequest("commandeCourante", "validate", null);
            assertNotNull(messageJSON);
            assertNotEquals(messageJSON, "");
            String message = mapper.readValue(messageJSON, String.class);
            assertEquals(message,"Commande vide");
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}