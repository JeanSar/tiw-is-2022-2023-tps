package fr.univlyon1.m2tiw.is.commandes.serveur;


import fr.univlyon1.m2tiw.is.commandes.controller.CommandeController;
import fr.univlyon1.m2tiw.is.commandes.controller.OptionController;
import fr.univlyon1.m2tiw.is.commandes.controller.VoitureController;
import fr.univlyon1.m2tiw.is.commandes.dao.*;
import fr.univlyon1.m2tiw.is.commandes.model.Commande;
import fr.univlyon1.m2tiw.is.commandes.model.Option;
import fr.univlyon1.m2tiw.is.commandes.model.Voiture;
import fr.univlyon1.m2tiw.is.commandes.services.*;

import java.sql.SQLException;
import java.util.Collection;

public class Serveur {

    private OptionController optionController;
    private VoitureController voitureController;
    private CommandeController commandeController;


    public Serveur() {
        try {
            // Instantiation des DAO
            OptionDAO optionDAO = new OptionDAOImpl();
            VoitureDAO voitureDAO = new VoitureDAOImpl();
            CommandeDAO commandeDAO = new CommandeDAOImpl();

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

    public Collection<Option> getAllOptions() {
        return optionController.getAllOptions();
    }

    public Voiture creerVoiture(String modele) {
        return voitureController.creerVoiture(modele);
    }

    public void ajouterConfiguration(Voiture voiture, Option option) {
        voitureController.ajouterConfiguration(voiture, option);
    }

    public void supprimerConfiguration(Voiture voiture, Option option) {
        voitureController.supprimerConfiguration(voiture, option);
    }
    public void ajouterVoiture(Long voitureId) {
        commandeController.ajouterVoiture(voitureId);
    }

    public void supprimerVoiture(Long voitureId) {
        commandeController.supprimerVoiture(voitureId);
    }
    public long validerCommandeCourante()  {
        return commandeController.validerCommandeCourante();
    }

    public Commande getCommande(Long id) {
        return commandeController.getCommande(id);
    }
}
