package nspk.main;

public class Confirmation implements RewriteRule {
	
	@Override
	public void execute(Principal p) {
		p.confirmation();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		
		return this.getClass() == obj.getClass();
	}
	
	@Override
	public String toString() {
		return this.getClass().getSimpleName();
	}
}
