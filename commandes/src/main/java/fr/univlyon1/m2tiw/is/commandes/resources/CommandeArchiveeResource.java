package fr.univlyon1.m2tiw.is.commandes.resources;

import fr.univlyon1.m2tiw.is.commandes.dao.NotFoundException;
import fr.univlyon1.m2tiw.is.commandes.model.Commande;
import fr.univlyon1.m2tiw.is.commandes.model.Option;

import java.sql.SQLException;
import java.util.Collection;

public interface CommandeArchiveeResource {
    Collection<Option> getAllOptions() throws SQLException;

    Commande getCommande(Long id) throws SQLException, NotFoundException;
}
