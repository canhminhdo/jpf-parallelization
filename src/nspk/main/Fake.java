package nspk.main;

public class Fake implements RewriteRule {
	
	@Override
	public void execute(Principal p) {
		p.fake();
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
