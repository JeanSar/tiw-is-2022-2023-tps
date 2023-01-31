package fr.univlyon1.m2tiw.is.commandes.resources;

import fr.univlyon1.m2tiw.is.commandes.dao.CommandeDAO;
import fr.univlyon1.m2tiw.is.commandes.dao.NotFoundException;
import fr.univlyon1.m2tiw.is.commandes.model.Commande;
import fr.univlyon1.m2tiw.is.commandes.model.Option;

import java.sql.SQLException;
import java.util.Collection;

public class CommandeArchiveeResourceImpl implements CommandeArchiveeResource {

    private final OptionResource optionResource;
    private final VoitureResource voitureResource;
    private final CommandeDAO commandeDAO;
    public CommandeArchiveeResourceImpl(OptionResource _optionResource, VoitureResource _voitureResource,
                                      CommandeDAO _commandeDAO) {
        optionResource= _optionResource;
        voitureResource = _voitureResource;
        commandeDAO = _commandeDAO;
    }
    @Override
    public Collection<Option> getAllOptions() throws SQLException {
        return this.optionResource.getAllOptions();
    }
    @Override
    public Commande getCommande(Long id) throws SQLException, NotFoundException {
        Commande commande = commandeDAO.getCommande(id);
        commande.getVoitures().addAll(voitureResource.getVoituresByCommande(id));
        return commande;
    }

}
