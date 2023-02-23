package fr.univlyon1.m2tiw.is.chainmanager.services;

import fr.univlyon1.m2tiw.is.chainmanager.models.Voiture;
import fr.univlyon1.m2tiw.is.chainmanager.services.dtos.MachineDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collection;

@Slf4j
@Service
public class MachineService {

    @Autowired
    private RestTemplate restTemplate;

    final String ROOT_URI = "http://localhost:8081/machines";

    public Collection<MachineDTO> getMachines() {
        log.info("Test getmachine");

        ResponseEntity<MachineDTO[]> response = restTemplate.getForEntity(ROOT_URI, MachineDTO[].class);
        MachineDTO[] machines = response.getBody();
        return Arrays.asList(machines);
    }

    public void envoieOptionsVoiture(String queueName, Voiture voiture) {
        Collection<String> options = voiture.getOptions();
        log.info("Envoi de des {} options '{}' pour la voiture {} sur la queue '{}'",
                options.size(), options, voiture.getId(), queueName);
        // TODO: TP3 utiliser RabbitTemplate pour envoyer une demande
        //  de reconfiguration sur la queue indiquée
    }
}
