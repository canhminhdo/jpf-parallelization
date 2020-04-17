package nspk.main;

/**
 * A refresh random number with unique identifier by String id
 * 
 * @author OgataLab
 *
 */
public class Rand {
	
	private String id;

	public Rand(String id) {
		this.id = id;
	}
	
	public String getId() {
		return id;
	}
	
	@Override
	public String toString() {
		return id;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		
		if (obj.getClass() != this.getClass()) {
			return false;
		}
		
		Rand r = (Rand)obj;
		
		return this.id.equals(r.getId());
	}
}
