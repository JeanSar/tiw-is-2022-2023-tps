package fr.univlyon1.m2tiw.is.commandes.serveur;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.univlyon1.m2tiw.is.commandes.controller.CommandeController;
import fr.univlyon1.m2tiw.is.commandes.controller.OptionController;
import fr.univlyon1.m2tiw.is.commandes.controller.VoitureController;
import fr.univlyon1.m2tiw.is.commandes.vue.Vue;
import org.picocontainer.Characteristics;
import org.picocontainer.DefaultPicoContainer;
import org.picocontainer.MutablePicoContainer;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ServeurImpl implements Serveur {

    private final OptionController optionController;
    private final VoitureController voitureController;
    private final CommandeController commandeController;
    private final Vue vue;

    public ServeurImpl() throws IOException, IllegalAccessException, NoSuchFieldException, ClassNotFoundException {
        // Instantiation du conteneur
        MutablePicoContainer pico = new DefaultPicoContainer();
        ClassLoader classL = getClass().getClassLoader();
        File file = new File(Objects.requireNonNull(classL.getResource("config.json")).getFile());
        Map<String, Object> configuration = convertJsonToMapStringObj(new File(file.getPath()));
        Map<String, Object> appConfiguration = (Map<String, Object>) configuration.get("application-config");

        List<Map<String, Object>> dbAccessComponents = (List<Map<String, Object>>) appConfiguration
                .get("dbAccess-component");
        for (Map<String, Object> component : dbAccessComponents) {
            Class<?> composantClass = Class.forName((String) component.get("class-name"));
            List<Map<String, Object>> params = (List<Map<String, Object>>) component.get("params");
            if (params != null) {
                Map<String, String> infosBDD = new HashMap<>();
                params.forEach(
                        (dbparams) -> infosBDD.put((String) dbparams.get("name"), (String) dbparams.get("value")));
                Field dbUrlFiels = composantClass.getDeclaredField("DB_URL");
                dbUrlFiels.setAccessible(true);
                dbUrlFiels.set(null, infosBDD.get("url"));
                Field dbUsernameField = composantClass.getDeclaredField("DB_USERNAME");
                dbUsernameField.setAccessible(true);
                dbUsernameField.set(null, infosBDD.get("user"));
                Field dbPasswordField = composantClass.getDeclaredField("DB_PASSWORD");
                dbPasswordField.setAccessible(true);
                dbPasswordField.set(null, infosBDD.get("password"));
            } else {
                throw new IOException();
            }
            pico.as(Characteristics.CACHE, Characteristics.CDI).addComponent(composantClass);
        }

        List<Map<String, Object>> javaComponents = (List<Map<String, Object>>) appConfiguration.get("java-components");
        for (Map<String, Object> component : javaComponents) {
            Class<?> composantClass = Class.forName((String) component.get("class-name"));
            pico.as(Characteristics.CACHE, Characteristics.CDI).addComponent(composantClass);
        }

        List<Map<String, Object>> persistenceComponents = (List<Map<String, Object>>) appConfiguration
                .get("persistence-components");
        for (Map<String, Object> component : persistenceComponents) {
            Class<?> composantClass = Class.forName((String) component.get("class-name"));
            pico.addComponent(composantClass);
        }

        List<Map<String, Object>> businessComponents = (List<Map<String, Object>>) appConfiguration
                .get("business-components");
        for (Map<String, Object> component : businessComponents) {
            Class<?> composantClass = Class.forName((String) component.get("class-name"));
            pico.as(Characteristics.CACHE, Characteristics.CDI).addComponent(composantClass);
        }

        List<Map<String, Object>> serviceComponents = (List<Map<String, Object>>) appConfiguration
                .get("service-components");
        for (Map<String, Object> component : serviceComponents) {
            Class<?> composantClass = Class.forName((String) component.get("class-name"));
            pico.as(Characteristics.CACHE, Characteristics.CDI).addComponent(composantClass);
        }

        List<Map<String, Object>> controllerComponents = (List<Map<String, Object>>) appConfiguration
                .get("controller-components");
        for (Map<String, Object> component : controllerComponents) {
            Class<?> composantClass = Class.forName((String) component.get("class-name"));
            pico.as(Characteristics.CACHE, Characteristics.CDI).addComponent(composantClass);
        }

        this.voitureController = (VoitureController) pico.getComponent(VoitureController.class);
        this.optionController = (OptionController) pico.getComponent(OptionController.class);
        this.commandeController = (CommandeController) pico.getComponent(CommandeController.class);
        this.vue = (Vue) pico.getComponent(Vue.class);

        optionController.start();
        voitureController.start();
        commandeController.start();
    }
    @Override
    public Object processRequest(String ressource, String methode, Map<String, Object> parametres) {
        switch(ressource) {
            case "voiture":
                return voitureController.process(ressource,methode,parametres);
            case "option":
                return optionController.process(ressource,methode,parametres);
            case "commandeArchivee":
            case "commandeCourante":
                return commandeController.process(ressource,methode,parametres);
            default:
                return vue.renderResourceNotFound();
        }
    }

    public static Map<String,Object> convertJsonToMapStringObj(File json) throws IOException{
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(json, new TypeReference<>(){});
    }
}
