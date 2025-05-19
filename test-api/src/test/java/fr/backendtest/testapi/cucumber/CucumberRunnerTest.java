package fr.backendtest.testapi.cucumber;

import fr.backendtest.testutils.testcontainer.AbstractITWithPostgreSQL;
import io.cucumber.java.ParameterType;
import io.cucumber.junit.platform.engine.Cucumber;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.junit.jupiter.Testcontainers;

@Cucumber
@CucumberContextConfiguration
@Sql(scripts = {
    "classpath:sql/clean_db.sql",
    "classpath:sql/customer.sql",
    "classpath:sql/item.sql"
})
@Testcontainers
@Transactional
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CucumberRunnerTest extends AbstractITWithPostgreSQL {

    @ParameterType("true|false")
    public Boolean booleanValue(String value) {
        return Boolean.valueOf(value);
    }
}
