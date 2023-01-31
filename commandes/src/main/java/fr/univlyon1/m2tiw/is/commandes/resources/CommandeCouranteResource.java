package fr.univlyon1.m2tiw.is.commandes.resources;

import fr.univlyon1.m2tiw.is.commandes.dao.NotFoundException;
import fr.univlyon1.m2tiw.is.commandes.services.EmptyCommandeException;

import java.sql.SQLException;

public interface CommandeCouranteResource {


    long validerCommandeCourante() throws EmptyCommandeException, SQLException, NotFoundException;
}
