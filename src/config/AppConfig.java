package config;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class AppConfig {
	
	private static String configFileName = "jpf.properties";
	
	private static AppConfig _instance = null;
	
	private Properties appProps = null;
	
	// project home directory
	private String projectDir;
	
	// application name
	private String appName;
	
	// version
	private String version;
	
	// secrete key to encrypt if needed
	private String secreteKey;
	
	// reach Depth for a sub state space running by a JPF instance
	private int reachDepth;
	// reach Bound for a sub state space running by a JPF instance
	private int reachBound;
	
	// maximum depth if running BMC 
	private int bmcDepth;
	private boolean isCaching;

	// running in remote mode
	private boolean isRemote;

	// RABBITMQ
	private boolean isUsingRabbitMQ;
	private String rabbitmqHost;
	private String rabbitmqUsername;
	private String rabbitmqPassword;

	// REDIS
	private boolean isUsingRedis;
	private String redisHost;
	private int redisPort;
	
	// MySQL
	private Boolean isUsingMySQL;
	private String mysqlHost;
	private String mysqlPort;
	private String mysqlUsername;
	private String mysqlPassword;
	private String mysqlDbName;

	public static AppConfig getInstance() {
		if (_instance == null) {
			_instance = new AppConfig();
		}
		return _instance;
	}
	
	public static AppConfig getInstance(String path) {
		if (_instance == null) {
			_instance = new AppConfig(path);
		}
		return _instance;
	}

	private AppConfig() {
		// initialize the app configuration from config.properties file
		String rootPath = System.getProperty("user.dir");
		String appConfigPath = rootPath + File.separator + configFileName;
		loadProps(appConfigPath);
		initialize();
	}
	
	private AppConfig(String path) {
		loadProps(path);
		initialize();
	}
	
	public static void reset() {
		_instance = null;
	}
	
	private void loadProps(String path) {
		try {
			appProps = new Properties();
			appProps.load(new FileInputStream(path));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Properties getConfig() {
		return appProps;
	}
	
	public void initialize() {
		appName = appProps.getProperty("target");
		assert appName != null : "missing a system under test";
		projectDir = appProps.getProperty("project.dir", System.getProperty("user.dir"));
		version = appProps.getProperty("version");
		secreteKey = appProps.getProperty("secreteKey");
		reachDepth = Integer.parseInt(appProps.getProperty("trace.depth", String.valueOf(Integer.MAX_VALUE)));
		reachBound = Integer.parseInt(appProps.getProperty("trace.bound", String.valueOf(Integer.MAX_VALUE)));
		// Bounded Model Checking configuration
		bmcDepth = Integer.parseInt(appProps.getProperty("jpf.bmc.depth", String.valueOf(Integer.MAX_VALUE)));
		isCaching = Boolean.valueOf(appProps.getProperty("jpf.caching", "false"));
		isRemote = Boolean.valueOf(appProps.getProperty("env.isRemote", "false"));
		
		// RABBITMQ
		isUsingRabbitMQ = Boolean.valueOf(appProps.getProperty("rabbitmq.isUsing", "true"));
		rabbitmqHost = appProps.getProperty("rabbitmq.host", "localhost");
		rabbitmqUsername = appProps.getProperty("rabbitmq.username");
		rabbitmqPassword = appProps.getProperty("rabbitmq.password");

		// REDIS
		isUsingRedis = Boolean.valueOf(appProps.getProperty("redis.isUsing", "true"));
		redisHost = appProps.getProperty("redis.host", "localhost");
		redisPort = Integer.parseInt(appProps.getProperty("redis.port", "6379"));
		
		// MySQL
		isUsingMySQL = Boolean.valueOf(appProps.getProperty("mysql.isEnable", "false"));
		mysqlHost = appProps.getProperty("mysql.host", "localhost");
		mysqlPort = appProps.getProperty("mysql.port", "3306");
		mysqlUsername = appProps.getProperty("mysql.username", "root");
		mysqlPassword = appProps.getProperty("mysql.password");
		mysqlDbName= appProps.getProperty("mysql.database");
	}
	
	public String getAppName() {
		return appName;
	}

	public String getConfigFileName() {
		return configFileName;
	}

	public String getProjectDir() {
		return projectDir;
	}

	public String getVersion() {
		return version;
	}

	public String getSecreteKey() {
		return secreteKey;
	}

	public int getReachDepth() {
		return reachDepth;
	}

	public int getReachBound() {
		return reachBound;
	}

	public int getBmcDepth() {
		return bmcDepth;
	}
	
	public boolean isRemote() {
		return isRemote;
	}
	
	public boolean isCaching() {
		return isCaching;
	}

	public boolean isUsingRabbitMQ() {
		return isUsingRabbitMQ;
	}

	public String getRabbitmqHost() {
		return rabbitmqHost;
	}

	public String getRabbitmqUsername() {
		return rabbitmqUsername;
	}

	public String getRabbitmqPassword() {
		return rabbitmqPassword;
	}

	public boolean isUsingRedis() {
		return isUsingRedis;
	}

	public String getRedisHost() {
		return redisHost;
	}

	public int getRedisPort() {
		return redisPort;
	}

	public Boolean isUsingMySQL() {
		return isUsingMySQL;
	}

	public String getMysqlHost() {
		return mysqlHost;
	}

	public String getMysqlPort() {
		return mysqlPort;
	}

	public String getMysqlUsername() {
		return mysqlUsername;
	}

	public String getMysqlPassword() {
		return mysqlPassword;
	}

	public String getMysqlDbName() {
		return mysqlDbName;
	}
}
