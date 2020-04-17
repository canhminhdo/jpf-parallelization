package database;

import java.sql.DriverManager;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Driver;

import config.AppConfig;

/**
 * Singleton MySQL instance
 * 
 * @author OgataLab
 *
 */
public class ConnectionFactory {
	
	public static Connection conn = null;
	
	/**
	 * Get singleton MySQL connection
	 * 
	 * @return {@link Connection}
	 */
	public static Connection getConnection() {
		try {
			if (conn == null || conn.isClosed()) {
				conn = initialize();
			}
			return conn;
		} catch (SQLException ex) {
			throw new RuntimeException("Error connecting to the database", ex);
		}
	}
	
	public static Connection initialize() throws SQLException {
		AppConfig config = AppConfig.getInstance();
		String schema = String.format("jdbc:mysql://%s:%s/%s", config.getMysqlHost(), config.getMysqlPort(), config.getMysqlDbName());
		DriverManager.registerDriver(new Driver());
		return (Connection) DriverManager.getConnection(schema, config.getMysqlUsername(), config.getMysqlPassword());
	}
	
	/**
	 * Close MySQL connection
	 * 
	 * @param conn
	 */
	public static void closeConnection() {
		try {
			if (conn != null) conn.close();
		} catch (SQLException ex) {
			throw new RuntimeException("Error closing the database", ex);
		}
	}
	
	/**
	 * Test connection
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		Connection connection = ConnectionFactory.getConnection();
		if (connection != null)
			System.out.println("Connected !!!");
		ConnectionFactory.closeConnection();
	}
}
