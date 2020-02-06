package project;

public class IDAssigner {
	private int baseID;
	
	
	public IDAssigner(int baseID) {
		// TODO Auto-generated constructor stub
		this.baseID =baseID;
		
		
	}
	
	public int next() {
		return baseID++;
	}
	public int getCurrentID() {
		return baseID;
	}

}
