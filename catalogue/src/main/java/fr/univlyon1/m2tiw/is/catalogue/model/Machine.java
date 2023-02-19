package fr.univlyon1.m2tiw.is.catalogue.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.util.ArrayList;
import java.util.Collection;

@Entity
public class Machine {
    @Id
    @GeneratedValue
    private Long id;
    private String modele;

    @OneToMany(mappedBy = "machine")
    private Collection<Configuration> configurations = new ArrayList<>();

    public Machine() {
    }

    public Machine(Long id, String modele) {
        this.id = id;
        this.modele = modele;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getModele() {
        return modele;
    }

    public void setModele(String modele) {
        this.modele = modele;
    }

    public Collection<Configuration> getConfigurations() {
        return configurations;
    }

    public void setConfigurations(Collection<Configuration> configurations) {
        this.configurations = configurations;
    }
}
