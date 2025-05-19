package fr.backendtest.testutils.testcontainer;

import lombok.experimental.UtilityClass;
import java.util.concurrent.atomic.AtomicInteger;

@UtilityClass
public class TestContainersUtils {

    public static final String BUILD_NETWORK_ID = "BUILD_NETWORK_ID";
    private static final AtomicInteger CONTAINER_SEQUENCE = new AtomicInteger(0);

    public static int getNextContainerId() {
        return CONTAINER_SEQUENCE.getAndIncrement();
    }

    public static boolean isExecutedInCIEnvironment() {
        return System.getenv(BUILD_NETWORK_ID) != null;
    }

    public static String determineContainerIP(String host, int containerIndex) {
        return TestContainersUtils.isExecutedInCIEnvironment()
            ? host.substring(0, host.length() - 2) + (3 + containerIndex)
            : host;
    }
}
