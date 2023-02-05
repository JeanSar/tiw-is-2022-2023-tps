package fr.univlyon1.m2tiw.is.commandes.serveur;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.univlyon1.m2tiw.is.commandes.model.Commande;
import fr.univlyon1.m2tiw.is.commandes.model.Option;
import fr.univlyon1.m2tiw.is.commandes.model.Voiture;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public class Mapper {

    public static String convertObjToJSON(Object o) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(o);
    }

    public static Map<String,Object> convertJsonToMapStringObj(File json) throws IOException{
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(json, new TypeReference<>(){});
    }

    public static Commande JSONtoCommande(String json) throws IOException {
        return new ObjectMapper().readValue(json, Commande.class);
    }

    public static Voiture JSONtoVoiture(String json) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(json, Voiture.class);
    }

    public static Option JSONtoOption(String json) throws IOException {
        return new ObjectMapper().readValue(json, Option.class);
    }

}