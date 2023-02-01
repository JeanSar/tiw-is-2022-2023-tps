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

import java.io.InputStream;

public class Pico {
    private final MutablePicoContainer picoContainer;
    private static Pico instance;
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private Pico() throws JSONException {
        // Récupération du fichier de config
        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        InputStream configStream = cl.getResourceAsStream("config.json");
        String configStr = null;
        try {
            configStr = new String(configStream.readAllBytes());
        } catch (Exception e) {
            logger.trace("Resource config.json not found", e);
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
        
        for (int i=0; i < serviceComponents.length(); i++) {
            JSONObject compo = serviceComponents.getJSONObject(i);
            addComponentFromJsonToClass(compo, service, servicePrefix);
        }
        for (int i=0; i < persistenceComponents.length(); i++) {
            JSONObject compo = persistenceComponents.getJSONObject(i);
            addComponentFromJsonToClass(compo, persistence, persistencePrefix);
        }
        for (int i=0; i < businessComponents.length(); i++) {
            JSONObject compo = businessComponents.getJSONObject(i);
            addComponentFromJsonToClass(compo, application, businessPrefix);
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