package fr.univlyon1.m2tiw.is.commandes.serveur;


import fr.univlyon1.m2tiw.is.commandes.controller.CommandeController;
import fr.univlyon1.m2tiw.is.commandes.controller.OptionController;
import fr.univlyon1.m2tiw.is.commandes.controller.VoitureController;
import fr.univlyon1.m2tiw.is.commandes.model.Commande;
import fr.univlyon1.m2tiw.is.commandes.model.Option;
import fr.univlyon1.m2tiw.is.commandes.model.Voiture;
import java.util.Collection;

public class Serveur {
    private final CommandeController commandeController;
    private final OptionController optionController;
    private final VoitureController voitureController;

    public Serveur() {
        commandeController = new CommandeController();
        optionController = new OptionController();
        voitureController = new VoitureController();
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
