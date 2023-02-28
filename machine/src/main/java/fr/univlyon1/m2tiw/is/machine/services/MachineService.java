package fr.univlyon1.m2tiw.is.machine.services;

import fr.univlyon1.m2tiw.is.machine.services.dtos.MachineDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
public class MachineService {
    @Autowired
    private RestTemplate restTemplate;

    final String ROOT_URI = "http://localhost:8080/machine";

    public MachineDTO getMachine(Long id) {
        try {
            log.info(String.format("GET %s", ROOT_URI));
            String url = String.format("{}/{}", ROOT_URI, id);
            MachineDTO machine = restTemplate.getForObject(url, MachineDTO.class);
            return machine;
        } catch (RestClientException e) {
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
