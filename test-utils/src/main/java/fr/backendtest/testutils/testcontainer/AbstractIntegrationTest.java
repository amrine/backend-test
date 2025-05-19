package fr.backendtest.testutils.testcontainer;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest
@Testcontainers
@Transactional
@AutoConfigureMockMvc
public abstract class AbstractIntegrationTest extends AbstractITWithPostgreSQL {
}
