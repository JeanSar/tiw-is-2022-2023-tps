package fr.univlyon1.m2tiw.is.chainmanager.services;

import fr.univlyon1.m2tiw.is.chainmanager.models.Statut;
import fr.univlyon1.m2tiw.is.chainmanager.models.StatutInconnuException;
import fr.univlyon1.m2tiw.is.chainmanager.models.Voiture;
import fr.univlyon1.m2tiw.is.chainmanager.repositories.VoitureRepository;
import fr.univlyon1.m2tiw.is.chainmanager.services.dtos.VoitureDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;

@Service
@Transactional
public class VoitureService {
    private VoitureRepository voitureRepository;

    public VoitureService(VoitureRepository voitureRepository) {
        this.voitureRepository = voitureRepository;
    }

    public VoitureDTO ajouteVoiture(VoitureDTO voitureDTO) throws StatutInconnuException {
        Voiture voiture = new Voiture();
        voiture.setStatut(Statut.AFAIRE);
        voiture.setOptions(new ArrayList<>(voitureDTO.options));
        voiture = voitureRepository.save(voiture);
        return new VoitureDTO(voiture);
    }

    public Collection<VoitureDTO> getAllVoitures() {
        return voitureRepository.findAll().stream().map(VoitureDTO::new).toList();
    }
}
