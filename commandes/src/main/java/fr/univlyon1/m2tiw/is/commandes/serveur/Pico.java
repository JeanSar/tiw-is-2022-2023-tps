package fr.univlyon1.m2tiw.is.commandes.serveur;

import fr.univlyon1.m2tiw.is.commandes.services.GestionCommandeServiceImpl;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.picocontainer.Characteristics;
import org.picocontainer.DefaultPicoContainer;
import org.picocontainer.MutablePicoContainer;
import org.picocontainer.behaviors.Caching;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Pico {
    private MutablePicoContainer picoContainer;
    private final Logger logger = LoggerFactory.getLogger(getClass());
    Pico() {
        try {
            // Récupération du fichier de config
            Path filePath = Paths.get("src/main/java/fr/univlyon1/m2tiw/is/commandes/config.json");
            String configStr = null;
            try {
                byte[] fileBytes = Files.readAllBytes(filePath);
                configStr = new String(fileBytes);
            } catch (IOException e) {
                logger.trace("Le fichier config.json n'a pas été trouvé", e);
                e.printStackTrace();
            }

            assert configStr != null;

            // Récupération des données
            JSONObject applicationConfig = new JSONObject(configStr).getJSONObject("application-config");

            JSONArray businessComponents = applicationConfig.getJSONArray("business-components");
            JSONArray serviceComponents = applicationConfig.getJSONArray("service-components");
            JSONArray persistenceComponents = applicationConfig.getJSONArray("persistence-components");

            // App
            String keyName = "commande";
            this.picoContainer = new DefaultPicoContainer(new Caching())
                    .as(Characteristics.SDI).addComponent("GestionCommandeService", GestionCommandeServiceImpl.class)
                    .addComponent(keyName, applicationConfig.getString("name"));

            // MVC
            var application = picoContainer.makeChildContainer();
            var persistence = application.makeChildContainer();
            var service = application.makeChildContainer();

            String basicPrefix = "_Component";
            String servicePrefix = "_Service" + basicPrefix;
            String businessPrefix = "_Controller" + servicePrefix;
            String persistencePrefix = "_Persistence" + servicePrefix;

            for (int i = 0; i < serviceComponents.length(); i++) {
                JSONObject compo = serviceComponents.getJSONObject(i);
                addComponentFromJsonToClass(compo, service, servicePrefix);
            }
            for (int i = 0; i < persistenceComponents.length(); i++) {
                JSONObject compo = persistenceComponents.getJSONObject(i);
                addComponentFromJsonToClass(compo, persistence, persistencePrefix);
            }
            for (int i = 0; i < businessComponents.length(); i++) {
                JSONObject compo = businessComponents.getJSONObject(i);
                addComponentFromJsonToClass(compo, application, businessPrefix);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void addComponentFromJsonToClass(Object json, MutablePicoContainer components, String Prefix) throws JSONException {
        JSONObject jsonObject = (JSONObject) json;
        String name = jsonObject.getString("class-name") + Prefix;
        try {
            Class<?> class_name = Class.forName(name);
            components.addComponent(class_name);
        } catch (ClassNotFoundException e) {
            logger.trace(getClass().getName() + " " + e.getMessage(), e);
        }
    }
}