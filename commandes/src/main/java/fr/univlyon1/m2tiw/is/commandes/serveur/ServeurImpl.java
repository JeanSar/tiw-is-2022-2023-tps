package fr.univlyon1.m2tiw.is.commandes.serveur;


import fr.univlyon1.m2tiw.is.commandes.controller.CommandeController;
import fr.univlyon1.m2tiw.is.commandes.controller.OptionController;
import fr.univlyon1.m2tiw.is.commandes.controller.VoitureController;
import fr.univlyon1.m2tiw.is.commandes.dao.*;
import fr.univlyon1.m2tiw.is.commandes.services.*;

import java.sql.SQLException;

public class ServeurImpl {

    private OptionController optionController;
    private VoitureController voitureController;
    private CommandeController commandeController;


    public ServeurImpl() {
        try {
            // Acces à la base de données
            DBAccess dbAccess = new DBAccess();

            // Instantiation des DAO
            OptionDAO optionDAO = new OptionDAOImpl(dbAccess);
            VoitureDAO voitureDAO = new VoitureDAOImpl(dbAccess);
            CommandeDAO commandeDAO = new CommandeDAOImpl(dbAccess);

            // Initialisation des DAO
            optionDAO.init();
            voitureDAO.init();
            commandeDAO.init();

            // Instantiation des services
            OptionService optionService = new OptionServiceImpl(optionDAO);
            VoitureService voitureService = new VoitureServiceImpl(voitureDAO, optionDAO);
            CommandeCouranteService commandeCouranteService = new CommandeCouranteServiceImpl(voitureService, commandeDAO);
            GestionCommandeService gestionCommandeService = new GestionCommandeServiceImpl(
                    commandeCouranteService, optionService, voitureService, commandeDAO
            );
            // Instantiation des controllers
            optionController = new OptionController(optionService);
            voitureController = new VoitureController(voitureService);
            commandeController = new CommandeController(commandeCouranteService, gestionCommandeService);

        } catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public String getAllOptions() {
        return optionController.getAllOptions();
    }

    public String creerVoiture(String modele) {
        return voitureController.creerVoiture(modele);
    }

    public void ajouterConfiguration(String voiture, String option) {
        voitureController.ajouterConfiguration(voiture, option);
    }

    public void supprimerConfiguration(String voiture, String option) {
        voitureController.supprimerConfiguration(voiture, option);
    }
    public void ajouterVoiture(String voitureId) {
        commandeController.ajouterVoiture(voitureId);
    }

    public void supprimerVoiture(String voitureId) {
        commandeController.supprimerVoiture(voitureId);
    }
    public String validerCommandeCourante()  {
        return commandeController.validerCommandeCourante();
    }

    public String getCommande(String id) {
        return commandeController.getCommande(id);
    }
}
