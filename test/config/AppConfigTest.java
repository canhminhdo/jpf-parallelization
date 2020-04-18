package config;

import static org.junit.Assert.assertEquals;

import java.io.File;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class AppConfigTest {
	static AppConfig config;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		String rootPath = System.getProperty("user.dir");
		String path = rootPath + File.separator + "jpfTest.properties";
		config = AppConfig.getInstance(path);
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		AppConfig.reset();
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testLoadConfig() {
		// expected result
		String version = "0.0.1";
		String secreteKey = "OgataLab";
		String appName = "tas.main.TestTAS";
		
		String projectDir = "/Users/ogataslab/eclipse-workspace/jpf-parallelization";
		int reachDepth = 100;
		int reachBound = Integer.MAX_VALUE;
		int bmcDepth = 400;
		boolean isCaching = false;
		boolean isRemote = false;
		
		// RABBITMQ
		boolean isUsingRabbitMQ = true;
		String rabbitmqHost = "localhost";
		String rabbitmqUsername = "";
		String rabbitmqPassword = "";

		// REDIS
		boolean isUsingRedis = true;
		String redisHost = "localhost";
		int redisPort = 6379;
		
		// MySQL
		boolean isUsingMySQL = false;
		String mysqlHost = "localhost";
		String mysqlPort = "3306";
		String mysqlUsername = "root";
		String mysqlPassword = "";
		String mysqlDbName= "ogatalab";
		
		assertEquals("version is not same", version, config.getVersion());
		assertEquals("secreteKey is not same", secreteKey, config.getSecreteKey());
		assertEquals("appName is not same", appName, config.getAppName());
		assertEquals("projectDir is not same", projectDir, config.getProjectDir());
		
		assertEquals("reachDepth is not same", reachDepth, config.getReachDepth());
		assertEquals("reachBound is not same", reachBound, config.getReachBound());
		assertEquals("bmcDepth is not same", bmcDepth, config.getBmcDepth());
		assertEquals("isCaching is not same", isCaching, config.isCaching());
		
		assertEquals("isRemote is not same", isRemote, config.isRemote());
		
		assertEquals("isUsingRabbitMQ is not same", isUsingRabbitMQ, config.isUsingRabbitMQ());
		assertEquals("rabbitmqHost is not same", rabbitmqHost, config.getRabbitmqHost());
		assertEquals("rabbitmqUsername is not same", rabbitmqUsername, config.getRabbitmqUsername());
		assertEquals("rabbitmqPassword is not same", rabbitmqPassword, config.getRabbitmqPassword());
		
		assertEquals("isUsingRedis is not same", isUsingRedis, config.isUsingRedis());
		assertEquals("redisHost is not same", redisHost, config.getRedisHost());
		assertEquals("redisPort is not same", redisPort, config.getRedisPort());
		
		assertEquals("isUsingMySQL is not same", isUsingMySQL, config.isUsingMySQL());
		assertEquals("rabbitmqHost is not same", mysqlHost, config.getMysqlHost());
		assertEquals("mysqlPort is not same", mysqlPort, config.getMysqlPort());
		assertEquals("mysqlUsername is not same", mysqlUsername, config.getMysqlUsername());
		assertEquals("mysqlPassword is not same", mysqlPassword, config.getMysqlPassword());
		assertEquals("mysqlDbName is not same", mysqlDbName, config.getMysqlDbName());
	}

}
