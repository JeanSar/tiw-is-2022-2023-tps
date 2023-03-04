package fr.univlyon1.m2tiw.is.chainmanager;

import fr.univlyon1.m2tiw.is.chainmanager.models.Voiture;
import fr.univlyon1.m2tiw.is.chainmanager.services.MachineService;
import fr.univlyon1.m2tiw.is.chainmanager.services.VoitureService;
import fr.univlyon1.m2tiw.is.chainmanager.services.dtos.MachineDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class ChainManagerApplicationTests {
	@Autowired
	private VoitureService voitureService;
	@Autowired
	private MachineService machineService;
	@Test
	void contextLoads() {
		assertNotNull(machineService);
		Collection<MachineDTO> machines = machineService.getMachines();
		assertNotNull(machines);
	}

	@Test
	void envoieOptionsVoiture() {
		Voiture voiture = new Voiture();
		voiture.setId((long) 1);
		Collection<String> options = new ArrayList<>();
		options.add("opt1");
		options.add("opt2");
		voiture.setOptions(options);
		voitureService.envoieOptions(voiture);
	}

}
