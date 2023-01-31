package fr.univlyon1.m2tiw.is.commandes.vue;
import com.fasterxml.jackson.core.JsonProcessingException;
import fr.univlyon1.m2tiw.is.commandes.controller.Controller;
import fr.univlyon1.m2tiw.is.commandes.model.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;

public class Vue {
    private static final Logger LOG = LoggerFactory.getLogger(Controller.class);

    public Vue() {
        String message = "Composant Vue démarré : " + this;
        System.out.println(message);
        LOG.debug(message);
    }
    public String render() {
        return "";
    }

    public String render(String message) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(message);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return render();
    }
    public String render(Commande commande) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(commande);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return render();
    }

    public String render(Voiture voiture) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(voiture);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return render();
    }

    public String render(Collection<Option> options) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(options);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return render();
    }

    public String render(long id) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(id);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return render();
    }
}
