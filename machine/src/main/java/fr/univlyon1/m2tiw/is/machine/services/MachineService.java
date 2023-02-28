package fr.univlyon1.m2tiw.is.machine.services;

import fr.univlyon1.m2tiw.is.machine.services.dtos.MachineDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;

@Slf4j
@Service
public class MachineService {
    @Autowired
    private RestTemplate restTemplate;

    @Value("${tiw.is.machine.catalogue.url}")
    private String ROOT_URI ;

    public MachineDTO getMachine(Long id) {
        try {
            log.info(String.format("GET %s", ROOT_URI));
            URI uri =  new URI(ROOT_URI + "/" + id);
            return restTemplate.getForObject(uri, MachineDTO.class);
        } catch (RestClientException | URISyntaxException e) {
            log.error(e.getMessage());
            return null;
        }
    }

    public MachineDTO createMachine(MachineDTO newMachine) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<MachineDTO> request =
                    new HttpEntity<MachineDTO>(newMachine, headers);
            log.info(String.format("GET %s", ROOT_URI));
            MachineDTO machine = restTemplate.postForObject(ROOT_URI, request, MachineDTO.class);
            return machine;
        } catch (RestClientException e) {
            log.error(e.getMessage());
            return null;
        }
    }
}
