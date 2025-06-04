package fr.backendtest.testutils.testcontainer;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

public class AbstractITWithPostgreSQL {

    static PostgreSQLContainerConfig postgreSQLContainerConfig;

    static {
        postgreSQLContainerConfig = new PostgreSQLContainerConfig();
        postgreSQLContainerConfig.start();
    }

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgreSQLContainerConfig::getJdbcUrl);
    }
}
