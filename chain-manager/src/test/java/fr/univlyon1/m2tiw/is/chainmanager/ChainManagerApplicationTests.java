package fr.univlyon1.m2tiw.is.chainmanager;

import fr.univlyon1.m2tiw.is.chainmanager.services.MachineService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ChainManagerApplicationTests {

	@Test
	void contextLoads() {
		MachineService ms = new MachineService();
		ms.getMachines();
	}

}
