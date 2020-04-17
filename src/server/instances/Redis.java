package server.instances;

/**
 * Redis instance configuration
 * 
 * @author OgataLab
 *
 */
public class Redis {

	String host = null;
	Integer port = null;

	public Redis(String host, Integer port) {
		this.host = host;
		this.port = port;
	}

	public String getHost() {
		return this.host;
	}

	public Integer getPort() {
		return this.port;
	}

}
