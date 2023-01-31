package fr.univlyon1.m2tiw.is.commandes.serveur;


import fr.univlyon1.m2tiw.is.commandes.controller.CommandeController;
import fr.univlyon1.m2tiw.is.commandes.controller.OptionController;
import fr.univlyon1.m2tiw.is.commandes.controller.VoitureController;
import fr.univlyon1.m2tiw.is.commandes.dao.*;
import fr.univlyon1.m2tiw.is.commandes.resources.*;
import fr.univlyon1.m2tiw.is.commandes.services.*;
import fr.univlyon1.m2tiw.is.commandes.vue.Vue;
import org.picocontainer.MutablePicoContainer;
import org.picocontainer.PicoBuilder;
import org.picocontainer.injectors.ConstructorInjection;

import java.util.Map;

public class ServeurImpl implements Serveur {

    private final OptionController optionController;
    private final VoitureController voitureController;
    private final CommandeController commandeController;
    private final Vue vue;


    public ServeurImpl() {
        // Instantiation du conteneur
        MutablePicoContainer pico = new PicoBuilder(new ConstructorInjection()).withCaching().build();

        // Ajouts des dépendances
        pico.addComponent(DBAccess.class);
        pico.addComponent(OptionDAOImpl.class);
        pico.addComponent(VoitureDAOImpl.class);
        pico.addComponent(CommandeDAOImpl.class);
        pico.addComponent(CommandeCouranteServiceImpl.class);
        pico.addComponent(CommandeCouranteResourceImpl.class);
        pico.addComponent(CommandeArchiveeResourceImpl.class);
        pico.addComponent(OptionResourceImpl.class);
        pico.addComponent(VoitureResourceImpl.class);
        pico.addComponent(Vue.class);
        pico.addComponent(OptionController.class);
        pico.addComponent(VoitureController.class);
        pico.addComponent(CommandeController.class);


        // Instantiation des controleurs avec résolution du référentiel de dépendances par le conteneur
        vue = pico.getComponent(Vue.class);
        optionController = pico.getComponent(OptionController.class);
        voitureController = pico.getComponent(VoitureController.class);
        commandeController = pico.getComponent(CommandeController.class);

        // Initilisation
        optionController.start();
        voitureController.start();
        commandeController.start();
        pico.start();

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
}
