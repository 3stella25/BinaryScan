package cs1302.api;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Allows access to api keys in a different file.
 */
public class KeyProperties {

    private static String vtKey;

    /**
     * Accesses the Virus Total api key.
     */
    public static void load() {

        String configPath = "resources/config.properties";

        try (FileInputStream configFileStream = new FileInputStream(configPath)) {
            Properties config = new Properties();
            config.load(configFileStream);
            config.list(System.out);
            vtKey = config.getProperty("virusTotalAPI.key");
            System.out.printf("vtKey = \"%s\"\n", vtKey);
        } catch (IOException ioe) {
            System.err.println(ioe);
            ioe.printStackTrace();
        }
    }

    /**
     * Returns the api Key. Very good for when you don't want
     * the user to see the api Key because it's kept private and people
     * cannot alter it.
     * @return String vtKey
     */
    public static String getVTKey() {
        return vtKey;
    }
}
