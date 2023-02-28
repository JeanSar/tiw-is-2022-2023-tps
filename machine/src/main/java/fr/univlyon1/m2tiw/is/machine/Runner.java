package fr.univlyon1.m2tiw.is.machine;

import fr.univlyon1.m2tiw.is.machine.services.ConfigurationService;
import fr.univlyon1.m2tiw.is.machine.services.MachineService;
import fr.univlyon1.m2tiw.is.machine.services.dtos.MachineDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class Runner implements CommandLineRunner {
    private ConfigurationService configurationService;
    private MachineService machineService;

    public Runner(ConfigurationService configurationService, MachineService machineService) {
        this.configurationService = configurationService;
        this.machineService = machineService;
    }

    @Override
    public void run(String... args) {
        log.info("Service machine '{}' reçoit les messages sur la queue '{}'", configurationService.getId(),configurationService.getQueueName());
        if(machineService.getMachine(configurationService.getId()) == null) {
            MachineDTO machineDTO = new MachineDTO(configurationService.getId(), configurationService.getQueueName());
            machineService.createMachine(machineDTO);
        }
    }
}
