package fr.backendtest.testutils.testcontainer;

import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

public class PostgreSQLContainerConfig extends PostgreSQLContainer<PostgreSQLContainerConfig> {

    private static final String IMAGE_VERSION = "postgres:15";
    private final Integer containerId;

    public PostgreSQLContainerConfig() {
        super(DockerImageName.parse(IMAGE_VERSION).asCompatibleSubstituteFor("postgres"));
        withReuse(true);
        containerId = TestContainersUtils.getNextContainerId();

        if (TestContainersUtils.isExecutedInCIEnvironment()) {
            withNetworkMode(System.getenv(TestContainersUtils.BUILD_NETWORK_ID));
        }
    }

    @Override
    public String getHost() {
        return TestContainersUtils.determineContainerIP(super.getHost(), containerId);
    }

    @Override
    public Integer getMappedPort(int port) {
        return TestContainersUtils.isExecutedInCIEnvironment()
            ? port
            : super.getMappedPort(port);
    }
}
