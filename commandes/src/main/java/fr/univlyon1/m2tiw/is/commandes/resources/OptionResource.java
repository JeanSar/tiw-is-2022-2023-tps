package fr.univlyon1.m2tiw.is.commandes.resources;

import fr.univlyon1.m2tiw.is.commandes.model.Option;

import java.sql.SQLException;
import java.util.Collection;

public interface OptionResource {
    Collection<Option> getAllOptions() throws SQLException;
}
