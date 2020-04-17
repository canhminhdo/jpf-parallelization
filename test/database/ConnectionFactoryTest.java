package database;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.sql.SQLException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.mysql.jdbc.Connection;

public class ConnectionFactoryTest {
	
	Connection conn;
	String url;
	
	@Before
	public void setUp() throws Exception {
		conn = ConnectionFactory.getConnection();
		url = "jdbc:mysql://localhost:3306/ogatalab";
	}

	@After
	public void tearDown() throws Exception {
		ConnectionFactory.closeConnection();
	}

	@Test
	public void testGetConnection() throws SQLException {
		assertFalse("Cannot make a connection", conn.isClosed());
	}

	@Test
	public void testCloseConnection() throws SQLException {
		ConnectionFactory.closeConnection();
		assertTrue("Cannot close the connection", conn.isClosed());
	}

}
