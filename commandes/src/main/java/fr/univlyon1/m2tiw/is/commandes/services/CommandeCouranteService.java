package fr.univlyon1.m2tiw.is.commandes.services;

import fr.univlyon1.m2tiw.is.commandes.dao.NotFoundException;
import fr.univlyon1.m2tiw.is.commandes.model.Commande;
import fr.univlyon1.m2tiw.is.commandes.model.Voiture;

import java.sql.SQLException;
import java.util.Collection;

public interface CommandeCouranteService {
    Commande creerCommandeCourante();
    void ajouterVoiture(Long voitureId) throws SQLException, NotFoundException;
    void supprimerVoiture(Long voitureId) throws NotFoundException, SQLException;
    Collection<Voiture> getAllVoitures();
    Commande getCommandeCourante();
}