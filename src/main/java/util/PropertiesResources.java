package util;

import com.mysql.cj.MysqlConnection;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Properties;

public class PropertiesResources {
    public static Properties getDatabaseProperties(String url)
    {
        Properties p = new Properties();
        try (InputStream in = MysqlConnection.class.getClassLoader().getResourceAsStream(url))
        {
            if (in == null)
            {
                throw new NullPointerException("You must specify a database.properties file");
            }
            p.load(new InputStreamReader(in));
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return p;
    }

}
