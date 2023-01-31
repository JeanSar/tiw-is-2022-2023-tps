package fr.univlyon1.m2tiw.is.commandes.services;

import fr.univlyon1.m2tiw.is.commandes.dao.NotFoundException;
import fr.univlyon1.m2tiw.is.commandes.model.Commande;
import fr.univlyon1.m2tiw.is.commandes.model.Voiture;
import fr.univlyon1.m2tiw.is.commandes.resources.VoitureResource;

import java.sql.SQLException;
import java.util.Collection;

public class CommandeCouranteServiceImpl implements CommandeCouranteService {
    private Commande commandeCourante;
    private final VoitureResource voitureResource;

    public CommandeCouranteServiceImpl(VoitureResource _voitureResource) {
        voitureResource = _voitureResource;
        creerCommandeCourante();
    }

    @Override
    public Commande creerCommandeCourante() {
        this.commandeCourante = new Commande(false);
        return commandeCourante;
    }

    @Override
    public void ajouterVoiture(Long voitureId) throws SQLException, NotFoundException {
        this.commandeCourante.addVoiture(voitureResource.getVoiture(voitureId));
    }

    @Override
    public void supprimerVoiture(Long voitureId) throws SQLException, NotFoundException {
        this.commandeCourante.removeVoiture(voitureResource.getVoiture(voitureId));
        this.voitureResource.supprimerVoiture(voitureId);
    }

    @Override
    public Collection<Voiture> getAllVoitures() {
        return commandeCourante.getVoitures();
    }

    @Override
    public Commande getCommandeCourante() {
        if (commandeCourante == null) {
            creerCommandeCourante();
        }
        return commandeCourante;
    }
}
