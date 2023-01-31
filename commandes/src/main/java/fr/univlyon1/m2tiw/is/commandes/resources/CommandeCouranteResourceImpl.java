package fr.univlyon1.m2tiw.is.commandes.resources;

import fr.univlyon1.m2tiw.is.commandes.dao.CommandeDAO;
import fr.univlyon1.m2tiw.is.commandes.dao.NotFoundException;
import fr.univlyon1.m2tiw.is.commandes.model.Commande;
import fr.univlyon1.m2tiw.is.commandes.model.Voiture;
import fr.univlyon1.m2tiw.is.commandes.services.CommandeCouranteService;
import fr.univlyon1.m2tiw.is.commandes.services.EmptyCommandeException;

import java.sql.SQLException;

public class CommandeCouranteResourceImpl implements CommandeCouranteResource {
    private final CommandeDAO commandeDAO;
    private final VoitureResource voitureResource;

    private final CommandeCouranteService commandeCouranteService;

    public CommandeCouranteResourceImpl(VoitureResource _voitureResource,
                                        CommandeDAO _commandeDAO,
                                        CommandeCouranteService _commandeCouranteService) {
        this.voitureResource = _voitureResource;
        this.commandeDAO = _commandeDAO;
        this.commandeCouranteService = _commandeCouranteService;
    }


    @Override
    public long validerCommandeCourante() throws EmptyCommandeException, SQLException, NotFoundException {
        if (commandeCouranteService.getAllVoitures().size() == 0)
            throw new EmptyCommandeException("Commande vide");
        Commande commandeCourante = commandeCouranteService.getCommandeCourante();
        commandeCourante.setFerme(true);
        commandeCourante = commandeDAO.saveCommande(commandeCourante);
        for (Voiture voiture : commandeCouranteService.getAllVoitures()) {
            voitureResource.sauverVoiture(voiture.getId(), commandeCourante);
        }
        long id = commandeCourante.getId();
        commandeCouranteService.creerCommandeCourante(); // On repart avec un nouveau panier vide
        return id;
    }

}
