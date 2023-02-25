package fr.univlyon1.m2tiw.is.chainmanager;

import fr.univlyon1.m2tiw.is.chainmanager.services.MachineService;
import fr.univlyon1.m2tiw.is.chainmanager.services.dtos.MachineDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class ChainManagerApplicationTests {
	@Autowired
	private MachineService machineService;
	@Test
	void contextLoads() {
		assertNotNull(machineService);
		Collection<MachineDTO> machines = machineService.getMachines();
		assertNotNull(machines);
		assertEquals(machines.size(), 0);
	}

}
