package db;

import java.sql.Connection;

/**
 * Class abstraction if the system need to change db
 * Class should be a singelton
 */
public interface ConnectionDB {
    Connection getDb();
}
