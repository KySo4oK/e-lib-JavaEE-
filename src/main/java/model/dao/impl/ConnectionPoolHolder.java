package model.dao.impl;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.sql.DataSource;
import java.io.FileReader;
import java.util.Properties;

public class ConnectionPoolHolder {
    private static final String DB_PROPERTIES =
            "C:\\Users\\KySo4oK\\IdeaProjects\\e-lib(JavaEE)\\src\\main\\resources\\db.properties";
    private static volatile DataSource dataSource;
    private final static Logger log = LogManager.getLogger(ConnectionPoolHolder.class);

    public static DataSource getDataSource() {

        if (dataSource == null) {
            synchronized (ConnectionPoolHolder.class) {
                if (dataSource == null) {
                    try (FileReader reader = new FileReader(DB_PROPERTIES)) {
                        Properties p = new Properties();
                        p.load(reader);
                        BasicDataSource ds = new BasicDataSource();
                        ds.setUrl(p.getProperty("db_url"));
                        ds.setUsername(p.getProperty("db_username"));
                        ds.setPassword(p.getProperty("db_password"));
                        ds.setMinIdle(Integer.parseInt(p.getProperty("db_min_idle")));
                        ds.setMaxIdle(Integer.parseInt(p.getProperty("db_max_idle")));
                        ds.setMaxOpenPreparedStatements(Integer.parseInt(
                                p.getProperty("db_max_open_prepared_statement")));
                        dataSource = ds;
                    } catch (Exception e) {
                        log.fatal("cannot connect to db - {}", e.getMessage());
                        System.exit(-1);
                    }
                }
            }
        }
        return dataSource;
    }
}
