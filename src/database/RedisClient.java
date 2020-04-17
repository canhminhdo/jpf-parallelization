package database;

import redis.clients.jedis.Jedis;

/**
 * Singleton Redis instance
 * 
 * @author OgataLab
 *
 */
public class RedisClient {
	private static RedisClient _instance = null;
	private Jedis jedis = null;
	
	/**
	 * Get singleton Redis instance
	 * 
	 * @param host
	 * @param port
	 * @return {@link RedisClient}
	 */
	public static RedisClient getInstance(String host, Integer port) {
		if (_instance == null)
			_instance = new RedisClient(host, port);

		return _instance;
	}

	private RedisClient(String host, Integer port) {
		this.jedis = new Jedis(host, port);
	}

	public Jedis getConnection() {
		return this.jedis;
	}
}
