package fr.univlyon1.m2tiw.is.machine.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.univlyon1.m2tiw.is.machine.services.dtos.ConfigurationDTO;
import fr.univlyon1.m2tiw.is.machine.services.dtos.MachineDTO;
import fr.univlyon1.m2tiw.is.machine.services.dtos.VoitureDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@Slf4j
@Service
public class VoitureService {

    @Autowired
    private RestTemplate restTemplate;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public void reconfigure(VoitureDTO payload) throws JsonProcessingException {
        log.info(payload.toString());
        try {
            for(String option: payload.options){
                ConfigurationDTO config = restTemplate.getForObject("http://localhost:8080/configuration/" + option , ConfigurationDTO.class);
                log.info(objectMapper.writeValueAsString(config));
            }
        } catch (Exception e) {
            log.info(e.getMessage());
        }
    }
}
