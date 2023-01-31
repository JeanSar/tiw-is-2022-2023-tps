package fr.univlyon1.m2tiw.is.commandes.services;

import fr.univlyon1.m2tiw.is.commandes.model.Commande;

public class GestionCommandeServiceImpl implements GestionCommandeService {

    private final CommandeCouranteService commandeCouranteService;

    public GestionCommandeServiceImpl(CommandeCouranteService _commandeCouranteService) {
        commandeCouranteService = _commandeCouranteService;
    }

    @Override
    public Commande getCommandeCourante() {
        return commandeCouranteService.getCommandeCourante();
    }
}