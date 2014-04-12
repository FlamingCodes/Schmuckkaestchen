package de.flamingcode;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public abstract class AbstractDAO {

	protected Properties prop = getProperties();

	protected Properties getProperties() {
		Properties props = new Properties();
		try {
			File configDir = new File(System.getenv("CATALINA_BASE"), "properties");
			File configFile = new File(configDir, "schmuckkaestchen.properties");
			InputStream in = new FileInputStream(configFile);
			props.load(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return props;
	}

	protected Connection getConnectionInstance() throws ClassNotFoundException,
			SQLException {
		String dbDriver = prop.getProperty("db_driver");
		Class.forName(dbDriver);
		Connection cn = DriverManager.getConnection(
				"jdbc:hsqldb:file:" + prop.getProperty("db_path"), prop.getProperty("db_username"),
				prop.getProperty("db_password"));
		return cn;
	}
}
