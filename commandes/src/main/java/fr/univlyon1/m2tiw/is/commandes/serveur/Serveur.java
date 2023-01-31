package fr.univlyon1.m2tiw.is.commandes.serveur;

import java.util.Map;

public interface Serveur {
    Object processRequest(String ressource, String methode, Map<String, Object> parametres);
}
