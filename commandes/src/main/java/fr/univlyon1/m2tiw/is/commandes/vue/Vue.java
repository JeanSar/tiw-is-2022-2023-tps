package fr.univlyon1.m2tiw.is.commandes.vue;
import com.fasterxml.jackson.core.JsonProcessingException;
import fr.univlyon1.m2tiw.is.commandes.model.*;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Collection;

public class Vue {
    public String render() {
        return "{}";
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
}
