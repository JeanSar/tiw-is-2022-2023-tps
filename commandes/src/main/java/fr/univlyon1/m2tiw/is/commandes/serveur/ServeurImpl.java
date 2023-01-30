package fr.univlyon1.m2tiw.is.commandes.serveur;


import fr.univlyon1.m2tiw.is.commandes.controller.CommandeController;
import fr.univlyon1.m2tiw.is.commandes.controller.OptionController;
import fr.univlyon1.m2tiw.is.commandes.controller.VoitureController;
import fr.univlyon1.m2tiw.is.commandes.dao.*;
import fr.univlyon1.m2tiw.is.commandes.services.*;
import org.picocontainer.DefaultPicoContainer;
import org.picocontainer.MutablePicoContainer;
public class ServeurImpl {

    private OptionController optionController;
    private VoitureController voitureController;
    private CommandeController commandeController;


    public ServeurImpl() {
        // Instantiation du conteneur
        MutablePicoContainer pico = new DefaultPicoContainer();
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
        optionController = (OptionController)pico.getComponent(OptionController.class);
        voitureController = (VoitureController)pico.getComponent(VoitureController.class);
        commandeController = (CommandeController)pico.getComponent(CommandeController.class);

//            // Acces à la base de données
//            DBAccess dbAccess = new DBAccess();
//
//            // Instantiation des DAO
//            OptionDAO optionDAO = new OptionDAOImpl(dbAccess);
//            VoitureDAO voitureDAO = new VoitureDAOImpl(dbAccess);
//            CommandeDAO commandeDAO = new CommandeDAOImpl(dbAccess);
//
//            // Instantiation des services
//            OptionService optionService = new OptionServiceImpl(optionDAO);
//            VoitureService voitureService = new VoitureServiceImpl(voitureDAO, optionDAO);
//            CommandeCouranteService commandeCouranteService = new CommandeCouranteServiceImpl(voitureService, commandeDAO);
//            GestionCommandeService gestionCommandeService = new GestionCommandeServiceImpl(
//                    commandeCouranteService, optionService, voitureService, commandeDAO
//            );
//            // Instantiation des controllers
//            optionController = new OptionController(optionService);
//            voitureController = new VoitureController(voitureService);
//            commandeController = new CommandeController(commandeCouranteService, gestionCommandeService);

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
