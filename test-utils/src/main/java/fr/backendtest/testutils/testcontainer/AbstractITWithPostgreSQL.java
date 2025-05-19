package fr.backendtest.testutils.testcontainer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
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
