package util;

import org.junit.jupiter.api.Test;

import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;

class PropertiesResourcesTest {

    @Test
    void getFilesProperties_goodUrlDatabaseInResources_returnPropertiesDatabase() {
        Properties p = PropertiesResources.getDatabaseProperties();

        String url = p.getProperty("URL");
        String user = p.getProperty("USER");
        String password = p.getProperty("PASSWORD");

        assertEquals("url",url);
        assertEquals("user",user);
        assertEquals("mdp",password);
    }

    @Test
    void getFilesProperties_badUrl_throwIOException() {
        assertThrows(NullPointerException.class, PropertiesResources::getMailProperties);

    }
}