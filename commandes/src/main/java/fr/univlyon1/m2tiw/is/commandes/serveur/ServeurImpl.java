package fr.univlyon1.m2tiw.is.commandes.serveur;


import fr.univlyon1.m2tiw.is.commandes.controller.CommandeController;
import fr.univlyon1.m2tiw.is.commandes.controller.OptionController;
import fr.univlyon1.m2tiw.is.commandes.controller.VoitureController;
import fr.univlyon1.m2tiw.is.commandes.dao.*;
import fr.univlyon1.m2tiw.is.commandes.services.*;
import org.picocontainer.MutablePicoContainer;
import org.picocontainer.PicoBuilder;
import org.picocontainer.injectors.ConstructorInjection;

public class ServeurImpl {

    private final OptionController optionController;
    private final VoitureController voitureController;
    private final CommandeController commandeController;


    public ServeurImpl() {
        // Instantiation du conteneur
        MutablePicoContainer pico = new PicoBuilder(new ConstructorInjection()).withCaching().build();

        // Ajouts des dépendances
        pico.addComponent(DBAccess.class);
        pico.addComponent(OptionDAOImpl.class);
        pico.addComponent(VoitureDAOImpl.class);
        pico.addComponent(CommandeDAOImpl.class);
        pico.addComponent(OptionServiceImpl.class);
        pico.addComponent(VoitureServiceImpl.class);
        pico.addComponent(CommandeCouranteServiceImpl.class);
        pico.addComponent(GestionCommandeServiceImpl.class);
        pico.addComponent(OptionController.class);
        pico.addComponent(VoitureController.class);
        pico.addComponent(CommandeController.class);

        // Instantiation des controleurs avec résolution du référentiel de dépendances par le conteneur
        optionController = pico.getComponent(OptionController.class);
        voitureController = pico.getComponent(VoitureController.class);
        commandeController = pico.getComponent(CommandeController.class);

        // Initilisation
        optionController.start();
        voitureController.start();
        commandeController.start();
    }

    public String getAllOptions() {
        return optionController.getAllOptions();
    }

    public String creerVoiture(String modele) {
        return voitureController.creerVoiture(modele);
    }

    public String ajouterConfiguration(String voiture, String option) {
        return voitureController.ajouterConfiguration(voiture, option);
    }

    public String supprimerConfiguration(String voiture, String option) {
        return voitureController.supprimerConfiguration(voiture, option);
    }
    public String ajouterVoiture(String voitureId) {
        return commandeController.ajouterVoiture(voitureId);
    }

    public String supprimerVoiture(String voitureId) {
        return commandeController.supprimerVoiture(voitureId);
    }
    public String validerCommandeCourante()  {
        return commandeController.validerCommandeCourante();
    }

    public String getCommande(String id) {
        return commandeController.getCommande(id);
    }
}
