package fr.univlyon1.m2tiw.is.chainmanager.repositories;

import fr.univlyon1.m2tiw.is.chainmanager.models.Voiture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VoitureRepository extends JpaRepository<Voiture,Long> {
}
