package util;

import business.exceptions.ObjectNotFoundException;
import business.facade.SessionFacade;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;

class PropertiesResourcesTest {

    @Test
    void getFilesProperties_goodUrlDatabaseInResources_returnPropertiesDatabase() {
        Properties p = PropertiesResources.getFilesProperties("database.properties");

        String url = p.getProperty("URL");
        String user = p.getProperty("USER");
        String password = p.getProperty("PASSWORD");

        assertEquals("url",url);
        assertEquals("user",user);
        assertEquals("mdp",password);
    }

    @Test
    void getFilesProperties_badUrl_throwIOException() {
        assertThrows(NullPointerException.class,() ->{
            PropertiesResources.getFilesProperties("test.properties");
        });

    }
}